package com.leo.labs.nacosmcp.pojo;

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
