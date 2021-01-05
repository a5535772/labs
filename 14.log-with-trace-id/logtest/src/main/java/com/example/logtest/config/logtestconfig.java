package com.example.logtest.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class logtestconfig {

    @Bean("iocExecutorService")
    public ExecutorService getExecutorService() {
        return Executors.newCachedThreadPool();
    }

    /**
     * feign请求拦截器
     */
    @Bean
    public CustomInterceptor getF() {
        return new CustomInterceptor();
    }
}
