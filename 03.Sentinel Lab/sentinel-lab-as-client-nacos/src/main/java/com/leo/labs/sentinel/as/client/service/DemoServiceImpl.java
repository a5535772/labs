package com.leo.labs.sentinel.as.client.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.Tracer;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.leo.labs.sentinel.as.client.handler.ExceptionUtil;

@Service
public class DemoServiceImpl implements DemoService {
	static final String FLOW_1 = "FLOW-flow1";
	static final String FLOW_2 = "FLOW-flow2";
	static final String FLOW_3 = "FLOW-flow3";

	// @see RuleConstant
	static final String DEGRADE_RT = "DEGRADE-degrade-rt";
	static final String DEGRADE_RATIO = "DEGRADE-degrade-ratio";
	static final String DEGRADE_COUNT = "DEGRADE-degrade-count";

	// 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
	@SentinelResource(value = FLOW_1, blockHandler = "handleException", blockHandlerClass = { ExceptionUtil.class })
	@Override
	public String flow1() {
		return "flow1";
	}

	@SentinelResource(value = FLOW_2, blockHandler = "handleException", blockHandlerClass = { ExceptionUtil.class })
	@Override
	public String flow2() {
		return "flow2";
	}

	@Override
	public String flow3() {
		String result;
		Entry entry = null;
		try {
			entry = SphU.entry(FLOW_3);
			result = "flow3";
		} catch (BlockException e) {
			result = ExceptionUtil.handleException(e);
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}
		return result;
	}

	@Override
	public String degrade_rt() {
		String result;
		Entry entry = null;
		try {
			entry = SphU.entry(DEGRADE_RT);
			result = "degrade-rt";
			Thread.sleep(2000l);
		} catch (BlockException e) {
			result = ExceptionUtil.handleException(e);
		} catch (InterruptedException e) {
			result = e.getMessage();
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}
		return result;
	}

	private AtomicInteger count = new AtomicInteger(0);

	/**
	 * @see ExceptionRatioDegradeDemo
	 */
	@Override
	public String degrade_ratio() {
		String result;
		Entry entry = null;
		try {
			entry = SphU.entry(DEGRADE_RATIO);
			result = "degrade-ratio";
			if (count.getAndIncrement() % 2 == 0) {
				throw new RuntimeException("throw runtime ratio");
			}
		} catch (BlockException e) {
			result = ExceptionUtil.handleException(e);
		} catch (Throwable t) {
			result = t.getMessage();
			Tracer.trace(t);
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}
		return result;
	}

	@Override
	public String degrade_count() {
		String result;
		Entry entry = null;
		try {
			entry = SphU.entry(DEGRADE_COUNT);
			result = "degrade-count";
			throw new RuntimeException("throw runtime count");
		} catch (BlockException e) {
			result = ExceptionUtil.handleException(e);
		} catch (Throwable t) {
			result = t.getMessage();
			Tracer.trace(t);
		} finally {
			if (entry != null) {
				entry.exit();
			}
		}
		return result;
	}

}
