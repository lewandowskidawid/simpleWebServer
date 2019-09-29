package org.dlewandowski.webserver.response;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;
import java.nio.CharBuffer;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.request.RequestInfo;
import org.junit.jupiter.api.Test;

class ResponseTest {

	@Test
	public void testResponseOutput() throws IOException {
		PipedInputStream pipeInput = new PipedInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(pipeInput));
		BufferedOutputStream out = new BufferedOutputStream(new PipedOutputStream(pipeInput));
		Socket socket = mock(Socket.class);
		Request request = mock(Request.class);

		when(socket.getOutputStream()).thenReturn(out);
		when(request.getRequestInfo()).thenReturn(new RequestInfo("GET", "/", "HTTP/1.1"));

		ResponseBuilder responseBuilder = new ResponseBuilder();
		Response tester = responseBuilder.from(socket, request);

		tester.setResponseStatus(ResponseStatus.SUCCESS);
		tester.addHeader("HeaderName1", "HeaderValue1");
		tester.addHeader("HeaderName2", new GregorianCalendar(2019, 6, 3, 12, 51, 32).getTime());
		tester.getOutputStream().write("This is a test message".getBytes());
		tester.flush();

		assertEquals("HTTP/1.1 200 OK", reader.readLine());
		assertEquals("HeaderName1: HeaderValue1", reader.readLine());
		assertEquals("HeaderName2: 2019/07/03 12:51:32", reader.readLine());
		assertEquals(StringUtils.EMPTY, reader.readLine());
		CharBuffer message = CharBuffer.allocate(1024);
		int value = reader.read(message);
		message.rewind();
		assertEquals("This is a test message", message.toString().substring(0, value));
	}

}