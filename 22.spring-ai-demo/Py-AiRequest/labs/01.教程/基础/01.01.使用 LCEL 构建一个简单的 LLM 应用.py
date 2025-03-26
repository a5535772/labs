from langchain_core.messages import HumanMessage, SystemMessage
from langchain_openai import ChatOpenAI

from labs.initializer import g

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)

messages = [
    SystemMessage(content="你是一个博学的智能聊天助手，请根据用户提问回答！"),
    HumanMessage(content="讲个笑话。"),
]

# result = model.invoke(messages)
# print(result)

print("===================")
# <!--IMPORTS:[{"imported": "StrOutputParser", "source": "langchain_core.output_parsers", "docs": "https://python.langchain.com/api_reference/core/output_parsers/langchain_core.output_parsers.string.StrOutputParser.html", "title": "Build a Simple LLM Application with LCEL"}]-->

from langchain_core.output_parsers import StrOutputParser

# 使用它的一种方法是单独使用它。例如，我们可以保存语言模型调用的结果，然后将其传递给解析器。
parser = StrOutputParser()
# print(parser.invoke(result))

# <!--IMPORTS:[{"imported": "ChatPromptTemplate", "source": "langchain_core.prompts", "docs": "https://python.langchain.com/api_reference/core/prompts/langchain_core.prompts.chat.ChatPromptTemplate.html", "title": "Build a Simple LLM Application with LCEL"}]-->
from langchain_core.prompts import ChatPromptTemplate

system_template = "Translate the following into {language}:"
prompt_template = ChatPromptTemplate.from_messages(
    [("system", system_template), ("user", "{text}")]
)

# result = prompt_template.invoke({"language": "italian", "text": "hi"})

chain = prompt_template | model | parser

result = chain.invoke({"language": "italian", "text": "hi"})
print(result)
