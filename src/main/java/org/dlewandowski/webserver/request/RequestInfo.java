package org.dlewandowski.webserver.request;

public class RequestInfo {

	private final String method;

	private final String uri;

	private final String httpVersion;

	public RequestInfo(String method, String uri, String httpVersion) {
		this.method = method;
		this.uri = uri;
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getHttpVersion() {
		return httpVersion;
	}
}
