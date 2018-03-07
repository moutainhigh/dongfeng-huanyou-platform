package com.navinfo.opentsp.dongfeng.system.model;

import com.navinfo.opentsp.dongfeng.common.command.BaseTcpCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.CanBusParam;

import java.util.List;

/**
 * 特殊数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-20
 * @modify
 * @copyright Navi Tsp
 */
public class Gps_2275_Cmd extends BaseTcpCommand {
    private int canChannel;
    private int frameType;
    private int collectionMode;
    private int baudRate;
    private int uploadCycle;
    private int collectionTime;
    private int collectCycle;
    private int alarmLimit;
    private int endianType;
    List<CanBusParam> canBusParamList;

    public int getCanChannel() {
        return canChannel;
    }

    public void setCanChannel(int canChannel) {
        this.canChannel = canChannel;
    }

    public int getFrameType() {
        return frameType;
    }

    public void setFrameType(int frameType) {
        this.frameType = frameType;
    }

    public int getCollectionMode() {
        return collectionMode;
    }

    public void setCollectionMode(int collectionMode) {
        this.collectionMode = collectionMode;
    }

    public int getBaudRate() {
        return baudRate;
    }

    public void setBaudRate(int baudRate) {
        this.baudRate = baudRate;
    }

    public int getUploadCycle() {
        return uploadCycle;
    }

    public void setUploadCycle(int uploadCycle) {
        this.uploadCycle = uploadCycle;
    }

    public int getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(int collectionTime) {
        this.collectionTime = collectionTime;
    }

    public int getCollectCycle() {
        return collectCycle;
    }

    public void setCollectCycle(int collectCycle) {
        this.collectCycle = collectCycle;
    }

    public int getAlarmLimit() {
        return alarmLimit;
    }

    public void setAlarmLimit(int alarmLimit) {
        this.alarmLimit = alarmLimit;
    }

    public int getEndianType() {
        return endianType;
    }

    public void setEndianType(int endianType) {
        this.endianType = endianType;
    }

    public List<CanBusParam> getCanBusParamList() {
        return canBusParamList;
    }

    public void setCanBusParamList(List<CanBusParam> canBusParamList) {
        this.canBusParamList = canBusParamList;
    }
}
