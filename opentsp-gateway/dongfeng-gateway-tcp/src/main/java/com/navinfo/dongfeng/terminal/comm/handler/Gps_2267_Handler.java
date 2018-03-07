package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.setting.parameter.LCCanBusSetting;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2267_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2267_CanBusSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Can数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-10-23
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2267_Handler extends NotValidateTokenAbstractHandler<Gps_2267_Cmd, CommonResult> {

    @Autowired
    private Gps_2267_CanBusSetting gps_2267_canBusSetting;

    public Gps_2267_Handler() {
        super(Gps_2267_Cmd.class, CommonResult.class);
    }

    protected Gps_2267_Handler(Class<Gps_2267_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发Can数据采集汇报设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2267_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2267_canBusSetting.processor(packet);
            logger.warn("指令号:2267 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2267_CAN_SETTING_ERROR);
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
    private Packet getSendPacket(Gps_2267_Cmd cmd) {
        LCCanBusSetting.CanPassage canPassage = LCCanBusSetting.CanPassage.valueOf(cmd.getPassages() + 1);

        LCCanBusSetting.ControlSetting.Builder builder = LCCanBusSetting.ControlSetting.newBuilder();
        builder.setPassages(canPassage);
        if (cmd.getTimingInterval() != null) {
            builder.setTimingInterval(cmd.getTimingInterval());
        }
        if (cmd.getUploadInterval() != null) {
            builder.setUploadInterval(cmd.getUploadInterval());
        }
        LCCanBusSetting.CanBusSetting canBusSetting = LCCanBusSetting.CanBusSetting.newBuilder().addSettings(builder.build()).build();
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(canBusSetting.toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
