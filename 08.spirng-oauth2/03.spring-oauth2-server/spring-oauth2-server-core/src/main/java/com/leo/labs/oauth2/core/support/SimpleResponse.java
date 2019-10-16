package com.leo.labs.oauth2.core.support;

public class SimpleResponse {
	public SimpleResponse(Object content) {
		super();
		this.content = content;
	}

	private Object content;

	public Object getContent() {
		return content;
	}

	public void setContent(Object content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "SimpleResponse [content=" + content + "]";
	}
}
