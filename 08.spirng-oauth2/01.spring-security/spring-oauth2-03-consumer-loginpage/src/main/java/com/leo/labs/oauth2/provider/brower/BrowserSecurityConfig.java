package com.leo.labs.oauth2.provider.brower;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin()// 表单方式登录
//				.loginPage("/imooc-signIn.html")// 设置登录URL
				.loginPage("/authentication/require")// 设置登录URL
				.loginProcessingUrl("/authentication/form")// 自定义用户名密码登录的URL，默认是
															// /login 详见 @code
															// UsernamePasswordAuthenticationFilter
															// 的构造函数
				.and().authorizeRequests()// 授权的页面中
				.antMatchers("/imooc-signIn.html","/authentication/require")// 访问此类请求
				.permitAll()// 不需要权限
				.anyRequest()// 其他此类请求
				.authenticated()// 需要认证
				.and().csrf().disable();// 这里暂时关闭csrf防跨站请求
	}

}
