import requests
from pydantic import BaseModel
from app.initializer import g
class ChatRequest(BaseModel):
    model: str
    messages: list
    stream: bool
    max_tokens: int
    # 不可以用None：Pydantic是一个用于数据验证和设置管理的库，它期望输入的值是特定类型的。在这个例子中，它期望的是字符串类型。
    # stop: str = None
    temperature: float
    top_p: float
    top_k: int
    frequency_penalty: float
    n: int
    response_format: dict

class GetChatBiz:
    def __init__(self):
        self.url = "https://api.siliconflow.cn/v1/chat/completions"
        self.headers = {
             "Authorization": g.conf.api_key,
            "Content-Type": "application/json"
        }

    def build_payload(self, content: str, stream: bool):
        return ChatRequest(
            model="deepseek-ai/DeepSeek-R1",
            messages=[
                {
                    "role": "user",
                    "content": content
                }
            ],
            stream=stream,
            max_tokens=512,
            stop=None,
            temperature=0.7,
            top_p=0.7,
            top_k=50,
            frequency_penalty=0.5,
            n=1,
            response_format={"type": "text"}
        ).dict()

    async def process_chat(self, content: str, stream: bool):
        payload = self.build_payload(content, stream)
        response = requests.post(self.url, json=payload, headers=self.headers, stream=True)
        return response
