package com.example.logtest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import feign.RequestInterceptor;
import feign.RequestTemplate;

public class CustomInterceptor implements RequestInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(CustomInterceptor.class);
    @Autowired
    Tracer tracer;

    @Override
    public void apply(RequestTemplate template) {
         logger.info("F MDC traceId is " + MDC.get("traceId"));
         if (tracer.currentSpan() != null) {
         logger.info("F tracer.currentSpan is null ");
         } else {
         logger.info("F tracer.currentSpan is good, and trace id is " +
         tracer.currentSpan().context().traceId());
         }

    }

}
