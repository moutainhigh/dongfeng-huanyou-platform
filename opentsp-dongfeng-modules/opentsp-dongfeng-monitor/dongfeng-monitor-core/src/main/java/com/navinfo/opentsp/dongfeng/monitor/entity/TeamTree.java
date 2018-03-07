package com.navinfo.opentsp.dongfeng.monitor.entity;


public class TeamTree extends RootTree {

	private Long online;
    private Long total;
	private Integer type;

	public TeamTree() {
		this.setOpen(true);
		this.setIsParent(true);
		this.setChecked(false);
		this.setOnline(0L);
		this.setTotal(0L);
		this.setpId("0");
		this.setType(0);
	}

	public Long getOnline() {
		return online;
	}
	public void setOnline(Long online) {
		this.online = online;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
