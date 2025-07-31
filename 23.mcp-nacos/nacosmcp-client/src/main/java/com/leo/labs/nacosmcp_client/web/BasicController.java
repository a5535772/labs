

package com.leo.labs.nacosmcp_client.web;


import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

@Controller
@RefreshScope
public class BasicController {


    // http://127.0.0.1:8089/hello?name=lisi
    @RequestMapping("/hello")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return "Hello " + name;
    }

    @Value("${author}")
    String author;

    // http://127.0.0.1:8089/author
    @RequestMapping("/author")
    @ResponseBody
    public String author() {
        return "author: " + author;
    }

    //    spring 官方clinet
    @Autowired
    private List<McpSyncClient> mcpSyncClients;



    // http://127.0.0.1:8089/client/getCurrentDateTime
    @RequestMapping("/client/getCurrentDateTime")
    @ResponseBody
    public void testClients() {
        for (McpSyncClient mcpSyncClient : mcpSyncClients) {
            McpSchema.CallToolRequest callToolRequest = new McpSchema.CallToolRequest("getCurrentDateTime", new HashMap<String, Object>());
            McpSchema.CallToolResult callToolResult = mcpSyncClient.callTool(callToolRequest);
            System.out.println(callToolResult);
        }
    }


}
