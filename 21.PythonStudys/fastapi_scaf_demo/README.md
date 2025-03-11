# fastapi-scaf ( => yourProj)

## What is this?
- by: axiner
- fastapi-scaf
- This is a fastapi scaf.
  - new project
  - add api
  - about project:
    - auto init project (conf, db, logger...)
    - auto register router
    - auto register middleware

## Installation
This package can be installed using pip (>=Python3.9):
> pip install fastapi-scaf

## Scaf Usage
- 1）new project
  - `fastapi-scaf new <project_name>`
- 2）add api
  - `cd to project root dir`
  - `fastapi-scaf add <api_name>`

## Project Run
- 1）cd to project root dir
- 2）execute command:
  - `pip install -r requirements.txt`
  - `uvicorn app.main:app --host=0.0.0.0 --port=8000 --log-level=debug --log-config=./config/uvicorn_logging.json --workers=5`
  - about uvicorn: [www.uvicorn.org](https://www.uvicorn.org/)

## Project Structure
- ABD: ABD模式
  - A   api
  - B   business
  - D   datatype
- 调用过程: main.py(initializer) - router(middleware) - api - business - (datatype)
- 结构如下: (命名经过多次修改敲定，简洁易懂，ABD目录贴合避免杂乱无章)
  ```
  └── fastapi-scaf
      ├── app                         (应用)
      │   ├── api                     ├── (api)
      │   │   └── v1                  │   └── (v1)
      │   ├── business                ├── (业务)
      │   ├── datatype                ├── (数据类型)
      │   ├── initializer             ├── (初始化)
      │   │   ├── conf                │   ├── (配置)
      │   │   ├── db                  │   ├── (数据库)
      │   │   ├── logger              │   ├── (日志)
      │   │   └── ...                 │   └── (...)
      │   ├── middleware              ├── (中间件)
      │   ├── router                  ├── (路由)
      │   ├── utils                   ├── (utils)
      │   └── main.py                 └── (main.py)
      ├── config                      (配置目录)
      ├── deploy                      (部署目录)
      ├── docs                        (文档目录)
      ├── log                         (日志目录)
      ├── .gitignore
      ├── LICENSE
      ├── README.md
      └── requirements.txt
  ```

## LICENSE
This project is released under the MIT License (MIT). See [LICENSE](LICENSE)



# 如何运行

#### a. 安装依赖

bash复制

```bash
pip install -r requirements.txt
```

#### b. 运行开发环境

从项目的根目录运行以下命令：

bash复制

```bash
python main_dev.py
```

#### c. 运行生产环境

从项目的根目录运行以下命令：

bash复制

```bash
uvicorn app.main:app --host=0.0.0.0 --port=8000 --log-level=info --log-config=./config/uvicorn_logging.json --workers=5
```

- `uvicorn app.main:app --workers=5` 表示启动 **5 个工作进程**。
- 如果不设置 `workers`，默认情况下只启动 **1 个工作进程**。

#### d. 推荐设置

- 如果你的服务器有多核 CPU，建议设置 `workers` 的值为 `(2 * num_cpu_cores) + 1`，这是一种常见的估算方法。
- 例如，你的服务器有 4 个 CPU 核心，则 `workers` 可设置为：`(2 * 4) + 1 = 9`。

扩展：如果使用 Gunicorn 或类似的多进程管理器，也支持类似设置 `workers` 的功能。

#### e. 运行生产环境参数化

在生产环境中，通常会推荐使用等效于 Gunicorn 的多进程服务器管理器，例如：

bash复制

```bash
gunicorn app.main:app -w 5 -k uvicorn.workers.UvicornWorker
```

- `-w 5` 表示启动 5 个工作进程。
- `-k uvicorn.workers.UvicornWorker` 指定使用的 Worker 类型为 Uvicorn。

**示例**

假设你的服务器有 4 个 CPU 核心，以下是一些推荐的 `workers` 设置：

- 批量计算密集型任务：`workers=4`。
- 网络请求密集型任务：`workers=9`。
