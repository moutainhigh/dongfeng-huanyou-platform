package com.navinfo.opentsp.dongfeng.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Log log = LogFactory.getLog(BaseException.class);
	
	/**
	 * 异常的根类对象
	 */
	private Throwable rootCause;
	private String message;
	
	public BaseException() {
		super();
	}
	
	public BaseException(String messgage) {
		super(messgage , null);
		this.message = messgage; 
		rootCause = this;
	}
	
	public BaseException(Throwable cause) {
		super("",cause);
		this.message = "";
	}
	
	public BaseException(String message , Throwable cause) {
		super(message , cause);
		if (cause instanceof BaseException) {
			rootCause = ((BaseException)cause).rootCause;
		} else {
			rootCause = cause;
		}
		if (log.isErrorEnabled()) {
			log.error(message, cause);
		}
		
		this.message = message;
	}
	
	
	
	public String getExceptionMessage() {
		return this.message;
	}
	
	public Throwable getRootCause() {
		return rootCause;
	}
}
