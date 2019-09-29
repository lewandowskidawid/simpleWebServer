package org.dlewandowski.webserver.request;

public class Request {

	private final RequestInfo requestInfo;

	Request(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
}
