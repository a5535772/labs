

package com.leo.labs.nacosmcp.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RefreshScope
public class BasicController {
    @Value("${author}")
    String author;

    // http://127.0.0.1:8088/hello?name=lisi
    @RequestMapping("/hello1")
    @ResponseBody
    public String hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return "Hello " + name;
    }

    // http://127.0.0.1:8088/author
    @RequestMapping("/hello")
    @ResponseBody
    public String author() {
        return "author: " + author;
    }

}
