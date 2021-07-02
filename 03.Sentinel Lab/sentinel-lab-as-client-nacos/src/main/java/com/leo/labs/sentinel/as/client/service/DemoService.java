package com.leo.labs.sentinel.as.client.service;

public interface DemoService {

	public String flow1();

	public String flow2();

	public String flow3();
	
	public String degrade_rt();
	
	public String degrade_ratio();
	
	public String degrade_count();
}
