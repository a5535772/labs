from fastapi import FastAPI

test_controller = FastAPI()

@test_controller.get("/hello")
def read_root():
    return {"message": "Hello from main2"}
