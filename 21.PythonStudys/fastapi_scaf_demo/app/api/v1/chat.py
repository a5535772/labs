import traceback

from fastapi import APIRouter, Depends
from fastapi.responses import StreamingResponse
from app.business.chat import (
    GetChatBiz,
)
from app.initializer import g

chat_router = APIRouter()


@chat_router.post("/chat", summary="chat详情")
async def chat(user_input: str):
    try:
        chat_service = GetChatBiz()
        response = await chat_service.process_chat(user_input, True)

        if response.status_code != 200:
            return {"status": "error", "message": f"Request failed with status code: {response.status_code}"}

        async def event_generator():
            for chunk in response.iter_content(chunk_size=8192):
                if not chunk:
                    continue

                decoded_chunk = chunk.decode('utf-8')
                if 'data: [DONE]' in decoded_chunk:
                    print(decoded_chunk)
                    yield decoded_chunk
                    continue

                delta_content = extract_delta_content(decoded_chunk)
                if delta_content:
                    print(delta_content)
                    yield f"data: {delta_content}\n\n"
                else:
                    print(decoded_chunk)
                    yield decoded_chunk

        return StreamingResponse(event_generator(), media_type="text/event-stream")

    except Exception as e:
        g.logger.error(traceback.format_exc())
        return {"status": "error", "message": f"An error occurred: {str(e)}"}


def extract_delta_content(decoded_chunk):
    start_index = decoded_chunk.find('"delta":')
    if start_index == -1:
        return None

    end_index = decoded_chunk.find('}', start_index)
    if end_index == -1:
        return None

    return decoded_chunk[start_index:end_index + 1]

@chat_router.get("/chat/{user_input}", summary="返回用户输入的详情")
async def get_chat(user_input: str):
    return {"user_input": user_input}

@chat_router.post("/chat/details", summary="返回用户输入的详情")
async def get_chat_details(user_input: str):
    return {"user_input": user_input}