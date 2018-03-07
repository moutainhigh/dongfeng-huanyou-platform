package com.navinfo.opentsp.dongfeng.common.util;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *  获取properties文件内容的工具类
 * @author zhangy
 */
public class PropertiesUtil {

	//存放配置文件的所有的key-value
	public static Map<String,String> allParam = new HashMap<String, String>();

	//文件路径
	private static String CONFIG_NAME = "conf.properties";

	//根据文件名称-key，返回相应key的值
	public static String getPropertiesByKey(String key){
		try {
			if(allParam.containsKey(CONFIG_NAME+":"+key)){
				return allParam.get(CONFIG_NAME+":"+key);
			}else{
				Resource resource = new ClassPathResource(CONFIG_NAME);
				InputStream in = resource.getInputStream();
				// 解决中文乱码
				BufferedReader bf = new BufferedReader(
						new InputStreamReader(in,"utf-8"));
				Properties p = new Properties();
				p.load(bf);
				Set<Entry<Object, Object>> allKey = p.entrySet();
				for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(CONFIG_NAME+":"+entry.getKey()),
							String.valueOf(entry.getValue()));
				}
				in.close();
				return allParam.get(CONFIG_NAME + ":" + key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//根据文件名称-key，返回相应key的值
	public static String getPropertiesByKey(String fileName, String key) {
		try {
			if (allParam.containsKey(fileName + ":" + key)) {
				return allParam.get(fileName + ":" + key);
			} else {
				Resource resource = new ClassPathResource(fileName);
				InputStream in = resource.getInputStream();
				// 解决中文乱码
				BufferedReader bf = new BufferedReader(new InputStreamReader(in,"utf-8"));
				Properties p = new Properties();
				p.load(bf);
				Set<Entry<Object, Object>> allKey = p.entrySet();
				for (Entry<Object, Object> entry : allKey) {
					allParam.put(String.valueOf(fileName + ":" + entry.getKey()),
							String.valueOf(entry.getValue()));
				}
				in.close();
				return allParam.get(fileName + ":" + key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//初始化config配置文件
	public static void initProperties() {
		try {
			if(!allParam.isEmpty()) {
				allParam.clear();
			}
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_NAME);
			// 解决中文乱码
			BufferedReader bf = new BufferedReader(
					new InputStreamReader(in,"utf-8"));
			Properties p = new Properties();
			p.load(bf);
			Set<Entry<Object, Object>> allKey = p.entrySet();
			for (Entry<Object, Object> entry : allKey) {
				allParam.put(String.valueOf(CONFIG_NAME + ":" +entry.getKey()),
						String.valueOf(entry.getValue()));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改或添加键值对 如果key存在，修改, 反之，添加。
	 * @param filePath 文件路径，即文件所在包的路径，例如：java/util/config.properties
	 * @param key 键
	 * @param value 键对应的值
	 */
	public static void writeData(String filePath, String key, String value) {
		Properties prop = new Properties();
		try {
			File file = new File(filePath);
			if (!file.exists())
				file.createNewFile();
			InputStream fis = new FileInputStream(file);
			prop.load(fis);
			//一定要在修改值之前关闭fis
			fis.close();
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(key, value);
			//保存，并加入注释
			prop.store(fos, "Update '" + key + "' value");
			fos.close();
		} catch (IOException e) {
			System.err.println("Visit " + filePath + " for updating " + value + " value error");
		}
	}

	/**
	 * 获取resources中配置文件的系统绝对路径
	 * @param fileName 文件路径，即文件所在包的路径，例如：java/util/config.properties
	 */
	public static String getResourceAbsolutePath(String fileName) {
		Resource resource = new ClassPathResource(fileName);
		String resourceAbsolutePath = null;
		try {
			resourceAbsolutePath =  resource.getFile().getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return resourceAbsolutePath;
	}
}
