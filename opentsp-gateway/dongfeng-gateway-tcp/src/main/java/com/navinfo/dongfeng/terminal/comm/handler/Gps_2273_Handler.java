package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.monitor.LCCANDataReportSetting;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2273_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2273_CANDataReportSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 终端初始化设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2273_Handler extends NotValidateTokenAbstractHandler<Gps_2273_Cmd, CommonResult> {

    @Autowired
    private Gps_2273_CANDataReportSetting gps_2273_canDataReportSetting;

    public Gps_2273_Handler() {
        super(Gps_2273_Cmd.class, CommonResult.class);
    }

    protected Gps_2273_Handler(Class<Gps_2273_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发Can数据采集汇报设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2273_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2273_canDataReportSetting.processor(packet);
            logger.warn("指令号:2273 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2273_INIT_SETTING_ERROR);
            logger.error("===== send Exception =====" + result.getMessage(), e);
        }
        return result;
    }

    /**
     * 入参转换
     *
     * @param cmd
     * @return
     */
    private Packet getSendPacket(Gps_2273_Cmd cmd) {
        LCCANDataReportSetting.CANDataReportSetting.Builder builder =
                LCCANDataReportSetting.CANDataReportSetting.newBuilder();

        builder.setEngineType(cmd.getEngineType());// 发动机类型
        builder.setVehType(cmd.getVehType());// 适用车辆类型
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(builder.build().toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
