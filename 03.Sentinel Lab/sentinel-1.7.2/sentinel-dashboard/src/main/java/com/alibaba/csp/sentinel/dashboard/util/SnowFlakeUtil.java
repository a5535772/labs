package com.alibaba.csp.sentinel.dashboard.util;

import com.alibaba.csp.sentinel.dashboard.tools.SnowFlake;

public class SnowFlakeUtil {

	private static final long DATA_CENTER_ID = 0l;
	private static final long MACHINE_ID = 0l;

	public static final SnowFlake SnowFlakeInstance = new SnowFlake(DATA_CENTER_ID, MACHINE_ID);

	public static long nextId() {
		return SnowFlakeInstance.nextId();

	}
}
