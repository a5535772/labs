# https://www.langchain.com.cn/docs/how_to/structured_output/

from typing import Annotated

from langchain_core.messages import HumanMessage
from langchain_core.tools import tool
from langchain_openai import ChatOpenAI

from labs.initializer import g

# .with_structured_output() 方法

llm = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen_tools
)


@tool
def add(
        a: Annotated[int, "First integer"],
        b: Annotated[int, "Second integer"],
) -> int:
    """Add two integers."""
    return a + b


# Let's inspect some of the attributes associated with the tool.
print(add.name)
print(add.description)
print(add.args)
print("=======================================")


@tool
def multiply(
        a: Annotated[int, "First integer"],
        b: Annotated[int, "Second integer"],
) -> int:
    """Multiply two integers."""
    return a * b


# Let's inspect some of the attributes associated with the tool.
print(multiply.name)
print(multiply.description)
print(multiply.args)
print("=======================================")

tools = [add, multiply]
llm_with_tools = llm.bind_tools(tools)

messages = [HumanMessage("What is 3 * 12? Also, what is 11 + 49?")]
ai_msg = llm_with_tools.invoke(messages)
print(ai_msg.tool_calls)
messages.append(ai_msg)

for tool_call in ai_msg.tool_calls:
    selected_tool = {"add": add, "multiply": multiply}[tool_call["name"].lower()]
    tool_msg = selected_tool.invoke(tool_call)
    messages.append(tool_msg)
print(messages)

result = llm_with_tools.invoke(messages)

print(result)
