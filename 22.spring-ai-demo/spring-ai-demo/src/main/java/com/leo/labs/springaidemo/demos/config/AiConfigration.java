package com.leo.labs.springaidemo.demos.config;

import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfigration {

    @Value("${labs.siliconflow.api-key}")
    private String siliconflowApiKey;

    @Value("${labs.siliconflow.baseUrl}")
    private String siliconflowBaseUrl;

    @Value("${labs.siliconflow.completionsPath}")
    private String siliconflowCompletionsPath;

    @Value("${labs.siliconflow.embeddingsPath}")
    private String siliconflowEmbeddingsPath;

    @Value("${labs.ark.api-key}")
    private String arkApiKey;

    @Value("${labs.ark.baseUrl}")
    private String arkBaseUrl;

    @Value("${labs.ark.completionsPath}")
    private String arkCompletionsPath;

    @Value("${labs.ark.embeddingsPath}")
    private String arkEmbeddingsPath;

    @Bean
    public InMemoryChatMemory get() {
        return new InMemoryChatMemory();
    }

    @Bean("siliconflowOpenAiApi")
    public OpenAiApi getSiliconflowOpenAiApi() {
        return OpenAiApi.builder()
                .apiKey(this.siliconflowApiKey)
                .baseUrl(this.siliconflowBaseUrl)
                .completionsPath(this.siliconflowCompletionsPath)
                .embeddingsPath(this.siliconflowEmbeddingsPath)
                .build();

    }

    @Bean("siliconflowOpenAiImageApi")
    public OpenAiImageApi getSiliconflowOpenAiImageApi() {
        return OpenAiImageApi.builder()
                .apiKey(this.siliconflowApiKey)
                .baseUrl(this.siliconflowBaseUrl)
//                imagesGenerationsPath固定为：/v1/images/generations，无法进行参数配置，如需改动，需要对OpenAiImageApi源码进行改造
                .build();
    }

    @Bean("arkOpenAiApi")
    public OpenAiApi getArkflowOpenAiApi() {
        return OpenAiApi.builder()
                .apiKey(this.arkApiKey)
                .baseUrl(this.arkBaseUrl)
                .completionsPath(this.arkCompletionsPath)
                .embeddingsPath(this.arkEmbeddingsPath)
                .build();

    }

}
