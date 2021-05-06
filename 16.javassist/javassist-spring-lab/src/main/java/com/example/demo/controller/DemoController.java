package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.BaiduFeignClient;
import com.example.demo.service.HelloApi;

@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    BaiduFeignClient baiduFeignClient;
    
    @Autowired
    HelloApi helloApi;

    @GetMapping("/baidu")
    public String baidu() {
        return baiduFeignClient.hello();
    }
    
    @GetMapping("/hello")
    public String hello() {
        return helloApi.hello("word");
    }   
}
