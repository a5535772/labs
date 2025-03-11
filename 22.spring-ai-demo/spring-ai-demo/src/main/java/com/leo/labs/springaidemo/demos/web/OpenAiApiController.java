/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.leo.labs.springaidemo.demos.web;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @author leo
 */
@RequestMapping("/gpt")
@Controller
public class OpenAiApiController implements InitializingBean {

    @Value("${labs.openai.api-key}")
    private String api_key;

    @Value("${labs.openai.baseUrl}")
    private String baseUrl;

    @Value("${labs.openai.completionsPath}")
    private String completionsPath;

    private OpenAiApi openAiApi;

    @Autowired
    private InMemoryChatMemory inMemoryChatMemory;

    //qw模型
    private static final String qw = "Qwen/Qwen2-1.5B-Instruct";

    //deepseek
    private static final String r1 = "Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B";

    private static final String DEFAULT_SYSTEM = "你是一个博学的智能聊天助手，请根据用户提问回答！";

    private static final String DEFAULT_PROMPT = "讲个笑话";


    /**
     * http://127.0.0.1:8080/gpt/chat<br/>
     * http://127.0.0.1:8080/gpt/chat?modelname=Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B&prompt=讲一个冷知识
     *
     * @param system
     * @param prompt
     * @param modelname
     * @return
     */
    @RequestMapping("/chat")
    @ResponseBody
    public String chat(@RequestParam(name = "system", defaultValue = DEFAULT_SYSTEM) String system,
                       @RequestParam(name = "prompt", defaultValue = DEFAULT_PROMPT) String prompt,
                       @RequestParam(name = "modelname", defaultValue = qw) String modelname) {

        var chatModel = OpenAiChatModel.builder().openAiApi(openAiApi).build();

        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultUser(DEFAULT_PROMPT)
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model(modelname)
                                .temperature(0.4)
                                .maxTokens(2000)
                                .topP(0.7)
                                .build()
                )
                .build();
        return openAiChatClient.prompt().user(prompt).system(system).advisors().call().content();
    }


    /**
     * http://127.0.0.1:8080/gpt/chat_in_memory<br/>
     * http://127.0.0.1:8080/gpt/chat_in_memory?modelname=Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B&prompt=讲一个冷知识
     *
     * @param system
     * @param prompt
     * @param modelname
     * @return
     */
    @RequestMapping("/chat_in_memory")
    @ResponseBody
    public String chatInMemory(@RequestParam(name = "system", defaultValue = DEFAULT_SYSTEM) String system,
                               @RequestParam(name = "prompt", defaultValue = DEFAULT_PROMPT) String prompt,
                               @RequestParam(name = "modelname", defaultValue = qw) String modelname, @RequestParam(name = "chatId", defaultValue = "1") String chatId) {

        var chatModel = OpenAiChatModel.builder().openAiApi(openAiApi).build();

        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultUser(DEFAULT_PROMPT)
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(inMemoryChatMemory),
                        new SimpleLoggerAdvisor()  //这里可以通过order的设置来指定Advisor的优先级
                        //new SimpleLoggerAdvisor(DEFAULT_CHAT_MEMORY_PRECEDENCE_ORDER-1)

                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model(modelname)
                                .temperature(0.4)
                                .maxTokens(2000)
                                .topP(0.7)
                                .build()
                )
                .build();
        ChatClient.ChatClientRequestSpec responseSpec = openAiChatClient.prompt().user(prompt).system(system);
        responseSpec.advisors(
                (a -> a
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
        );

        return responseSpec.call().content();
    }

    /**
     * http://127.0.0.1:8080/gpt/stream<br/>
     * http://127.0.0.1:8080/gpt/stream?modelname=Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B&prompt=讲一个冷知识
     *
     * @param system
     * @param prompt
     * @param modelname
     * @return
     */
    @RequestMapping("/stream")
    @ResponseBody
    public Flux<String> stream(@RequestParam(name = "system", defaultValue = DEFAULT_SYSTEM) String system,
                               @RequestParam(name = "prompt", defaultValue = DEFAULT_PROMPT) String prompt,
                               @RequestParam(name = "modelname", defaultValue = qw) String modelname, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        var chatModel = OpenAiChatModel.builder().openAiApi(openAiApi).build();

        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultUser(DEFAULT_PROMPT)
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model(modelname)
                                .temperature(0.4)
                                .maxTokens(2000)
                                .topP(0.7)
                                .build()
                )
                .build();
        return openAiChatClient.prompt().user(prompt).system(system).stream().content();
    }


    /**
     * http://127.0.0.1:8080/gpt/stream_in_memory<br/>
     * http://127.0.0.1:8080/gpt/stream_in_memory?modelname=Pro/deepseek-ai/DeepSeek-R1-Distill-Qwen-1.5B&prompt=讲一个冷知识
     *
     * @param system
     * @param prompt
     * @param modelname
     * @return
     */
    @RequestMapping("/stream_in_memory")
    @ResponseBody
    public Flux<String> streamInMemory(@RequestParam(name = "system", defaultValue = DEFAULT_SYSTEM) String system,
                                       @RequestParam(name = "prompt", defaultValue = DEFAULT_PROMPT) String prompt,
                                       @RequestParam(name = "modelname", defaultValue = qw) String modelname,
                                       @RequestParam(name = "chatId", defaultValue = "1") String chatId, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");

        var chatModel = OpenAiChatModel.builder().openAiApi(openAiApi).build();

        var openAiChatClient = ChatClient.builder(chatModel)
                .defaultSystem(DEFAULT_SYSTEM)
                .defaultUser(DEFAULT_PROMPT)
                // 实现 Logger 的 Advisor
                .defaultAdvisors(
                        new MessageChatMemoryAdvisor(inMemoryChatMemory),
                        new SimpleLoggerAdvisor()
                )
                // 设置 ChatClient 中 ChatModel 的 Options 参数
                .defaultOptions(
                        OpenAiChatOptions.builder()
                                .model(modelname)
                                .temperature(0.4)
                                .maxTokens(2000)
                                .topP(0.7)
                                .build()
                )
                .build();
        ChatClient.ChatClientRequestSpec responseSpec = openAiChatClient.prompt().user(prompt).system(system);
        responseSpec.advisors(
                (advisorSpec -> advisorSpec
                        .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                        .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
        );
        return responseSpec.stream().content();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        this.openAiApi = OpenAiApi.builder()
                .apiKey(this.api_key)
                .baseUrl(this.baseUrl)
                .completionsPath(this.completionsPath)
                .build();
    }

}
