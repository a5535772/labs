# https://www.langchain.com.cn/docs/how_to/structured_output/
from langchain_openai import ChatOpenAI

from labs.initializer import g

# .with_structured_output() 方法

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)

chunks = []
for chunk in model.stream("what color is the sky?"):
    chunks.append(chunk)
    print(chunk.content, end="|", flush=True)

print("====================================")

print(chunks[0])

# 循环chunks，并相加每一个元素
print("====================================")
print(chunks[0] + chunks[1] + chunks[2] + chunks[3] + chunks[4])
