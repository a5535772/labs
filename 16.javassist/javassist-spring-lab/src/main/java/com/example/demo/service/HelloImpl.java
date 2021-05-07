package com.example.demo.service;

import org.springframework.stereotype.Service;

@Service("helloApi")
public class HelloImpl implements HelloApi {

    @Override
    public String hello(String word) {
        return "hello : " + word;
    }

}
