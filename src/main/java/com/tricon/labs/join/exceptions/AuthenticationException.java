package com.tricon.labs.join.exceptions;


public class AuthenticationException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5591091852567082131L;

	public AuthenticationException(String message) {
		super(ExceptionType.AUTHENTICATION, message, null);
	}

}
