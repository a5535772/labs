package com.leo.labs.hystrix.filters.command.file;

import java.util.concurrent.atomic.AtomicLong;

import com.leo.labs.hystrix.filters.command.LeoLabDefaultResponse;
import com.leo.labs.hystrix.filters.command.LeoLabResponse;
import com.leo.labs.hystrix.filters.command.support.AbstractLeoLabCommand;
import com.leo.labs.hystrix.filters.command.support.LeoLabHystrixProperties;

/**
 * 参考 AbstractRibbonCommand
 * 
 * @author LeoZhangli
 *
 */
public class LeoLabHystrixommand extends AbstractLeoLabCommand {
	public LeoLabHystrixommand(String apiName) {
		super(apiName);
	}

	static final AtomicLong Counter = new AtomicLong(0);

	@Override
	protected LeoLabResponse run() throws Exception {
		long currentId = Counter.incrementAndGet();
		if (currentId % 2 == 0) {
			Thread.sleep(LeoLabHystrixProperties.ExecutionTimeoutInMilliseconds+1);
		}
		return new LeoLabDefaultResponse(currentId);
	}

}
