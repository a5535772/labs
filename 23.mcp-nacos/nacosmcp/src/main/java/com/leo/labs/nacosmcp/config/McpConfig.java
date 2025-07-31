package com.leo.labs.nacosmcp.config;

import com.leo.labs.nacosmcp.aitools.CustomerTools;
import com.leo.labs.nacosmcp.aitools.DateTimeTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {
    @Autowired
    CustomerTools customerTools;
    @Autowired
    DateTimeTools dateTimeTools;

    @Bean
    public ToolCallbackProvider tools() {
        return MethodToolCallbackProvider.builder().toolObjects(customerTools, dateTimeTools).build();
    }
}
