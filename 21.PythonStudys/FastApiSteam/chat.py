from fastapi import FastAPI, Request
from fastapi.responses import StreamingResponse
import requests

app = FastAPI()

url = "https://api.siliconflow.cn/v1/chat/completions"

@app.post("/chat")
async def chat(request: Request):
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

    response = requests.post(url, json=payload, headers=headers, stream=True)

    if response.status_code == 200:
        async def event_generator():
            for chunk in response.iter_content(chunk_size=8192):
                if chunk:
                    decoded_chunk = chunk.decode('utf-8')
                    yield decoded_chunk
        return StreamingResponse(event_generator(), media_type="text/event-stream")
    else:
        return {"status": "error", "message": f"Request failed with status code: {response.status_code}"}
