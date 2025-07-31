from langchain.runnables.hub import HubRunnable
from langchain_core.runnables import ConfigurableField

# url is https://api.smith.langchain.com/commits/rlm/rag-prompt/latest

prompt = HubRunnable("rlm/rag-prompt").configurable_fields(
    owner_repo_commit=ConfigurableField(
        id="hub_commit",
        name="Hub Commit",
        description="The Hub commit to pull from",
    )
)

val=prompt.invoke({"question": "foo", "context": "bar"})
print(val)