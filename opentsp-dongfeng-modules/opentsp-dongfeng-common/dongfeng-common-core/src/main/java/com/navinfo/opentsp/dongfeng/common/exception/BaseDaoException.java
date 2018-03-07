package com.navinfo.opentsp.dongfeng.common.exception;

public class BaseDaoException extends BaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public BaseDaoException() {
		super();
	}
	
	public BaseDaoException(String message) {
		super(message);
	}
	
	public BaseDaoException(Throwable cause) {
		super(cause);
	}
	
	public BaseDaoException(String message , Throwable cause) {
		super(message , cause);
	}

}
