package org.dlewandowski.webserver.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.request.Request;

public class Response {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final String httpVersion;

	private final OutputStream outputStream;

	private final Socket socket;

	private final Map<String, String> headers;

	private ResponseCode responseCode;

	private boolean committed;

	private Response(String httpVersion, Socket socket) throws IOException {
		this.httpVersion = httpVersion;
		this.outputStream = socket.getOutputStream();
		this.socket = socket;
		this.headers = new HashMap<>();
		this.committed = false;
	}

	public static Response from(Socket socket, Request request) throws IOException {
		return new Response(request.getRequestInfo().getHttpVersion(), socket);
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addHeader(String key, Date value) {
		headers.put(key, DATE_FORMAT.format(value));
	}

	public OutputStream getOutputStream() throws SocketException {
		if (!committed) {
			commit();
		}
		return outputStream;
	}

	public void commit() throws SocketException {
		if (!committed) {
			if (isKeepAliveConnectionRequested()) {
				configureKeepAliveConnection();
			}
			sendHeaders();
			committed = true;
		}
	}

	public void flush() throws IOException {
		commit();
		outputStream.flush();
	}

	private boolean isKeepAliveConnectionRequested() {
		return headers.getOrDefault("Connection", StringUtils.EMPTY).equalsIgnoreCase("Keep-Alive");
	}

	private void configureKeepAliveConnection() throws SocketException {
		socket.setKeepAlive(true);
		socket.setSoTimeout(50000);
		addHeader("Keep-Alive", "timeout=5");
	}

	private void sendHeaders() {
		final PrintWriter writer = new PrintWriter(outputStream);
		writer.println(String.format("%s %d %s", httpVersion, responseCode.getResponseCode(), responseCode.getMessage()));
		for (Map.Entry<String, String> header : headers.entrySet()) {
			writer.println(header.getKey() + ": " + header.getValue());
		}
		writer.println();
		writer.flush();
	}
}
