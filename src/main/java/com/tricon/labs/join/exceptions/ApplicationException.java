package com.tricon.labs.join.exceptions;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tricon.labs.join.util.ErrorUtil;
import com.tricon.labs.join.util.GenUtil;


/**
 * 
 * @author shailesh
 *
 */
public class ApplicationException extends Exception {
	
	private static Logger LOGGER = LoggerFactory.getLogger(ApplicationException.class);
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5363087106424062159L;
	
	/**
	 * 
	 */
	private final ExceptionType type;
	
	/**
	 * 
	 */
	protected PreserveError error;
	
	/**
	 * 
	 */
	private int responseCode;
	
	public ApplicationException(int respnseCode, Throwable cause) {
		super(cause);
		this.type = getResponseType(respnseCode);
		this.error = new PreserveError(type.toString(), cause == null ? null : cause.getMessage());
		this.responseCode = respnseCode;
		LOGGER.error("type - {}, errorCode - {}, errorMessage - {}", this.type, this.error.getErrorCode(), this.error.getErrorMessage());
	}
	
	/**
	 * 
	 */
	public ApplicationException(int respnseCode, String errorCode, String message) {
		super(message);
		this.type = getResponseType(respnseCode);
		this.error = new PreserveError(errorCode, message);
		this.responseCode = respnseCode;
		LOGGER.error("type - {}, errorCode - {}, errorMessage - {}", this.type, this.error.getErrorCode(), this.error.getErrorMessage());
	}
	
	/**
	 * To be used in case of abstraction for detailed error stack
	 * @param text
	 * @param cause
	 */
	public ApplicationException(ExceptionType type, PreserveError error) {
		super(error.getErrorMessage());
		this.type = type;
		this.error = error;
		this.responseCode = getResponseCode(type);
		LOGGER.error("type - {}, errorCode - {}, errorMessage - {}", this.type, error.getErrorCode(), error.getErrorMessage());
		LOGGER.error(GenUtil.getJsonFromObject(error));
	}

	/**
	 * @param message
	 * @param cause
	 */
	public ApplicationException(ExceptionType type, String message, Throwable cause) {
		super(cause);
		if (message == null && cause != null)
			message = cause.getMessage();
		this.type = type;
		this.error = new PreserveError(type.toString(), message);
		this.responseCode = getResponseCode(type);
		LOGGER.error("type - {}, errorCode - {}, errorMessage - {}", this.type, this.error.getErrorCode(), message);
		if (cause != null)
			LOGGER.error(ErrorUtil.stackTraceString(cause.getStackTrace()));
	}
	
	public PreserveError getError() {
		return error;
	}

	public void setError(PreserveError error) {
		this.error = error;
	}

	public ExceptionType getType() {
		return type;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
	
	public static int getResponseCode(ExceptionType type) {
		switch (type) {
		case AUTHENTICATION:
			return HttpServletResponse.SC_FORBIDDEN;
		case AUTHORISATION:
			return HttpServletResponse.SC_FORBIDDEN;
		case DATA_VALIDATION:
			return HttpServletResponse.SC_BAD_REQUEST;
		case DATABASE:
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		default:
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
	}
	
	public static ExceptionType getResponseType(int code) {
		switch (code) {
		case HttpServletResponse.SC_FORBIDDEN:
			return ExceptionType.AUTHENTICATION;
		case HttpServletResponse.SC_INTERNAL_SERVER_ERROR:
			return ExceptionType.INTERNAL_SERVER;
		case HttpServletResponse.SC_NOT_FOUND:
			return ExceptionType.NOT_FOUND;
		default:
			return ExceptionType.INTERNAL_SERVER;
		}
	}
}
