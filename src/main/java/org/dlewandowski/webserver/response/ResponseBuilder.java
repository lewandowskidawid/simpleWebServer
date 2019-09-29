package org.dlewandowski.webserver.response;

import java.net.Socket;

import org.dlewandowski.webserver.request.Request;

/**
 * Allows to create {@link Response} object
 */
public class ResponseBuilder {

	public Response from(Socket socket, Request request) {
		return new Response(request.getRequestInfo().getHttpVersion(), socket);
	}
}
