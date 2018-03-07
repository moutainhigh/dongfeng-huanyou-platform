package com.navinfo.opentsp.dongfeng.common.constant;

import com.navinfo.opentsp.dongfeng.common.util.ColumnStyle;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.TimePrecision;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-14
 * @modify
 * @copyright Navi Tsp
 */
public class ReportConfigConstants {

    /**
     * ReportConfig 方法，实现动态导出列
     *
     * @param metadata
     * @param jobClass
     * @return
     */
//    public ReportConfig build(String metadata, Class<? extends Object> jobClass){
//        try {
//            if (!StringUtils.isEmpty(metadata)) {
//                columns.clear();
//                setColumn(JSONArray.fromObject(metadata), jobClass);
//            }
//        } catch (BaseServiceException ex) {
//            throw new BaseServiceRuntimeException(ex);
//        }
//        return this;
//    }

    public static final int EXCEL_START_ROW_NUMBER = 0;
    public static final int EXPORT_CURRENT_FLAG = 0;
    public static final int EXPORT_ALL_FLAG = 1;

    public static ReportConfig stationLoadConfig = new ReportConfig("服务站负荷分析列表");

    static {
        stationLoadConfig.setColumn("", "序号", 0);
        stationLoadConfig.setColumn("name", "服务站", 1);
        stationLoadConfig.setColumn("address", "服务站地址", 2);
        stationLoadConfig.setColumn("phone", "联系方式", 3);
        stationLoadConfig.setColumn("manager", "服务经理", 4);
        stationLoadConfig.setColumn("serviceLevel", "服务星级", 5);
        stationLoadConfig.setColumn("reviewsLevel", "定评星级", 6);
        stationLoadConfig.setColumn("stationEnable", "状态", 7);
        stationLoadConfig.setColumn("actualTrains", "实际车次", 8);
    }

    public static ReportConfig scanCodeConfig = new ReportConfig("车辆扫码列表");

    static {

        scanCodeConfig.setColumn("", "序号", 0);
        scanCodeConfig.setColumn("chassisNum", "底盘号", 1);
        scanCodeConfig.setColumn("plateNum", "车牌号", 2);
        scanCodeConfig.setColumn("terminalId", "终端ID", 3);
        scanCodeConfig.setColumn("sim", "SIM卡号", 4);
        scanCodeConfig.setColumn("dealer", "经销商", 5);
        scanCodeConfig.setColumn("scanOpera", "扫码动作", 6);
        scanCodeConfig.setColumn("operator", "操作人", 7);
        scanCodeConfig.setColumn("operaDate", "操作时间", 8);
        scanCodeConfig.setColumn("lon,lat", "扫码位置", 9, ";");
        scanCodeConfig.setColumn("remark", "备注", 10);
//        scanCodeConfig.setColumn("syncStateDesc", "同步状态", 11);
    }

    public static ReportConfig stationOverTimeAlarmsConfig = new ReportConfig("服务站超时报警列表");

    static {

        stationOverTimeAlarmsConfig.setColumn("", "序号", 0);
        stationOverTimeAlarmsConfig.setColumn("chassisNum", "底盘号", 1);
        stationOverTimeAlarmsConfig.setColumn("plateNumber", "车牌号", 2);
        stationOverTimeAlarmsConfig.setColumn("beidouMachineID", "北斗一体机ID", 3);
        stationOverTimeAlarmsConfig.setColumn("fkControllerID", "FK控制器ID", 4);
        stationOverTimeAlarmsConfig.setColumn("dealer", "所属经销商", 5);
        stationOverTimeAlarmsConfig.setColumn("client", "所属客户", 6);
        stationOverTimeAlarmsConfig.setColumn("carModel", "车辆型号", 7);
        stationOverTimeAlarmsConfig.setColumn("engineModel", "发动机型号", 8);
        stationOverTimeAlarmsConfig.setColumn("engineType", "发动机类型", 9);
        stationOverTimeAlarmsConfig.setColumn("stationName", "服务站", 10);
        stationOverTimeAlarmsConfig.setColumn("stationAddress", "服务站地址", 11);
        stationOverTimeAlarmsConfig.setColumn("contacts", "联系人", 12);
        stationOverTimeAlarmsConfig.setColumn("inboundTime", "入站时间", 13);
        stationOverTimeAlarmsConfig.setColumn("lastAlarmTime", "末次报警时间", 14);
        stationOverTimeAlarmsConfig.setColumn("parkingTimes", "停车总时长", 15);
        stationOverTimeAlarmsConfig.setColumn("parkingThreshold", "滞留阈值", 16);
        stationOverTimeAlarmsConfig.setColumn("isOvertime", "是否超时", 17);
    }

