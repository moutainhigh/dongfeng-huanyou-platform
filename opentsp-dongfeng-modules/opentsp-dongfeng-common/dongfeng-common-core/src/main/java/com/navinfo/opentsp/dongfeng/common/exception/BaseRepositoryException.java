package com.navinfo.opentsp.dongfeng.common.exception;

public class BaseRepositoryException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseRepositoryException() {
		super();
	}
	
	public BaseRepositoryException(String message) {
		super(message);
	}
	
	public BaseRepositoryException(Throwable cause) {
		super(cause);
	}
	
	public BaseRepositoryException(String message , Throwable cause) {
		super(message , cause);
	}

}
