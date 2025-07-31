package com.leo.labs.nacosmcp.service;

import com.leo.labs.nacosmcp.pojo.Customer;

public interface CustomerApi {
    Customer findById(Long id);
}
