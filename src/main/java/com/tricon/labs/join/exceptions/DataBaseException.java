package com.tricon.labs.join.exceptions;


public class DataBaseException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9003096730679870261L;

	/**
	 * 
	 */
	/*public DataBaseException() {
		super(ExceptionType.DATABASE);
	}*/

	/**
	 * 
	 * @param message
	 */
	public DataBaseException(String message) {
		super(ExceptionType.DATABASE, message, null);
	}

	public DataBaseException(String message, Throwable e) {
		super(ExceptionType.DATABASE, message, e);
	}

}
