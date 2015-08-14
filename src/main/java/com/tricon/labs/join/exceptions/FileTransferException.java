package com.tricon.labs.join.exceptions;

public class FileTransferException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9003096730679870261L;

	/**
	 * 
	 */
	/*public FileTransferException() {
		super(ExceptionType.FILE_TRANSFER);
	}
*/
	/**
	 * 
	 * @param message
	 */
	public FileTransferException(String message) {
		super(ExceptionType.FILE_TRANSFER, message, null);
	}

	public FileTransferException(String message, Throwable e) {
		super(ExceptionType.FILE_TRANSFER, message, e);
	}

}
