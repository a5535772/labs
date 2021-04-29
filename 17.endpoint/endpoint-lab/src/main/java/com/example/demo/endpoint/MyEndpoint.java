package com.example.demo.endpoint;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

@Endpoint(id = "myEndpoint")
public class MyEndpoint {
    
    @Value("${arthas.appName}")
    String appName;

    @ReadOperation
    public Map<String, Object> invoke() {
        Map<String, Object> result = new HashMap<>();
        result.put("appName", appName);
        result.put("currentTimeMillis", System.currentTimeMillis());
        return result;
    }

}
