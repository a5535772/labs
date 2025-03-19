package com.leo.labs.springaidemo.demos.aitools;

import com.leo.labs.springaidemo.demos.pojo.Customer;
import com.leo.labs.springaidemo.demos.service.CustomerApi;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("customerTools")
public class CustomerTools {
    @Autowired
    CustomerApi customerApi;

    @Tool(description = "Retrieve customer information")
    Customer getCustomerInfo(ToolContext toolContext) {
        System.out.println("now getCustomerInfo");
        return customerApi.findById((Long) toolContext.getContext().get("id"));
    }

    @Tool(description = "Update customer information")
    void updateCustomerInfo(Long id, String name, @ToolParam(required = false) String email, ToolContext toolContext) {
        System.out.println("Updated info for customer with id: " + id + ", name: " + name + ", email: " + email);
    }


}