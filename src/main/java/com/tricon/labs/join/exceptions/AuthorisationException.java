package com.tricon.labs.join.exceptions;


public class AuthorisationException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8357229569693568963L;

	/*public AuthorisationException() {
		super(ExceptionType.AUTHORISATION);
	}
*/
	public AuthorisationException(String message) {
		super(ExceptionType.AUTHORISATION, message, null);
	}

}
