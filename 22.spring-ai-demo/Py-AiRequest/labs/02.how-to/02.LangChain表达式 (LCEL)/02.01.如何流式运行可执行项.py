from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

from labs.initializer import g

# .with_structured_output() 方法

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)

# chunks = []
# for chunk in model.stream("what color is the sky?"):
#     chunks.append(chunk)
#     print(chunk.content, end="|", flush=True)


# 链
prompt = ChatPromptTemplate.from_template("tell me a joke about {topic}")
parser = StrOutputParser()
chain = prompt | model | parser


for chunk in chain.stream({"topic": "parrot"}):
    print(chunk, end="|", flush=True)