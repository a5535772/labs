/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leo.labs.sentinel.client.starter.init;

import java.util.List;
import java.util.Properties;

import com.alibaba.csp.sentinel.config.SentinelConfig;
import com.alibaba.csp.sentinel.datasource.ReadableDataSource;
import com.alibaba.csp.sentinel.datasource.nacos.NacosDataSource;
import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.init.InitOrder;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.util.AppNameUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.leo.labs.sentinel.client.starter.common.NacosConstants;

/**
 * @author leo
 * @since 1.7.2
 */
@InitOrder(0)
public class NacosClientInitFunc implements InitFunc {
	@Override
	public void init() throws Exception {

		String appName = AppNameUtil.getAppName();
		Properties properties = getNacosProperties();

		// flow-rules，该方法会覆盖loadRules的内容，如果想要在本地规则生效钱，先loader融合本地和云端的规则，可以配置InstantiationSentinelBeanPostProcessor到spring中，来强制云端加载的优先级
		initFlowRule(appName, properties);
		// degrade-rules
		initDegradeRule(appName, properties);

	}

	private Properties getNacosProperties() {
		Properties properties = new Properties();
		properties.put(PropertyKeyConst.SERVER_ADDR, SentinelConfig.getConfig("csp.sentinel.client.nacos.serverAddr"));
		properties.put(PropertyKeyConst.NAMESPACE, SentinelConfig.getConfig("csp.sentinel.client.nacos.namespace"));
		return properties;
	}

	private static void initFlowRule(String appName, Properties properties) {
		ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new NacosDataSource<>(properties,
				NacosConstants.GROUP_ID, appName + NacosConstants.FLOW_DATA_ID_POSTFIX,
				source -> JSON.parseObject(source, new TypeReference<List<FlowRule>>() {
				}));
		FlowRuleManager.register2Property(flowRuleDataSource.getProperty());
	}

	private static void initDegradeRule(String appName, Properties properties) {
		ReadableDataSource<String, List<DegradeRule>> degradeflowRuleDataSource = new NacosDataSource<>(properties,
				NacosConstants.GROUP_ID, appName + NacosConstants.DEGRADE_DATA_ID_POSTFIX,
				source -> JSON.parseObject(source, new TypeReference<List<DegradeRule>>() {
				}));
		DegradeRuleManager.register2Property(degradeflowRuleDataSource.getProperty());
	}
}
