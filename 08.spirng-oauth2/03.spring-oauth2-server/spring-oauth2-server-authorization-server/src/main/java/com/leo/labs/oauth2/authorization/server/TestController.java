package com.leo.labs.oauth2.authorization.server;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
	@GetMapping("/hello")
	public String hello() {
		return "1";
	}
	@GetMapping("/oauth/abc")
	public String abc() {
		return "1";
	}	
	
	@GetMapping("/user/me")
	public Object getCurrentUser() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
