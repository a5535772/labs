package com.leo.labs.oauth2.zuul.resource.server.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.BearerTokenExtractor;
import org.springframework.security.oauth2.provider.authentication.TokenExtractor;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Component
public class TokenFilter extends ZuulFilter {
	@Autowired
	private ResourceServerTokenServices tokenServices;

	private TokenExtractor tokenExtractor = new BearerTokenExtractor();

	private AccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		final HttpServletRequest request = context.getRequest();
		Authentication authentication = tokenExtractor.extract(request);
		String token = (String) authentication.getPrincipal();
		Map<String, ?> map = this.checkToken(token);
		System.out.println("==============I Can do anything here==========");
		System.out.println(map);
		return null;
	}

	/**
	 * PRE过滤器: 在请求被路由之前调用, 可用来实现身份验证、在集群中选择请求的微服务、记录调试信息等;
	 */
	@Override
	public String filterType() {
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		return FilterConstants.PRE_DECORATION_FILTER_ORDER - 2;
	}

	private Map<String, ?> checkToken(String value) {

		OAuth2AccessToken token = tokenServices.readAccessToken(value);
		if (token == null) {
			throw new InvalidTokenException("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new InvalidTokenException("Token has expired");
		}

		OAuth2Authentication authentication = tokenServices.loadAuthentication(token.getValue());

		Map<String, ?> response = accessTokenConverter.convertAccessToken(token, authentication);

		return response;
	}

}
