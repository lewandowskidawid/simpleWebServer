package org.dlewandowski.webserver.server;

import java.io.IOException;
import java.net.Socket;

import org.dlewandowski.webserver.processor.RequestProcessor;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;

class RequestHandler implements Runnable {

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
			e.printStackTrace();
		} finally {
			closeSocket();
		}
	}

	private void closeSocket() {
		if (!socket.isClosed()) {
			try {
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
