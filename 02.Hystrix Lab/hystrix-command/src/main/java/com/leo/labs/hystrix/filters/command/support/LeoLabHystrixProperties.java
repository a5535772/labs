package com.leo.labs.hystrix.filters.command.support;

/**
 * 使用单例作常量Constants
 * 
 * @author LeoZhangli
 *
 */
public enum LeoLabHystrixProperties {
	LeoLabHystrixProperties();

	public static final int ExecutionTimeoutInMilliseconds = 1000;//生产建议用5000
	
	public static final int CircuitBreakerRequestVolumeThreshold = 10;
	public static final int CircuitBreakerErrorThresholdPercentage = 20;
	public static final int CircuitBreakerSleepWindowInMilliseconds = 5000;
}
