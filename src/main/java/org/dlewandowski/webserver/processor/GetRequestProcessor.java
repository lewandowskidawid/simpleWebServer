package org.dlewandowski.webserver.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;

class GetRequestProcessor {

	private static final String QUERY_STRING_MARK = "?";

	private final Request request;

	private final Response response;

	private final String directoryPath;

	public GetRequestProcessor(Request request, Response response, String directoryPath) {
		this.request = request;
		this.response = response;
		this.directoryPath = directoryPath;
	}

	public void processRequest() throws IOException {
		File requestedFile = getRequestedResource();
		if (requestedFile.exists() && requestedFile.canRead()) {
			if (requestedFile.isDirectory()) {
				sendNotImplementedOperationResponse();
			} else {
				sendRequestedFile(requestedFile);
			}
		} else {
			sendNonExistingResourceResponse();
		}
	}

	private void sendRequestedFile(File requestedFile) throws IOException {
		String mimeType = getFileMimeType(requestedFile);
		response.setResponseStatus(ResponseStatus.SUCCESS);
		response.addHeader("Date", new Date());
		response.addHeader("Content-Length", String.valueOf(requestedFile.length()));
		if (StringUtils.isNotEmpty(mimeType)) {
			response.addHeader("Content-Type", mimeType);
		}
		Files.copy(requestedFile.toPath(), response.getOutputStream());
	}

	private String getFileMimeType(File requestedFile) throws IOException {
		return Files.probeContentType(requestedFile.toPath());
	}

	private void sendNonExistingResourceResponse() {
		response.setResponseStatus(ResponseStatus.NON_EXISTING_RESOURCE);
		response.addHeader("Date", new Date());
	}

	private void sendNotImplementedOperationResponse() {
		response.setResponseStatus(ResponseStatus.NOT_IMPLEMENTED_OPERATION);
		response.addHeader("Date", new Date());
	}

	private File getRequestedResource() {
		String url = StringUtils.substringBefore(request.getRequestInfo().getUri(), QUERY_STRING_MARK);
		File result = new File(directoryPath + url);
		if (result.isDirectory()) {
			result = result.toPath().resolve("index.html").toFile();

		}
		return result;
	}

}
