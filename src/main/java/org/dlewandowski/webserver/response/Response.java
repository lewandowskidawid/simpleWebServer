package org.dlewandowski.webserver.response;

import java.io.OutputStream;

import org.dlewandowski.webserver.Action;
import org.dlewandowski.webserver.request.Request;

public class Response {

	private final Action action;

	private Response(Action action) {
		this.action = action;
	}

	public static Response from(Request request) {
		Action action = ActionFactory.calculateActionType(request.getMethod());
		return new Response(action);
	}

	public void sendResponse(OutputStream stream) {
		action.sendResponse(stream);
	}
}
