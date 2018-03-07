package com.navinfo.opentsp.dongfeng.common.exception;

public class BaseRepositoryRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseRepositoryRuntimeException() {
		super();
	}
	
	public BaseRepositoryRuntimeException(String message) {
		super(message);
	}
	
	public BaseRepositoryRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public BaseRepositoryRuntimeException(String message , Throwable cause) {
		super(message , cause);
	}

}
