package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.annotation.Report;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtil
{
    
	private final static Log logger = LogFactory.getLog(ExcelUtil.class);
    public static File getExcel(ReportConfig config, List<? extends Object> list, String sheetName, int startNumber)
    {
        
        // HSSFWorkbook workbook = null;
        XSSFWorkbook workbook = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        
        FileInputStream sourceFileStream = null;
        FileOutputStream fos = null;
        File targetFile = null;
        String target = config.getTargetPath();
        String fileName = config.getFileName();
        InputStream in = null;
        try
        {
            // 如果存在模板文件则先读取模板文件,根据模板文件生成POI对象.
            if (!StringUtil.isEmpty(config.getSourcePath()))
            {
                // 获取模板
                in = HttpUtil.getHttpInputStream(config.getSourcePath());
                if (null == in || in.available() <= 0)
                {
                    return null;
                }
                workbook = new XSSFWorkbook(in);
            }
            else
            {
                workbook = new XSSFWorkbook();
            }
            
            if (StringUtil.isEmpty(sheetName))
            {
                sheetName = Long.toString(Calendar.getInstance().getTimeInMillis());
            }
            // XSSFSheet sheet = workbook.createSheet(new String(sheetName.getBytes(),"utf-8"));
            // workbook.removeSheetAt(workbook.getSheetIndex("Sheet"));// 删除默认可视sheet
            workbook.removeSheetAt(workbook.getSheetIndex("Sheet"));// 删除默认可视sheet
            XSSFSheet sheet = workbook.createSheet();
            
            workbook.setSheetName(0, StringUtils.isEmpty(config.getFileName()) ? sheetName : config.getFileName());
            XSSFCellStyle textStyle = createTextStyle(workbook);
            
            boolean hasHeader = false;
            List<JSONObject> headers = config.getColumns();
            hasHeader = createHeader(workbook, sheet, headers);
            
            if (!hasHeader)
            {
                if (null == list || list.isEmpty())
                {
                    return null;
                }
                hasHeader = createHeader(config, workbook, sheet, list.get(0));
                if (!hasHeader)
                {
                    return null;
                }
            }
            
            // 返回一个excel的输入流
            if (null == list || list.isEmpty())
            {
                target = fileName + format.format(Calendar.getInstance().getTime()) + ".xlsm";
                
                targetFile = new File(target);
                fos = new FileOutputStream(targetFile);
                workbook.write(fos);
                return targetFile;
            }
            for (int index = 0, length = list.size(); index < length; index++)
            {
                
                XSSFRow row = sheet.createRow(index + 1);
                
                for (int column = 0, columns = headers.size(); column < columns; column++)
                {
                    
                    XSSFCell cell = row.createCell(column);
                    JSONObject json = headers.get(column);
                    String name = json.getString("key");
                    
                    Object styleObj = json.get("style");
                    
                    Object precesionObj = json.get("precision");
                    
                    String style = null;
                    String precision = null;
                    if (null != styleObj)
                    {
                        style = (String)styleObj;
                    }
                    
                    if (null != precesionObj)
                    {
                        precision = (String)precesionObj;
                    }
                    
                    cell.setCellStyle(textStyle);
                    
                    // 如果key没有对应的字段名则表示该列是序号列.
                    if (StringUtil.isEmpty(name))
                    {
                        cell.setCellValue(Integer.toString(startNumber + index + 1));
                        continue;
                    }
                    
                    Object obj = list.get(index);
                    String columnValue = "";
                    
                    if (name.indexOf(",") != -1)
                    {
                        
                        StringTokenizer strTokenizer = new StringTokenizer(name, ",");
                        String split = json.getString("split");
                        
                        while (strTokenizer.hasMoreElements())
                        {
                            String fieldName = strTokenizer.nextToken();
                            Field field = obj.getClass().getDeclaredField(fieldName);
                            field.setAccessible(true);
                            Object value = field.get(obj);
                            columnValue += null != value ? value.toString() + split : "" + split;
                        }
                        columnValue = columnValue.substring(0, columnValue.lastIndexOf(split));
                    }
                    else
                    {
                        Field field = obj.getClass().getDeclaredField(name);
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        
                        String formatedDateValue = null;
                        
                        if (null != style)
                        {
                        	SimpleDateFormat dateFormat = new SimpleDateFormat(json.getString("format"));
                            switch (style)
                            {
                                case "DATE":
                                {
                                    
                                    if (null != value)
                                    {
                                        
                                        if (Number.class.isAssignableFrom(value.getClass()))
                                        {
//                                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                            if (0 < Long.parseLong(value.toString()))
                                            {
                                                
                                                switch (StringUtil.isEmpty(precision) ? TimePrecision.SECOND.getPrecision()
                                                    : precision)
                                                {
                                                    case "SECOND":
                                                    {
                                                        formatedDateValue =
                                                            dateFormat.format(new Date(
                                                                Long.parseLong(value.toString()) * 1000));
                                                        break;
                                                    }
                                                    case "MILLISECOND":
                                                    {
                                                        formatedDateValue =
                                                            dateFormat.format(new Date(Long.parseLong(value.toString())));
                                                        break;
                                                    }
                                                    default:
                                                    {
                                                        formatedDateValue =
                                                            dateFormat.format(new Date(
                                                                Long.parseLong(value.toString()) * 1000));
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }

                                    if (!StringUtil.isEmpty(formatedDateValue))
                                    {
                                        columnValue += null != formatedDateValue ? formatedDateValue.toString() : "";
                                    }
                                    else
                                    {
                                        columnValue = null != value && Long.parseLong(value.toString()) > 0 ? value.toString() : "";
                                    }
                                    break;
                                }
                                default:
                                {
                                    columnValue = null != value ? value.toString() : "";
                                }
                            }
                        }
                        else
                        {
                            columnValue = null != value ? value.toString() : "";
                        }
                    }
                    
                    cell.setCellValue(columnValue);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                }
            }
            fileName = config.getFileName();
            target = fileName + format.format(Calendar.getInstance().getTime()) + ".xlsm";
            targetFile = new File(target);
            fos = new FileOutputStream(targetFile);
            workbook.write(fos);
            
            // return new FileInputStream(target);
            return targetFile;
        }
        catch (Exception e)
        {
        	
            logger.error("ExcelUtil ====> getExcel : ", e);
        }
        finally
        {
            try
            {
                if (null != fos)
                {
                    fos.close();
                }
                if (in != null)
                {
                    in.close();
                }
                
                if (null != sourceFileStream)
                {
                    sourceFileStream.close();
                }
            }
            catch (Exception e)
            {
            	logger.error("ExcelUtil ====> getExcel : ", e);
            }
        }
        return null;
    }
    
    /**
     * @param config excel 信息
     * @param sheetIndex 起始sheet
     * @param rowIndex 起始行
     * @param cls 要装换的对象的class
     * @return
     * @throws Exception
     */
    public static List<Object> readExcel(ReportConfig config, int sheetIndex, int rowIndex, Class<? extends Object> cls)
        throws Exception
    {
        if (StringUtil.isEmpty(config.getSourcePath()))
        {
            throw new FileNotFoundException("文件不存在");
        }
        // 加载属性
        List<Object> list = new ArrayList<>();
        Map<Object, Object> map = new HashMap<>();
        for (JSONObject jsonObject : config.getColumns())
        {
            map.put(jsonObject.get("number"), jsonObject.get("key"));
        }
        Workbook workbook;
        InputStream in = null;
        try
        {
            if (config.getSourcePath().startsWith("http"))
            {
                // 获取模板
                in = HttpUtil.getHttpInputStream(config.getSourcePath());
                
                if (null == in || in.available() <= 0)
                {
                    //如果读取到的输入流为空则等待2s,终端导入功能bug优化
                    Thread.sleep(2000);
                }
                if (null == in || in.available() <= 0)
                {
                    return null;
                }
                if (config.getSourcePath().endsWith("xlsx"))
                { // excel 2007
                    workbook = new XSSFWorkbook(in);
                }
                else
                {
                    workbook = new HSSFWorkbook(in);
                }
            }
            else
            {// 本地调用
                File file = new File(config.getSourcePath());
                if (config.getSourcePath().endsWith("xlsm"))
                { // excel 2007
                    workbook = new XSSFWorkbook(new FileInputStream(file));
                }
                else
                {
                    workbook = new HSSFWorkbook(new FileInputStream(file));
                }
            }
            Sheet childSheet = workbook.getSheetAt(sheetIndex);
            Object localObject = null;
            for (int j = rowIndex; j <= childSheet.getLastRowNum(); j++)
            {
                // 读取行元素
                Row row = childSheet.getRow(j);
                if (null != row)
                {
                    Object object = cls.newInstance();
                    for (int k = 0; k < row.getLastCellNum(); k++)
                    {
                        // 读取单元格
                        Cell cell = row.getCell((short)k);
                        if (cell == null)
                        {
                            continue;
                        }
                        else
                        {
                            // 判断获取类型
                            switch (cell.getCellType())
                            {
                                case HSSFCell.CELL_TYPE_NUMERIC:
                                    localObject = cell.getNumericCellValue();
                                    break;
                                case HSSFCell.CELL_TYPE_STRING:
                                    localObject = cell.getStringCellValue().trim();
                                    break;
                                case HSSFCell.CELL_TYPE_BOOLEAN:
                                    localObject = new Boolean(cell.getBooleanCellValue());
                                    break;
                                case HSSFCell.CELL_TYPE_BLANK:
                                    localObject = "";
                                    break;
                                case HSSFCell.CELL_TYPE_FORMULA:
                                    int a =
                                        (cell.getCellFormula().indexOf("+") + 1)
                                            + (cell.getCellFormula().indexOf('/') + 1)
                                            + (cell.getCellFormula().indexOf('*') + 1)
                                            + (cell.getCellFormula().indexOf('-') + 1);
                                    if (a <= 0)
                                    {
                                        localObject = cell.getCellFormula();
                                    }
                                    else if (a > 0)
                                    {
                                        localObject = cell.getNumericCellValue();
                                    }
                                    break;
                                case HSSFCell.CELL_TYPE_ERROR:
                                    localObject = new Byte(cell.getErrorCellValue());
                                    break;
                                default:
                                    System.out.println("未知类型");
                                    break;
                            }
                            // 动态设置值
                            Field field = cls.getDeclaredField(map.get(k).toString());
                            field.setAccessible(true);
                            if (localObject instanceof Double)
                            {
                                DecimalFormat decimalFormat = new DecimalFormat("########");// 格式化设置
                                field.set(object, decimalFormat.format(localObject));
                            }
                            else
                            {
                                field.set(object, localObject.toString().trim());
                            }
                        }
                    }
                    list.add(object);
                }
            }
            
        }
        catch (Exception e)
        {
            
            e.printStackTrace();
        }
        finally
        {
            if (in != null)
            {
                in.close();
            }
        }
        return list;
    }
    
    private static boolean createHeader(XSSFWorkbook workbook, XSSFSheet sheet, List<JSONObject> headers)
    {
        
        boolean hasHeader = false;
        XSSFRow headerRow = sheet.createRow(0);
        XSSFCellStyle headerStyle = createHeaderStyle(workbook);
        
        if (null != headers && !headers.isEmpty())
        {
            
            hasHeader = true;
            for (int index = 0, length = headers.size(); index < length; index++)
            {
                JSONObject json = headers.get(index);
                
                XSSFCell cell = headerRow.createCell(index);
                cell.setCellStyle(headerStyle);
                cell.setCellValue(json.getString("message"));
                int clength = json.getString("message").length() * 600;
                
                String width = json.getString("width");
                
                if (StringUtil.isEmpty(width))
                {
                    if (clength > sheet.getColumnWidth(index))
                    {
                        sheet.setColumnWidth(index, clength);
                    }
                    else
                    {
                        sheet.setColumnWidth(index, sheet.getColumnWidth(index));
                    }
                }
                else
                {
                    sheet.setColumnWidth(index, Integer.valueOf(width));
                }
            }
        }
        return hasHeader;
    }
    
    private static boolean createHeader(ReportConfig config, XSSFWorkbook workbook, XSSFSheet sheet, Object object)
    {
        
        if (null == object)
        {
            return false;
        }
        
        Field[] fields = object.getClass().getDeclaredFields();
        
        if (null == fields || fields.length <= 0)
        {
            return false;
        }
        
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(Report.class))
            {
                
                Report report = field.getAnnotation(Report.class);
                String message = report.header();
                int number = report.columnNum();
                String split = report.split();
                String key = field.getName();
                ColumnStyle style = report.style();
                TimePrecision precision = report.precision();
                String width = report.width();
                
                config.setColumn(key, message, number, split, width, style, precision , report.format());
            }
        }
        if (null == config.getColumns() || config.getColumns().isEmpty())
        {
            return false;
        }
        return createHeader(workbook, sheet, config.getColumns());
    }
    
    private static XSSFCellStyle createHeaderStyle(XSSFWorkbook workbook)
    {
        // 生成一个样式
        XSSFCellStyle style = workbook.createCellStyle();
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setLocked(true);
        XSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style.setFont(font);// 把字体应用到当前的样式
        return style;
    }
    
    private static XSSFCellStyle createTextStyle(XSSFWorkbook workbook)
    {
        // 生成并设置另一个样式
        XSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_JUSTIFY);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        XSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        style2.setFont(font2);// 把字体应用到当前的样式
        return style2;
    }
}
