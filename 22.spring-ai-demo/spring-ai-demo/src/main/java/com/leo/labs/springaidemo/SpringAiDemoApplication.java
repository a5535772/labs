package com.leo.labs.springaidemo;

import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 如果你用spring-ai-alibaba，自动装饰的类是 DashScopeAutoConfiguration.class
 */
@SpringBootApplication(exclude = {OpenAiAutoConfiguration.class})
public class SpringAiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringAiDemoApplication.class, args);
    }

}
