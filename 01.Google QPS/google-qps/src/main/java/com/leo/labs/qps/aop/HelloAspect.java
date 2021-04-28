package com.leo.labs.qps.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import com.google.gson.Gson;

@Aspect
@Component
public class HelloAspect {
//    @Pointcut("@annotation(com.leo.labs.qps.aop.APPRequest)")
//    public void servicePointCut() {}

    @Around(value="@annotation(com.leo.labs.qps.aop.AppRequest) && @annotation(appRequest)")
    public Object excuete(ProceedingJoinPoint joinPoint,AppRequest appRequest) throws Throwable {
        System.out.println(new Gson().toJson(joinPoint.getArgs()));
        
        System.out.println(joinPoint.getTarget().getClass());
        
        System.out.println(joinPoint.getSignature().getName());

        System.out.println(appRequest.flag());
        
        Object result = joinPoint.proceed();

        return result;

    }
}
