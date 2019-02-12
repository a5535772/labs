package com.leo.labs.hystrix.filters.command.support;

import com.leo.labs.hystrix.filters.command.LeoLabCommand;
import com.leo.labs.hystrix.filters.command.LeoLabResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixCommandProperties.ExecutionIsolationStrategy;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;

/**
 * 参考了
 * 
 * <li>https://www.jianshu.com/p/39763a0bd9b8</li>
 * <li>https://www.jianshu.com/p/6e9619cbdfc3?tdsourcetag=s_pctim_aiomsg</li>
 * <li><code>AbstractRibbonCommand</code></li>
 * 
 * @author LeoZhangli
 *
 */
public abstract class AbstractLeoLabCommand extends HystrixCommand<LeoLabResponse> implements LeoLabCommand {
	private static final String LeoLabCommandGroupKey = "LeoLabCommand";
	private static final String KeySeparator = "|";
	private static final int HystrixThreadPoolCoreSize = 4;// HystrixThreadPool's default value is 10

	protected AbstractLeoLabCommand(String commandKey) {
		super(getSetter(commandKey));
	}

	protected static Setter getSetter(final String commandKey) {
		Setter commandSetter = Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(LeoLabCommandGroupKey))
				.andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));
		commandSetter.andCommandPropertiesDefaults(getCommandProperties());
		commandSetter.andThreadPoolPropertiesDefaults(getThreadPoolProperties());
		commandSetter.andThreadPoolKey(getThreadPoolKey(commandKey));
		return commandSetter;
	}

	private static HystrixThreadPoolKey getThreadPoolKey(final String commandKey) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(commandKey).append(KeySeparator).append(HystrixThreadPoolCoreSize).toString();
		return HystrixThreadPoolKey.Factory.asKey(stringBuilder.toString());
	}

	private static com.netflix.hystrix.HystrixThreadPoolProperties.Setter getThreadPoolProperties() {
		return HystrixThreadPoolProperties.Setter().withCoreSize(HystrixThreadPoolCoreSize);
	}

	private static HystrixCommandProperties.Setter getCommandProperties() {
		HystrixCommandProperties.Setter setter = HystrixCommandProperties.Setter();
		setter.withRequestCacheEnabled(Boolean.FALSE)// 设置请求缓存是否对HystrixCollapser.execute()和HystrixCollapser.queue()的调用起作用。默认值：true
				.withExecutionIsolationStrategy(ExecutionIsolationStrategy.THREAD)// HystrixCommand.run()的隔离策略THREAD，SEMAPHORE。默认值：THREAD
				.withExecutionIsolationThreadInterruptOnTimeout(Boolean.TRUE)// 设置HystrixCommand.run()的执行是否在超时发生时被中断。默认值：true
				.withExecutionTimeoutEnabled(Boolean.TRUE)// 设置HystrixCommand.run()的执行是否有超时限制。默认值：true
				.withExecutionTimeoutInMilliseconds(LeoLabHystrixProperties.ExecutionTimeoutInMilliseconds)// 设置调用者等待命令执行的超时限制，超过此时间，HystrixCommand被标记为TIMEOUT，并执行回退逻辑。默认值：1000（毫秒）
				.withCircuitBreakerEnabled(Boolean.TRUE)// 设置断路器是否起作用。 默认值：true
				.withCircuitBreakerRequestVolumeThreshold(LeoLabHystrixProperties.CircuitBreakerRequestVolumeThreshold)// 设置在一个滚动窗口（默认10S）中，打开断路器的最少请求数。默认值：20
				.withCircuitBreakerErrorThresholdPercentage(
						LeoLabHystrixProperties.CircuitBreakerErrorThresholdPercentage)// 设置打开回路并启动回退逻辑的错误比率。默认值：50 %
				.withCircuitBreakerSleepWindowInMilliseconds(
						LeoLabHystrixProperties.CircuitBreakerSleepWindowInMilliseconds);// 设置在回路被打开，拒绝请求到再次尝试请求并决定回路是否继续打开的时间。默认值：5000（毫秒）
		return setter;
	}
	
	@Override
	protected LeoLabResponse getFallback() {
		return super.getFallback();
	}
}
