package com.navinfo.dongfeng.terminal.comm.cache;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataSubscribeFailCache {

	private final static DataSubscribeFailCache instance = new DataSubscribeFailCache();
	private static Map<String, List<Long>> cache = new ConcurrentHashMap<String, List<Long>>();//<ip_port_time,List<sims>>
	
	public static final DataSubscribeFailCache instance() {
		return instance;
	}
	
	public Map<String, List<Long>> getCache(){
		return cache;
	}
	
	/***
	 * 缓存添加,不会覆盖以前的
	 * @param ip_port_time
	 * @param sims
	 */
	public void add(String ip_port_time,List<Long> sims){
		if(sims!=null&&sims.size()>0){
			List<Long> tempList=null;
			for(String key:cache.keySet()){
				if(key.startsWith(ip_port_time.substring(0,ip_port_time.lastIndexOf("_")))){
					tempList.addAll(cache.get(key));
					cache.remove(key);
					break;
				}
			}
			tempList.addAll(sims);
			cache.put(ip_port_time, tempList);
		}
	}

	/*****
	 * 各RP失败的sim加载，覆盖以前缓存
	 * @param ip_port_time
	 * @param sims
	 */
	public void load(String ip_port_time,List<Long> sims){
		if(sims!=null&&sims.size()>0){
			for(String key:cache.keySet()){
				if(key.startsWith(ip_port_time.substring(0,ip_port_time.lastIndexOf("_")))){
					cache.remove(key);
					break;
				}
			}
			cache.put(ip_port_time, sims);
		}
	}
	/***
	 * 根据RP的ip和端口获取订阅失败缓存
	 * @param ip_port
	 * @return
	 */
	public List<Long> get(String ip_port){
		List<Long> tempList=null;
		for(String key:cache.keySet()){
				if(key.startsWith(ip_port)){
					tempList=cache.get(key);
					break;
				}
		}
		return tempList;
	}

	/*****
	 * 根据RP的ip和端口删除订阅失败缓存
	 * @param ip_port
	 */
	public void del(String ip_port){
		for(String key:cache.keySet()){
			if(key.startsWith(ip_port)){
				cache.remove(key);
				break;
			}
		}
	}
	/****
	 * 根据key删除订阅失败缓存
	 * @param ip_port_time
	 */
	public void delByKey(String ip_port_time){
				cache.remove(ip_port_time);
	}

	/***
	 * 情况订阅失败缓存
	 */
	public void clear(){
		cache.clear();
	}
}
