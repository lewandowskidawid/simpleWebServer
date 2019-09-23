package org.dlewandowski.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Request {

	private static final int HTTP_METHOD_PARAM_INDEX = 0;

	private static final int URI_PARAM_INDEX = 1;

	private static final int HTTP_VERSION_PARAM_INDEX = 2;

	private static final String HEADER_KEY_VALUE_SEPARATOR = ": ";

	private final RequestInfo requestInfo;

	private final Map<String, String> headers;

	private Request(RequestInfo requestInfo, Map<String, String> headers) {
		this.requestInfo = requestInfo;
		this.headers = headers;
	}

	public static Request from(Socket socket) throws IOException {
		InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(streamReader);
		RequestInfo requestInfo = extractRequestInfo(reader);
		Map<String, String> headers = extractRequestHeaders(reader);
		return new Request(requestInfo, headers);
	}

	private static RequestInfo extractRequestInfo(BufferedReader reader) throws IOException {
		RequestInfo result = null;
		String data = reader.readLine();
		if (StringUtils.isNotEmpty(data)) {
			String[] parameters = data.split(" ");
			String method = parameters[HTTP_METHOD_PARAM_INDEX];
			String uri = parameters[URI_PARAM_INDEX];
			String httpVersion = parameters[HTTP_VERSION_PARAM_INDEX];
			result = new RequestInfo(method, uri, httpVersion);
		}
		return result;
	}

	private static Map<String, String> extractRequestHeaders(BufferedReader reader) throws IOException {
		Map<String, String> headers = new HashMap<>();
		String line;
		while (StringUtils.isNotEmpty(line = reader.readLine())) {
			String key = StringUtils.substringBefore(line, HEADER_KEY_VALUE_SEPARATOR);
			String value = StringUtils.substringAfter(line, HEADER_KEY_VALUE_SEPARATOR);
			headers.put(key, value);
		}
		return headers;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}
}
