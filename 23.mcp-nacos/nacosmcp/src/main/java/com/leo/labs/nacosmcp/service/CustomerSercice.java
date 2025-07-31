package com.leo.labs.nacosmcp.service;

import com.leo.labs.nacosmcp.pojo.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerSercice implements CustomerApi {
    @Override
    public Customer findById(Long id) {
        if (1000L == id) {
            return Customer.builder()
                    .id(1000L)
                    .name("leo")
                    .email("leo@qq.com")
                    .phone("123456789").build();
        }
        return null;
    }
}
