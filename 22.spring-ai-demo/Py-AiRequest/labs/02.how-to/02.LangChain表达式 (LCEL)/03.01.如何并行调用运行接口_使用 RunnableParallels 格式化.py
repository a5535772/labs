from langchain_community.vectorstores import FAISS
from langchain_core.embeddings import Embeddings
from langchain_core.output_parsers import StrOutputParser
from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnablePassthrough
from langchain_openai import ChatOpenAI

from labs.helps.tools import SimpleEmbedding
from labs.initializer import g



model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)
vectorstore = FAISS.from_texts(
    ["harrison worked at kensho"],
    embedding=SimpleEmbedding(dim=256)  # 使用自定义简易嵌入
)

retriever = vectorstore.as_retriever()

print(retriever.invoke("where did harrison work?"))

template = """Answer the question based only on the following context:
{context}

Question: {question}
"""

prompt = ChatPromptTemplate.from_template(template)

retrieval_chain = (
        {"context": retriever, "question": RunnablePassthrough()}
        | prompt
        | model
        | StrOutputParser()
)

for chunk in retrieval_chain.stream("where did harrison work?"):
    print(chunk, end="|", flush=True)
