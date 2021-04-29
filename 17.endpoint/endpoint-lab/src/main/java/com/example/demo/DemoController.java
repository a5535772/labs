package com.example.demo;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController implements InitializingBean {

    @Value("${arthas.appName}")
    String appName;
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("appName is " + appName);
    }

}
