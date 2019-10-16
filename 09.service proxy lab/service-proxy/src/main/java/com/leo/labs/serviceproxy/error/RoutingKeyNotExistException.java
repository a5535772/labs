package com.leo.labs.serviceproxy.error;

public class RoutingKeyNotExistException extends ProxyException {

	private static final long serialVersionUID = 2546751421909369450L;

	public RoutingKeyNotExistException(String message) {
		super(message);
	}

	public RoutingKeyNotExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
