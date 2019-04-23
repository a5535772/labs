package com.leo.labs.sentinel.as.client.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;

@Configuration
public class SentinelAspectConfiguration implements InitializingBean {
	public static final Integer qps = 1;

	@Bean
	public SentinelResourceAspect sentinelResourceAspect() {
		return new SentinelResourceAspect();
	}

	/**
	 * 通过 API 修改比较直观，可以通过以下几个 API 修改不同的规则：
	 * 
	 * FlowRuleManager.loadRules(List<FlowRule> rules); // 修改流控规则
	 * DegradeRuleManager.loadRules(List<DegradeRule> rules); // 修改降级规则
	 * SystemRuleManager.loadRules(List<SystemRule> rules); // 修改系统规则
	 * AuthorityRuleManager.loadRules(List<AuthorityRule> rules); // 修改授权规则
	 */
	// 定义规则
	private void initFlowRules() {
		List<FlowRule> rules = new ArrayList<>();
		List<String> ruleResourceList = buildRuleResourceList();
		for (String resource : ruleResourceList) {
			rules.add(buildRule(resource));
		}
		FlowRuleManager.loadRules(rules);
	}

	private List<String> buildRuleResourceList() {
		List<String> ruleResourceList = new ArrayList<>();
		ruleResourceList.add("helloWorld");
		ruleResourceList.add("helloWorldNoBlocker");
		ruleResourceList.add("hello");
		return ruleResourceList;
	}

	private FlowRule buildRule(String resource) {
		FlowRule rule = new FlowRule();
		rule.setResource(resource);
		rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
		rule.setCount(qps.doubleValue());
		return rule;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initFlowRules();
	}
}