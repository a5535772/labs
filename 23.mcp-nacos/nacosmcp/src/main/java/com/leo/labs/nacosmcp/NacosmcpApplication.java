package com.leo.labs.nacosmcp;

//import org.springframework.ai.autoconfigure.openai.OpenAiAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


//@SpringBootApplication(exclude = {OpenAiAutoConfiguration.class})
@SpringBootApplication
@EnableDiscoveryClient
public class NacosmcpApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosmcpApplication.class, args);
    }

}
