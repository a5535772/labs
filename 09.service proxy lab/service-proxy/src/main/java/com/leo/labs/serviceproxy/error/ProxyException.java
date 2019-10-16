package com.leo.labs.serviceproxy.error;

@SuppressWarnings("serial")
public class ProxyException extends RuntimeException {

	/**
	 * Constructor for AspectException.
	 * @param message the exception message
	 */
	public ProxyException(String message) {
		super(message);
	}

	/**
	 * Constructor for AspectException.
	 * @param message the exception message
	 * @param cause the root cause, if any
	 */
	public ProxyException(String message, Throwable cause) {
		super(message, cause);
	}

}