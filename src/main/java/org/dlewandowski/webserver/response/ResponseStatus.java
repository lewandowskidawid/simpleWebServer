package org.dlewandowski.webserver.response;

public enum ResponseStatus {
	SUCCESS(200, "OK"),
	NON_EXISTING_RESOURCE(404, "Not Found"),
	NOT_IMPLEMENTED_OPERATION(501, "Not Implemented");

	private final int code;

	private final String message;

	ResponseStatus(int responseCode, String message) {
		this.code = responseCode;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
