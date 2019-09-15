package org.dlewandowski.webserver.response;

public enum ResponseCode {
	SUCCESS(200, "OK"),
	NON_EXISTING_RESOURCE(404, "Not Found"),
	NOT_IMPLEMENTED_OPERATION(501, "Not Implemented");

	private final int responseCode;

	private final String message;

	ResponseCode(int responseCode, String message){
		this.responseCode = responseCode;
		this.message = message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public String getMessage() {
		return message;
	}
}
