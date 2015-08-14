package com.tricon.labs.join.exceptions;

public class PropertyNotFoundException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9003096730679870261L;

	/**
	 * 
	 */
	/*public PropertyNotFoundException() {
		super(ExceptionType.APPLICATION_PROPERTY);
	}*/

	/**
	 * 
	 * @param message
	 */
	public PropertyNotFoundException(String message) {
		super(ExceptionType.APPLICATION_PROPERTY, message, null);
	}

	public PropertyNotFoundException(String message, Throwable e) {
		super(ExceptionType.APPLICATION_PROPERTY, message, e);
	}

}
