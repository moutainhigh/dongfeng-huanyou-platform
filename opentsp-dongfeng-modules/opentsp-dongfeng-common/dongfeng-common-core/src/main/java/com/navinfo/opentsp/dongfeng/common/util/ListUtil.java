package com.navinfo.opentsp.dongfeng.common.util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListUtil {
	public static <T> List<List<T>> splitList(List<T> datas, int size) {
		if (CollectionUtils.isEmpty(datas)) {
			return Collections.emptyList();
		}
		List<List<T>> results = new ArrayList<List<T>>();
		int length = datas.size();
		int subSize = length % size == 0 ? length / size : length / size + 1;
		int start = 0;
		int end = 0;
		for (int i = 0; i < subSize; i++) {
			start = i * size;
			end = (start + size) > length ? length : (start + size);
			results.add(datas.subList(start, end));
		}
		return results;
	}

	public static <T> boolean isEmpty(List<T> datas) {
	    return datas == null || datas.isEmpty();
	}

	public static <T> List<T> removeNullObject(List<T> datas) {
		List<T> results = new ArrayList<T>();
		if (!isEmpty(datas)) {
			for (T data : datas) {
				if (null != data) {
					results.add(data);
				}
			}
		}
		return results;
	}

	public static <T> boolean isEmpty(T[] datas) {
	    return null == datas || datas.length == 0;
	}

	public static <T> Object[] list2Array(List<T> datas) {
		if (isEmpty(datas)) {
			return new Object[0];
		}
		return datas.toArray();
	}

	public static <T> String list2String(List<T> datas) {
		StringBuffer buf = new StringBuffer();
		if (isEmpty(datas)) {
			return buf.toString();
		}
		for (T data : datas) {
			buf.append(data.toString()).append(",");
		}
		return buf.substring(0, buf.length() - 1).toString();
	}
}
