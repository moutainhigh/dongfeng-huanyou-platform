package com.navinfo.dongfeng.terminal.comm.common;


/**
 * ID生成工厂
 * 
 * @author aerozh-lgw
 * 
 */
public final class IDFactory {
	private final static SerialNumber serialNumber = new SerialNumber();
//	private final static AuthenticationCode authenticationCode = new AuthenticationCode();
	private final static UniqueMark UNIQUE_MARK = new UniqueMark();
	/**
	 * 生成一个ID
	 * 
	 * @param idType
	 *            {@link IDType}
	 * @return Object
	 */
	public static Object builderID(IDType idType) {
		if (idType.ordinal() == IDType.SerialNumber.ordinal()) {
			return serialNumber.next();
//		} else if (idType.ordinal() == IDType.AuthenticationCode.ordinal()) {
//			return authenticationCode.next();
		} else if(idType.ordinal() == IDType.UniqueMark.ordinal()) {
			return UNIQUE_MARK.next();
		} else {
			return null;
		}
	}

	/**
	 * ID类型
	 * 
	 * @author aerozh-lgw
	 * 
	 */
	public static enum IDType {
		/** 流水号 */
		SerialNumber,
		/** 鉴权码 */
//		AuthenticationCode,
		UniqueMark
	}
}
