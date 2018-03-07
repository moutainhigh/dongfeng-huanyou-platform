package com.navinfo.dongfeng.terminal.comm.common;

import com.lc.core.protocol.common.LCLocationData;
import com.lc.core.protocol.common.LCServerCommonRes;
import com.lc.core.protocol.platform.LCBatchLocationQueryRes;
import com.lc.core.protocol.terminal.LCDownCommonRes;
import com.lc.core.protocol.terminal.monitor.LCAnswerQuestion;
import com.lc.core.protocol.terminal.monitor.LCMediaDataQueryRes;
import com.lc.core.protocol.terminal.monitor.LCTakePhotographyRes;
import com.lc.core.protocol.terminal.monitor.LCTerminalStatusControlRes;
import com.lc.core.protocol.terminal.query.LCCallNameRes;
import com.lc.core.protocol.terminal.query.LCQueryTerminalPropertyRes;
import com.lc.core.protocol.terminal.query.travelrecorder.*;
import com.lc.core.protocol.terminal.query.travelrecorder.block.LCModifyRecord;
import com.lc.core.protocol.terminal.setting.LCParameterQueryRes;
import com.lc.core.protocol.terminal.setting.LCParameterQueryTextRes;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * 流水号转换
 * 
 * @author Administrator
 * 
 */
public class SerialNumberExchange
{
    
    protected final static Logger log = LoggerFactory.getLogger(SerialNumberExchange.class);
    
