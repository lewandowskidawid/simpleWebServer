package org.dlewandowski.webserver.response;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.dlewandowski.webserver.Action;

class GetAction implements Action {

	@Override
	public void sendResponse(OutputStream stream) {
		String message = "<TITLE>Hello!</TITLE><p>Hello world</p>";
		PrintWriter out = new PrintWriter(stream, true);
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		out.println("HTTP/1.1 200 OK");
		out.println("Content-Type: text/html");
		out.println("Date: "+dateFormat.format(date));
		int len = message.length();
		out.println("Content-Length: " + len);
		out.println(""); // The content starts afters this empty line
		out.println(message);
	}
}
