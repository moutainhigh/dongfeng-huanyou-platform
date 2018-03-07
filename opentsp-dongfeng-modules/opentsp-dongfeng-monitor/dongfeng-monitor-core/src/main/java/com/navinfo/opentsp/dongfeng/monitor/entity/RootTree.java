package com.navinfo.opentsp.dongfeng.monitor.entity;

import java.math.BigInteger;

public class RootTree {
	
	private String id;
	private BigInteger did;
	private String pId;
	private String name;
	private Boolean open;
    private Boolean isParent;
    private Boolean checked;

	public RootTree() {
		this.open = true;
		this.isParent = true;
		this.checked = false;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public BigInteger getDid() {
		return did;
	}

	public void setDid(BigInteger did) {
		this.did = did;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getOpen() {
		return open;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
    
}
