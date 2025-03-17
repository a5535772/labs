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

import com.leo.labs.springaidemo.demos.aitools.CustomerTools;
import com.leo.labs.springaidemo.demos.aitools.DateTimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

import static com.leo.labs.springaidemo.demos.statics.Constants.DOUBAO_1_5_PRO_32K_250115;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_CONVERSATION_ID_KEY;
import static org.springframework.ai.chat.client.advisor.AbstractChatMemoryAdvisor.CHAT_MEMORY_RETRIEVE_SIZE_KEY;

/**
 * @author leo，这个是生成图片的
 */
@RequestMapping("/ai")
@Controller
public class OpenAiToolCallbackController {

    @Autowired
    @Qualifier("siliconflowOpenAiApi")
    private OpenAiApi siliconflowOpenAiApi;

    @Autowired
    @Qualifier("arkOpenAiApi")
    private OpenAiApi arkOpenAiApi;

    @Autowired
    private InMemoryChatMemory inMemoryChatMemory;

    @Autowired
    private DateTimeTools dateTimeTools;
    @Autowired
    private CustomerTools customerTools;


    private static final String DEFAULT_SYSTEM = "你是一个博学的智能聊天助手，请根据用户提问回答！";

    /**
     * http://127.0.0.1:8080/ai/tools?prompt=明天是星期几?<br/>
     * http://127.0.0.1:8080/ai/tools?prompt=帮我设置明天早上10点的闹钟<br/>
     * http://127.0.0.1:8080/ai/tools?prompt=查询我的用户信息<br/>
     * http://127.0.0.1:8080/ai/tools?prompt=更新我的用户邮箱<br/>
     * http://127.0.0.1:8080/ai/tools?prompt=查询我的姓名，并更新我的姓名为张馨予<br/>
     *
     * @param system
     * @param prompt
     * @param modelname
     * @return
     */
    @RequestMapping("/tools")
    @ResponseBody
    public String chat(@RequestParam(name = "system", defaultValue = DEFAULT_SYSTEM) String system,
                       @RequestParam(name = "prompt", defaultValue = "What day is tomorrow?") String prompt,
                       @RequestParam(name = "modelname", defaultValue = DOUBAO_1_5_PRO_32K_250115) String modelname,
                       @RequestParam(name = "chatId", defaultValue = "1") String chatId) {

        var chatModel = OpenAiChatModel.builder().openAiApi(arkOpenAiApi).build();

        var openAiChatClient = ChatClient.builder(chatModel)
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
        return openAiChatClient.prompt().user(prompt)
                .tools(dateTimeTools, customerTools)
                .toolContext(Map.of("id", 1000L))
                .system(system)
                .advisors(
                        (advisorSpec -> advisorSpec
                                .param(CHAT_MEMORY_CONVERSATION_ID_KEY, chatId)
                                .param(CHAT_MEMORY_RETRIEVE_SIZE_KEY, 100))
                ).call().content();
    }

}
