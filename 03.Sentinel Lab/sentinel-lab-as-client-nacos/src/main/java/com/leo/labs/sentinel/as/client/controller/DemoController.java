package com.leo.labs.sentinel.as.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.context.ContextUtil;
import com.leo.labs.sentinel.as.client.service.DemoService;

import cn.hutool.json.JSONUtil;

@RestController
@RequestMapping("/demo")
public class DemoController {
	@Autowired
	DemoService demoService;

	@GetMapping("flow1")
	public String flow1() {
		System.out.println(Thread.currentThread() + "  flow1-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.flow1();
		System.out.println(Thread.currentThread() + "  flow1-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}

	@GetMapping("flow2")
	public String flow2() {
		System.out.println(Thread.currentThread() + "  flow2-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.flow2();
		System.out.println(Thread.currentThread() + "  flow2-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}

	@GetMapping("flow3")
	public String flow3() {
		System.out.println(Thread.currentThread() + "  flow3-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.flow3();
		System.out.println(Thread.currentThread() + "  flow3-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}

	@GetMapping("degrade/rt")
	public String degrade_rt() {
		System.out.println(Thread.currentThread() + "  degrade/rt-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.degrade_rt();
		System.out.println(Thread.currentThread() + "  degrade/rt-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}
	
	@GetMapping("degrade/ratio")
	public String degrade_ratio() {
		System.out.println(Thread.currentThread() + "  degrade/ratio-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.degrade_ratio();
		System.out.println(Thread.currentThread() + "  degrade/ratio-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}
	
	@GetMapping("degrade/count")
	public String degrade_count() {
		System.out.println(Thread.currentThread() + "  degrade/count-1" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		String result = demoService.degrade_count();
		System.out.println(Thread.currentThread() + "  degrade/count-2" + JSONUtil.toJsonStr(ContextUtil.getContext()));
		return result;
	}	

}
