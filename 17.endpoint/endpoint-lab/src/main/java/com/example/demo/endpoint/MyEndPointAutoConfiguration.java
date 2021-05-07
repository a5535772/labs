package com.example.demo.endpoint;

import org.springframework.boot.actuate.autoconfigure.endpoint.condition.ConditionalOnAvailableEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;


@ConditionalOnProperty(name = "leo.labs.my.endpoint.enabled", matchIfMissing = true)
public class MyEndPointAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnAvailableEndpoint
    public MyEndpoint myEndPoint() {
        return new MyEndpoint();
    }
}
