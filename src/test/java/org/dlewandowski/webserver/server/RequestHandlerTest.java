package org.dlewandowski.webserver.server;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.dlewandowski.webserver.processor.RequestProcessor;
import org.dlewandowski.webserver.processor.RequestProcessorProvider;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.request.RequestBuilder;
import org.dlewandowski.webserver.request.RequestInfo;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseBuilder;
import org.dlewandowski.webserver.response.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.omg.CORBA.portable.InputStream;

class RequestHandlerTest {

	@Test
	public void testIfSocketIsClosed() throws IOException {
		Socket socket = mock(Socket.class);
		when(socket.getInputStream()).thenReturn(mock(InputStream.class));
		when(socket.getOutputStream()).thenReturn(mock(OutputStream.class));
		when(socket.isClosed()).thenReturn(false);
		RequestBuilder requestBuilder = mock(RequestBuilder.class);
		Request request = mock(Request.class);
		when(requestBuilder.from(socket)).thenReturn(request);
		when(request.getRequestInfo()).thenReturn(mock(RequestInfo.class));
		ResponseBuilder responseBuilder = mock(ResponseBuilder.class);
		Response response = mock(Response.class);
		when((responseBuilder.from(socket, request))).thenReturn(response);
		when(response.getResponseStatus()).thenReturn(ResponseStatus.SUCCESS);
		RequestProcessorProvider requestProcessorProvider = mock(RequestProcessorProvider.class);
		when(requestProcessorProvider.get(request, response, "/")).thenReturn(mock(RequestProcessor.class));
		RequestHandler tester = new RequestHandler(socket, "/", requestBuilder, responseBuilder, requestProcessorProvider);

		tester.run();

		verify(socket).close();
	}

	@Test
	public void testIfSocketIsClosedWhenException() throws IOException {
		Socket socket = mock(Socket.class);
		RequestHandler tester = new RequestHandler(socket, "/", null, null, null);

		try {
			tester.run();
		} catch (RuntimeException e) {
			verify(socket).close();
		}

	}

}