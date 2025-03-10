## Spring AI Alibaba 开发 AI 智能体调用 DeepSeek 本地模型
接下来，我们就详细演示如何在本地部署 DeepSeek 模型，并通过 Spring AI Alibaba 开发应用，调用大模型能力。

1. 下载 Ollama 并安装运行 DeepSeek 本地模型
2. 使用 Spring AI Alibaba 开发应用，调用 DeepSeek 模型
3. 无需联网、私有数据完全本地存储，为 Java 应用赋予 AI 智能

### 本地部署 DeepSeek Qwen 蒸馏版模型

#### MacOS & Windows 安装
进入 [Ollama 官方网站](https://ollama.com/) 后，可以看到 Ollama 已经支持 DeepSeek-R1 模型部署：

![ollama-homepage](https://java2ai.com/img/blog/deepseek/ollama-homepage.png)

点击 DeepSeek-R1 的链接可以看到有关 deepseek-r1 的详细介绍。

点击 `Download` 按钮下载并安装 Ollama，安装完成后，按照提示使用 `Command + Space` 打开终端，运行如下命令：

```shell
# 运行安装 DeepSeek-R1-Distill-Qwen-1.5B 蒸馏模型
ollama run deepseek-r1:1.5b
```

#### Linux 安装

```bash
# 安装Ollama
curl -fsSL https://ollama.com/install.sh | sh

# 运行安装 DeepSeek-R1-Distill-Qwen-1.5B 蒸馏模型
ollama run deepseek-r1:1.5b
```

> 目前 deepseek-r1 模型大小提供了多个选择，包括 1.5b、7b、8b、14b、32b、70b、671b。
> 请根据你机器的显卡配置进行选择，这里只选择最小的 1.5b 模型来做演示。通常来说，8G 显存可以部署 8B 级别模型；24G 显存可以刚好适配到 32B 的模型。
> ![ollama-deepseek-r1](/img/blog/deepseek/ollama-deepseek-r1-distill.png)

### Spring AI Alibaba 创建应用，调用本地模型

使用 Spring AI Alibaba 开发应用与使用普通 Spring Boot 没有什么区别，只需要增加 `spring-ai-alibaba-starter` 依赖，将 `ChatClient` Bean 注入就可以实现与模型聊天了。

在项目中加入`spring-ai-alibaba-starter` 依赖，由于咱们的模型是通过 ollama 运行的，这里我们也加入 `spring-ai-ollama-spring-boot-starter` 依赖。

```xml
<dependency>
  <groupId>org.springframework.ai</groupId>
  <artifactId>spring-ai-ollama-spring-boot-starter</artifactId>
  <version>1.0.0-M5</version>
</dependency>
```


> 注意：由于 spring-ai 相关依赖包还没有发布到中央仓库，如出现 spring-ai-core 等相关依赖解析问题，请在您项目的 pom.xml 依赖中加入如下仓库配置。
>
>
>```xml
><repositories>
>	<repository>
>		<id>spring-milestones</id>
>		<name>Spring Milestones</name>
>		<url>https://repo.spring.io/milestone</url>
>		<snapshots>
>			<enabled>false</enabled>
>		</snapshots>
>	</repository>
><repositories>
>```

配置模型地址，在 application.properties 中配置模型的 baseUrl 与 model 名称。

```plain
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=deepseek-r1
```


注入 `ChatClient`。

```java
@RestController
public class ChatController {

	private final ChatClient chatClient;

	public ChatController(ChatClient.Builder builder) {
		this.chatClient = builder.build();
	}

	@GetMapping("/chat")
	public String chat(String input) {
		return this.chatClient.prompt()
				.user(input)
				.call()
				.content();
	}
}
```