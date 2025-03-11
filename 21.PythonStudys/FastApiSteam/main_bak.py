import requests

url = "https://api.siliconflow.cn/v1/chat/completions"

payload = {
    "model": "deepseek-ai/DeepSeek-R1",
    "messages": [
        {
            "role": "user",
            "content": "中国大模型行业2025年将会迎来哪些机遇和挑战？"
        }
    ],
    "stream": True,
    "max_tokens": 512,
    "stop": None,
    "temperature": 0.7,
    "top_p": 0.7,
    "top_k": 50,
    "frequency_penalty": 0.5,
    "n": 1,
    "response_format": {"type": "text"}
}
headers = {
    "Authorization": "Bearer sk-nelxcpuwepgvvxbwbyvbbmfyeairkamgannlpwsxftuufyvh",
    "Content-Type": "application/json"
}

response = requests.post(url, json=payload, headers=headers, stream=True) # 此处request需要指定stream模式

# print(response.text)

# 打印流式返回信息
if response.status_code == 200:
    for chunk in response.iter_content(chunk_size=8192):
        if chunk:
            decoded_chunk = chunk.decode('utf-8')
            print(decoded_chunk, end='')
else:
    print('Request failed with status code:', response.status_code)