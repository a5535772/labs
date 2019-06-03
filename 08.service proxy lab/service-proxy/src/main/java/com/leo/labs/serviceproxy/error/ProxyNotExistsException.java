package com.leo.labs.serviceproxy.error;

public class ProxyNotExistsException extends ProxyException {

	private static final long serialVersionUID = 2546751421909369450L;

	public ProxyNotExistsException(String message) {
		super(message);
	}

	public ProxyNotExistsException(String message, Throwable cause) {
		super(message, cause);
	}

}
