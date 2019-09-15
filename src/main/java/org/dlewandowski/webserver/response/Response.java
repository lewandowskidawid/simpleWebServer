package org.dlewandowski.webserver.response;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.dlewandowski.webserver.request.Request;

public class Response {

	private final String httpVersion;

	private final OutputStream outputStream;

	private final Map<String, String> headers;

	private ResponseCode responseCode;

	private boolean committed;

	public Response(String httpVersion, OutputStream outputStream) {
		this.httpVersion = httpVersion;
		this.outputStream = outputStream;
		this.headers = new HashMap<>();
		this.committed = false;
	}

	public static Response from(Socket socket, Request request) throws IOException {
		return new Response(request.getRequestInfo().getHttpVersion(), socket.getOutputStream());
	}

	public void setResponseCode(ResponseCode responseCode) {
		this.responseCode = responseCode;
	}

	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	public OutputStream getOutputStream() {
		if (!committed) {
			commit();
		}
		return outputStream;
	}

	public void commit() {
		if (committed) {
			return;
		}
		committed = true;
		final PrintWriter writer = new PrintWriter(outputStream);
		writer.println(String.format("%s %d %s", httpVersion, responseCode.getResponseCode(), responseCode.getMessage()));
		for (Map.Entry<String, String> header : headers.entrySet()) {
			writer.println(header.getKey() + ": " + header.getValue());
		}
		writer.println();
		writer.flush();
	}

	public void flush() throws IOException {
		commit();
		outputStream.flush();
	}
}
