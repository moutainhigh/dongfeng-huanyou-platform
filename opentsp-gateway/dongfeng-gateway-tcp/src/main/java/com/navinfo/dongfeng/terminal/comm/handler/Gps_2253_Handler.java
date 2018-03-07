package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.terminal.setting.parameter.LCReportTacticsAndInterval;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2253_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2253_ReportTacticsAndInterval;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 位置数据采集汇报设置
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-04-11
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2253_Handler extends NotValidateTokenAbstractHandler<Gps_2253_Cmd, CommonResult> {
    @Autowired
    private Gps_2253_ReportTacticsAndInterval gps_2253_reportTacticsAndInterval;

    public Gps_2253_Handler() {
        super(Gps_2253_Cmd.class, CommonResult.class);
    }

    protected Gps_2253_Handler(Class<Gps_2253_Cmd> commandType, Class<CommonResult> resultType) {
        super(commandType, resultType);
    }

    /**
     * 下发位置数据采集汇报设置
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2253_Cmd cmd) {
        CommonResult result = new CommonResult();
        try {
            // 入参转换
            Packet packet = this.getSendPacket(cmd);
            // 指令下发
            gps_2253_reportTacticsAndInterval.processor(packet);
            logger.warn("指令号:2253 流水号:" + packet.getSerialNumber());
        } catch (Exception e) {
            result.fillResult(ReturnCode.CMD_2253_POSITION_SETTING_ERROR);
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
    private Packet getSendPacket(Gps_2253_Cmd cmd) {
        LCReportTacticsAndInterval.ReportTacticsAndInterval.Builder builder =
                LCReportTacticsAndInterval.ReportTacticsAndInterval.newBuilder();
        // LCReportTacticsAndInterval.ReportTacticsAndInterval.ReportProgram reportProgram =
        // LCReportTacticsAndInterval.ReportTacticsAndInterval.ReportProgram.valueOf(cmd.getProgram());
        // builder.setProgram(reportProgram);
        LCReportTacticsAndInterval.ReportTacticsAndInterval.ReportTactics reportTactics = LCReportTacticsAndInterval.ReportTacticsAndInterval.ReportTactics.valueOf(cmd.getTactics());
        builder.setTactics(reportTactics);// 位置汇报策略 0:定时 1:定距 2：定时定距
        builder.setDefaultTime(cmd.getDefaultTime());// 缺省时间间隔
        builder.setDefaultDistance(cmd.getDefaultDistance());// 缺省距离间隔
        builder.setInflectionAngle(cmd.getInflectionAngle());// 拐点补偿设置(<180度)
        // builder.setFenceRadius(cmd.getFenceRadius());// 电子围栏半径（非法位移阀值），单位米
        builder.setNotLoginDistance(cmd.getNotLoginDistance());// 未登录距离间隔
        builder.setNotLoginTime(cmd.getNotLoginTime());// 未登录时间间隔
        builder.setSleepingDistance(cmd.getSleepingDistance());// 休眠距离间隔
        builder.setSleepingTime(cmd.getSleepingTime());// 休眠时间间隔
        builder.setUrgentDistance(cmd.getUrgentDistance());// 紧急报警距离间隔
        builder.setUrgentTime(cmd.getUrgentTime());// 紧急报警时间间隔
        builder.setAlarmInterval(cmd.getIllegalDrivingTime());
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setContentForBytes(builder.build().toByteArray());
        packet.setUniqueMark(Convert.fillZero(cmd.getUniqueMark(), 12));
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
}
