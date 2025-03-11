package com.leo.labs.springaidemo.mains;

import org.junit.jupiter.api.Test;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;

import java.util.Map;

public class DeekSeekTest extends BaseLabTest {

    static String r1 = "Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B";
    static String current_mode = r1;

    private static final String DEFAULT_PROMPT = "讲个笑话";

    @Test
    void test() {
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

        var result = Map.of("generation", chatModel.call(DEFAULT_PROMPT));
        System.out.println(result);
    }
}
