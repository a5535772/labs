package com.leo.labs.springaidemo.mains;

import org.junit.jupiter.api.Test;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

public class OpenAiApiTest extends BaseLabTest {

    static String qw = "Qwen/Qwen2-1.5B-Instruct";

    static String r1 = "Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B";

    static String current_mode = r1;

    private static final String DEFAULT_PROMPT = "讲个笑话";

    private static final String DEFAULT_SYSTEM = "你是一个博学的智能聊天助手，请根据用户提问回答！";


    @Test
    void test() {
        var openAiApi = OpenAiApi.builder()
                .apiKey(api_key)
                .baseUrl(baseUrl)
                .completionsPath(completionsPath)
                .build();

        var openAiChatOptions = OpenAiChatOptions.builder()
                .model(current_mode)
                .temperature(0.4)
                .maxTokens(2000)
                .build();

        var chatModel = new OpenAiChatModel(openAiApi, openAiChatOptions);


//call by chatmodel
//        ChatResponse response = chatModel.call(
//                new Prompt("Generate the names of 5 famous pirates."));


        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                // 实现 Chat Memory 的 Advisor
                // 在使用 Chat Memory 时，需要指定对话 ID，以便 Spring AI 处理上下文。
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(new InMemoryChatMemory())
                )
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .topP(0.7)
                                .build()
                )
                .build();

        var responseSpec = openAiChatClient.prompt(DEFAULT_PROMPT).call();
        ChatResponse response = responseSpec.chatResponse();
//        var generation=(org.springframework.ai.chat.model.Generation)response.getResults().get(0);
//        AssistantMessage assistantMessage=generation.getOutput();
//        System.out.println(responseSpec.reasoner());

        System.out.println(responseSpec.content());

    }

//    /**
//     * ChatClient 简单调用
//     */
//    @GetMapping("/simple/chat")
//    public String simpleChat() {
//
//        return openAiChatClient.prompt(DEFAULT_PROMPT).call().content();
//    }
//
//    /**
//     * ChatClient 流式调用
//     */
//    @GetMapping("/stream/chat")
//    public Flux<String> streamChat(HttpServletResponse response) {
//
//        response.setCharacterEncoding("UTF-8");
//        return openAiChatClient.prompt(DEFAULT_PROMPT).stream().content();
//    }
}
