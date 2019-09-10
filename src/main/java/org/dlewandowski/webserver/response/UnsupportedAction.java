package org.dlewandowski.webserver.response;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.dlewandowski.webserver.Action;

class UnsupportedAction implements Action {

	@Override
	public void sendResponse(OutputStream stream) {
		PrintWriter out = new PrintWriter(stream, true);
		out.println("HTTP/1.1 501");
	}
}
