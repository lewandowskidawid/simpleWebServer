package org.dlewandowski.webserver.processor;

import java.io.IOException;

import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;

public class RequestProcessor {

	private final Request request;

	private final Response response;

	private final String directoryPath;

	public RequestProcessor(Request request, Response response, String directoryPath) {
		this.request = request;
		this.response = response;
		this.directoryPath = directoryPath;
	}

	public void processRequest() throws IOException {
		if (isGetRequest()) {
			GetRequestProcessor processor = new GetRequestProcessor(request, response, directoryPath);
			processor.processRequest();
		} else {
			UnsupportedRequestProcessor processor = new UnsupportedRequestProcessor(response);
			processor.processRequest();
		}
	}

	private boolean isGetRequest() {
		return "GET".equals(request.getRequestInfo().getMethod());
	}

}
