package com.navinfo.opentsp.dongfeng.common.constant;

/**
 * 文件导入导出常量类
 *
 * Created by zhangyu on 2017/3/30.
 */
public class ReportConstants
{

    /**
     * 文件导出区分
     *
     * @author zhangy
     *
     */
    public enum FileExportFlg
    {

        // exportFlg("导出当前分页信息", 0),
        // exportFlg("导出全部车辆信息", 1),
        current("导出当前分页信息", 0), all("导出全部车辆信息", 1);

        private FileExportFlg(String name, int value)
        {
            this.name = name;
            this.value = value;
        }

        private String name;

        private int value;

        public String getName()
        {
            return name;
        }

        public int getValue()
        {
            return value;
        }

        FileExportFlg(String name)
        {
            this.name = name;
        }

        FileExportFlg(int value)
        {
            this.value = value;
        }

    }

}
