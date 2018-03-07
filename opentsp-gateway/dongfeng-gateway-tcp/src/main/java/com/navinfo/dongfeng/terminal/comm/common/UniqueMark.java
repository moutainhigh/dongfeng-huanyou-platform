package com.navinfo.dongfeng.terminal.comm.common;

import java.util.UUID;

public class UniqueMark implements ID{
	
	@Override
	public Object next() {
		return UUID.randomUUID().toString();
	}

}
