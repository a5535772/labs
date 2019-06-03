package com.leo.labs.serviceproxy.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 在需要客户端，需要proxy的类&属性上加入后，会自动扫描
 * @author LeoZhangli
 *
 */
@Target({ ElementType.FIELD,ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteProxyClient {
	/**
	 * 注解别名
	 * 
	 * @return
	 */
	public String value() default "";
}
