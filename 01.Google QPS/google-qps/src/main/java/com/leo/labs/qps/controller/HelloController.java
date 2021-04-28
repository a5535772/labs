package com.leo.labs.qps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.util.concurrent.RateLimiter;
import com.leo.labs.qps.aop.DemoService;

@RestController
public class HelloController {
    public static final String Succ = "succ";
    public static final String Fail = "fail";

    @GetMapping("hello")
    public String hello() {
        return Succ;
    }

    RateLimiter rateLimiter = RateLimiter.create(1);

    @GetMapping("helloLimited")
    public String helloLimited() {
        if (rateLimiter.tryAcquire()) {
            return Succ;
        } else {
            return Fail;
        }
    }

    @Autowired
    DemoService demoService;

    @GetMapping("testApo")
    public String testAop() {
        demoService.flagTrue("this is true");
        demoService.flagFalse("this is false");
        return "good";
    }
}
