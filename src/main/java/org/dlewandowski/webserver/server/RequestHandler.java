package org.dlewandowski.webserver.server;

import java.io.IOException;
import java.net.Socket;

import org.apache.log4j.Logger;
import org.dlewandowski.webserver.processor.RequestProcessor;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;

class RequestHandler implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(RequestHandler.class);

	private final Socket socket;

	private final String directoryPath;

	public RequestHandler(Socket socket, String directoryPath) {
		this.socket = socket;
		this.directoryPath = directoryPath;
	}

	@Override
	public void run() {
		try {
			Request request = Request.from(socket);
			Response response = Response.from(socket, request);
			RequestProcessor requestProcessor = new RequestProcessor(request, response, directoryPath);
			requestProcessor.processRequest();
			response.flush();
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
