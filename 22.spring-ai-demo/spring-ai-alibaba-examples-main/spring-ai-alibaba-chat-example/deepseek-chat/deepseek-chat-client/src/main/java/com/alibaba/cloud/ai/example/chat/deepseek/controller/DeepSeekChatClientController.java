/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.example.chat.deepseek.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

/**
 * @author 北极星
 */
@RestController
public class DeepSeekChatClientController {

    private static final String DEFAULT_PROMPT = "你好，介绍下你自己！";

    private final ChatModel chatModel;

    private final ChatClient DeepSeekChatClient;

    public DeepSeekChatClientController (OpenAiChatModel chatModel) {
        this.chatModel = chatModel;

        this.DeepSeekChatClient = ChatClient.builder(chatModel).defaultAdvisors(new MessageChatMemoryAdvisor(new InMemoryChatMemory()))
                // 实现 Logger 的 Advisor
                .defaultAdvisors(new SimpleLoggerAdvisor())
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(OpenAiChatOptions.builder().temperature(0.7d).build()).build();
    }

    /**
     * 使用自定义参数调用DeepSeek模型
     *
     * @return ChatResponse 包含模型响应结果的封装对象
     * @apiNote 当前硬编码指定模型为deepseek-chat，温度参数0.7以平衡生成结果的创造性和稳定性
     */
    @GetMapping(value = "/ai/customOptions")
    public ChatResponse testDeepSeekCustomOptions () {
        return this.DeepSeekChatClient.prompt("Generate the names of 5 famous pirates.").call().chatResponse();
    }

    /**
     * 执行默认提示语的AI生成请求
     *
     * @return Map 包含生成结果的键值对，格式为{ "generation": 响应内容 }
     */
    @GetMapping("/ai/generate")
    public Map<String, Object> testEasyChat () {
        return Map.of("generation", this.DeepSeekChatClient.prompt(DEFAULT_PROMPT).call());
    }

    /**
     * 流式生成接口 - 支持实时获取生成过程的分块响应
     *
     * @return Flux<ChatResponse> 响应式流对象，包含分块的模型响应数据
     * @see Flux 基于Project Reactor的响应式流对象
     */
    @GetMapping("/ai/stream")
    public Flux<ChatResponse> testDeepSeekGenerateWithStream () {
        return this.DeepSeekChatClient.prompt(DEFAULT_PROMPT).stream().chatResponse();
    }
}
