package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.pojo.HyBasicdataPojo;

import java.util.ArrayList;
import java.util.List;


/**
 * @author wenya
 *
 */
public class ServiceTypeCvt {

	/**
	 * 获取服务类型相关明细
	 * 
	 * @param id
	 * @param name
	 * @param content
	 * @param basiccontent
	 * @return
	 */
	public static ServiceTypeGroup transSPTContent(int id, String name, String content,
			List<HyBasicdataPojo> basiccontent) {
		// 非大运平台去掉金融用户类型
		ServiceTypeGroup group = new ServiceTypeGroup();
		String[] check = content == null ? null : content.split(",");
		group.setId(id);
		group.setName(name);
		List<ServiceTypeItem> items = new ArrayList<ServiceTypeItem>();
		if (check != null && basiccontent != null && check.length > 0 && basiccontent.size() > 0) {
			for (HyBasicdataPojo key : basiccontent) {
				for (int i = 0; i < check.length; i++) {
					if (check[i].equals(key.getDataCode())) {
						ServiceTypeItem item = new ServiceTypeItem();
						item.setCode(key.getDataCode());
						item.setName(key.getDataValue());
						items.add(item);
					}
				}
			}
		}
		group.setItems(items);
		return group;
	}

	/**
	 * 获取预约相关明细 
	 * 
	 * @param id
	 * @param name
	 * @param content
	 * @param basiccontent
	 * @return
	 */
	public static ServiceTypeGroup transAppoSPTContent(int id, String name, String content,
			List<HyBasicdataPojo> basiccontent) {
		ServiceTypeGroup group = new ServiceTypeGroup();
		String[] check = content == null ? null : content.split(",");
		group.setId(id);
		group.setName(name);
		List<ServiceTypeItem> items = new ArrayList<ServiceTypeItem>();
		if (check != null && basiccontent != null && check.length > 0 && basiccontent.size() > 0) {
			for (HyBasicdataPojo key : basiccontent) {
				for (int i = 0; i < check.length;) {
					if (check[i].equals(key.getDataCode())) {
						ServiceTypeItem item = new ServiceTypeItem();
						item.setCode(key.getDataCode());
						item.setName(key.getDataValue());
						item.setCheckedCount(Integer.parseInt(check[i + 1]));
						items.add(item);
					}
					i += 2;
				}
			}
		}
		group.setItems(items);
		return group;
	}

}
