package com.navinfo.opentsp.dongfeng.common.exception;

public class BaseServiceException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseServiceException() {
		super();
	}
	
	public BaseServiceException(String message) {
		super(message);
	}
	
	public BaseServiceException(Throwable cause) {
		super(cause);
	}
	
	public BaseServiceException(String message , Throwable cause) {
		super(message , cause);
	}
}
