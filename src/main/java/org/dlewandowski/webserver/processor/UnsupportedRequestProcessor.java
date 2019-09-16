package org.dlewandowski.webserver.processor;

import java.util.Date;

import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseCode;

public class UnsupportedRequestProcessor {

	private final Response response;

	public UnsupportedRequestProcessor(Response response) {
		this.response = response;
	}

	public void processRequest() {
		sendNotImplementedOperationResponse();
	}

	private void sendNotImplementedOperationResponse() {
		response.setResponseCode(ResponseCode.NOT_IMPLEMENTED_OPERATION);
		response.addHeader("Date", new Date());
	}
}
