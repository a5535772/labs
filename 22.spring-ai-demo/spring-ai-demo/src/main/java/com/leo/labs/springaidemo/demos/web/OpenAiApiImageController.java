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

import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author leo，这个是生成图片的
 */
@RequestMapping("/ai")
@Controller
public class OpenAiApiImageController {

    @Autowired
    @Qualifier("siliconflowOpenAiImageApi")
    private OpenAiImageApi siliconflowOpenAiImageApi;


    /**
     * http://127.0.0.1:8080/ai/v1/images_generations<br/>
     * demo image:https://sc-maas.oss-cn-shanghai.aliyuncs.com/outputs/db780e81-7964-417a-bc78-d3e31baef112_0.png?OSSAccessKeyId=LTAI5tQnPSzwAnR8NmMzoQq4&Expires=1741948593&Signature=iyJNFaLOlq5GjzAAaQMQdKPSH1w%3D
     * 视情况使用，因为各家网站现在实现的不同，比如硅基流动遵循了openai，但是火山目前是独立的api，另外，硅基流动有限流
     * @param message
     * @return
     */
    @RequestMapping("/v1/images_generations")
    @ResponseBody
    public String embedding(@RequestParam(value = "message", defaultValue = "画一个夕阳下驾驶EVA的少年") String message) {
        var openaiImageModel = new OpenAiImageModel(this.siliconflowOpenAiImageApi);
        ImageResponse response = openaiImageModel.call(
                new ImagePrompt(message,
                        OpenAiImageOptions.builder()
                                .model("Kwai-Kolors/Kolors")
                                .quality("hd")
                                .N(4)
                                .height(1024)
                                .width(1024).build())
        );
        return response.getResult().getOutput().getUrl();

    }
}
