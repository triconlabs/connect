package com.tricon.labs.join.exceptions;


public class MessageException extends ApplicationException {
	
	public static enum MessageExceptionType {
		TWILIO
	}
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4587234721607063548L;

	/**
	 * 
	 */
	/*public MessageException() {
		super(ExceptionType.MSG_SENDING);
	}*/

	/**
	 * 
	 * @param message
	 */
	public MessageException(String message) {
		super(ExceptionType.MSG_SENDING, message, null);
	}

	/**
	 * 
	 * @param type
	 * @param e
	 */
	public MessageException(MessageExceptionType type, Throwable e) {
		super(ExceptionType.MSG_SENDING, new PreserveError(getErrorCode(type), getErrorMessage()));
	}

	/**
	 * 
	 * @return
	 */
	private static String getErrorMessage() {
		return PreserveError.getErrorMessage(ExceptionType.MSG_SENDING);
	}

	/**
	 * 
	 * @param type
	 * @return
	 */
	private static String getErrorCode(MessageExceptionType type) {
		return PreserveError.getErrorCode(ExceptionType.MSG_SENDING) + "_" + type;
	}

}
