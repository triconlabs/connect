package com.tricon.labs.join.exceptions;


public class PreserveError {
	
	private String errorCode;
	
	private String errorMessage;
	
	public PreserveError(String code, String message) {
		this.errorCode = code;
		this.errorMessage = message;
	}
	
	public static String getErrorCode(ExceptionType type) {
		switch (type) {
		case AUTHENTICATION:
			return "AUTHENTICATION_ERROR";
		case AUTHORISATION:
			return "AUTHORISATION_ERROR";
		case DATABASE:
			return "DATABASSE_ERROR";
		case USER_REGISTRATION:
			return "USER_REGISTRATION_ERROR";
		case DATA_VALIDATION:
			return "VALIDATION_ERROR";
		case MSG_SENDING:
			return "MESSAGE_SENDING_ERROR";
		default:
			return "UNKNOWN_ERROR";
		}
	}
	
	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public static String getErrorMessage(ExceptionType type) {
		switch (type) {
		case AUTHENTICATION:
			return "unknown user";
		case AUTHORISATION:
			return "user not allowed";
		case DATABASE:
			return "database not responding";
		case MSG_SENDING:
			
		default:
			return "some error happened";
		}
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
