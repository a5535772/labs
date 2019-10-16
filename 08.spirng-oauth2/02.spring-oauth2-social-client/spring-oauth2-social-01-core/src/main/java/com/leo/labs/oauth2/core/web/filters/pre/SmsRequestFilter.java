
package com.leo.labs.oauth2.core.web.filters.pre;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.leo.labs.oauth2.core.properties.SecurityConstants;
import com.leo.labs.oauth2.core.validate.exception.ValidateException;

/**
 * 
 * <邀请码的验证filter>
 * 
 * @Title: InviteCodeFilter.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午12:31:23
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SmsRequestFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String SMS_CODE = "smsCode";

	private AuthenticationFailureHandler authenticationFailureHandler;

	public SmsRequestFilter authenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
		return this;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (isLoginRequest(request) && isPost(request)) {
			try {
				valadate(new ServletWebRequest(request));
			} catch (AuthenticationException e) {
				log.error("InviteCodeFilter error,msg is {}", e.getMessage());
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

	private void valadate(ServletWebRequest servletWebRequest) throws ServletRequestBindingException {
		String inviteCode = ServletRequestUtils.getStringParameter(servletWebRequest.getRequest(), SMS_CODE);
		if (StringUtils.isBlank(inviteCode)) {
			throw new ValidateException("短信验证码不存在");
		}
		if (!StringUtils.equalsIgnoreCase(inviteCode, "2345")) {
			throw new ValidateException("短信验证码错误");
		}
	}

	private boolean isPost(HttpServletRequest request) {
		return StringUtils.equalsIgnoreCase(request.getMethod(), RequestMethod.POST.name());
	}

	private boolean isLoginRequest(HttpServletRequest request) {
		return StringUtils.equals(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_MOBILE, request.getRequestURI());
	}

}
