package com.leo.labs.oauth2.core.validate.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * 
 * <一句话功能简述>
 * @Title: ValidateException.java
 * @Description: <功能详细描述>
 * @author  Leo Zhang
 * @date 2019年9月11日下午4:08:14
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidateException extends AuthenticationException {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4116983831870774854L;

	public ValidateException(String msg) {
		super(msg);
	}

}
