package com.leo.labs.serviceproxy.basiclogic.test;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ConcurrentHashMapTest {

	private Map<String, String> map = new ConcurrentHashMap<>();

	@Test
	public void dotest() throws InterruptedException {
		RunClz a1 = new RunClz("1", "1");
		RunClz a2 = new RunClz("1", "2");
//		RunClz b1 = new RunClz("2", "1");

		a1.start();
		a2.start();
//		b1.start();

		a1.join();
		a2.join();
//		b1.join();

		System.out.println(map.get("1"));
	}

	private void set(String key, String value) throws InterruptedException {
		System.out.println("进入1层 key:" + key + " value:" + value);
		if (map.get(key) == null) {
			System.out.println("进入2层 key:" + key + " value:" + value);
			synchronized (key) {
				Thread.sleep(5000l);
				System.out.println("进入3层 key:" + key + " value:" + value);
				
				if (map.get(key) == null) {
					System.out.println("设置 key:" + key + " value:" + value);
					map.put(key, value);
				}
			}
		}

	}

	private class RunClz extends Thread {

		public RunClz(String key, String value) {
			super();
			this.key = key;
			this.value = value;
		}

		private String key;
		private String value;

		public void run() {
			try {
				set(key, value);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
