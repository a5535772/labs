///*
// * Copyright 2013-2020 the original author or authors.
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *      https://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//
//package org.springframework.cloud.sleuth.instrument.web.servlet;
//
//import java.io.IOException;
//import java.util.concurrent.atomic.AtomicBoolean;
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.slf4j.MDC;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.sleuth.CurrentTraceContext;
//import org.springframework.cloud.sleuth.Span;
//import org.springframework.cloud.sleuth.SpanCustomizer;
//import org.springframework.cloud.sleuth.TraceContext;
//import org.springframework.cloud.sleuth.Tracer;
//import org.springframework.cloud.sleuth.http.HttpServerHandler;
//import org.springframework.cloud.sleuth.http.HttpServerResponse;
//import com.example.logtest.config.F;
//
//public final class TracingFilter implements Filter {
//    private static final Logger logger = LoggerFactory.getLogger(F.class);
//    final ServletRuntime servlet = ServletRuntime.get();
//
//    final CurrentTraceContext currentTraceContext;
//
//    final HttpServerHandler handler;
//    
//    @Autowired
//    Tracer tracer;
//
//    public static TracingFilter create(CurrentTraceContext currentTraceContext, HttpServerHandler httpServerHandler) {
//        return new TracingFilter(currentTraceContext, httpServerHandler);
//    }
//
//    TracingFilter(CurrentTraceContext currentTraceContext, HttpServerHandler httpServerHandler) {
//        this.currentTraceContext = currentTraceContext;
//        this.handler = httpServerHandler;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = servlet.httpServletResponse(response);
//        
//        logger.info("doFilter MDC traceId is "+MDC.get("traceId"));
//        if (tracer.currentSpan() == null) {
//            logger.info("doFilter tracer.currentSpan is null");
//        } else {
//            logger.info("doFilter tracer.currentSpan is good, and trace id is  " + tracer.currentSpan().context().traceId());
//        }
//        
//
//        // Prevent duplicate spans for the same request
//        TraceContext context = (TraceContext) request.getAttribute(TraceContext.class.getName());
//        
//        TraceContext context2=null;
//        //add by zl
//        if (context2 == null && StringUtils.isNoneBlank(MDC.get("traceId"))) {
//            context2=new TraceContext() {
//                @Override
//                public String traceId() {
//                    return  MDC.get("traceId");
//                }
//                @Override
//                public String spanId() {
//                    return  MDC.get("spanId");
//                }
//                @Override
//                public Boolean sampled() {
//                    return Boolean.valueOf(MDC.get("sampled"));
//                }
//                
//                @Override
//                public String parentId() {
//                    return MDC.get("parentId");
//                }
//            };
//        }
//        
//        
//        if (context != null) {
//            // A forwarded request might end up on another thread, so make sure it is
//            // scoped
//            CurrentTraceContext.Scope scope = currentTraceContext.maybeScope(context);
//            System.out.println(context);
//            System.out.println(context2);
//            try {
//                chain.doFilter(request, response);
//            }
//            finally {
//                scope.close();
//            }
//            return;
//        }
//
//        Span span = handler.handleReceive(new HttpServletRequestWrapper(req));
//
//        // Add attributes for explicit access to customization or span context
//        request.setAttribute(SpanCustomizer.class.getName(), span);
//        request.setAttribute(TraceContext.class.getName(), span.context());
//        SendHandled sendHandled = new SendHandled();
//        request.setAttribute(SendHandled.class.getName(), sendHandled);
//
//        Throwable error = null;
//        CurrentTraceContext.Scope scope = currentTraceContext.newScope(span.context());
//        try {
//            // any downstream code can see Tracer.currentSpan() or use
//            // Tracer.currentSpanCustomizer()
//            chain.doFilter(req, res);
//        }
//        catch (Throwable e) {
//            error = e;
//            throw e;
//        }
//        finally {
//            // When async, even if we caught an exception, we don't have the final
//            // response: defer
//            if (servlet.isAsync(req)) {
//                servlet.handleAsync(handler, req, res, span);
//            }
//            else if (sendHandled.compareAndSet(false, true)) {
//                // we have a synchronous response or error: finish the span
//                HttpServerResponse responseWrapper = HttpServletResponseWrapper.create(req, res, error);
//                handler.handleSend(responseWrapper, span);
//            }
//            scope.close();
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//    }
//
//    /**
//     * Special type used to ensure handleSend is only called once.
//     */
//    static final class SendHandled extends AtomicBoolean {
//
//    }
//}
