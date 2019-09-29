package org.dlewandowski.webserver.server;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.dlewandowski.webserver.processor.RequestProcessor;
import org.dlewandowski.webserver.processor.RequestProcessorProvider;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.request.RequestBuilder;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseBuilder;

/**
 * Interpreters request and respond to it by finding proper {@link RequestProcessor}. When communication ends the {@link Socket} is being closed.
 */
class RequestHandler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(RequestHandler.class);

	private final Socket socket;

	private final String directoryPath;

	private final RequestBuilder requestBuilder;

	private final ResponseBuilder responseBuilder;

	private final RequestProcessorProvider requestProcessorProvider;

	public RequestHandler(Socket socket, String directoryPath, RequestBuilder requestBuilder, ResponseBuilder responseBuilder,
			RequestProcessorProvider requestProcessorProvider) {
		this.socket = socket;
		this.directoryPath = directoryPath;
		this.requestBuilder = requestBuilder;
		this.responseBuilder = responseBuilder;
		this.requestProcessorProvider = requestProcessorProvider;
	}

	@Override
	public void run() {
		try {
			Request request = requestBuilder.from(socket);
			Response response = responseBuilder.from(socket, request);
			RequestProcessor requestProcessor = requestProcessorProvider.get(request, response, directoryPath);
			requestProcessor.process();
			response.flush();
			LOGGER.debug(String.format("Request: %d %d, %s %s", Thread.currentThread().getId(), response.getResponseStatus().getCode(),
					request.getRequestInfo().getMethod(), request.getRequestInfo().getResourcePath()));
		} catch (IOException e) {
			LOGGER.error("Cannot serve the request", e);
		} finally {
			closeSocket();
		}
	}

	private void closeSocket() {
		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				LOGGER.error("Cannot close the socket", e);
			}
		}
	}
}
