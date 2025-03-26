#初始化一个G，读取conf
import os

from labs.initializer.conf import init_conf
from labs.initializer.token_counter import init_token_counter


class G():
    """
    全局变量
    """
    conf = None
    token_counter = None
    def setup(self):
        """
        初始化
        """
        self.conf = init_conf()
        self.token_counter = init_token_counter()


g = G()
g.setup()
# 建议：
# 为了避免G下的全局变量在未初始化时使用，
# 请使用以下方式调用：g.conf.xxx
# 而不是在模块预先定义全局变量再调用
# <<< 建议

os.environ["LANGCHAIN_TRACING_V2"] =g.conf.langsmith_langchain_tracing_v2
os.environ["LANGCHAIN_API_KEY"] = g.conf.langsmith_langchain_api_key