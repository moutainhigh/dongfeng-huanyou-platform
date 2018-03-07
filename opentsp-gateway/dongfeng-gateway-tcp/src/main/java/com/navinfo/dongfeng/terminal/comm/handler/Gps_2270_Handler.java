package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.monitor.LCDrivingBehaviorSetting;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2270_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2270_DrivingBehaviorSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 违规驾驶行为阀值设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2270_Handler extends NotValidateTokenAbstractHandler<Gps_2270_Cmd, CommonResult> {

    /**
     * 未选中驾驶行为项
     */
    private static final int DRIVER_BEHAVIOR_UN_SELECTED = -1;

    @Autowired
    private Gps_2270_DrivingBehaviorSetting gps_2270_drivingBehaviorSetting;

    public Gps_2270_Handler() {
        super(Gps_2270_Cmd.class, CommonResult.class);
    }

    protected Gps_2270_Handler(Class<Gps_2270_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发违规驾驶行为阀值设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2270_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2270_drivingBehaviorSetting.processor(packet);
            logger.warn("指令号:2270 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2270_DRIVING_BEHAVIOR_ERROR);
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
    private Packet getSendPacket(Gps_2270_Cmd cmd) {
        LCDrivingBehaviorSetting.DrivingBehaviorSetting.Builder drivingBehaviorBuilder =
                LCDrivingBehaviorSetting.DrivingBehaviorSetting.newBuilder();
        if (cmd.getAcceleration() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setAcceleration(cmd.getAcceleration());
        }
        if (cmd.getDeceleration() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setDeceleration(cmd.getDeceleration());
        }
        if (cmd.getLowOil() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setLowOil(cmd.getLowOil());
        }
        if (cmd.getSlideNeutral() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setSlideNeutral(cmd.getSlideNeutral());
        }
        if (cmd.getRpmThreshold() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setRpmThreshold(cmd.getRpmThreshold());
        }
        if (cmd.getThrottle() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setThrottle(cmd.getThrottle());
        }
        if (cmd.getDisparitySpeed() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setDisparitySpeed(cmd.getDisparitySpeed());
        }
        if (cmd.getBrakePressure() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setBrakePressure(cmd.getBrakePressure());
        }
        if (cmd.getLongTimeBreaking() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setLongTimeBreaking(cmd.getLongTimeBreaking());
        }
        if (cmd.getLongTimeClutch() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setLongTimeClutch(cmd.getLongTimeClutch());
        }
        if (cmd.getParkedTime() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setParkedTimke(cmd.getParkedTime());
        }
        if (cmd.getColdBootTime() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setColdBootTime(cmd.getColdBootTime());
        }
        if (cmd.getIdlingTime() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setIdlingTime(cmd.getIdlingTime());
        }
        if (cmd.getSharpTurning() != DRIVER_BEHAVIOR_UN_SELECTED) {
            drivingBehaviorBuilder.setSharpTurning(cmd.getSharpTurning());
        }
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(drivingBehaviorBuilder.build().toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
