
from langchain_core.output_parsers import StrOutputParser, JsonOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_openai import ChatOpenAI

from labs.initializer import g

# .with_structured_output() 方法

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)


chunks = []
async def ask_model():
    async for chunk in model.astream("what color is the sky?"):
        chunks.append(chunk)
        print(chunk.content, end="|", flush=True)

import asyncio

asyncio.run(ask_model())