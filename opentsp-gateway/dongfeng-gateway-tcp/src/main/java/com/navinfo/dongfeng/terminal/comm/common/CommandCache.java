package com.navinfo.dongfeng.terminal.comm.common;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 协议缓存
 * 
 * @author aerozh
 * 
 */
public final class CommandCache {
	private static Map<String, Command> cache = new ConcurrentHashMap<String, Command>();
	private final static CommandCache instance = new CommandCache();

	private CommandCache() {
	}

	public final static CommandCache getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param send : com.aerozh.shanghai.client2bureau.procotol.send
	 * @param receiver : com.aerozh.shanghai.client2bureau.procotol.receiver
	 */
	public final void initialize(String send, String receiver) {
		String[] downProtocol = new String[] { send, receiver };
//		SearchHelper searchHelper = new SearchHelper(Command.class);

		// 加载下行链路协议解析类
//		for (String string : downProtocol) {
//			List<Class<?>> list = searchHelper.getClassesForFile(File f, string pkg);
//			if (list != null) {
//				for (Class<?> claxx : list) {
//					String cmd = claxx.getSimpleName();
//					String[] cmds = StringUtils.split(cmd, "_", true);
//					try {
//						CommandCache.getInstance().addCommand(cmds[1], (Command) claxx.newInstance());
//					} catch (InstantiationException e) {
//						e.printStackTrace();
//					} catch (IllegalAccessException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
	}

	/**
	 * 添中一个协议解析类
	 * 
	 * @param cmd
	 *            指令号
	 * @param command
	 *            解析类
	 */
	public final void addCommand(String cmd, Command command) {
		cache.put(cmd, command);
	}

	/**
	 * 获取一个协议解析类
	 * 
	 * @param cmd
	 *            指令号
	 * @return DogleCommand
	 */
	public final Command getCommand(String cmd) {
		return cache.get(cmd);
	}

	public final Map<String, Command> get() {
		return cache;
	}
}
