package com.example.demo.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import com.example.demo.javassist.tools.HotSwaperInjecter;
import com.example.demo.javassist.tools.MethodVariableNameUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class HelloApiBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    HotSwaperInjecter hotSwaperInjecter = HotSwaperInjecter.getInstance();
    static final String targetBeanName = "com.example.demo.service.HelloImpl";


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        FeignClient feignClient = bean.getClass().getAnnotation(FeignClient.class);
        System.out.println("-------------------");
        System.out.println(bean.getClass().getName());
        System.out.println(feignClient);
        System.out.println("================");
        if (isHelloApi(bean) && defaultListableBeanFactory.containsBean(beanName)) {

            hotSwaperInjecter.injectPrint(bean, MethodVariableNameUtil.getMethodNames(bean.getClass()));
        }

        return bean;

    }



    private boolean isHelloApi(Object bean) {
        return bean.getClass().getName().equals(targetBeanName);
    }



    private boolean isBaiduFeignClient(FeignClient feignClient) {
        return feignClient != null;
    }



    // bean初始化方法调用后被调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
