package com.leo.labs.springaidemo.demos.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String phone;
}
