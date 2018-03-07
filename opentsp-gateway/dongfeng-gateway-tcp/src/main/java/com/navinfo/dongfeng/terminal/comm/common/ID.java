package com.navinfo.dongfeng.terminal.comm.common;

/**
 * ID生成器接口
 * 
 * @author aerozh-lgw
 * 
 */
interface ID {
	/**
	 * 获取下一个ID
	 * 
	 * @return Object
	 */
	abstract Object next();
}
