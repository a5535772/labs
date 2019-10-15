package com.leo.labs.oauth2.authorization.server.browser.filters.pre;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.google.gson.Gson;
import com.leo.labs.oauth2.core.security.authentication.LabSimpleAuthenticationToken;

public class LabUserTokenAuthorizeFilter extends OncePerRequestFilter {
	private Logger log = LoggerFactory.getLogger(this.getClass());

	private String suffix = "/oauth/authorize";

	@Autowired
	private UserDetailsService simpleInternalMemoryUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String labUserToken = getLabUserToken(request);
		if (isAuthorizeRequest(request) && StringUtils.isNotBlank(labUserToken)) {
			UserDetails userDetails = getUserDetailsByToken(labUserToken);
			if (ObjectUtils.allNotNull(userDetails)) {
				Authentication authentication = buildTemplateAuthentication(request, userDetails);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.info("初始化 Authentication 到 filter 成功 ,labUserToken : {}", labUserToken);
				log.info("初始化 Authentication 到 filter 成功 ,,authentication:{} ", new Gson().toJson(authentication));
			}
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * 偷懒了，这里应该是使用token从真实的业务缓存中获取到labUserToken
	 */
	private UserDetails getUserDetailsByToken(String labUserToken) {
		return simpleInternalMemoryUserDetailsService.loadUserByUsername(labUserToken);
	}

//	private Authentication buildTemplateAuthentication(HttpServletRequest request, UserDetails userDetails) {
//
//		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
//				userDetails.getAuthorities());
//		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//		return authentication;
//	}

	private Authentication buildTemplateAuthentication(HttpServletRequest request, UserDetails userDetails) {

		LabSimpleAuthenticationToken authentication = new LabSimpleAuthenticationToken(userDetails,
				userDetails.getAuthorities());
		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		return authentication;
	}

	String tokenHead = "Bearer ";
	String tokenHeader = "Authorization";

	private String getLabUserToken(HttpServletRequest request) {
		String authHeader = request.getHeader(this.tokenHeader);
		if (authHeader != null && authHeader.startsWith(tokenHead)) {
			return authHeader.substring(tokenHead.length()); // The part after "Bearer "
		}
		return null;
	}

	private boolean isAuthorizeRequest(HttpServletRequest request) {
		return request.getRequestURI().endsWith(suffix);
	}

}
