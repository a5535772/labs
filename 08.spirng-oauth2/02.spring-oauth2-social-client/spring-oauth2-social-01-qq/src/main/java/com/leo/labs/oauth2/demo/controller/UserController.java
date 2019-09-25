package com.leo.labs.oauth2.demo.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

@RestController
public class UserController {
	@Autowired
	private ProviderSignInUtils providerSignInUtils;

	@GetMapping("/user/regist")
	public void regist(User user, HttpServletRequest request) {
		User newUser = this.regUserInDB(user);
		providerSignInUtils.doPostSignUp(newUser.getUsername(), new ServletWebRequest(request));
	}

	private User regUserInDB(User user) {
		return user;
	}

	/**
	 * 127.0.0.1:8080/hello
	 * <p/>
	 * 默认用户名 user
	 */
	@GetMapping("/hello")
	public String hello() {
		return "hello";
	}

	@GetMapping("/user/me1")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	@GetMapping("/user/me2")
	public Object getCurrentUserSimple(Authentication authentication) {
		return authentication;
	}

	@GetMapping("/user/details")
	public Object getCurrentUserSimple(@AuthenticationPrincipal UserDetails userDetails) {
		return userDetails;
	}

}