    public static ReportConfig rSerpartAppointment = new ReportConfig("服务站服务配件预约列表");

    static {

        rSerpartAppointment.setColumn("", "序号", 0);
        rSerpartAppointment.setColumn("appointmentId", "预约号", 1);
        rSerpartAppointment.setColumn("chassisNum", "底盘号", 2);
        rSerpartAppointment.setColumn("carCph", "车牌号", 3);
        rSerpartAppointment.setColumn("terId", "北斗一体机ID号", 4);
        rSerpartAppointment.setColumn("carTerId", "FK控制器ID号", 5);
        rSerpartAppointment.setColumn("companyName", "所属经销商", 6);
        rSerpartAppointment.setColumn("carOwner", "所属客户", 7);
        rSerpartAppointment.setColumn("carType", "车辆型号", 8);
        rSerpartAppointment.setColumn("engineNumber", "发动机型号", 9);
        rSerpartAppointment.setColumn("appoPhone", "预约联系方式", 10);
        rSerpartAppointment.setColumn("appoStation", "预约服务站", 11);
        rSerpartAppointment.setColumn("appoName", "预约人", 12);
        rSerpartAppointment.setColumn("appoTime", "预约时间", 13);
        rSerpartAppointment.setColumn("appoSubmitTime", "预约提交时间", 14);
        rSerpartAppointment.setColumn("appoService", "预约服务", 15);
        rSerpartAppointment.setColumn("appoParts", "预约配件", 16);
        rSerpartAppointment.setColumn("appoComment", "预约备注", 17);
        rSerpartAppointment.setColumn("inTime", "入站时间", 18);
        rSerpartAppointment.setColumn("outTime", "出站时间", 19);
        rSerpartAppointment.setColumn("commTime", "点评时间", 20);
        rSerpartAppointment.setColumn("commTechLevel", "技术质量星级", 21);
        rSerpartAppointment.setColumn("commServiceLevel", "服务质量星级", 22);
        rSerpartAppointment.setColumn("commTotalLevel", "总体评价星级", 23);
        rSerpartAppointment.setColumn("appointmentStatus", "预约状态", 24);

    }

    public static ReportConfig wsxReport = new ReportConfig("车辆在库未上线列表");

