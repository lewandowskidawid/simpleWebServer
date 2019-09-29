package org.dlewandowski.webserver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.dlewandowski.webserver.processor.RequestProcessorProvider;
import org.dlewandowski.webserver.request.RequestBuilder;
import org.dlewandowski.webserver.response.ResponseBuilder;

public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class);

	private final String directory;

	private final int port;

	private final int threadsNumber;

	private ExecutorService executorService;

	public Server(String directory, int port, int threadsNumber) {
		this.directory = directory;
		this.port = port;
		this.threadsNumber = threadsNumber;
	}

	public void start() throws IOException {
		RequestBuilder requestBuilder = new RequestBuilder();
		ResponseBuilder responseBuilder = new ResponseBuilder();
		RequestProcessorProvider requestProcessorProvider = new RequestProcessorProvider();
		try (ServerSocket listener = new ServerSocket(port)) {
			executorService = Executors.newFixedThreadPool(threadsNumber);
			LOGGER.info("Server runs at: http://localhost:" + port);
			while (true) {
				try {
					Socket socket = listener.accept();
					executorService.execute(new RequestHandler(socket, directory, requestBuilder, responseBuilder, requestProcessorProvider));
				} catch (RuntimeException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
		} finally {
			stop();
		}
	}

	public void stop() {
		if (executorService != null) {
			executorService.shutdownNow();
		}
	}
}
