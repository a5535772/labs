from typing import List, Optional

# pip install tiktoken
import tiktoken
from langchain_core.messages import BaseMessage, ToolMessage, HumanMessage, AIMessage, SystemMessage


class TokenCounter:
    """A class to count tokens for messages using tiktoken."""

    # Hardcoded values extracted as class attributes with explanations
    TOKENS_PER_REPLY = 3  # Every reply is primed with <|start|>assistant<|message|>
    TOKENS_PER_MESSAGE = 3
    TOKENS_PER_NAME = 1

    def __init__(self):
        """Initialize the TokenCounter with a cached tiktoken encoder."""
        self.encoder = tiktoken.get_encoding("o200k_base")

    def tiktoken_counter(self, messages: List[BaseMessage]) -> int:
        """
        Count tokens for a list of messages.

        Args:
            messages (List[BaseMessage]): A list of message objects.

        Returns:
            int: Total token count.

        Raises:
            ValueError: If an unsupported message type is encountered.
        """
        num_tokens = self.TOKENS_PER_REPLY

        for msg in messages:
            if isinstance(msg, HumanMessage):
                role = "user"
            elif isinstance(msg, AIMessage):
                role = "assistant"
            elif isinstance(msg, ToolMessage):
                role = "tool"
            elif isinstance(msg, SystemMessage):
                role = "system"
            else:
                raise ValueError(f"Unsupported message type {msg.__class__}")

            # Ensure content and name are strings or None
            content = msg.content if isinstance(msg.content, str) else ""
            name = msg.name if isinstance(msg.name, str) else ""

            num_tokens += (
                    self.TOKENS_PER_MESSAGE
                    + self.str_token_counter(role)
                    + self.str_token_counter(content)
            )
            if name:
                num_tokens += self.TOKENS_PER_NAME + self.str_token_counter(name)

        return num_tokens

    def str_token_counter(self, text: str) -> int:
        """
        Count tokens for a given string using the cached encoder.

        Args:
            text (str): Input string to count tokens for.

        Returns:
            int: Token count for the input string.
        """
        return len(self.encoder.encode(text))


def init_token_counter() -> TokenCounter:
    """
    Initialize and return a TokenCounter instance.

    Returns:
        TokenCounter: An initialized TokenCounter instance.
    """
    return TokenCounter()
