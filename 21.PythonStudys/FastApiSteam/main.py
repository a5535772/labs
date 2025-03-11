from fastapi import FastAPI
from fastapi.middleware.trustedhost import TrustedHostMiddleware
from starlette.middleware.cors import CORSMiddleware
from chat_controller import app as chat_app
from hello import test_controller as hello_app

app = FastAPI()

# Mount the first app at the root path
app.mount("/v1", chat_app)

# Mount the second app at a different path
app.mount("/test", hello_app)

# Add middleware if needed
app.add_middleware(
    TrustedHostMiddleware, allowed_hosts=["*"]
)

app.add_middleware(
    CORSMiddleware,
    allow_origins=["*"],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# Run the application using uvicorn
if __name__ == "__main__":
    import uvicorn
    uvicorn.run(app, host="0.0.0.0", port=8080)
