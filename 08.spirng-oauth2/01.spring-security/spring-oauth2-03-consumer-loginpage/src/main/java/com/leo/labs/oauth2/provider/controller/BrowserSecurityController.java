package com.leo.labs.oauth2.provider.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.leo.labs.oauth2.provider.dto.SimpleResponse;

@RestController
public class BrowserSecurityController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private RequestCache requestCache = new HttpSessionRequestCache();// http请求缓存工具

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@RequestMapping("/authentication/require")
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentications(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		if (savedRequest != null) {
			String targetURL = savedRequest.getRedirectUrl();
			if (StringUtils.endsWithIgnoreCase(targetURL, ".html")) {
				redirectStrategy.sendRedirect(request, response, "/imooc-signIn.html");
			}
		}
		return new SimpleResponse("请使用.html结尾的请求访问登录页面");
	}
}
