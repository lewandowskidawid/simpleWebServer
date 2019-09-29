package org.dlewandowski.webserver.processor;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.request.RequestInfo;
import org.dlewandowski.webserver.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class RequestProcessorProviderTest {

	@Test
	public void testGetRequestType() {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.getRequestInfo()).thenReturn(new RequestInfo("GET", "/", "HTTP/1.1"));
		RequestProcessorProvider tester = new RequestProcessorProvider();

		RequestProcessor result = tester.get(request, response, "/");

		assertTrue(result instanceof GetRequestProcessor);
	}

	@ParameterizedTest
	@ValueSource(strings = { "POST", "PUT", "DELETE", "PATCH", "OPTIONS", "HEAD", "" })
	public void testOtherRequestTypes(String method) {
		Request request = mock(Request.class);
		Response response = mock(Response.class);
		when(request.getRequestInfo()).thenReturn(new RequestInfo(method, "/", "HTTP/1.1"));
		RequestProcessorProvider tester = new RequestProcessorProvider();

		RequestProcessor result = tester.get(request, response, "/");

		assertTrue(result instanceof UnsupportedRequestProcessor);
	}

}