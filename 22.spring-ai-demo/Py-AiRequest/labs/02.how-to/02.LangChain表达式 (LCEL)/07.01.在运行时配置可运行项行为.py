from langchain_core.prompts import PromptTemplate
from langchain_core.runnables import ConfigurableField
from langchain_openai import ChatOpenAI
from langchain_core.output_parsers import StrOutputParser

from labs.initializer import g

# 可配置字段
model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen,
    temperature=0).configurable_fields(
    temperature=ConfigurableField(
        id="llm_temperature",
        name="LLM Temperature",
        description="The temperature of the LLM",
    )
)

# model.with_config(configurable={"llm_temperature": 0.9}).invoke("pick a random number")

prompt = PromptTemplate.from_template("Pick a random number above {x}")
chain = prompt | model

print(chain.invoke({"x": 0}))

print(chain.with_config(configurable={"llm_temperature": 0.9}).invoke({"x": 0}))

# 使用 HubRunnables