package com.leo.labs.serviceproxy.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 路由键必须以显性key传输<br/>
 * 优先级 ElementType.METHOD > ElementType.TYPE <br/>
 * ElementType.TYPE 可作用于整个类
 * 
 * @author LeoZhangli
 *
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface RoutingKey {
	/** 路由key在方法中第N个位置 */
	public int value() default 0;
}
