package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.setting.parameter.LCCanDataCycleUploadSetting;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2275_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.model.system.terminal.CanBusParam;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2275_CanDataCycleUploadSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 殊数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-20
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2275_Handler extends NotValidateTokenAbstractHandler<Gps_2275_Cmd, CommonResult> {
    @Autowired
    private Gps_2275_CanDataCycleUploadSetting gps_2275_canDataCycleUploadSetting;

    public Gps_2275_Handler() {
        super(Gps_2275_Cmd.class, CommonResult.class);
    }

    protected Gps_2275_Handler(Class<Gps_2275_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发违规驾驶行为阀值设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2275_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2275_canDataCycleUploadSetting.processor(packet);
            logger.warn("指令号:2275 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2275_CAN_DATA_CYCLE_UPLOAD_SETTING_ERROR);
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendPacket(Gps_2275_Cmd cmd) {
        List<LCCanDataCycleUploadSetting.CanDataCycleUploadItems> itemsList = null;
        if (cmd.getCanBusParamList() != null) {
            itemsList = new ArrayList<>(cmd.getCanBusParamList().size());
            for (CanBusParam canBusParam : cmd.getCanBusParamList()) {
                LCCanDataCycleUploadSetting.CanDataCycleUploadItems uploadItem = LCCanDataCycleUploadSetting.CanDataCycleUploadItems.newBuilder()
                        .setParaId(canBusParam.getPid())
                        .setCanId(Integer.parseInt(canBusParam.getCanId(), 16))
                        .setBegin(canBusParam.getBegin())
                        .setOffset(canBusParam.getOffset())
                        .build();
                itemsList.add(uploadItem);
            }
        }
        LCCanDataCycleUploadSetting.CanDataCycleUploadSetting canDataCycleUploadSetting = LCCanDataCycleUploadSetting.CanDataCycleUploadSetting.newBuilder()
                .setCanChannel(cmd.getCanChannel())
                .setFrameType(cmd.getFrameType())
                .setCollectionMode(cmd.getCollectionMode())
                .setBaudRate(cmd.getBaudRate())
                .setUploadCycle(cmd.getUploadCycle())
                .setCollectionTime(cmd.getCollectionTime())
                .setAlarmLimit(cmd.getAlarmLimit())
                .setEndianType(cmd.getEndianType())
                .addAllItems(itemsList)
                .build();
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(canDataCycleUploadSetting.toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
