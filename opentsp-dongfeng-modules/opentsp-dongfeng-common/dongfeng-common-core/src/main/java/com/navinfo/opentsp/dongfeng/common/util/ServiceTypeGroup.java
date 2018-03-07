package com.navinfo.opentsp.dongfeng.common.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务，配件，提升工具明细
 * 姜玉 2016-9-13 14:47:48
 */
public class ServiceTypeGroup {
    private int id;
    private String name;
    private List<ServiceTypeItem> items = new ArrayList<ServiceTypeItem>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ServiceTypeItem> getItems() {
		return items;
	}

	public void setItems(List<ServiceTypeItem> items) {
		this.items = items;
	}
}

