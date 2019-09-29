package org.dlewandowski.webserver.request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import org.apache.commons.lang3.StringUtils;

public class RequestBuilder {

	private static final int HTTP_METHOD_PARAM_INDEX = 0;

	private static final int URI_PARAM_INDEX = 1;

	private static final int HTTP_VERSION_PARAM_INDEX = 2;

	public Request from(Socket socket) throws IOException {
		InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
		BufferedReader reader = new BufferedReader(streamReader);
		RequestInfo requestInfo = extractRequestInfo(reader);
		return new Request(requestInfo);
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
}
