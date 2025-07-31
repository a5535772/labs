

# 本地环境

## nacos

```
docker run --name nacos-standalone-derby `
    -e MODE=standalone `
    -e NACOS_AUTH_TOKEN=VGhpc0lzQVNlY3JldEtleUZvck5hY29zQURNSU4xMjM0NTY3ODk= `
    -e NACOS_AUTH_IDENTITY_KEY=nacos `
    -e NACOS_AUTH_IDENTITY_VALUE=nacos `
    -p 8080:8080 `
    -p 8848:8848 `
    -p 9848:9848 `
    -d nacos/nacos-server:latest
```

这个密钥 VGhpc0lzQVNlY3JldEtleUZvck5hY29zQURNSU4xMjM0NTY3ODk= 解码后是 ThisIsASecretKeyForNacosADMIN123456789，长度为44字节，满足至少32字节的要求



# 配置的坑







# 阿里会议灵感

serverless

nacos 3.0

higress

ragflow	,	graphrag-base on adb

dify on sae



![image-20250729110031728](./imgs/image-20250729110031728.png)





![image-20250729110233770](./imgs/image-20250729110233770.png)



![image-20250729110809089](./imgs/image-20250729110809089.png)



![image-20250729111117608](./imgs/image-20250729111117608.png)





# 兼容性问题排查



```
<!-- 截止2025.07.31，spring-cloud-starter-alibaba-nacos-config中央仓库最新版本是2023.0.3.3,该版本
依赖配置为spring cloud 3.2.x ，该版本依赖nacos-client版本为2.2.x，
然后矛盾的是 spring-ai项目必须依赖springboot3.4.x，nacos-mcp需要使用nacos-client 3.x，应该是官方现在还没来得及升级spring cloud alibaba版本
介于项目主要要使用spring-ai，spring-ai-mcp能力，所以这里需要指定版本如下
-->
<spring-boot.version>3.4.7</spring-boot.version>
<spring-cloud.version>2024.0.2</spring-cloud.version>
<nacos-client.version>3.0.2</nacos-client.version>
```



APPLICATION FAILED TO START

***************************

Description:

An attempt was made to call a method that does not exist. The attempt was made from the following location:

    org.springframework.ai.mcp.server.autoconfigure.McpServerAutoConfiguration.lambda$mcpSyncServer$4(McpServerAutoConfiguration.java:197)

The following method did not exist:

    'org.springframework.ai.tool.ToolCallback[] org.springframework.ai.tool.ToolCallbackProvider.getToolCallbacks()'

The calling method's class, org.springframework.ai.mcp.server.autoconfigure.McpServerAutoConfiguration, was loaded from the following location:

    jar:file:/D:/install/maven/dependy/repositoryali/org/springframework/ai/spring-ai-autoconfigure-mcp-server/1.0.0/spring-ai-autoconfigure-mcp-server-1.0.0.jar!/org/springframework/ai/mcp/server/autoconfigure/McpServerAutoConfiguration.class

The called method's class, org.springframework.ai.tool.ToolCallbackProvider, is available from the following locations:

    jar:file:/D:/install/maven/dependy/repositoryali/org/springframework/ai/spring-ai-core/1.0.0-M6/spring-ai-core-1.0.0-M6.jar!/org/springframework/ai/tool/ToolCallbackProvider.class
    jar:file:/D:/install/maven/dependy/repositoryali/org/springframework/ai/spring-ai-model/1.0.0/spring-ai-model-1.0.0.jar!/org/springframework/ai/tool/ToolCallbackProvider.class

The called method's class hierarchy was loaded from the following locations:

    org.springframework.ai.tool.ToolCallbackProvider: file:/D:/install/maven/dependy/repositoryali/org/springframework/ai/spring-ai-core/1.0.0-M6/spring-ai-core-1.0.0-M6.jar





```
org.springframework.ai.mcp.server.autoconfigure.McpServerAutoConfiguration
项调用    org.springframework.ai.tool.ToolCallbackProvider.getToolCallbacks

```
