package com.leo.labs.oauth2.resource.server.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class DemoController {

	@GetMapping("/hello")
	public String me() {
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
