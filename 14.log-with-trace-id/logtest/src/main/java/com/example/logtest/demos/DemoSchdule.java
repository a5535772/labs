package com.example.logtest.demos;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.ttl.threadpool.TtlExecutors;
import com.example.logtest.feignclients.DemoFeignClient;

// @Component
// @EnableScheduling
@RestController
public class DemoSchdule {
    private static final Logger logger = LoggerFactory.getLogger(DemoSchdule.class);

    @Autowired
    DemoFeignClient demoFeignClient;

    @Autowired
    Tracer tracer;

    @Qualifier("iocExecutorService")
    @Autowired
    ExecutorService iocExecutorService;

    ExecutorService executorService = Executors.newCachedThreadPool();

    ExecutorService ttlExecutorService = TtlExecutors.getTtlExecutorService(executorService);

    // @Scheduled(fixedDelay = 5000l)
    public void normal_ok() throws Exception {
        logger.info("normal_begins");
        demoFeignClient.t0_1();
        demoFeignClient.t0_2();
    }

    // @Scheduled(fixedDelay = 1000l)
    // @GetMapping("lost")
    public void new_thread_feign_lost_trace_id() throws Exception {
        logger.info("new_thread_begins");
        new Thread(new t0_1_task()).start();
    }

    // @Scheduled(fixedDelay = 1000l)
    @GetMapping("/good")
    public void async_ok() throws Exception {
        logger.info("async_begins");

        printTraceId();

        demoFeignClient.t0_1_async();
    }



    // @Scheduled(fixedDelay = 5000l)
    @GetMapping("/lost")
    public void thread_pool_feign_lost_trace_id() throws Exception {
        logger.info("thread_pool_begins");
        printTraceId();
        executorService.execute(new t0_1_task());
    }
    
    @GetMapping("/lost2Good")
    public void thread_pool_feign_lost_2_good_trace_id() throws Exception {
        logger.info("thread_pool_begins");
        printTraceId();
        executorService.execute(new t0_1__with_span_task(tracer.currentSpan(), tracer));
    }    

    // @Scheduled(fixedDelay = 5000l)
    public void ioc_thread_pool_feign_ok() throws Exception {
        logger.info("ioc_thread_pool_begins");
        iocExecutorService.execute(new t0_1_task());
    }

    /**
     * 
     * <一句话功能简述>
     * 
     * @Title: DemoSchdule.java
     * @Description: <功能详细描述>
     * @author Leo Zhang
     * @date 2020年12月21日下午6:36:15
     * @see [相关类/方法]
     * @since [产品/模块版本]
     */
    public class t0_1_task implements Runnable {
        @Override
        public void run() {
            logger.info("t0_1_task_here");
            demoFeignClient.t0_1();
        }
    }
    
    public class t0_1__with_span_task implements Runnable {
        public t0_1__with_span_task(Span span, Tracer tracer) {
            super();
            this.span = span;
            this.tracer = tracer;
        }
        private Span span;
        private Tracer tracer;
        @Override
        public void run() {
            tracer.withSpan(this.span);
            logger.info("t0_1_task_here");
            demoFeignClient.t0_1();
        }
    } 

    private void printTraceId() {
        if (tracer.currentSpan() == null) {
            logger.info("tracer is null");
        } else {
            logger.info("tracer is good, and trace id is  " + tracer.currentSpan().context().traceId());
        }
    }

}
