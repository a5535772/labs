package com.leo.labs.sentinel.as.client.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.slots.block.Rule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

import cn.hutool.json.JSONUtil;

@RequestMapping("/rules")
@RestController
public class RuleController {

	@GetMapping("/flow")
	@ResponseBody
	public String getFlowRules() {
		return JSONUtil.toJsonStr(FlowRuleManager.getRules());
	}

	@GetMapping("/degrade")
	@ResponseBody
	public String getDegradeRules() {
		return JSONUtil.toJsonStr(DegradeRuleManager.getRules());
	}

	@GetMapping("/all")
	@ResponseBody
	public String getAllRules() {
		List<Rule> rules = new ArrayList<>();
		rules.addAll(FlowRuleManager.getRules());
		rules.addAll(DegradeRuleManager.getRules());
		return JSONUtil.toJsonStr(rules);
	}
}
