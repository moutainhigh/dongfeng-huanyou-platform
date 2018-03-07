package com.navinfo.opentsp.dongfeng.common.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseRuntimeException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Log log = LogFactory.getLog(BaseRuntimeException.class);
	
	private Throwable rootCause;
	
	public BaseRuntimeException() {
		super();
	}
	
	public BaseRuntimeException(String message) {
		super(message, null);
		rootCause = this;
	}
	
	public BaseRuntimeException(Throwable cause) {
		super("", cause);
	}
	
	public BaseRuntimeException(String message , Throwable cause) {
		super(message , cause);
		
		if (cause instanceof BaseRuntimeException) {
			rootCause = ((BaseRuntimeException)cause).rootCause;
		} else {
			rootCause = cause;
		}
		
		if (log.isErrorEnabled()) {
			log.error(message, cause);
		}
		
	}
	
	public Throwable getRootThrowable() {
		return rootCause;
	}

}
