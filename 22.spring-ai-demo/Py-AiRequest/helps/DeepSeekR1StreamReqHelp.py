import requests

url = "https://api.siliconflow.cn/v1/chat/completions"

payload = {
    "model": "Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B",
    "messages": [
        {
            "role": "system",
            "content": "你是一个博学的智能聊天助手，请根据用户提问回答！"
        },
        {
            "role": "user",
            "content": "讲个笑话"
        }
    ],
    "stream": True,
    "max_tokens": 2000,
    "stop": None,
    "temperature": 0.7,
    "top_p": 0.7,
    "top_k": 50,
    "frequency_penalty": 0.5,
    "n": 1,
    "response_format": {"type": "text"}
}
headers = {
    "Authorization": "Bearer sk-",
    "Content-Type": "application/json"
}


# 使用stream=True，可以获取流式数据
response = requests.request("POST", url, json=payload, headers=headers, stream=True)
for chunk in response.iter_lines():
    if chunk:
        print(chunk.decode('utf-8'))
