from fastapi.middleware.cors import CORSMiddleware


class Cors:
    middleware_class = CORSMiddleware
    allow_origins = [
        "http://localhost:8000",
        # 可添加其他所需
    ]
    allow_credentials = True
    allow_methods = ["*"]
    allow_headers = ["*"]
