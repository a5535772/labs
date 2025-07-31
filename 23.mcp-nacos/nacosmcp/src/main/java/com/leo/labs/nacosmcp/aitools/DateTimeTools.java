package com.leo.labs.nacosmcp.aitools;

import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Leo<p>
 * 详见 https://docs.spring.io/spring-ai/reference/1.0/api/tools.html
 * <p>
 * Spring AI provides built-in support for specifying tools (i.e. ToolCallback(s)) from methods in two ways:
 * <p>
 * 1、declaratively, using the @Tool annotation
 * <p>
 * 2、programmatically, using the low-level MethodToolCallback implementation.
 * <p>
 * Here is an example of how to use the @Tool annotation:
 */
@Component("dateTimeTools")
public class DateTimeTools {
    @Tool(description = "Get the current date and time in the user's timezone")
    String getCurrentDateTime(ToolContext toolContext) {
        System.out.println("getCurrentDateTime");
        return LocalDateTime.now().atZone(LocaleContextHolder.getTimeZone().toZoneId()).toString();
    }


    @Tool(description = "Set a user alarm for the given time")
    void setAlarm(@ToolParam(description = "Time in ISO-8601 format") String time,ToolContext toolContext) {
        LocalDateTime alarmTime = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME);
        System.out.println("Alarm set for " + alarmTime);
    }


}
