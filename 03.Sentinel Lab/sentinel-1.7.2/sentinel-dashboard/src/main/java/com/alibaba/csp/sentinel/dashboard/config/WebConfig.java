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
package com.alibaba.csp.sentinel.dashboard.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.csp.sentinel.adapter.servlet.CommonFilter;
import com.alibaba.csp.sentinel.adapter.servlet.callback.WebCallbackManager;
import com.alibaba.csp.sentinel.dashboard.auth.AuthorizationInterceptor;
import com.alibaba.csp.sentinel.dashboard.auth.LoginAuthenticationFilter;
import com.alibaba.csp.sentinel.dashboard.jackson.databind.CustomLongJsonSerializer;
import com.alibaba.csp.sentinel.util.StringUtil;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * @author leyou
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Autowired
	private LoginAuthenticationFilter loginAuthenticationFilter;

	@Autowired
	private AuthorizationInterceptor authorizationInterceptor;

	//add by leo
	@Autowired
	private HttpMessageConverters httpMessageConverters;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authorizationInterceptor).addPathPatterns("/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/resources/");
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.htm");
	}

	/**
	 * Add {@link CommonFilter} to the server, this is the simplest way to use
	 * Sentinel for Web application.
	 */
	@Bean
	public FilterRegistrationBean sentinelFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(new CommonFilter());
		registration.addUrlPatterns("/*");
		registration.setName("sentinelFilter");
		registration.setOrder(1);
		// If this is enabled, the entrance of all Web URL resources will be unified as
		// a single context name.
		// In most scenarios that's enough, and it could reduce the memory footprint.
		registration.addInitParameter(CommonFilter.WEB_CONTEXT_UNIFY, "true");

		logger.info("Sentinel servlet CommonFilter registered");

		return registration;
	}

	@PostConstruct
	public void doInit() {
		Set<String> suffixSet = new HashSet<>(Arrays.asList(".js", ".css", ".html", ".ico", ".txt", ".woff", ".woff2"));
		// Example: register a UrlCleaner to exclude URLs of common static resources.
		WebCallbackManager.setUrlCleaner(url -> {
			if (StringUtil.isEmpty(url)) {
				return url;
			}
			if (suffixSet.stream().anyMatch(url::endsWith)) {
				return null;
			}
			return url;
		});
	}

	@Bean
	public FilterRegistrationBean authenticationFilterRegistration() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
		registration.setFilter(loginAuthenticationFilter);
		registration.addUrlPatterns("/*");
		registration.setName("authenticationFilter");
		registration.setOrder(0);
		return registration;
	}

	/**
	 * MappingJackson2HttpMessageConverter 实现了HttpMessageConverter 接口，
	 * httpMessageConverters.getConverters()
	 * 返回的对象里包含了MappingJackson2HttpMessageConverter
	 * 
	 * @return
	 */
	@Bean
	public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
		return new MappingJackson2HttpMessageConverter(new CustomJacksonMapper());
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.addAll(httpMessageConverters.getConverters());
	}

	/**
	 * 返回Long转换为String
	 * @see https://blog.csdn.net/u012620150/article/details/90033244
	 * @see https://blog.csdn.net/weixin_37162010/article/details/81484230
	 * @author leo zhang
	 */
	public class CustomJacksonMapper extends ObjectMapper {
		private static final long serialVersionUID = -7317930389867069539L;

		public CustomJacksonMapper() {
			super();
			this.configure(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN, true);
			this.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
			this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			this.setSerializationInclusion(Include.NON_NULL);

			SimpleModule simpleModule = new SimpleModule();
			simpleModule.addSerializer(Long.class, CustomLongJsonSerializer.instance);
			simpleModule.addSerializer(Long.TYPE, CustomLongJsonSerializer.instance);
			simpleModule.addSerializer(long.class, CustomLongJsonSerializer.instance);
			registerModule(simpleModule);

		}
	}
}
