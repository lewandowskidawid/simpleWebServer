package org.dlewandowski.webserver;

import org.dlewandowski.webserver.server.Server;

public class App {

	public static final int PORT = 65535;

	public static void main(String[] args) {
		try {
			Server server = new Server(PORT);
			server.start();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}