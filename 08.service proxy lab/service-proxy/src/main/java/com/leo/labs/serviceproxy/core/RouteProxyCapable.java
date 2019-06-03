package com.leo.labs.serviceproxy.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RouteProxyCapable {
	/**
	 * 注解别名
	 * 
	 * @return
	 */
	public String value() default "";
}
