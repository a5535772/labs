package com.leo.labs.oauth2.demo.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "labs.security.auto", name = "reg")
public class DemoConnectionSignUp implements ConnectionSignUp {

	@Override
	public String execute(Connection<?> connection) {
		return connection.getDisplayName();// 这里应该生产主键
	}

}
