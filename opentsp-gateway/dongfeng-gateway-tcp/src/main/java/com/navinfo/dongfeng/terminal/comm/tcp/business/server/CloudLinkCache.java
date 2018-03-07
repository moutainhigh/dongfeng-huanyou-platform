package com.navinfo.dongfeng.terminal.comm.tcp.business.server;

import com.lc.core.protocol.platform.auth.LCMultiServerAuthRes;
import com.lc.core.protocol.platform.auth.LCRequestLoginKeyRes;
import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;



/**
 * 与位置云的链路缓存
 * 
 * @author baitao
 *
 */
public class CloudLinkCache {
	public static IoSession cache = null;
	public static Map<String,IoSession> caches = new ConcurrentHashMap<String,IoSession>();//ip端口与session缓存
	private final static CloudLinkCache instance = new CloudLinkCache();
	private static List<LCRequestLoginKeyRes.RequestLoginKeyRes> codeCache = new ArrayList<LCRequestLoginKeyRes.RequestLoginKeyRes>();// 鉴权请求应答
	private static List<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo> codesCache = new ArrayList<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo>();// 多服务鉴权
	private static Map<Long,String> carIpCache = new ConcurrentHashMap<Long,String>();
	private static List<String> rpIpPortCache = new ArrayList<String>();// RP服务器IP及其端口缓存
	private static Map<String, Boolean> rpIslogin=new ConcurrentHashMap<String, Boolean>();//rp地址端口是否登陆缓冲
	private static Map<String, CloudServerStart> cloudServerStartCache=new ConcurrentHashMap<String, CloudServerStart>();//rp地址端口服务连接启动缓冲
	
	private static Map<String, CloudServerStart> cloudIpPortServerStart=new HashMap<String, CloudServerStart>();//云ip端口与启动服务实例映射

	public final static CloudLinkCache instance() {
		return instance;
	}
	public final  IoSession getCache() {
		return cache;
	}
	public final  void setCache(IoSession c) {
		cache = c;
	}
	/**
	 * 新增
	 * @param key
	 * @param session
	 */
	public  void addCache(String key,IoSession session){
		caches.put(key, session);
	}

	public void addCodeCache(LCRequestLoginKeyRes.RequestLoginKeyRes res) {
		codeCache.add(res);
	}

	public List<LCRequestLoginKeyRes.RequestLoginKeyRes> getCodeCache() {
		return codeCache;
	}

	public void addCodesCache(LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo infos) {
		codesCache.add(infos);
	}

	public List<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo> getCodesCache() {
		return codesCache;
	}
	
	/**
	 * 获得
	 * @param key
	 */
	public  IoSession getcache(String key){
		return caches.get(key);
	}
	
	/**
	 * 删除
	 * @param key
	 * @param session
	 */
	public  void delCache(String key){
		if(caches.get(key)!=null&&caches.get(key).isConnected())
			caches.get(key).close();
		caches.remove(key);
	}
	
	/**
	 * 关闭连接
	 * @param key
	 * @param session
	 */
	public  void removeConnectCache(String key){
		if(caches.containsKey(key)){
			IoSession session = caches.get(key);
			if(session != null){
				session.setAttribute("isconnect", false);
			}
		}
	}
	
	/**
	 * 获得所有
	 * @return
	 */
	public  Map<String,IoSession> getcaches(){
		return caches;
	}
	/*****
	 * 云ip端口与启动服务实例映射新增
	 * @param ipPort
	 * @param cloudServerStart
	 */
	public void addCloudIpPortServer(String ipPort,CloudServerStart cloudServerStart){
		cloudIpPortServerStart.put(ipPort, cloudServerStart);
	}
	
	/*****
	 * 云ip端口与启动服务实例映射 获取
	 * @param ipPort
	 * @return
	 */
	public CloudServerStart getCloudIpPortServer(String ipPort){
		return cloudIpPortServerStart.get(ipPort);
	}
	/****
	 * 云ip端口与启动服务实例映射 删除
	 * @param ipPort
	 */
	public void delCloudIpPortServer(String ipPort){
		cloudIpPortServerStart.remove(ipPort);
	}
	/************
	 * 云ip端口与启动服务实例映射 清空
	 */
	public void clearCloudIpPortServer(){
		cloudIpPortServerStart.clear();
	}
	
	public String getCarIpCache(long carId) {
		return carIpCache.get(carId);
	}
	public void addCarIpCache(long carId, String ip) {
		carIpCache.put(carId, ip);
	}
	
	public Map<Long,String> getCarIpCache(){
		return carIpCache;
	}
	
	public Map<String, CloudServerStart> getCloudServerStartCache(){
		return cloudServerStartCache;
	}
	
	public void addRpIpPortCache(String RpIp) {
		if(!rpIpPortCache.contains(RpIp)){
		  rpIpPortCache.add(RpIp);
		}
	}
	
	public List<String> getRpIpPortCache(){
		return rpIpPortCache;
	}
	/***
	 * rp服务是否登录
	 * @return
	 */
	public Map<String, Boolean> getRpIslogin(){
		return rpIslogin;
	}
}
