package com.leo.labs.sentinel.as.client.service;

import org.springframework.stereotype.Service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.leo.labs.sentinel.as.client.handler.ExceptionUtil;

@Service
public class HelloServiceImpl implements HelloService {

	// 对应的 `handleException` 函数需要位于 `ExceptionUtil` 类中，并且必须为 static 函数.
	@SentinelResource(value = "helloWorld", blockHandler = "handleException", blockHandlerClass = {
			ExceptionUtil.class })
	@Override
	public String helloWorld() {
		return "helloWorld";
	}

	@SentinelResource(value = "helloWorldNoBlocker")
	@Override
	public String helloWorldNoBlocker() {
		return "helloWorldNoBlocker";
	}

	/**
	 * ========================================分割线========================================
	 **/

	// 原函数
	@SentinelResource(value = "hello", blockHandler = "exceptionHandler", fallback = "helloFallback")
	public String hello(long s) {
		return String.format("Hello at %d", s);
	}

	// Fallback 函数，函数签名与原函数一致.
	public String helloFallback(long s) {
		return String.format("Halooooo %d", s);
	}

	// Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
	public String exceptionHandler(long s, BlockException ex) {
		// Do some log here.
		ex.printStackTrace();
		return "Oops, error occurred at " + s;
	}

}
