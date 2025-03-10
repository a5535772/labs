package com.leo.labs.springaidemo.mains;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class OpenAiApiSteamTest extends BaseLabTest {

    static String qw = "Qwen/Qwen2-1.5B-Instruct";
    static String r1 = "Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B";
    static String current_mode = r1;
    private static final String DEFAULT_PROMPT = "讲个笑话";
    private static final String DEFAULT_SYSTEM = "你是一个博学的智能聊天助手，请根据用户提问回答！";

    @Test
    void test() throws ExecutionException, InterruptedException, TimeoutException {
        var openAiApi = OpenAiApi.builder()
                .apiKey(this.api_key)
                .baseUrl(this.baseUrl)
                .completionsPath(this.completionsPath)
                .build();

        var openAiChatOptions = OpenAiChatOptions.builder()
                .model(current_mode)
                .temperature(0.4)
                .maxTokens(2000)
                .build();

        var chatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);

        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话 ID，以便 Spring AI 处理上下文。
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory()),
                        // 实现 Logger 的 Advisor
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .topP(0.7)
                                .build()
                )
                .build();
        ChatClient.StreamResponseSpec streamResp = openAiChatClient.prompt(DEFAULT_PROMPT).stream();

        //打印
        CompletableFuture<Void> future = new CompletableFuture<>();
        streamResp.content().subscribe(
                System.out::println,
                throwable -> {
                    // 如果发生异常，完成 Future
                    future.completeExceptionally(throwable);
                },
                () -> {
                    // 操作完成时，完成 Future
                    future.complete(null);
                });

        // 等待异步操作完成，最多等待 5 秒
        future.get(10000l, TimeUnit.SECONDS);

        // 打印测试完成
        System.out.println("Test completed.");
    }

}
