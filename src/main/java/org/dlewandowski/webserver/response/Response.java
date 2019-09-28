package org.dlewandowski.webserver.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.dlewandowski.webserver.request.Request;

public class Response {

	private final String httpVersion;

	private final Socket socket;

	private final Map<String, String> headers;

	private final DateFormat dateFormat;

	private ResponseStatus responseStatus;

	private boolean committed;

	private Response(String httpVersion, Socket socket) {
		this.httpVersion = httpVersion;
		this.socket = socket;
		this.headers = new LinkedHashMap<>();
		this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.committed = false;
	}

	public static Response from(Socket socket, Request request) {
		return new Response(request.getRequestInfo().getHttpVersion(), socket);
	}

	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public void addHeader(String key, Date value) {
		headers.put(key, dateFormat.format(value));
	}

	public OutputStream getOutputStream() throws IOException {
		if (!committed) {
			commit();
		}
		return socket.getOutputStream();
	}

	public void flush() throws IOException {
		getOutputStream().flush();
	}

	public ResponseStatus getResponseStatus() {
		return responseStatus;
	}

	private void commit() throws IOException {
		if (!committed) {
			sendHeaders();
			committed = true;
		}
	}

	private void sendHeaders() throws IOException {
		//OutputStream needs to be directly requrested from socket to avoid loop
		final PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.println(String.format("%s %d %s", httpVersion, responseStatus.getCode(), responseStatus.getMessage()));
		for (Map.Entry<String, String> header : headers.entrySet()) {
			writer.println(header.getKey() + ": " + header.getValue());
		}
		writer.println();
		writer.flush();
	}
}
