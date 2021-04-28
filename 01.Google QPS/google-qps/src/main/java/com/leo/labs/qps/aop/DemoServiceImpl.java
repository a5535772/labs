package com.leo.labs.qps.aop;

import org.springframework.stereotype.Service;

@Service
public class DemoServiceImpl implements DemoService {

    @AppRequest(flag = true)
    @Override
    public String flagTrue(String param) {
        return param;
    }

    @AppRequest(flag = false)
    @Override
    public String flagFalse(String param) {
        return param;
    }

}
