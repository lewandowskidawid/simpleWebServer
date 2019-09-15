package org.dlewandowski.webserver.server;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	private final String directory;

	private final int port;

	private final int threadsNumber;

	private ExecutorService executorService;

	public Server(String directory, int port, int threadsNumber) {
		this.directory = directory;
		this.port = port;
		this.threadsNumber = threadsNumber;
	}

	public void start() {
		try (ServerSocket listener = new ServerSocket(port)) {
			executorService = Executors.newFixedThreadPool(threadsNumber);
			System.out.println("The date server is running...");
			while (true) {
				Socket socket = listener.accept();
				executorService.execute(new RequestHandler(socket, directory));
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (executorService != null) {
				executorService.shutdownNow();
			}
		}
	}

	public void stop() {
//ToDO is it needed or finally statement is enough
	}
}
