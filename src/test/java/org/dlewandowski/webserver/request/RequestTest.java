package org.dlewandowski.webserver.request;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RequestTest {

	@Mock
	private Socket socket;

	@Test
	public void createRequestObject() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("org/dlewandowski/webserver/request/request_data.txt")) {
			when(socket.getInputStream()).thenReturn(inputStream);

			Request tester = Request.from(socket);

			assertEquals("GET", tester.getRequestInfo().getMethod());
			assertEquals("/index.html", tester.getRequestInfo().getResourcePath());
			assertEquals("HTTP/1.1", tester.getRequestInfo().getHttpVersion());
		}
	}
}