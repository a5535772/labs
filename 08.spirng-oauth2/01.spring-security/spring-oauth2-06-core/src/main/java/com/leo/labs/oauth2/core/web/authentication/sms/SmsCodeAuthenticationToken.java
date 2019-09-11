package com.leo.labs.oauth2.core.web.authentication.sms;

import java.util.Collection;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * <一句话功能简述>
 * 
 * @Title: SMSCodeAuthenticationToken.java
 * @Description: <功能详细描述>
 * @author Leo Zhang
 * @date 2019年9月11日下午4:38:53
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SmsCodeAuthenticationToken extends AbstractAuthenticationToken {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3365784759402020210L;

	// ~ Instance fields
	// ================================================================================================

	private final Object principal;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * This constructor can be safely used by any code that wishes to create a
	 * <code>UsernamePasswordAuthenticationToken</code>, as the
	 * {@link #isAuthenticated()} will return <code>false</code>.
	 *
	 */
	public SmsCodeAuthenticationToken(Object principal) {
		super(null);
		this.principal = principal;
		setAuthenticated(false);
	}

	/**
	 * This constructor should only be used by <code>AuthenticationManager</code> or
	 * <code>AuthenticationProvider</code> implementations that are satisfied with
	 * producing a trusted (i.e. {@link #isAuthenticated()} = <code>true</code>)
	 * authentication token.
	 *
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public SmsCodeAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.principal = principal;
		super.setAuthenticated(true); // must use super, as we override
	}

	// ~ Methods
	// ========================================================================================================

	public Object getCredentials() {
		return null;
	}

	public Object getPrincipal() {
		return this.principal;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) {
		if (isAuthenticated) {
			throw new IllegalArgumentException(
					"Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
		}

		super.setAuthenticated(false);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((principal == null) ? 0 : principal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmsCodeAuthenticationToken other = (SmsCodeAuthenticationToken) obj;
		if (principal == null) {
			if (other.principal != null)
				return false;
		} else if (!principal.equals(other.principal)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "SMSCodeAuthenticationToken [principal=" + principal + "]";
	}

}
