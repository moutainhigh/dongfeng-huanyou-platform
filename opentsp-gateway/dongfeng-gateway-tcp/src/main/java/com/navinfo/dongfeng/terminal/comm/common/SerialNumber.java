package com.navinfo.dongfeng.terminal.comm.common;

/**
 * 生成流水号
 * 
 * @author aerozh-lgw
 * @see {@link ID}
 */
class SerialNumber implements ID {
	private static volatile long id = 0;

	@Override
	public Object next() {
		String temp = "0000";
		String hex = Long.toHexString(id++);
		if (id >= 65535L)
			id = 0;
		return (temp.substring(0, 4 - hex.length()) + hex).toUpperCase();
	}

}
