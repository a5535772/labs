# Import relevant functionality
from langchain_community.tools.tavily_search import TavilySearchResults
from langchain_core.messages import HumanMessage
from langchain_openai import ChatOpenAI
from langgraph.checkpoint.memory import MemorySaver
from langgraph.prebuilt import create_react_agent

from labs.initializer import g

search = TavilySearchResults(
    max_results=2,
    tavily_api_key=g.conf.tools_tavily_api_key,
)
# search_results = search.invoke("what is the weather in SF")
# print(search_results)
# If we want, we can create other tools.
# Once we have all the tools we want, we can put them in a list that we will reference later.
tools = [search]

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen_tools,  # tools
)

model_with_tools = model.bind_tools(tools)


# 添加内存
memory = MemorySaver()

# 创建代理
agent_executor = create_react_agent(model, tools,checkpointer=memory)

config = {"configurable": {"thread_id": "abc123"}}

# response = agent_executor.invoke(
#     {"messages": [HumanMessage(content="whats the weather in sf?")]}
# )
# print(response["messages"])

# 流式消息
# 我们已经看到如何使用 .invoke 调用代理以获取最终响应。如果代理正在执行多个步骤，这可能需要一些时间。为了显示中间进度，我们可以在消息发生时流式返回消息。
# for chunk in agent_executor.stream(
#     {"messages": [HumanMessage(content="whats the weather in sf?")]}
# ):
#     print(chunk)
#     print("----")




# 流式令牌
import asyncio

async def run_agent():
    async for event in agent_executor.astream_events(
            {"messages": [HumanMessage(content="whats the weather in sf?")]}, config  #, version="v1" Why v1? default is v2
    ):
        kind = event["event"]
        if kind == "on_chain_start":
            if (
                    event["name"] == "Agent"
            ):  # Was assigned when creating the agent with `.with_config({"run_name": "Agent"})`
                print(
                    f"Starting agent: {event['name']} with input: {event['data'].get('input')}"
                )
        elif kind == "on_chain_end":
            if (
                    event["name"] == "Agent"
            ):  # Was assigned when creating the agent with `.with_config({"run_name": "Agent"})`
                print()
                print("--")
                print(
                    f"Done agent: {event['name']} with output: {event['data'].get('output')['output']}"
                )
        if kind == "on_chat_model_stream":
            content = event["data"]["chunk"].content
            if content:
                # Empty content in the context of OpenAI means
                # that the model is asking for a tool to be invoked.
                # So we only print non-empty content
                print(content, end="|")
        elif kind == "on_tool_start":
            print("--")
            print(
                f"Starting tool: {event['name']} with inputs: {event['data'].get('input')}"
            )
        elif kind == "on_tool_end":
            print(f"Done tool: {event['name']}")
            print(f"Tool output was: {event['data'].get('output')}")
            print("--")

# 使用 asyncio.run() 运行异步函数
if __name__ == "__main__":
    asyncio.run(run_agent())



for chunk in agent_executor.stream(
    {"messages": [HumanMessage(content="hi im bob!")]}, config
):
    print(chunk)
    print("----")

for chunk in agent_executor.stream(
    {"messages": [HumanMessage(content="whats my name?")]}, config
):
    print(chunk)
    print("----")