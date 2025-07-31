from langchain_core.embeddings import Embeddings


class SimpleEmbedding(Embeddings):
    def __init__(self, dim=256):
        self.dim = dim  # 固定向量维度

    def embed_documents(self, texts):
        return [self._text_to_vector(text) for text in texts]

    def embed_query(self, text):
        return self._text_to_vector(text)

    def _text_to_vector(self, text):
        # 将字符ASCII码转换为二进制特征
        vector = [0] * self.dim
        for i, c in enumerate(text[:self.dim]):  # 截断超长部分
            vector[i] = ord(c) % 2  # 奇偶二进制特征
        return vector