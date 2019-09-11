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
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leo.labs.oauth2.browser.support.SimpleResponse;
import com.leo.labs.oauth2.core.properties.LoginResponseType;
import com.leo.labs.oauth2.core.properties.SecurityProperties;

/**
 * 
 * <一句话功能简述>
 * @Title: LabsAuthenctiationFailureHandler.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年9月11日下午12:12:36
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@Component("labsAuthenctiationFailureHandler")
public class LabsAuthenctiationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;

	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AuthenticationFailureHandler#onAuthenticationFailure(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("登录失败");
		
		if (LoginResponseType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			if(exception instanceof BadCredentialsException) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}else {
				response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			}
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
		}else{
			super.onAuthenticationFailure(request, response, exception);
		}
		
		
	}

}
