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

import org.springframework.ai.embedding.EmbeddingRequest;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.OpenAiEmbeddingOptions;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.leo.labs.springaidemo.demos.statics.Constants.EMBEDDING_MODEL_NAME;

/**
 * @author leo
 */
@RequestMapping("/ai")
@Controller
public class OpenAiApiEmbeddingController {


    @Autowired
    @Qualifier("siliconflowOpenAiApi")
    private OpenAiApi siliconflowOpenAiApi;

    /**
     * http://127.0.0.1:8080/ai/embedding<br/>
     *
     * @param message
     * @return
     */
    @RequestMapping("/embedding")
    @ResponseBody
    public EmbeddingResponse embedding(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {

        var embeddingModel = new OpenAiEmbeddingModel(this.siliconflowOpenAiApi);
        //Use Runtime Options
        EmbeddingResponse embeddingResponse = embeddingModel.call(
                new EmbeddingRequest(List.of(message),
                        OpenAiEmbeddingOptions.builder()
                                .model(EMBEDDING_MODEL_NAME)
                                .build()));

        return embeddingResponse;
    }


}
