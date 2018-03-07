package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.setting.parameter.LCRealTimeDataCollect;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2274_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2274_RealTimeDataCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Can数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-20
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2274_Handler extends NotValidateTokenAbstractHandler<Gps_2274_Cmd, CommonResult> {
    @Autowired
    private Gps_2274_RealTimeDataCollect gps_2274_realTimeDataCollect;

    public Gps_2274_Handler() {
        super(Gps_2274_Cmd.class, CommonResult.class);
    }

    protected Gps_2274_Handler(Class<Gps_2274_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发违规驾驶行为阀值设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2274_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2274_realTimeDataCollect.processor(packet);
            logger.warn("指令号:2274 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2274_REAL_TIME_DATA_COLLECTION_ERROR);
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
    private Packet getSendPacket(Gps_2274_Cmd cmd) {
        LCRealTimeDataCollect.RealTimeDataCollect realTimeDataCollect = LCRealTimeDataCollect.RealTimeDataCollect.newBuilder()
                .setCollectCycle(cmd.getCollectCycle())
                .setCollectionTime(cmd.getCollectionTime())
                .setUploadCycle(cmd.getUploadCycle())
                .build();
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(realTimeDataCollect.toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
