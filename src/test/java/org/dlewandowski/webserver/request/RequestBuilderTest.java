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
class RequestBuilderTest {

	@Mock
	private Socket socket;

	@Test
	public void testRequestCreation() throws IOException {
		try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("org/dlewandowski/webserver/request/request_data.txt")) {
			when(socket.getInputStream()).thenReturn(inputStream);

			RequestBuilder tester = new RequestBuilder();
			Request request = tester.from(socket);

			assertEquals("GET", request.getRequestInfo().getMethod());
			assertEquals("/index.html", request.getRequestInfo().getResourcePath());
			assertEquals("HTTP/1.1", request.getRequestInfo().getHttpVersion());
		}
	}

}