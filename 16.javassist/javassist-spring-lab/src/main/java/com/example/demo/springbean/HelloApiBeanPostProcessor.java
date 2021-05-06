package com.example.demo.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.stereotype.Component;
import com.example.demo.javassist.tools.HotSwaperInjecter;
import com.example.demo.javassist.tools.MethodVariableNameUtil;
import lombok.extern.slf4j.Slf4j;


/**
 * how to use<br/>
 * 1.需要把tools.jar加到classpath<br/>
 * 2.增加JVM选项 -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000<br/>
 * 
 * @author leo zhang
 *
 */
@Slf4j
@Component
public class HelloApiBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private DefaultListableBeanFactory defaultListableBeanFactory;

    HotSwaperInjecter hotSwaperInjecter = HotSwaperInjecter.getInstance();
    static final String targetBeanName = "com.example.demo.service.HelloImpl";

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (isHelloApi(bean) && defaultListableBeanFactory.containsBean(beanName)) {
            hotSwaperInjecter.injectPrint(bean, MethodVariableNameUtil.getMethodNames(bean.getClass()));
        }
        return bean;
    }

    private boolean isHelloApi(Object bean) {
        return bean.getClass().getName().equals(targetBeanName);
    }

    // bean初始化方法调用后被调用
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

}
