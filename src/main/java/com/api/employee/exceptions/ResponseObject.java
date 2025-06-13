package com.api.employee.exceptions;

public class ResponseObject {
	
	private String statusMessage;
	private int statusCode;
	private ErrorObject errorResponse;
	private long serverResponseTime;
	private Object data;
	
	public String getStatusMessage() {
		return statusMessage;
	}
	public void setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
	}
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public ErrorObject getErrorResponse() {
		return errorResponse;
	}
	public void setErrorResponse(ErrorObject errorResponse) {
		this.errorResponse = errorResponse;
	}
	public long getServerResponseTime() {
		return serverResponseTime;
	}
	public void setServerResponseTime(long serverResponseTime) {
		this.serverResponseTime = serverResponseTime;
	}
	
	
	public Object getData() {
	    return data;
	}

	public void setData(Object data) {
	    this.data = data;
	}

}
