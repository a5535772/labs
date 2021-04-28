package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoController {

    @Autowired
    BaiduFeignClient baiduFeignClient;

    @GetMapping("/hi")
    public String hi() {
        return baiduFeignClient.hello();
    }
}
