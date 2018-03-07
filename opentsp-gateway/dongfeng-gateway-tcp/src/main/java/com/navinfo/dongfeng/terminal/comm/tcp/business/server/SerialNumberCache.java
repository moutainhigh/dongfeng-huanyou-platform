package com.navinfo.dongfeng.terminal.comm.tcp.business.server;

import org.apache.mina.core.session.IoSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 消息流水号缓存
 * 
 * @author wangzhaoren
 * 
 */
public class SerialNumberCache {

	private static Map<String, IoSession> serialNumberSessionCache = new ConcurrentHashMap<String, IoSession>();// 前端消息流水号和前端链路的缓存
	private static Map<String, String> gpsSerialNumberCache = new ConcurrentHashMap<String, String>();// GPS消息流水号和前端消息流水号的缓存

	private final static SerialNumberCache instance = new SerialNumberCache();

	private SerialNumberCache() {
	}

	public final static SerialNumberCache instance() {
		return instance;
	}

	// 消息流水号和链路的缓存
	public Map<String, IoSession> getSerialNumberSessionCache() {
		return serialNumberSessionCache;
	}

	public void addSerialNumberSessionCache(String serialNumber, IoSession session) {
		serialNumberSessionCache.put(serialNumber, session);
	}

	public void removeSerialNumberSessionCache(String serialNumber) {
		serialNumberSessionCache.remove(serialNumber);
	}

	public IoSession getSerialNumberSessionCache(String SerialNumber) {
		return serialNumberSessionCache.get(SerialNumber);
	}

	public void clearSerialNumberSessionCache() {
		serialNumberSessionCache.clear();
	}

	// GPS消息流水号和前端消息流水号的缓存
	public Map<String, String> getGpsSerialNumberSessionCache() {
		return gpsSerialNumberCache;
	}

	public void addGpsSerialNumberSessionCache(String id, String serialNumber) {
		gpsSerialNumberCache.put(id, serialNumber);
	}

	public void removeGpsSerialNumberSessionCache(String id) {
		gpsSerialNumberCache.remove(id);
	}

	public String getGpsSerialNumberSessionCache(String id) {
		return gpsSerialNumberCache.get(id);
	}

	public void clearGpsSerialNumberSessionCache() {
		gpsSerialNumberCache.clear();
	}
}
