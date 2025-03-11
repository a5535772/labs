from fastapi import FastAPI, Request, Depends
from fastapi.responses import StreamingResponse
from pydantic import BaseModel
from chat_service import ChatService

# Create a FastAPI instance for the chat controller
app = FastAPI()

@app.post("/chat")
async def chat(content: str, stream: bool = True, chat_service: ChatService = Depends(ChatService)):
    response = await chat_service.process_chat(content, stream)
    if response.status_code == 200:
        async def event_generator():
            for chunk in response.iter_content(chunk_size=8192):
                if chunk:
                    decoded_chunk = chunk.decode('utf-8')
                    yield decoded_chunk
        return StreamingResponse(event_generator(), media_type="text/event-stream")
    else:
        return {"status": "error", "message": f"Request failed with status code: {response.status_code}"}
