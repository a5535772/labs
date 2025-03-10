package com.leo.labs.springaidemo.demos.config;

import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AiConfigration {

    @Bean
    public InMemoryChatMemory get() {
        return new InMemoryChatMemory();
    }
}
