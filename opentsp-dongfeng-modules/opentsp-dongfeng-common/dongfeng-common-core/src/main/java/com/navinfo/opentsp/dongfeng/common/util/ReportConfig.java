package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.annotation.Report;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class ReportConfig {
	
	private static Log logger = LogFactory.getLog(ReportConfig.class);
	
	private List<JSONObject> columns = new ArrayList<JSONObject>();
	
	private String sourcePath;
	
	private String targetPath;
	
	private String filename;
	
	public ReportConfig(String filename) {
		
		//			String converterUtf8 = new String(filename.getBytes() , "utf-8");
		this.filename = filename;
	}
	
	public String getFileName() {
		
		if (StringUtil.isEmpty(filename)) {
			return "";
		}
		return filename;
	}
	
	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getTargetPath() {

		if (!StringUtil.isEmpty(targetPath)) {
			targetPath = targetPath.replace("\\\\", "/");
			if (targetPath.lastIndexOf("/") == -1) {
				targetPath = targetPath+"/";
			}
		} else {
			ClassPathResource resource = new ClassPathResource("/");
			try {
				URL url = resource.getURL();
				
				targetPath = url.getPath();
				targetPath = targetPath.replace("\\\\", "/");
				if (targetPath.lastIndexOf("/") == -1) {
					targetPath = targetPath+"/";
				}
			} catch (IOException e) {
                logger.error(e.getMessage(), e);
			}
		}
		return targetPath;
	}

	public void setTargetPath(String targetPath) {
		this.targetPath = targetPath;
	}

	public void setColumn(JSONArray metadata , Class<?> clazz) throws BaseServiceException {
		
		if (null == clazz) {
			return;
		}
		String errorKey = "";
		setColumn("", "序号", 0);
		Field field = null;
		try {

			if (null != metadata && !metadata.isEmpty()) {
				
				for (int index = 0 , length = metadata.size() ; index < length ; index++) {
					JSONObject json = metadata.getJSONObject(index);
					if (null != json && !json.isEmpty()) {
						Iterator<String> ite = json.keys();
						while(ite.hasNext()) {
							String key = ite.next();
							String desc = json.getString(key);
							
							field = clazz.getDeclaredField(key);
							field.setAccessible(true);
							
							//获取字段上的注解
							Report reportAnnotation = field.getAnnotation(Report.class);
							TimePrecision precision = reportAnnotation.precision();
							
							if (!StringUtils.isEmpty(precision.getPrecision())) {
								setColumn(key, desc, reportAnnotation.columnNum(), ColumnStyle.DATE, precision , reportAnnotation.format());
								errorKey = key;
							} else {
								setColumn(key, desc, reportAnnotation.columnNum());
								errorKey = key;
							}
						}
					}
				}
			} else {
				Field[] fields = clazz.getFields();
				
				if(null != fields && fields.length > 0) {
					for (Field f : fields) {
						f.setAccessible(true);
						field = f;
						Report report = f.getAnnotation(Report.class);

						TimePrecision precision = report.precision();
						
						if (null != precision) {
							setColumn(StringUtil.isEmpty(f.getName()) ? "" : f.getName(), StringUtil.isEmpty(report.header()) ? "" : report.header(), report.columnNum() , ColumnStyle.DATE , precision , report.format());
						} else {
							setColumn(StringUtil.isEmpty(f.getName()) ? "" : f.getName(), StringUtil.isEmpty(report.header()) ? "" : report.header(), report.columnNum());
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("ReportConfig ====> "+(null != field ? field.getName() : "")+" ====> : " , e);
			throw new BaseServiceException(e);
		}
	}
	
	public void setColumn(String key , String message , int number) {
		setColumn(key, message, number, "");
	}
	
	public void setColumn(String key , String message , int number , String split) {
		setColumn(key, message, number, split , null);
	}
	
	public void setColumn(String key , String message , int number , ColumnStyle style , TimePrecision precision) {
		setColumn(key, message, number, null , null , style , precision , null);
	}
	
	public void setColumn(String key , String message , int number , ColumnStyle style , TimePrecision precision , FormatStyle format) {
		setColumn(key, message, number, null , null , style , precision , format);
	}
	
	public void setColumn(String key , String message , int number , String split , String width) {
		setColumn(key , StringUtil.isEmpty(message) ? "" : message , number , StringUtil.isEmpty(split) ? "" : split , StringUtil.isEmpty(width) ? "" : width , null , null , null);
	}
	
	public void setColumn(String key , String message , int number , String split , String width , ColumnStyle style , TimePrecision precision , FormatStyle format) {
		
		JSONObject column = new JSONObject();
		column.accumulate("key", key);
		column.accumulate("message", StringUtil.isEmpty(message) ? "" : message);
		column.accumulate("number", number);
		column.accumulate("split", StringUtil.isEmpty(split) ? "" : split);
		column.accumulate("width", StringUtil.isEmpty(width) ? "" : width);
		if (null != style) {
			column.accumulate("style", style.getStyle());
		}
		
		if (null != precision) {
			column.accumulate("precision", precision.getPrecision());
			column.accumulate("format", null == format ? FormatStyle.DATE.getFormat() : format.getFormat());
		}
		
		columns.add(column);
	}
	
	public List<JSONObject> getColumns() {
		
		columns.sort(new Comparator<JSONObject>() {
			@Override
			public int compare(JSONObject o1, JSONObject o2) {
				return o1.getInt("number") < o2.getInt("number") ? -1 : o1.getInt("number") == o2.getInt("number") ? 0 : 1;
			}
		});
		return columns;
	}
	
//	public static void main(String[] args) {
//		
//		ReportConfig config = new ReportConfig();
//		
//		config.setColumn("name", "姓名", 2);
//		config.setColumn("sex", "性别", 1);
//		config.setColumn("age", "年龄", 4);
//		
//		List<JSONObject> list = config.getColumns();
//		for (JSONObject json : list) {
//			System.out.println(json);
//		}
//	}
}
