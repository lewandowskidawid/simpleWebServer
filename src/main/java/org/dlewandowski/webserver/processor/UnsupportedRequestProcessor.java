package org.dlewandowski.webserver.processor;

import java.util.Date;

import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;

/**
 * The class is responsible for handling all unsupported request types
 */
class UnsupportedRequestProcessor implements RequestProcessor {

	private final Response response;

	public UnsupportedRequestProcessor(Response response) {
		this.response = response;
	}

	@Override
	public void process() {
		sendNotImplementedOperationResponse();
	}

	private void sendNotImplementedOperationResponse() {
		response.setResponseStatus(ResponseStatus.NOT_IMPLEMENTED_OPERATION);
		response.addHeader("Date", new Date());
	}
}