    /***
     * 流水号转换
     * 
     * @param command
     * @param contant
     * @return byte[]
     */
    public static byte[] LCChangeToClient(String command, byte[] contant)
    {
        byte[] rbyte = contant;
        
        try
        {
            if (command.equals("3001"))
            {
                LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCDownCommonRes.DownCommonRes.Builder builder = LCDownCommonRes.DownCommonRes.newBuilder();
                builder.setResponseId(res.getResponseId());
                builder.setResult(res.getResult());
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                rbyte = builder.build().toByteArray();
            }
            if (command.equals("1100"))
            {
                LCServerCommonRes.ServerCommonRes res = LCServerCommonRes.ServerCommonRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCServerCommonRes.ServerCommonRes.Builder builder = LCServerCommonRes.ServerCommonRes.newBuilder();
                builder.setResponseId(res.getResponseId());
                builder.setResults(res.getResults());
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3050"))
            {
                LCCallNameRes.CallNameRes res = LCCallNameRes.CallNameRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCCallNameRes.CallNameRes.Builder builder = LCCallNameRes.CallNameRes.newBuilder();
                LCLocationData.LocationData.Builder data = LCLocationData.LocationData.newBuilder();
                data.setAlarm(res.getData().getAlarm());
                data.setStatus(res.getData().getStatus());
                data.setLongitude(res.getData().getLongitude());
                data.setLatitude(res.getData().getLatitude());
                data.setOriginalLng(res.getData().getOriginalLng());
                data.setOriginalLat(res.getData().getOriginalLat());
                data.setHeight(res.getData().getHeight());
                data.setSpeed(res.getData().getSpeed());
                data.setDirection(res.getData().getDirection());
                data.setGpsDate(res.getData().getGpsDate());
                data.setMileage(res.getData().getMileage());
                data.setReceiveDate(res.getData().getReceiveDate());
                data.setIsPatch(res.getData().getIsPatch());
                data.setOil(res.getData().getOil());
                data.setRecorderSpeed(res.getData().getRecorderSpeed());
                data.addAllSpeedAddition(res.getData().getSpeedAdditionList());
                data.addAllAreaAddition(res.getData().getAreaAdditionList());
                data.addAllRouteAddition(res.getData().getRouteAdditionList());
                data.addAllTemAlarm(res.getData().getTemAlarmList());
                data.setStarStatus(res.getData().getStarStatus());
                data.setStarNumber(res.getData().getStarNumber());
                
                builder.setData(data.build());
                builder.setResult(res.getResult());
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("1203"))
            {
                LCBatchLocationQueryRes.BatchLocationQueryRes res =
                    LCBatchLocationQueryRes.BatchLocationQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCBatchLocationQueryRes.BatchLocationQueryRes.Builder builder =
                    LCBatchLocationQueryRes.BatchLocationQueryRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.addAllLocationObject(res.getLocationObjectList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3055"))
            {
                LCTRVersionRes.TRVersionRes res = LCTRVersionRes.TRVersionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRVersionRes.TRVersionRes.Builder builder = LCTRVersionRes.TRVersionRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setVersionYear(res.getVersionYear());
                builder.setModifyVersion(res.getModifyVersion());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3056"))
            {
                LCTRSpeedCollectionRes.TRSpeedCollectionRes res =
                    LCTRSpeedCollectionRes.TRSpeedCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRSpeedCollectionRes.TRSpeedCollectionRes.Builder builder =
                    LCTRSpeedCollectionRes.TRSpeedCollectionRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllSpeedDataBlock(res.getSpeedDataBlockList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3057"))
            {
                LCTRDoubtCollectionRes.TRDoubtCollectionRes res =
                    LCTRDoubtCollectionRes.TRDoubtCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRDoubtCollectionRes.TRDoubtCollectionRes.Builder builder =
                    LCTRDoubtCollectionRes.TRDoubtCollectionRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllDoubtDataBlock(res.getDoubtDataBlockList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3058"))
            {
                LCTRPulseCollectionRes.TRPulseCollectionRes res =
                    LCTRPulseCollectionRes.TRPulseCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRPulseCollectionRes.TRPulseCollectionRes.Builder builder =
                    LCTRPulseCollectionRes.TRPulseCollectionRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setPulseFactor(res.getPulseFactor());
                builder.setCurrentDate(res.getCurrentDate());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3160"))
            {
                LCAnswerQuestion.AnswerQuestion res = LCAnswerQuestion.AnswerQuestion.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCAnswerQuestion.AnswerQuestion.Builder builder = LCAnswerQuestion.AnswerQuestion.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setAnswerId(res.getAnswerId());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3302"))
            {
                LCParameterQueryRes.ParameterQueryRes res = LCParameterQueryRes.ParameterQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCParameterQueryRes.ParameterQueryRes.Builder builder =
                    LCParameterQueryRes.ParameterQueryRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                if (res.hasCanbus())
                    builder.setCanbus(res.getCanbus());
                if (res.hasCollision())
                    builder.setCollision(res.getCollision());
                if (res.hasConfig())
                    builder.setConfig(res.getConfig());
                if (res.hasDriving())
                    builder.setDriving(res.getDriving());
                if (res.hasGnss())
                    builder.setGnss(res.getGnss());
                if (res.hasInterval())
                    builder.setInterval(res.getInterval());
                if (res.hasNumbers())
                    builder.setNumbers(res.getNumbers());
                if (res.hasParameter())
                    builder.setParameter(res.getParameter());
                if (res.hasParking())
                    builder.setParking(res.getParking());
                if (res.hasPermit())
                    builder.setPermit(res.getPermit());
                if (res.hasPicture())
                    builder.setPicture(res.getPicture());
                if (res.hasProcess())
                    builder.setProcess(res.getProcess());
                if (res.hasRollover())
                    builder.setRollover(res.getRollover());
                if (res.hasSetting())
                    builder.setSetting(res.getSetting());
                if (res.hasSpeeding())
                    builder.setSpeeding(res.getSpeeding());
                if (res.hasVehiclesetting())
                    builder.setVehiclesetting(res.getVehiclesetting());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3303"))
            {
                LCParameterQueryTextRes.ParameterQueryTextRes res =
                    LCParameterQueryTextRes.ParameterQueryTextRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCParameterQueryTextRes.ParameterQueryTextRes.Builder builder =
                    LCParameterQueryTextRes.ParameterQueryTextRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setParaContent(res.getParaContent());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3153"))
            {
                LCTakePhotographyRes.TakePhotographyRes res = LCTakePhotographyRes.TakePhotographyRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTakePhotographyRes.TakePhotographyRes.Builder builder = LCTakePhotographyRes.TakePhotographyRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResults(res.getResults());
                builder.setResult(res.getResult());// 20150107,caozhen,位置云调整新增
                builder.addAllMediaIdentify(res.getMediaIdentifyList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3408"))
            {
                // TerminalUpgradePackageRes res=TerminalUpgradePackageRes.parseFrom(contant);
                // String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                // String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                // TerminalUpgradePackageRes.Builder builder=TerminalUpgradePackageRes.newBuilder();
                
            }
            else if (command.equals("3060"))
            {
                LCTRCollectDriverInfoRes.TRCollectDriverInfoRes res = LCTRCollectDriverInfoRes.TRCollectDriverInfoRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectDriverInfoRes.TRCollectDriverInfoRes.Builder builder = LCTRCollectDriverInfoRes.TRCollectDriverInfoRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setDriverLicenseNumber(res.getDriverLicenseNumber());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3061"))
            {
                LCTRCollectRealTimeRes.TRCollectRealTimeRes res = LCTRCollectRealTimeRes.TRCollectRealTimeRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectRealTimeRes.TRCollectRealTimeRes.Builder builder = LCTRCollectRealTimeRes.TRCollectRealTimeRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setCurrentDate(res.getCurrentDate());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3062"))
            {
                LCTRCollectMileageRes.TRCollectMileageRes res = LCTRCollectMileageRes.TRCollectMileageRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectMileageRes.TRCollectMileageRes.Builder builder = LCTRCollectMileageRes.TRCollectMileageRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setCurrentDate(res.getCurrentDate());
                builder.setInstallDate(res.getInstallDate());
                builder.setOriginalMileage(res.getOriginalMileage());
                builder.setCumulativeMileage(res.getCumulativeMileage());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3063"))
            {
                LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes res = LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes.Builder builder = LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setIdentifyCode(res.getIdentifyCode().replaceAll(" ", ""));
                builder.setLicenseNumber(res.getLicenseNumber());
                builder.setLicenseType(res.getLicenseType());
                rbyte = builder.build().toByteArray();
                // System.out.println(res.getIdentifyCode()+"---"+res.getLicenseNumber()+"----"+res.getLicenseType());
            }
            else if (command.equals("3064"))
            {
                LCTRCollectStatusSignalRes.TRCollectStatusSignalRes res = LCTRCollectStatusSignalRes.TRCollectStatusSignalRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectStatusSignalRes.TRCollectStatusSignalRes.Builder builder = LCTRCollectStatusSignalRes.TRCollectStatusSignalRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setCurrentDate(res.getCurrentDate());
                builder.addAllDatas(res.getDatasList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3065"))
            {
                LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes res = LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes.Builder builder = LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setManufacturerAuthCode(res.getManufacturerAuthCode());
                builder.setProductsAuthCode(res.getProductsAuthCode());
                builder.setProductDate(res.getProductDate());
                builder.setProductsCode(res.getProductsCode());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3066"))
            {
                LCTRCollectLocationDataRes.TRCollectLocationDataRes res = LCTRCollectLocationDataRes.TRCollectLocationDataRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectLocationDataRes.TRCollectLocationDataRes.Builder builder = LCTRCollectLocationDataRes.TRCollectLocationDataRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllBlocks(res.getBlocksList());
                rbyte = builder.build().toByteArray();
                for (LCTRCollectLocationDataRes.LocationDataBlock db : res.getBlocksList())
                {
                    // System.out.println("num:"+num+", getBeginDate:"+db.getBeginDate()+", getDatasCount:"+db.getDatasCount()+", ");
                }
            }
            else if (command.equals("3067"))
            {
                LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes res = LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes.Builder builder = LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllBlocks(res.getBlocksList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3068"))
            {
                LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes res = LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes.Builder builder = LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllRecords(res.getRecordsList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3069"))
            {
                LCTRCollectPowerRecordRes.TRCollectPowerRecordRes res = LCTRCollectPowerRecordRes.TRCollectPowerRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectPowerRecordRes.TRCollectPowerRecordRes.Builder builder = LCTRCollectPowerRecordRes.TRCollectPowerRecordRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllRecords(res.getRecordsList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3070"))
            {
                LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes res = LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes.Builder builder = LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllRecords(res.getRecordsList());
                rbyte = builder.build().toByteArray();
                for (LCModifyRecord.ModifyRecord db : res.getRecordsList())
                {
                    // System.out.println("3070,num:"+num+", getOccurDate:"+db.getOccurDate()+", getTypes:"+db.getTypes()+", ");
                }
            }
            else if (command.equals("3071"))
            {
                LCTRCollectSpeedLogRes.TRCollectSpeedLogRes res = LCTRCollectSpeedLogRes.TRCollectSpeedLogRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTRCollectSpeedLogRes.TRCollectSpeedLogRes.Builder builder = LCTRCollectSpeedLogRes.TRCollectSpeedLogRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllLogBlocks(res.getLogBlocksList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3134"))
            {
                LCQueryTerminalPropertyRes.QueryTerminalPropertyRes res = LCQueryTerminalPropertyRes.QueryTerminalPropertyRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCQueryTerminalPropertyRes.QueryTerminalPropertyRes.Builder builder = LCQueryTerminalPropertyRes.QueryTerminalPropertyRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setType(res.getType());
                builder.setManufacturerId(res.getManufacturerId());
                builder.setTerminalModel(res.getTerminalModel());
                builder.setTerminalIdentity(res.getTerminalIdentity());
                builder.setTerminalICCID(res.getTerminalICCID());
                builder.setHardwareVersion(res.getHardwareVersion());
                builder.setFirmwareVersion(res.getFirmwareVersion());
                builder.setProperty(res.getProperty());
                builder.setModule(res.getModule());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3165"))
            {
                LCMediaDataQueryRes.MediaDataQueryRes res = LCMediaDataQueryRes.MediaDataQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCMediaDataQueryRes.MediaDataQueryRes.Builder builder = LCMediaDataQueryRes.MediaDataQueryRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.addAllQueryData(res.getQueryDataList());
                rbyte = builder.build().toByteArray();
            }
            else if (command.equals("3170"))
            {
                LCTerminalStatusControlRes.TerminalStatusControlRes res = LCTerminalStatusControlRes.TerminalStatusControlRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                LCTerminalStatusControlRes.TerminalStatusControlRes.Builder builder = LCTerminalStatusControlRes.TerminalStatusControlRes.newBuilder();
                builder.setSerialNumber(Integer.parseInt(oldNum, 16));
                builder.setResult(res.getResult());
                builder.setControlType(res.getControlType());
                builder.setTypes(res.getTypes());
                rbyte = builder.build().toByteArray();
            }
            
        }
        catch (Exception e)
        {
            log.error("发送前端时-流水号转换异常", e);
        }
        
        return rbyte;
    }
    
    /***
     * 获取客户端链路
     * 
     * @param command
     * @param contant
     * @return IoSession
     */
    public static IoSession linkToClient(String command, byte[] contant)
    {
        try
        {
            if (command.equals("3001"))
            {
                LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                if (oldNumKey != null && !oldNumKey.equals(""))
                    return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
                else
                    return null;
            }
            else if (command.equals("1100"))
            {
                LCServerCommonRes.ServerCommonRes res = LCServerCommonRes.ServerCommonRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num);
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3050"))
            {
                LCCallNameRes.CallNameRes res = LCCallNameRes.CallNameRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num);
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("1203"))
            {
                LCBatchLocationQueryRes.BatchLocationQueryRes res = LCBatchLocationQueryRes.BatchLocationQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3055"))
            {
                LCTRVersionRes.TRVersionRes res = LCTRVersionRes.TRVersionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3056"))
            {
                LCTRSpeedCollectionRes.TRSpeedCollectionRes res = LCTRSpeedCollectionRes.TRSpeedCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3057"))
            {
                LCTRDoubtCollectionRes.TRDoubtCollectionRes res = LCTRDoubtCollectionRes.TRDoubtCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3058"))
            {
                LCTRPulseCollectionRes.TRPulseCollectionRes res = LCTRPulseCollectionRes.TRPulseCollectionRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3160"))
            {
                LCAnswerQuestion.AnswerQuestion res = LCAnswerQuestion.AnswerQuestion.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3302"))
            {
                LCParameterQueryRes.ParameterQueryRes res = LCParameterQueryRes.ParameterQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3303"))
            {
                LCParameterQueryTextRes.ParameterQueryTextRes res = LCParameterQueryTextRes.ParameterQueryTextRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3153"))
            {
                LCTakePhotographyRes.TakePhotographyRes res = LCTakePhotographyRes.TakePhotographyRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3060"))
            {
                LCTRCollectDriverInfoRes.TRCollectDriverInfoRes res = LCTRCollectDriverInfoRes.TRCollectDriverInfoRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3061"))
            {
                LCTRCollectRealTimeRes.TRCollectRealTimeRes res = LCTRCollectRealTimeRes.TRCollectRealTimeRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3062"))
            {
                LCTRCollectMileageRes.TRCollectMileageRes res = LCTRCollectMileageRes.TRCollectMileageRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3063"))
            {
                LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes res = LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3064"))
            {
                LCTRCollectStatusSignalRes.TRCollectStatusSignalRes res = LCTRCollectStatusSignalRes.TRCollectStatusSignalRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3065"))
            {
                LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes res = LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3066"))
            {
                LCTRCollectLocationDataRes.TRCollectLocationDataRes res = LCTRCollectLocationDataRes.TRCollectLocationDataRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3067"))
            {
                LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes res = LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3068"))
            {
                LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes res = LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3069"))
            {
                LCTRCollectPowerRecordRes.TRCollectPowerRecordRes res = LCTRCollectPowerRecordRes.TRCollectPowerRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3070"))
            {
                LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes res = LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3071"))
            {
                LCTRCollectSpeedLogRes.TRCollectSpeedLogRes res = LCTRCollectSpeedLogRes.TRCollectSpeedLogRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3134"))
            {
                LCQueryTerminalPropertyRes.QueryTerminalPropertyRes res = LCQueryTerminalPropertyRes.QueryTerminalPropertyRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3165"))
            {
                LCMediaDataQueryRes.MediaDataQueryRes res = LCMediaDataQueryRes.MediaDataQueryRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
            else if (command.equals("3170"))
            {
                LCTerminalStatusControlRes.TerminalStatusControlRes res = LCTerminalStatusControlRes.TerminalStatusControlRes.parseFrom(contant);
                String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                String oldNumKey = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "");
                return SerialNumberCache.instance().getSerialNumberSessionCache(oldNumKey);
            }
        }
        catch (Exception e)
        {
            log.error("发送前端时-获取客户端链路异常", e);
        }
        return null;
    }
    
    /*********
     * 获取protobuffer内的流水号到响应流水号字段
     * 
     * @param packet
     * @return
     */
    public static Packet serialNumberChange(Packet packet)
    {
        try
        {
            if (packet.getCommand().equals("3001"))
            {
                LCDownCommonRes.DownCommonRes res = LCDownCommonRes.DownCommonRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            if (packet.getCommand().equals("1100"))
            {
                LCServerCommonRes.ServerCommonRes res = LCServerCommonRes.ServerCommonRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
            }
            else if (packet.getCommand().equals("3050"))
            {
                LCCallNameRes.CallNameRes res = LCCallNameRes.CallNameRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("1203"))
            {
                LCBatchLocationQueryRes.BatchLocationQueryRes res = LCBatchLocationQueryRes.BatchLocationQueryRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
            }
            else if (packet.getCommand().equals("3055"))
            {
                LCTRVersionRes.TRVersionRes res = LCTRVersionRes.TRVersionRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3056"))
            {
                LCTRSpeedCollectionRes.TRSpeedCollectionRes res = LCTRSpeedCollectionRes.TRSpeedCollectionRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3057"))
            {
                LCTRDoubtCollectionRes.TRDoubtCollectionRes res = LCTRDoubtCollectionRes.TRDoubtCollectionRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3058"))
            {
                LCTRPulseCollectionRes.TRPulseCollectionRes res = LCTRPulseCollectionRes.TRPulseCollectionRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3160"))
            {
                LCAnswerQuestion.AnswerQuestion res = LCAnswerQuestion.AnswerQuestion.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
            }
            else if (packet.getCommand().equals("3302"))
            {
                LCParameterQueryRes.ParameterQueryRes res = LCParameterQueryRes.ParameterQueryRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3303"))
            {
                LCParameterQueryTextRes.ParameterQueryTextRes res = LCParameterQueryTextRes.ParameterQueryTextRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3153"))
            {
                LCTakePhotographyRes.TakePhotographyRes res = LCTakePhotographyRes.TakePhotographyRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3408"))
            {
                // TerminalUpgradePackageRes res=TerminalUpgradePackageRes.parseFrom(contant);
                // String num = Convert.decimalToHexadecimal(res.getSerialNumber(), 4);
                // String oldNum = SerialNumberCache.instance().getGpsSerialNumberSessionCache(num + "").split("_")[0];
                // TerminalUpgradePackageRes.Builder builder=TerminalUpgradePackageRes.newBuilder();

            }
            else if (packet.getCommand().equals("3060"))
            {
                LCTRCollectDriverInfoRes.TRCollectDriverInfoRes res = LCTRCollectDriverInfoRes.TRCollectDriverInfoRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3061"))
            {
                LCTRCollectRealTimeRes.TRCollectRealTimeRes res = LCTRCollectRealTimeRes.TRCollectRealTimeRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3062"))
            {
                LCTRCollectMileageRes.TRCollectMileageRes res = LCTRCollectMileageRes.TRCollectMileageRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3063"))
            {
                LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes res = LCTRCollectVehicleInfoRes.TRCollectVehicleInfoRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3064"))
            {
                LCTRCollectStatusSignalRes.TRCollectStatusSignalRes res = LCTRCollectStatusSignalRes.TRCollectStatusSignalRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3065"))
            {
                LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes res = LCTRCollectOnlyCodeRes.TRCollectOnlyCodeRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3066"))
            {
                LCTRCollectLocationDataRes.TRCollectLocationDataRes res = LCTRCollectLocationDataRes.TRCollectLocationDataRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3067"))
            {
                LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes res = LCTRCollectOvertimeRecordRes.TRCollectOvertimeRecordRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3068"))
            {
                LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes res = LCTRCollectDriverIdentityRes.TRCollectDriverIdentityRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3069"))
            {
                LCTRCollectPowerRecordRes.TRCollectPowerRecordRes res = LCTRCollectPowerRecordRes.TRCollectPowerRecordRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3070"))
            {
                LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes res = LCTRCollectParaModifyRecordRes.TRCollectParaModifyRecordRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3071"))
            {
                LCTRCollectSpeedLogRes.TRCollectSpeedLogRes res = LCTRCollectSpeedLogRes.TRCollectSpeedLogRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3134"))
            {
                LCQueryTerminalPropertyRes.QueryTerminalPropertyRes res = LCQueryTerminalPropertyRes.QueryTerminalPropertyRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3165"))
            {
                LCMediaDataQueryRes.MediaDataQueryRes res = LCMediaDataQueryRes.MediaDataQueryRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            else if (packet.getCommand().equals("3170"))
            {
                LCTerminalStatusControlRes.TerminalStatusControlRes res = LCTerminalStatusControlRes.TerminalStatusControlRes.parseFrom(packet.getContentForBytes());
                packet.setAnswerSerialNumber(res.getSerialNumber() + "");
                packet.addParameter("result", res.getResult().getNumber() + "");
            }
            
        }
        catch (Exception e)
        {
            log.error("发送前端时-流水号转换异常", e);
        }
        return packet;
    }

}
