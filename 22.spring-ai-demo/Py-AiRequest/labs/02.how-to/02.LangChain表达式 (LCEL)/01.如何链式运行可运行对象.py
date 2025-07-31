from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnableParallel
from langchain_openai import ChatOpenAI

from labs.initializer import g

# .with_structured_output() 方法

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)

prompt = ChatPromptTemplate.from_template("tell me a joke about {topic}")

chain = prompt | model | StrOutputParser()

analysis_prompt = ChatPromptTemplate.from_template("is this a funny joke? {joke}")

composed_chain = {"joke": chain} | analysis_prompt | model | StrOutputParser()

result = composed_chain.invoke({"topic": "bears"})

print(result)