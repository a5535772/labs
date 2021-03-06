package com.leo.labs.oauth2.browser.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

import com.leo.labs.oauth2.browser.authentication.LabsAuthenctiationFailureHandler;
import com.leo.labs.oauth2.browser.authentication.LabsAuthenticationSuccessHandler;
import com.leo.labs.oauth2.core.properties.SecurityConstants;
import com.leo.labs.oauth2.core.properties.SecurityProperties;
import com.leo.labs.oauth2.core.web.authentication.sms.SmsCodeAuthenticationSecurityConfig;
import com.leo.labs.oauth2.core.web.filters.pre.InviteCodeFilter;
import com.leo.labs.oauth2.core.web.filters.pre.SmsRequestFilter;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	SecurityProperties securityProperties;
	@Autowired
	private LabsAuthenticationSuccessHandler labsAuthenticationSuccessHandler;// 成功处理器
	@Autowired
	private LabsAuthenctiationFailureHandler labsAuthenctiationFailureHandler;// 失败处理器
	@Autowired
	private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	@Autowired
	private SpringSocialConfigurer labsSocialConfigurer;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new InviteCodeFilter().authenticationFailureHandler(labsAuthenctiationFailureHandler),
				UsernamePasswordAuthenticationFilter.class)// 添加邀请码前置过滤器
				.addFilterBefore(new SmsRequestFilter().authenticationFailureHandler(labsAuthenctiationFailureHandler),
						UsernamePasswordAuthenticationFilter.class)
				.formLogin()// 表单方式登录
				.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)// 登录页的地址
				.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)// 自定义用户名密码登录的URL，默认是
				// /login 详见 @code
				// UsernamePasswordAuthenticationFilter
				// 的构造函数
				.successHandler(labsAuthenticationSuccessHandler)// 登录成功后的handler，默认是
																	// ForwardAuthenticationSuccessHandler 详见
																	// FormLoginConfigurer
				.failureHandler(labsAuthenctiationFailureHandler)// 失败处理器
				.and().authorizeRequests()// 授权的页面中的此类请求
				.antMatchers(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL// 登录响应的URL
						, securityProperties.getBrowser().getLoginPage()// 登录页面地址
						, "/user/regist")// 注册页面
				.permitAll()// 不需要权限
				.anyRequest()// 其他此类请求
				.authenticated()// 需要认证
				.and().csrf().disable()// 这里暂时关闭csrf防跨站请求
				.apply(smsCodeAuthenticationSecurityConfig)// 把 smsCodeAuthenticationSecurityConfig 的配置都加入了浏览器配置了
				.and().apply(labsSocialConfigurer);
		;
	}

}
