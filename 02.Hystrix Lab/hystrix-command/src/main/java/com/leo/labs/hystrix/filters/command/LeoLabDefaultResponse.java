package com.leo.labs.hystrix.filters.command;

public class LeoLabDefaultResponse implements LeoLabResponse {
	private long currentId;

	public LeoLabDefaultResponse(long currentId) {
		super();
		this.currentId = currentId;
	}

	public long getCurrentId() {
		return currentId;
	}

	public void setCurrentId(long currentId) {
		this.currentId = currentId;
	}

	@Override
	public String toString() {
		return "LeoLabDefaultResponse [currentId=" + currentId + "]";
	}

}
