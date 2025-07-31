package com.leo.labs.nacosmcp.aitools;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MCPClientDemo {
    
    private static final String SERVICE_URL = "http://169.254.57.111:8088/sse/updateCustomerInfo";
    
    public static void main(String[] args) {
        try {
            // 调用更新客户信息工具
            String result = updateCustomerInformation(12345, "张三", "zhangsan@example.com");
            System.out.println("调用结果: " + result);
        } catch (Exception e) {
            System.err.println("调用MCP服务时发生错误: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 调用MCP服务更新客户信息工具
     * @param arg0 客户ID
     * @param arg1 客户姓名
     * @param arg2 客户联系方式
     * @return 服务响应结果
     */
    public static String updateCustomerInformation(Integer arg0, String arg1, String arg2) throws IOException {
        URL url = new URL(SERVICE_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        
        // 设置请求方法和头部
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setRequestProperty("Accept", "application/json");
        connection.setDoOutput(true);
        
        // 构造JSON请求体
        String jsonInputString = String.format(
            "{\"arg0\": %d, \"arg1\": \"%s\", \"arg2\": \"%s\"}", 
            arg0, arg1, arg2
        );
        
        // 发送请求
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }
        
        // 读取响应
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                return response.toString();
            }
        } else {
            throw new IOException("HTTP错误码: " + responseCode);
        }
    }
}
