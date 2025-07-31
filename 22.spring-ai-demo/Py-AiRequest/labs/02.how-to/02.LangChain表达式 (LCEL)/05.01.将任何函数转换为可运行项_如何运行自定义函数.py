from operator import itemgetter

from langchain_core.prompts import ChatPromptTemplate
from langchain_core.runnables import RunnableLambda
from langchain_openai import ChatOpenAI

from labs.initializer import g

model = ChatOpenAI(
    base_url=g.conf.silicon_flow_base_url,
    api_key=g.conf.silicon_flow_api_key,
    model=g.conf.silicon_flow_models_qwen
)


def length_function(text):
    return len(text)


def _multiple_length_function(text1, text2):
    return len(text1) * len(text2)


def multiple_length_function(_dict):
    return _multiple_length_function(_dict["text1"], _dict["text2"])


prompt = ChatPromptTemplate.from_template("what is {a} + {b}")

chain1 = prompt | model

chain = (
        {
            "a": itemgetter("foo") | RunnableLambda(length_function),
            "b": {"text1": itemgetter("foo"), "text2": itemgetter("bar")}
                 | RunnableLambda(multiple_length_function),
        }
        | prompt
        | model
)

chain.invoke({"foo": "bar", "bar": "gah"})

from langchain_core.output_parsers import StrOutputParser
from langchain_core.runnables import chain
prompt1 = ChatPromptTemplate.from_template("Tell me a joke about {topic}")
prompt2 = ChatPromptTemplate.from_template("What is the subject of this joke: {joke}")

@chain
def custom_chain(text):
    prompt_val1 = prompt1.invoke({"topic": text})
    output1 = model.invoke(prompt_val1)
    parsed_output1 = StrOutputParser().invoke(output1)
    chain2 = prompt2 | model | StrOutputParser()
    return chain2.invoke({"joke": parsed_output1})

print(custom_chain.invoke("bears"))