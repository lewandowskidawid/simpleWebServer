package org.dlewandowski.webserver.request;

import java.net.URI;

/**
 * Exposes basic request parameters.
 */
public class RequestInfo {

	private final String method;

	private final String uri;

	private final String httpVersion;

	public RequestInfo(String method, String uri, String httpVersion) {
		this.method = method;
		this.uri = URI.create(uri).getPath();
		this.httpVersion = httpVersion;
	}

	public String getMethod() {
		return method;
	}

	public String getResourcePath() {
		return uri;
	}

	public String getHttpVersion() {
		return httpVersion;
	}
}
