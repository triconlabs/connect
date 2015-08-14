package com.tricon.labs.join.exceptions;

public class ValidationException extends ApplicationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9003096730679870261L;

	/**
	 * 
	 * @param message
	 */
	public ValidationException(String message) {
		super(ExceptionType.DATA_VALIDATION, message, null);
	}

	public ValidationException(String message, Throwable e) {
		super(ExceptionType.DATA_VALIDATION, message, e);
	}
}
