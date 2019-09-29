package org.dlewandowski.webserver.processor;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;
import org.junit.jupiter.api.Test;

class UnsupportedRequestProcessorTest {

	@Test
	public void testResponseStatusCode() {
		Response response = mock(Response.class);
		UnsupportedRequestProcessor tester = new UnsupportedRequestProcessor(response);

		tester.process();

		verify(response, times(1)).setResponseStatus(ResponseStatus.NOT_IMPLEMENTED_OPERATION);
	}

}