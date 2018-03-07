package com.navinfo.opentsp.dongfeng.common.exception;

public class BaseServiceRuntimeException extends BaseRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseServiceRuntimeException() {
		super();
	}
	
	public BaseServiceRuntimeException(String message) {
		super(message);
	}
	
	public BaseServiceRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public BaseServiceRuntimeException(String message , Throwable cause) {
		super(message , cause);
	}

}
