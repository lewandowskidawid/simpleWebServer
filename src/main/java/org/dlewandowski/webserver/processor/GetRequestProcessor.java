package org.dlewandowski.webserver.processor;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Date;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.resources.ResourceProvider;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseStatus;

class GetRequestProcessor {

	private static final byte[] DOES_NOT_EXIStS = "Requested resource dost not exist".getBytes();

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
				sendForbiddenOperationResponse();
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

	private String getFileMimeType(File requestedFile) {
		InputStream inputStream = GetRequestProcessor.class.getClassLoader().getResourceAsStream("mimetypes.default");
		return new MimetypesFileTypeMap(inputStream).getContentType(requestedFile);
	}

	private void sendNonExistingResourceResponse() throws IOException {
		response.setResponseStatus(ResponseStatus.NON_EXISTING_RESOURCE);
		response.addHeader("Date", new Date());
		response.addHeader("Content-Type", "text/plain");
		response.getOutputStream().write(DOES_NOT_EXIStS);
	}

	private void sendForbiddenOperationResponse() {
		response.setResponseStatus(ResponseStatus.FORBIDDEN);
		response.addHeader("Date", new Date());
	}

	private File getRequestedResource() {
		ResourceProvider resourceProvider = new ResourceProvider(directoryPath, request.getRequestInfo().getResourcePath());
		return resourceProvider.getResource();
	}

}
