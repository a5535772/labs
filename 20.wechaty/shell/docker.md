# Linux

@see https://wechaty.readthedocs.io/zh-cn/latest/introduction/use-padlocal-protocol/

```
export WECHATY_LOG="verbose"
export WECHATY_PUPPET="wechaty-puppet-padlocal"
export WECHATY_PUPPET_PADLOCAL_TOKEN="puppet_padlocal_XXXXXX"

export WECHATY_PUPPET_SERVER_PORT="9001"

可使用代码随机生成UUID：import uuid;print(uuid.uuid4());

export WECHATY_TOKEN="1fe5f846-3cfb-401d-b20c-XXXXX"

docker run -ti \
  --name wechaty_puppet_service_token_gateway \
  --rm \
  -e WECHATY_LOG \
  -e WECHATY_PUPPET \
  -e WECHATY_PUPPET_PADLOCAL_TOKEN \
  -e WECHATY_PUPPET_SERVER_PORT \
  -e WECHATY_TOKEN \
  -p "$WECHATY_PUPPET_SERVER_PORT:$WECHATY_PUPPET_SERVER_PORT" \
  wechaty/wechaty:0.56
```



# Windows



```shell
#pad-local
docker run -ti --name wechaty_puppet_service_token_gateway_1.20 -e WECHATY_LOG="verbose" -e WECHATY_PUPPET="wechaty-puppet-padlocal" -e WECHATY_PUPPET_PADLOCAL_TOKEN="puppet_padlocal_c4624c6cf8dd4083927c7dc28b6b2fc9" -e WECHATY_PUPPET_SERVER_PORT="9001" -e WECHATY_TOKEN="2f50f487-1b06-4ab6-927a-3d146edea0a3zl01" -p "9001:9001" wechaty/wechaty:latest


#pad-local
docker run -ti --name wechaty_puppet_service_token_gateway_0.65 -e WECHATY_LOG="verbose" -e WECHATY_PUPPET="wechaty-puppet-padlocal" -e WECHATY_PUPPET_PADLOCAL_TOKEN="puppet_padlocal_c4624c6cf8dd4083927c7dc28b6b2fc9" -e WECHATY_PUPPET_SERVER_PORT="9001" -e WECHATY_TOKEN="2f50f487-1b06-4ab6-927a-3d146edea0a3zl01" -p "9001:9001" wechaty/wechaty:0.65


#web
docker run -ti --name wechaty_puppet_service_token_gateway_web_0.65 -v "C:/temp/wechaty/065web.memory-card.json":"/wechaty/2f50f487-1b06-4ab6-927a-3d146edea0a3zl01.memory-card.json" -e WECHATY_LOG="verbose" -e WECHATY_PUPPET="wechaty-puppet-wechat" -e WECHATY_PUPPET_SERVER_PORT="9001" -e WECHATY_TOKEN="2f50f487-1b06-4ab6-927a-3d146edea0a3zl01" -p "9001:9001" wechaty/wechaty:0.65

docker run -ti --name wechaty_puppet_service_token_gateway_web_0.65 -v "C:/temp/wechaty/2f50f487-1b06-4ab6-927a-3d146edea0a3zl01.memory-card.json":"/wechaty/2f50f487-1b06-4ab6-927a-3d146edea0a3zl01.memory-card.json" -e WECHATY_LOG="verbose" -e WECHATY_PUPPET="wechaty-puppet-wechat" -e WECHATY_PUPPET_SERVER_PORT="9001" -e WECHATY_TOKEN="2f50f487-1b06-4ab6-927a-3d146edea0a3zl01" -p "9001:9001" wechaty/wechaty:0.65
```





python 3.7

pip install wechaty

pip uninstall urllib3

pip install urllib3==1.26.20



pip install -r D:\work\github\labs\20.wechaty\requirements-dev.txt   ???
