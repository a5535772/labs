package com.leo.labs.oauth2.core.validate;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4116983831870774854L;

	public ValidateCodeException(String msg) {
		super(msg);
	}

}
