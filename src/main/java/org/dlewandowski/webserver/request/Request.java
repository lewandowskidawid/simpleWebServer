package org.dlewandowski.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Request {

	private static final int HTTP_METHOD_PARAM_INDEX = 0;

	private static final int URI_PARAM_INDEX = 1;

	private static final int HTTP_VERSION_PARAM_INDEX = 2;

	private static final String HEADER_KEY_VALUE_SEPARATOR = ":";

	private final HttpMethod method;

	private final String uri;

	private final String httpVersion;

	private final Map<String, String> headers;

	private Request(HttpMethod method, String uri, String httpVersion, Map<String, String> headers) {
		this.method = method;
		this.uri = uri;
		this.httpVersion = httpVersion;
		this.headers = headers;
	}

	public static Request from(Socket socket) {
		HttpMethod method = HttpMethod.OTHER;
		String uri = StringUtils.EMPTY;
		String httpVersion = StringUtils.EMPTY;
		Map<String, String> headers = new HashMap<>();
		System.out.println("New Request");
		try {
			InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
			BufferedReader reader = new BufferedReader(streamReader);

			String line = reader.readLine();
			if (StringUtils.isNotEmpty(line)){
				System.out.println("First line: " + line);
				String[] parameters = line.split(" ");
				//TODO support for other needs to be provided
				method = HttpMethod.valueOf(parameters[HTTP_METHOD_PARAM_INDEX]);
				uri = parameters[URI_PARAM_INDEX];
				httpVersion = parameters[HTTP_VERSION_PARAM_INDEX];
				while (StringUtils.isNotEmpty(line = reader.readLine())) {
					System.out.println("Line: " +line);
					String key = StringUtils.substringBefore(line, HEADER_KEY_VALUE_SEPARATOR);
					String value = StringUtils.substringAfter(line, HEADER_KEY_VALUE_SEPARATOR);
					headers.put(key, value);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new Request(method, uri, httpVersion, headers);
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public Map<String, String> getHeaders() {
		return Collections.unmodifiableMap(headers);
	}
}
