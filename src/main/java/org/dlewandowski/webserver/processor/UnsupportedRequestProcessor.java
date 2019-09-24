package org.dlewandowski.webserver.processor;

import java.util.Date;

import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;

class UnsupportedRequestProcessor {

	private final Response response;

	public UnsupportedRequestProcessor(Response response) {
		this.response = response;
	}

	public void processRequest() {
		sendNotImplementedOperationResponse();
	}

	private void sendNotImplementedOperationResponse() {
		response.setResponseStatus(ResponseStatus.NOT_IMPLEMENTED_OPERATION);
		response.addHeader("Date", new Date());
	}
}
