from pydantic.v1 import BaseSettings
from pathlib import Path
import yaml

from labs.initializer.token_counter import TokenCounter

APP_DIR = Path(__file__).resolve().parent.parent

CONFIG_DIR = APP_DIR.parent.joinpath("config")
YAML_NAME = "config.yaml"
YAML_PATH = CONFIG_DIR.joinpath(YAML_NAME)

class Conf(BaseSettings):
    # 这里可以定义你的配置项，例如：py
    # app_name: str
    # database_url: str
    yaml_conf: dict = None

    # +++++++++ 硅基流动中配置 +++++++++
    silicon_flow_api_key: str = None
    silicon_flow_base_url: str = None
    silicon_flow_completions_path: str = None
    silicon_flow_embeddings_path: str = None
    # +++++++++ 模型层 +++++++++
    silicon_flow_models_deepSeek_r1: str = None
    silicon_flow_models_qwen: str = None
    silicon_flow_models_qwen_tools: str = None
    silicon_flow_models_embedding_model: str = None

    # +++++++++ ark流动中配置 +++++++++
    ark_api_key: str = None
    ark_base_url: str = None
    ark_completions_path: str = None
    ark_embeddings_path: str = None
    # +++++++++ 模型层 +++++++++
    ark_flow_models_doubao_1_5_vision_pro: str = None
    ark_flow_models_doubao_1_5_pro_32k: str = None


    langsmith_langchain_api_key: str = None
    langsmith_langchain_tracing_v2: str = None

    tools_tavily_api_key: str = None


    def setup(self, func_name: str = "conf_from_yaml"):
        _ = getattr(self, func_name)
        _()
        # 特殊配置：比如设置默认值、类型转换或其他操作
        # 如：self.foo = _("foo", "foo")
        # <<< 特殊配置
        self.yaml_conf = dict()
        return self

    def conf_from_yaml(self, name: str = None, default=None):
        if not self.yaml_conf:
            self.yaml_conf = self.load_yaml()
            for k, v in self.yaml_conf.items():  # auto
                setattr(self, k, v)
        return self.yaml_conf.get(name, default)

    def load_yaml(self):
        with open(str(YAML_PATH), 'r') as file:
            return yaml.safe_load(file)


def init_conf() -> Conf:
    return Conf().setup()