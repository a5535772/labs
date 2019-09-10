//package com.leo.labs.oauth2.provider.web.service;
//
//import java.util.Collection;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import com.leo.labs.oauth2.provider.web.dto.SimpleUserDetails;
//
///**
// * 
// * 
// * 
// * @Title: CustomerAuthenticationProvider.java
// * @Description:
// *               <h1>自定义用户认证实现</h1> 认证是由AuthenticationManager 来管理的， 但是真正进行认证的是
// *               AuthenticationManager 中定义的AuthenticationProvider。
// *               <p/>
// *               AuthenticationManager 中可以定义有多个 AuthenticationProvider，
// *               我们在SecurityConfigSecurityConfig中添加了当前的Provider 。
// * 
// * @author Leo Zhang
// * @date 2019年9月9日下午3:52:31
// */
//public class CustomerAuthenticationProvider extends DaoAuthenticationProvider {
//	//
//	//
////	    /** 规则校验 */
////	    @Resource(name = "passwordService")
////	    private PasswordService passwordService;
//	//
////	    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
//	//
//	// 构造函数中注入
//	public CustomerAuthenticationProvider(UserDetailsService userDetailsService) {
//		this.setUserDetailsService(userDetailsService);
//	}
//
//	//
//	/**
//	 * 自定义验证方式
//	 */
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//		String username = authentication.getName();
//		String password = (String) authentication.getCredentials();
//
//		SimpleUserDetails simpleUserDetails = (SimpleUserDetails) this.getUserDetailsService().loadUserByUsername(username);
//
//		// 按登录规则校验用户
//		if(password.equals(simpleUserDetails.getPassword())) {
//			
//		}
//
////		Collection<? extends GrantedAuthority> authorities = userDetails.getAuthorities();
////		Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
////				JSON.toJSONString(simpleUserDetails, SerializerFeature.WriteMapNullValue), password, authorities);
////		return authenticationToken;
//		Object principalToReturn = simpleUserDetails;
//		return createSuccessAuthentication(principalToReturn, authentication, simpleUserDetails);
//
//	}
//
//	@Override
//	public boolean supports(Class<?> arg0) {
//		return true;
//	}
//}
