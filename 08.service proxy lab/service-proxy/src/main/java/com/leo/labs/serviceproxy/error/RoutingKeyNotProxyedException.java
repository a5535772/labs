package com.leo.labs.serviceproxy.error;

public class RoutingKeyNotProxyedException extends ProxyException {

	private static final long serialVersionUID = 2546751421909369450L;

	public RoutingKeyNotProxyedException(String message) {
		super(message);
	}

	public RoutingKeyNotProxyedException(String message, Throwable cause) {
		super(message, cause);
	}

}
