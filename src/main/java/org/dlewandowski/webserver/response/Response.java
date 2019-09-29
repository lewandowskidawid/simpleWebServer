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

/**
 * Allows to send HTTP response to {@link Socket}
 */
public class Response {

	private final String httpVersion;

	private final Socket socket;

	private final Map<String, String> headers;

	private final DateFormat dateFormat;

	private ResponseStatus responseStatus;

	private boolean committed;

	Response(String httpVersion, Socket socket) {
		this.httpVersion = httpVersion;
		this.socket = socket;
		this.headers = new LinkedHashMap<>();
		this.dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		this.committed = false;
	}

	/**
	 * Sets response status which is send in the response header
	 *
	 * @param responseStatus status to set
	 */
	public void setResponseStatus(ResponseStatus responseStatus) {
		this.responseStatus = responseStatus;
	}

	/**
	 * Add custom header to the response
	 *
	 * @param key   name of the header
	 * @param value value of the header
	 */
	public void addHeader(String key, String value) {
		headers.put(key, value);
	}

	/**
	 * Add custom header to the response.
	 *
	 * @param key   name of the header
	 * @param value value of the header. The value is converted to yyyy/MM/dd HH:mm:ss format
	 */
	public void addHeader(String key, Date value) {
		headers.put(key, dateFormat.format(value));
	}

	/**
	 * When method is called first time then all already provided headers are being sent to {@link Socket}.
	 *
	 * @return {@link OutputStream} object to manipulate response message
	 * @throws IOException when headers cannot be commited to the {@link Socket} or {@link OutputStream} cannot be provided
	 */
	public OutputStream getOutputStream() throws IOException {
		if (!committed) {
			commit();
		}
		return socket.getOutputStream();
	}

	/**
	 * Flushes all content to the {@link Socket}
	 *
	 * @throws IOException when flush operation fails
	 */
	public void flush() throws IOException {
		getOutputStream().flush();
	}

	/**
	 * Allows to retrieve currently configured {@link ResponseStatus}
	 *
	 * @return set {@link ResponseStatus} object
	 */
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