package com.leo.labs.springaidemo.demos.service;

import com.leo.labs.springaidemo.demos.pojo.Customer;

public interface CustomerApi {
    Customer findById(Long id);
}
