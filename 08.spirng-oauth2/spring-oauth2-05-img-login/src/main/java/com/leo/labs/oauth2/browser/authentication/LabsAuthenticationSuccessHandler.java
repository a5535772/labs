/**
 * 
 */
package com.leo.labs.oauth2.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.labs.oauth2.core.properties.LoginResponseType;
import com.leo.labs.oauth2.core.properties.SecurityProperties;

/**
 * 
 * <一句话功能简述>
 * @Title: ImoocAuthenticationSuccessHandler.java
 * @Description: 登录成功有的处理方法
 * @author  Leo Zhang
 * @date 2019年9月10日下午1:12:33
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("labsAuthenticationSuccessHandler")
public class LabsAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SecurityProperties securityProperties;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.web.authentication.
	 * AuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.
	 * HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * org.springframework.security.core.Authentication)
	 */
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		log.info("登录成功");

		if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(authentication));
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}

	}

}
