package com.navinfo.opentsp.dongfeng.system.constant;

public enum CarStorageState {
	
	NOT_EMPTY(0) , EMPTY(1);
	
	private int state;
	
	private CarStorageState(int state) {
		this.state = state;
	}
	
	public int getState() {
		return this.state;
	}
}
