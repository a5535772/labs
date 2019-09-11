package com.leo.labs.oauth2.core.validate.processors;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: ValidateProcessor.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午4:02:48
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ValidateProcessor {
	/**
	 * 校验验证码
	 * 
	 * @param servletWebRequest
	 * @throws Exception
	 */
	void validate(ServletWebRequest servletWebRequest);

}