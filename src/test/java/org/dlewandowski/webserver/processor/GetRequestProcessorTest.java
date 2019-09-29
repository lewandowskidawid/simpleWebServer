package org.dlewandowski.webserver.processor;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.request.RequestInfo;
import org.dlewandowski.webserver.resources.ResourceProvider;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetRequestProcessorTest {

	@Mock
	private Response response;

	@Mock
	private Request request;

	@Mock
	private ResourceProvider resourceProvider;

	@Test
	public void testWhenFileExists() throws IOException {
		when(request.getRequestInfo()).thenReturn(new RequestInfo("GET", "/index.html", "HTTP/1.1"));
		when(resourceProvider.getResource(anyString())).thenReturn(new File("./src/test/resources/index.html"));
		when(response.getOutputStream()).thenReturn(mock(OutputStream.class));

		GetRequestProcessor tester = new GetRequestProcessor(request, response, resourceProvider);
		tester.process();

		verify(response, times(1)).setResponseStatus(ResponseStatus.SUCCESS);
	}

	@Test
	public void testWhenFileDoesNotExists() throws IOException {
		when(request.getRequestInfo()).thenReturn(new RequestInfo("GET", "/nonExisting.html", "HTTP/1.1"));
		when(resourceProvider.getResource(anyString())).thenReturn(new File("./src/test/resources/nonExisting"));
		when(response.getOutputStream()).thenReturn(mock(OutputStream.class));

		GetRequestProcessor tester = new GetRequestProcessor(request, response, resourceProvider);
		tester.process();

		verify(response, times(1)).setResponseStatus(ResponseStatus.NON_EXISTING_RESOURCE);
	}

	@Test
	public void testWhenFileIsDirectory() throws IOException {
		when(request.getRequestInfo()).thenReturn(new RequestInfo("GET", "/nonExisting.html", "HTTP/1.1"));
		when(resourceProvider.getResource(anyString())).thenReturn(new File("./src/test/resources/js"));

		GetRequestProcessor tester = new GetRequestProcessor(request, response, resourceProvider);
		tester.process();

		verify(response, times(1)).setResponseStatus(ResponseStatus.FORBIDDEN);
	}

}