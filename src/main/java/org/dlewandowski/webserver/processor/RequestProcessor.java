package org.dlewandowski.webserver.processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.dlewandowski.webserver.request.Request;
import org.dlewandowski.webserver.response.Response;
import org.dlewandowski.webserver.response.ResponseCode;

public class RequestProcessor {

	private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	private final Request request;

	private final Response response;

	private final String directoryPath;

	public RequestProcessor(Request request, Response response, String directoryPath) {
		this.request = request;
		this.response = response;
		this.directoryPath = directoryPath;
	}

	public void processRequest() {
		if ("GET".equals(request.getRequestInfo().getMethod())) {
			File requestedFile = getRequestedFile();
			if (requestedFile.exists()) {
				if (requestedFile.isDirectory()) {
					sendNotImplementedOperationResponse();
				} else {
					sendRequestedFile(requestedFile);
				}
			} else {
				sendNonExistingResourceResponse();
			}
		} else {
			sendNotImplementedOperationResponse();
		}
	}

	private void sendRequestedFile(File requestedFile) {
		String mimeType = getFileMimeType(requestedFile);
		response.setResponseCode(ResponseCode.SUCCESS);
		response.addHeader("Date", DATE_FORMAT.format(new Date()));
		response.addHeader("Content-Length",  String.valueOf(requestedFile.length()));
		if (StringUtils.isNotEmpty(mimeType)) {
			response.addHeader("Content-Type",  mimeType);
		}
		try {
			Files.copy(requestedFile.toPath(), response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getFileMimeType(File requestedFile){
		String result = StringUtils.EMPTY;
		try {
			result = Files.probeContentType(requestedFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	private void sendNonExistingResourceResponse() {
		response.setResponseCode(ResponseCode.NON_EXISTING_RESOURCE);
		response.addHeader("Date", DATE_FORMAT.format(new Date()));
	}

	private void sendNotImplementedOperationResponse() {
		response.setResponseCode(ResponseCode.NOT_IMPLEMENTED_OPERATION);
		response.addHeader("Date", DATE_FORMAT.format(new Date()));
	}

	private File getRequestedFile() {
		return new File(directoryPath + request.getRequestInfo().getUri());
	}

}
