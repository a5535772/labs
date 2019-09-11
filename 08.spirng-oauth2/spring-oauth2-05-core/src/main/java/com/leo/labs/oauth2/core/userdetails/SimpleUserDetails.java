package com.leo.labs.oauth2.core.userdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 
 * @Title: SimpleUserDetails.java
 * @Description: just a Simple UserDetails
 * @author Leo Zhang
 * @date 2019年9月9日下午2:37:38
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SimpleUserDetails implements UserDetails {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8524169405106032460L;

	private String username;

	private String password;

	private User user;

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String getPassword() {
		return password;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	/**
	 * 重写getAuthorities方法，将用户的角色作为权限
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		/** 后续带完善，可以根据 普通用户、vip、授权用户等进行设置 */
		/** 也可以业务解耦，这里可以不全是权限范围，有统一服务提供 */
		return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_SUPER");
	}
}
