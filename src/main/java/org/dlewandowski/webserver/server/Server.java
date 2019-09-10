package org.dlewandowski.webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;

public class Server {

	private final int port;

	public Server(int port) {
		this.port = port;
	}

	public void start() {
		try (ServerSocket listener = new ServerSocket(port)) {
			System.out.println("The date server is running...");
			while (true) {
				try (Socket socket = listener.accept()) {
					test(socket);
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private synchronized void test(Socket socket) throws IOException {
		Request request = Request.from(socket);
		Response response = Response.from(request);
		response.sendResponse(socket.getOutputStream());
	}
}
