package org.dlewandowski.webserver.processor;

import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.resources.ResourceProvider;
import org.dlewandowski.webserver.response.Response;

/**
 * Provides proper {@link RequestProcessor} depends on request HTTP method
 */
public class RequestProcessorProvider {

	/**
	 * The method calculates proper {@link RequestProcessor}.
	 *
	 * @param request       the parameters is
	 * @param response
	 * @param directoryPath
	 * @return
	 */
	public RequestProcessor get(Request request, Response response, String directoryPath) {
		RequestProcessor result;
		if (isGetRequest(request)) {
			result = new GetRequestProcessor(request, response, new ResourceProvider(directoryPath));
		} else {
			result = new UnsupportedRequestProcessor(response);
		}
		return result;
	}

	private boolean isGetRequest(Request request) {
		return "GET".equals(request.getRequestInfo().getMethod());
	}

}
