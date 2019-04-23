package com.leo.labs.sentinel.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

public interface ExceptionUtil {

	public static String handleException(BlockException ex) {
		return "Oops: " + ex.getClass().getCanonicalName();
	}
}
