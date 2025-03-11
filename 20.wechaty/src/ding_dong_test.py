import asyncio
import os

from wechaty import Wechaty, Message

os.environ['WECHATY_PUPPET_SERVICE_TOKEN'] = "puppet_padlocal_c4624c6cf8dd4083927c7dc28b6b2fc9"
os.environ['WECHATY_PUPPET_SERVICE_ENDPOINT'] = "127.0.0.1:9001"

bot = Wechaty();
class MyBot(Wechaty):
    async def on_message(self, msg: Message) -> None:
        text = msg.text()
        if text == "ding":
            await msg.say("dong")

asyncio.run(MyBot().start())