    static {

        wsxReport.setColumn("chassis_no", "底盘号", 1);
        wsxReport.setColumn("work_center", "工作中心", 2);
        wsxReport.setColumn("materials_id", "整车编码", 3);
        wsxReport.setColumn("materials_name", "整车名称", 4);
        wsxReport.setColumn("plan_time", "计划时间", 5, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("xx_sj", "下线时间", 6, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("zx_sj", "转运时间", 7, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("qualified_time", "合格时间", 8, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("instock_time", "入库时间", 9, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("removal_time", "出库时间", 10, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("storage_location", "库位", 11);
        wsxReport.setColumn("sale_id", "销售编码", 12);
        wsxReport.setColumn("last_time", "末次有效位置时间", 13, ColumnStyle.DATE, TimePrecision.SECOND);
        wsxReport.setColumn("last_location", "末次有效位置", 14);
    }

    public static ReportConfig nsoodleReport = new ReportConfig("未售车辆出经销商库异常列表");

    static {

        nsoodleReport.setColumn("chassisNum", "底盘号", 1);
        nsoodleReport.setColumn("carCph", "车牌号", 2);
        nsoodleReport.setColumn("carModel", "车辆型号", 3);
        nsoodleReport.setColumn("teamDealerCode", "经销商编码", 4);
        nsoodleReport.setColumn("teamName", "经销商名称", 5);
        nsoodleReport.setColumn("secdNameLatlon", "网点信息 ", 6);
        nsoodleReport.setColumn("warehouseLog", "入库位置-经度", 7);
        nsoodleReport.setColumn("warehouseLat", "入库位置-纬度", 8);
        nsoodleReport.setColumn("warehouseTime", "入库时间", 9, ColumnStyle.DATE, TimePrecision.SECOND);
        nsoodleReport.setColumn("outOfLibraryLog", "出库位置-经度  ", 10);
        nsoodleReport.setColumn("outOfLibraryLat", "出库位置-纬度  ", 11);
        nsoodleReport.setColumn("outOfLibraryTime", "出库时间", 12, ColumnStyle.DATE, TimePrecision.SECOND);
        nsoodleReport.setColumn("lastLoction", "当前（末次）位置", 13);
    }

    public static ReportConfig saleStateReport = new ReportConfig("销售状态报表");

    static {

        saleStateReport.setColumn("chassisNum", "底盘号", 1);
        saleStateReport.setColumn("carCph", "车牌号", 2);
        saleStateReport.setColumn("terminalBDCode", "北斗一体机ID号", 3);
        saleStateReport.setColumn("terminalFKCode", "FK控制器ID号", 4);
        saleStateReport.setColumn("teamName", "所属经销商", 5);
        saleStateReport.setColumn("teamCode", "经销商编码 ", 6);
        saleStateReport.setColumn("carOwnerName", "所属客户", 7);
        saleStateReport.setColumn("carTypeName", "车辆型号", 8);
        saleStateReport.setColumn("engNumber", "发动机型号", 9);
        saleStateReport.setColumn("engTypeName", "发动机类型  ", 10);
        saleStateReport.setColumn("saleDateStr", "AAK销售时间  ", 11);
        saleStateReport.setColumn("saleStatusName", "AAK销售状态", 12);
        saleStateReport.setColumn("invoiceNumber", "发票号", 13);
        saleStateReport.setColumn("businessCode", "证件号", 14);
        saleStateReport.setColumn("autoFlagName", "录入方式", 15);
        saleStateReport.setColumn("registerTimeStr", "末次注册时间", 16);
        saleStateReport.setColumn("firstTime", "首次有位置时间", 17);
        saleStateReport.setColumn("firstLocation", "首次有效位置", 18);
        saleStateReport.setColumn("lastTime", "末次有效位置时间", 19);
        saleStateReport.setColumn("lastLocation", "末次有效位置", 20);
    }

    public static ReportConfig saleRemovalReport = new ReportConfig("出库销售报表");

    static {

        saleRemovalReport.setColumn("", "序号", 0);
        saleRemovalReport.setColumn("chassisNum", "底盘号", 1);
        saleRemovalReport.setColumn("carCph", "车牌号", 2);
        saleRemovalReport.setColumn("terminalBDCode", "北斗一体机ID号", 3);
        saleRemovalReport.setColumn("terminalFKCode", "FK控制器ID号", 4);
        saleRemovalReport.setColumn("teamName", "所属经销商", 5);
        saleRemovalReport.setColumn("teamCode", "经销商编码 ", 6);
        saleRemovalReport.setColumn("carOwnerName", "所属客户", 7);
        saleRemovalReport.setColumn("carTypeName", "车辆型号", 8);
        saleRemovalReport.setColumn("engNumber", "发动机型号", 9);
        saleRemovalReport.setColumn("engTypeName", "发动机类型  ", 10);
        saleRemovalReport.setColumn("saleDateStr", "STD销售时间  ", 11);
        saleRemovalReport.setColumn("saleStatusName", "STD销售状态", 12);
        saleRemovalReport.setColumn("autoFlagName", "录入方式", 13);
        saleRemovalReport.setColumn("removalTimeStr", "出库时间", 14);
        saleRemovalReport.setColumn("firstTime", "首次有效位置时间", 15);
        saleRemovalReport.setColumn("firstLocation", "首次有效位置", 16);
        saleRemovalReport.setColumn("lastTime", "末次有效位置时间", 17);
        saleRemovalReport.setColumn("lastLocation", "末次有效位置", 18);
    }

    public static ReportConfig oilReport = new ReportConfig("油量变化统计分析报表");

    static {
        oilReport.setColumn("", "序号", 0);
        oilReport.setColumn("chassisNum", "底盘号", 1);
        oilReport.setColumn("carCph", "车牌号", 2);
        oilReport.setColumn("terId", "终端ID(北斗)", 3);
        oilReport.setColumn("fkId", "终端ID(FK控制器)", 4);
        oilReport.setColumn("companyName", "所属经销商", 5);
        oilReport.setColumn("carOwner", "所属客户", 6);
        oilReport.setColumn("carType", "车辆型号", 7);
        oilReport.setColumn("engineType", "发动机类型", 8);
        oilReport.setColumn("engineNumber", "发动机型号", 9);
        oilReport.setColumn("beginDateStr", "起始时间", 10);
        oilReport.setColumn("beginLocation", "起始位置", 11);
        oilReport.setColumn("endDateStr", "结束时间", 12);
        oilReport.setColumn("endLocation", "结束位置", 13);
        oilReport.setColumn("mileage", "当日行驶里程(KM)", 14);
        oilReport.setColumn("fuelCspDaily", "当日行驶能耗(L)", 15);
        oilReport.setColumn("fuelCspRate", "当日百公里能耗(L)", 16);
        oilReport.setColumn("fuelCspTotalRate", "平均百公里能耗(L)", 17);
        oilReport.setColumn("lastTirePressure", "当日最新胎压", 18, "", "30000");
        oilReport.setColumn("lastTireTempreture", "当日最新胎温", 19, "", "30000");
    }


    public static ReportConfig tireReport = new ReportConfig("胎温胎压详情报表");

    static {
        tireReport.setColumn("", "序号", 0);
        tireReport.setColumn("chassisNum", "底盘号", 1);
        tireReport.setColumn("carCph", "车牌号", 2);
        tireReport.setColumn("terId", "终端ID(北斗)", 3);
        tireReport.setColumn("fkId", "终端ID(FK控制器)", 4);
        tireReport.setColumn("companyName", "所属经销商", 5);
        tireReport.setColumn("carOwner", "所属客户", 6);
        tireReport.setColumn("carType", "车辆型号", 7);
        tireReport.setColumn("engineNumber", "发动机型号", 8);
        tireReport.setColumn("engineType", "发动机类型", 9);
        tireReport.setColumn("tirePressurePercent", "胎压", 10, "", "30000");
        tireReport.setColumn("tireTempreturePercent", "胎温", 11, "", "30000");
        tireReport.setColumn("timeStr", "GPS时间", 12);
    }


    public static ReportConfig lsReport = new ReportConfig("车辆生产线漏扫报表");

    static {

        lsReport.setColumn("", "序号", 0);
        lsReport.setColumn("chassisNum", "底盘号", 1);
        lsReport.setColumn("workCenter", "工作中心", 2);
        lsReport.setColumn("materialsId", "整车编码", 3);
        lsReport.setColumn("materialsName", "整车名称", 4);
        lsReport.setColumn("planTime", "计划时间", 5);
        lsReport.setColumn("instockTime", "合格时间", 6);
        lsReport.setColumn("qualifiedTime", "入库时间", 7);
        lsReport.setColumn("removalTime", "出库时间", 8);
        lsReport.setColumn("storageLoc", "库位", 9);
    }

    public static ReportConfig carCountReport = new ReportConfig("车次统计明细报表");

    static {

        carCountReport.setColumn("", "序号", 0);
        carCountReport.setColumn("chassicNum", "底盘号", 1);
        carCountReport.setColumn("carNum", "车牌号", 2);
        carCountReport.setColumn("carType", "车辆类型", 3);
        carCountReport.setColumn("engineType", "发动机类型", 4);
        carCountReport.setColumn("structureNum", "车架号", 5);
        carCountReport.setColumn("govCityName", "统计区域（省）", 6);
        carCountReport.setColumn("govName", "统计区域（市）", 7);
        carCountReport.setColumn("count", "统计车次", 8);
        carCountReport.setColumn("lastCarDateStr", "末次有效位置时间", 9);
        carCountReport.setColumn("lastLon,lastLat", "末次有效位置", 10, ";");
    }

    public static ReportConfig onlineReport = new ReportConfig("信息同步上线报表");

    static {

        onlineReport.setColumn("", "序号", 0);
        onlineReport.setColumn("chassisNum", "底盘号", 1);
        onlineReport.setColumn("carCph", "车牌号", 2);
        onlineReport.setColumn("terId", "北斗一体机ID号", 3);
        onlineReport.setColumn("fkId", "FK控制器ID号", 4);
        onlineReport.setColumn("companyName", "所属经销商", 5);
        onlineReport.setColumn("dealerCode", "经销商编码", 6);
        onlineReport.setColumn("carOwner", "所属客户", 7);
        onlineReport.setColumn("carType", "车辆型号", 8);
        onlineReport.setColumn("engineNumber", "发动机型号", 9);
        onlineReport.setColumn("engineType", "发动机类型", 10);
        onlineReport.setColumn("saleDateStr", "STD销售时间", 11);
        onlineReport.setColumn("saleStatus", "STD销售状态", 12);
        onlineReport.setColumn("aakSaleDateStr", "AAK销售时间", 13);
        onlineReport.setColumn("aakSaleStatusStr", "AAK销售状态", 14);
        onlineReport.setColumn("autoFlag", "录入方式", 15);
        onlineReport.setColumn("syncDateStr", "同步时间", 16);
        onlineReport.setColumn("firstLinkTimeStr", "首次连线时间", 17);
        onlineReport.setColumn("firstLocationDateStr", "首次有效位置时间", 18);
        onlineReport.setColumn("firstLocation", "首次有效位置", 19);
        onlineReport.setColumn("lastLocationDateStr", "末次有效位置时间", 20);
        onlineReport.setColumn("lastLocation", "末次有效位置", 21);
    }

    public static ReportConfig faultSummary = new ReportConfig("故障汇总报表");

    static {

        faultSummary.setColumn("", "序号", 0);
        faultSummary.setColumn("chassisNum", "底盘号", 1);
        faultSummary.setColumn("plateNum", "车牌号", 2);
        faultSummary.setColumn("bdTerCode", "北斗一体机ID", 3);
        faultSummary.setColumn("fkTerCode", "FK控制器ID", 4);
        faultSummary.setColumn("tName", "所属经销商", 5);
        faultSummary.setColumn("businessName", "所属客户", 6);
        faultSummary.setColumn("carModel", "车辆型号", 7);
        faultSummary.setColumn("engineNumber", "发动机型号", 8);
        faultSummary.setColumn("engineType", "发动机类型", 9);
        faultSummary.setColumn("structureNum", "车架号", 10);
        faultSummary.setColumn("breakdownDis", "故障类型", 11);
        faultSummary.setColumn("spn", "SPN", 12);
        faultSummary.setColumn("fmi", "FMI", 13);
        faultSummary.setColumn("occurDate", "故障发生时间", 14);
        faultSummary.setColumn("duration", "持续时长", 15);
        faultSummary.setColumn("bLoction", "起始位置", 16);
        faultSummary.setColumn("eLoction", "结束位置", 17);
    }


    public static ReportConfig breakdown = new ReportConfig("故障提醒报表");

    static {
        breakdown.setColumn("", "序号", 0);
        breakdown.setColumn("chassisNum", "底盘号", 1);
        breakdown.setColumn("carCph", "车牌号", 2);
        breakdown.setColumn("terId", "北斗一体机ID", 3);
        breakdown.setColumn("carTerId", "FK控制器ID", 4);
        breakdown.setColumn("companyName", "所属经销商", 5);
        breakdown.setColumn("carOwner", "所属客户", 6);
        breakdown.setColumn("carType", "车辆型号", 7);
        breakdown.setColumn("engineTypeStr", "发动机型号", 8);
        breakdown.setColumn("engineNumber", "发动机类型", 9);
        breakdown.setColumn("breakdownDis", "故障类型", 10);
        breakdown.setColumn("spnFmi", "SPN_FMI", 11);
        breakdown.setColumn("occurDate", "故障发生时间", 12);
        breakdown.setColumn("bLoction", "发生位置", 13);
    }

    public static ReportConfig carOperateReport = new ReportConfig("运营车况查看");

    static {

        carOperateReport.setColumn("", "序号", 0);
        carOperateReport.setColumn("chassisNum", "底盘号", 1);
        carOperateReport.setColumn("plateNum", "车牌号", 2);
        carOperateReport.setColumn("engineType", "发动机类型", 3);
        carOperateReport.setColumn("syncTime", "同步时间", 4);
        carOperateReport.setColumn("lastGpsTime", "末次有效位置时间", 5);
        carOperateReport.setColumn("lastGpsLng,lastGpsLat", "末次有效位置", 6, ";");
        carOperateReport.setColumn("accStatus", "点火状态", 7);
        carOperateReport.setColumn("gpsCommu", "GPS通信状态", 8);
        carOperateReport.setColumn("gpsPower", "GPS电源状态", 9);
        carOperateReport.setColumn("gpsWire", "GPS天线状态", 10);
        carOperateReport.setColumn("gpsLocation", "GPS定位状态", 11);
//        carOperateReport.setColumn("mileage", "整车里程（Km）", 12);
//        carOperateReport.setColumn("cumuRunningTime", "发动机累计运行时间", 13);
//        carOperateReport.setColumn("oilValue", "当前油/气量(%)", 14);
//        carOperateReport.setColumn("rotation", "当前转速(rpm)", 15);
//        carOperateReport.setColumn("accPedalPos", "油门开度(%)", 16);
//        carOperateReport.setColumn("gpsMileage", "GPS总里程(KM)", 17);
//        carOperateReport.setColumn("mileageDD", "仪表里程(KM)", 18);
//        carOperateReport.setColumn("mileage", "ECU里程(KM)", 19);
//        carOperateReport.setColumn("totalFuelConsumption", "总燃油油耗(L)", 20);
//        carOperateReport.setColumn("totalFuelConsumption", "ECU油耗(L)", 21);
//        carOperateReport.setColumn("integralFuelConsumption", "终端积分油耗(L)", 22);
//        carOperateReport.setColumn("oilValue", "平台计算油耗(L)", 23);


//        carOperateReport.setColumn("mileage", "整车里程（Km）", 12);
        carOperateReport.setColumn("cumuRunningTime", "发动机累计运行时间", 12);
        carOperateReport.setColumn("oilValue", "当前油/气量", 13);
        carOperateReport.setColumn("rotation", "当前转速(rpm)", 14);
        carOperateReport.setColumn("accPedalPos", "油门开度(%)", 15);
        carOperateReport.setColumn("gpsMileage", "标准里程(KM)", 16);
        carOperateReport.setColumn("totalFuelConsumption", "标准油耗(L)", 17);

    }

    public static ReportConfig rearInstallVehicleReport = new ReportConfig("后装车辆统计报表");

    static {

        rearInstallVehicleReport.setColumn("", "序号", 0);
        rearInstallVehicleReport.setColumn("serviceStation", "服务站名称", 1);
        rearInstallVehicleReport.setColumn("serviceStationCode", "服务站编码", 2);
        rearInstallVehicleReport.setColumn("lon,lat", "所属省份", 3, ";");
        rearInstallVehicleReport.setColumn("date", "安装日期", 4);
        rearInstallVehicleReport.setColumn("count", "安装车辆数", 5);
    }

    public static ReportConfig scanCodeImportConfig = new ReportConfig("车辆扫码空入空出导入");

    static {
        scanCodeImportConfig.setColumn("id", "序号", 0);
        scanCodeImportConfig.setColumn("vinCode", "VIN码", 1);
        scanCodeImportConfig.setColumn("companyCode", "制造公司代码", 2);
        scanCodeImportConfig.setColumn("productionFactoryCode", "生产工厂代码", 3);
        scanCodeImportConfig.setColumn("shiftCode", "班次代码", 4);
        scanCodeImportConfig.setColumn("sellRepositCode", "销售资源库代码", 5);
        scanCodeImportConfig.setColumn("physicalLibraryCode", "物理库代码", 6);
        scanCodeImportConfig.setColumn("warehouseCode", "仓库库区代码", 7);
        scanCodeImportConfig.setColumn("qualityCheckerCode", "质检员代码", 8);
        scanCodeImportConfig.setColumn("reverseDriverCode", "倒车司机代码", 9);
        scanCodeImportConfig.setColumn("operatorNum", "操作员编号", 10);
    }

    public static ReportConfig usersConfig = new ReportConfig("用户信息列表");

    static {
        usersConfig.setColumn("", "序号", 0);
        usersConfig.setColumn("accountName", "账号", 1);
        usersConfig.setColumn("accountType", "用户类型", 2);
        usersConfig.setColumn("jobType", "岗位", 3);
        usersConfig.setColumn("accountLinktel", "联系方式", 4);
        usersConfig.setColumn("lockAccount", "锁定状态", 5);
        usersConfig.setColumn("expiredStatus", "过期状态", 6);
        usersConfig.setColumn("termVilidate", "有效期", 7);
        usersConfig.setColumn("createAccount", "创建人", 8);
        usersConfig.setColumn("accountDate", "创建时间", 9);
    }

    public static ReportConfig dealersConfig = new ReportConfig("经销商信息列表");

    static {
        dealersConfig.setColumn("", "序号", 0);
        dealersConfig.setColumn("tname", "经销商名称", 1);
        dealersConfig.setColumn("dealerCode", "经销商编码", 2);
        dealersConfig.setColumn("pname", "所在销售区域", 3);
        dealersConfig.setColumn("companyAddress", "经销商地址", 4);
        dealersConfig.setColumn("enableRadius", "激活半径（KM）", 5);
        dealersConfig.setColumn("lockRadius", "锁车半径（KM）", 6);
        dealersConfig.setColumn("companyLinkman", "联系人", 7);
        dealersConfig.setColumn("companyLinktel", "联系电话", 8);
        dealersConfig.setColumn("tAccountName", "创建人", 9);
        dealersConfig.setColumn("tDate", "创建时间", 10);
    }

    public static ReportConfig businessesConfig = new ReportConfig("客户信息列表");

    static {
        businessesConfig.setColumn("", "序号", 0);
        businessesConfig.setColumn("businessName", "客户名称", 1);
        businessesConfig.setColumn("customerType", "客户类型", 2);
        businessesConfig.setColumn("businessCode", "证件号码", 3);
        businessesConfig.setColumn("addressName", "所属省市", 4);
        businessesConfig.setColumn("linkPerson", "联系人", 5);
        businessesConfig.setColumn("linkTelpone", "联系方式", 6);
        businessesConfig.setColumn("fax", "传真", 7);
        businessesConfig.setColumn("addressCommon", "详细地址", 8);
    }

    public static ReportConfig exportTimelyDataConfig = new ReportConfig("实时数据");

    static {
        exportTimelyDataConfig.setColumn("", "序号", 0);
        exportTimelyDataConfig.setColumn("gpsTime", "时间（毫秒级）", 1);
        exportTimelyDataConfig.setColumn("longitude", "Gps经度", 2);
        exportTimelyDataConfig.setColumn("latitude", "Gps纬度", 3);
        exportTimelyDataConfig.setColumn("address", "位置", 4);
        exportTimelyDataConfig.setColumn("gear", "档位", 5);
        exportTimelyDataConfig.setColumn("speed", "车速", 6);
        exportTimelyDataConfig.setColumn("clutchSwitch", "离合器信号", 7);
        exportTimelyDataConfig.setColumn("accelerator", "油门", 8);
        exportTimelyDataConfig.setColumn("height", "海拔高度", 9);
        exportTimelyDataConfig.setColumn("brake", "制动信号", 10);
        exportTimelyDataConfig.setColumn("rotation", "发动机转速", 11);
        exportTimelyDataConfig.setColumn("engineOutputTorque", "发动机输出扭矩", 12);
        exportTimelyDataConfig.setColumn("realTimeOilConsumption", "发动机瞬时油耗", 13);
        exportTimelyDataConfig.setColumn("fuelConsumptionRate", "发动机燃油消耗率", 14);
    }

    public static ReportConfig lastLocationConfig = new ReportConfig("末次位置统计表");

    static {
        lastLocationConfig.setColumn("", "序号", 0);
        lastLocationConfig.setColumn("vinNum", "VIN码", 1);
        lastLocationConfig.setColumn("chassisNum", "底盘号", 2);
        lastLocationConfig.setColumn("plateNum", "车牌号", 3);
        lastLocationConfig.setColumn("gpsTime", "末次有效位置时间", 4);
        lastLocationConfig.setColumn("lastValidLon,lastValidLat", "末次有效位置", 5, ";");
        lastLocationConfig.setColumn("standardMileage", "标准里程(KM)", 6);
        lastLocationConfig.setColumn("standardFuelCon", "标准油耗(L)", 7);
        lastLocationConfig.setColumn("dealerCode", "经销商编码", 8);
        lastLocationConfig.setColumn("dealerName", "经销商名称", 9);
        lastLocationConfig.setColumn("outDate", "出库日期", 10);
    }

    public static ReportConfig errorLastLocationConfig = new ReportConfig("末次位置统计错误信息表");

    static {
        errorLastLocationConfig.setColumn("", "序号", 0);
        errorLastLocationConfig.setColumn("vinNum", "VIN码", 1);
    }

    public static ReportConfig disklibraryConfig = new ReportConfig("盘库统计报表");

    static {
        disklibraryConfig.setColumn("", "序号", 0);
        disklibraryConfig.setColumn("vin", "VIN码", 1);
        disklibraryConfig.setColumn("productCode", "产品代码", 2);
        disklibraryConfig.setColumn("invaFlag", "有效标识", 3);
        disklibraryConfig.setColumn("gunCode", "扫描枪代码", 4);
        disklibraryConfig.setColumn("dealerName", "经销商名称", 5);
        disklibraryConfig.setColumn("dealerCode", "经销商代码", 6);
        disklibraryConfig.setColumn("province", "省份", 7);
        disklibraryConfig.setColumn("scanTime", "扫描时间", 8);
        disklibraryConfig.setColumn("scanStatus", "扫描信息状态", 9);
        disklibraryConfig.setColumn("netName", "网点名称", 10);
        disklibraryConfig.setColumn("netCode", "网点代码", 11);
        disklibraryConfig.setColumn("netAddress", "网点位置", 12);
        disklibraryConfig.setColumn("carLng,carLat", "车辆位置", 13, ";");
        disklibraryConfig.setColumn("remark", "备注", 14);
    }


    public static ReportConfig badDrivingAnalysisReportConfig = new ReportConfig("不良驾驶行为");

    static {
        badDrivingAnalysisReportConfig.setColumn("", "序号", 0);
        badDrivingAnalysisReportConfig.setColumn("chassisNum", "底盘号", 1);
        badDrivingAnalysisReportConfig.setColumn("plateNum", "车牌号", 2);
        badDrivingAnalysisReportConfig.setColumn("bdTerCode", "北斗一体机ID", 3);
        badDrivingAnalysisReportConfig.setColumn("fkTerCode", "FK控制器ID", 4);
        badDrivingAnalysisReportConfig.setColumn("tName", "所属经销商", 5);
        badDrivingAnalysisReportConfig.setColumn("businessName", "所属客户", 6);
        badDrivingAnalysisReportConfig.setColumn("carModel", "车辆型号", 7);
        badDrivingAnalysisReportConfig.setColumn("structureNum", "车架号", 8);
        badDrivingAnalysisReportConfig.setColumn("engineType", "发动机类型", 9);


        badDrivingAnalysisReportConfig.setColumn("badDrivingDate", "异常驾驶时间", 10);
        badDrivingAnalysisReportConfig.setColumn("fastSpeedCount", "超速次数", 11);
        badDrivingAnalysisReportConfig.setColumn("accelerateCount", "急加速次数", 12);
        badDrivingAnalysisReportConfig.setColumn("decelerateCount", "急减速次数", 13);
        badDrivingAnalysisReportConfig.setColumn("sharpTurnCount", "急转弯次数", 14);
        badDrivingAnalysisReportConfig.setColumn("longIdlingCount", "超长怠速次数", 15);
        badDrivingAnalysisReportConfig.setColumn("highGearStartCount", "二挡起步次数", 16);
        badDrivingAnalysisReportConfig.setColumn("coldingStartCount", "冷车运行次数", 17);
        badDrivingAnalysisReportConfig.setColumn("nightDrivingCount", "夜晚开车次数", 18);
        badDrivingAnalysisReportConfig.setColumn("lowGearHighSpeedCount", "低挡高速次数", 19);
        badDrivingAnalysisReportConfig.setColumn("fullAcceleratorCount", "全油门次数", 20);
        badDrivingAnalysisReportConfig.setColumn("largeAcceleratorCount", "大油门次数", 21);
    }
}
