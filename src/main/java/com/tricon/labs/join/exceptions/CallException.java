package com.tricon.labs.join.exceptions;


public class CallException extends ApplicationException {
	
	public static enum CallExceptionType {
		TWILIO
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4587234721607063548L;

	/**
	 * 
	 */
	/*public CallException() {
		super(ExceptionType.MSG_SENDING);
	}*/

	/**
	 * 
	 * @param message
	 */
	public CallException(String message) {
		super(ExceptionType.PHONE_CALL, message, null);
	}

	/**
	 * 
	 * @param type
	 * @param e
	 */
	public CallException(CallExceptionType type, Throwable e) {
		super(ExceptionType.PHONE_CALL, new PreserveError(getErrorCode(type), getErrorMessage()));
	}

	/**
	 * 
	 * @return
	 */
	private static String getErrorMessage() {
		return PreserveError.getErrorMessage(ExceptionType.PHONE_CALL);
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static String getErrorCode(CallExceptionType type) {
		return PreserveError.getErrorCode(ExceptionType.PHONE_CALL) + "_" + type;
	}

}
