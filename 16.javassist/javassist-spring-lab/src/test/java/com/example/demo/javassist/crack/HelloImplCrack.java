package com.example.demo.javassist.crack;

import com.example.demo.service.HelloApi;

public class HelloImplCrack implements HelloApi {

    @Override
    public String hello(String word) {
        return "Hello,I Cracked it : " + word;
    }

}
