package com.navinfo.dongfeng.terminal.comm.handler;

import com.lc.core.protocol.common.LCMessageSign;
import com.lc.core.protocol.terminal.monitor.LCDispatchMessage;
import com.navinfo.dongfeng.terminal.comm.cmd.Gps_2151_Cmd;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.common.handler.NotValidateTokenAbstractHandler;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.result.ReturnCode;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.gps.Gps_2151_DispatchMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class Gps_2151_Handler extends NotValidateTokenAbstractHandler<Gps_2151_Cmd, CommonResult>
{
    @Autowired
    private Gps_2151_DispatchMessage gps_2151_dispatchMessage;
    
    public Gps_2151_Handler()
    {
        super(Gps_2151_Cmd.class, CommonResult.class);
    }
    
    protected Gps_2151_Handler(Class<Gps_2151_Cmd> commandType, Class<CommonResult> resultType)
    {
        super(commandType, resultType);
    }
    
    /**
     * 下发调度短信指令
     *
     * @param cmd
     * @return
     */
    @Override
    public CommonResult businessHandle(Gps_2151_Cmd cmd)
    {
        CommonResult result = new CommonResult();
        try
        {
            List<String> communicationIds = Arrays.asList(cmd.getCommunicationIds().split(","));
            for (String com : communicationIds)
            {
                // 入参转换
                Packet packet = this.getSendPacket(com, cmd);
                // 指令下发
                gps_2151_dispatchMessage.processor(packet);
                logger.warn("指令号:2151,流水号:"+packet.getSerialNumber()+",显示类型:"+cmd.getType()+",消息内容:"+cmd.getContent()+",通讯号:"+cmd.getCommunicationIds());
            }
            
        }
        catch (Exception e)
        {
            result.fillResult(ReturnCode.CMD_2151_ERROR);
            logger.error("===== send Exception =====" + result.getMessage() , e);
        }
        return result;
    }
    
    /**
     * 入参转换
     *
     * @param communicationId 终端通讯号
     * @param cmd
     * @return
     */
    private Packet getSendPacket(String communicationId, Gps_2151_Cmd cmd)
    {
        LCMessageSign.MessageSign messageSign = constructMessageSign(cmd.getType());
        LCDispatchMessage.DispatchMessage dispatchMessage =
            LCDispatchMessage.DispatchMessage.newBuilder()
                .setSigns(messageSign)
                .setMessageContent(cmd.getContent())
                .build();
        Packet packet = new Packet();
        packet.setCommand(cmd.getCommand());
        packet.setUniqueMark(Convert.fillZero(communicationId, 12));
        packet.setContentForBytes(dispatchMessage.toByteArray());
        packet.setSerialNumber(cmd.getSerialNumber());
        return packet;
    }
    
    /**
     * 构建显示方式 类名：MessageSign 域类型 数据类型 字段 描述说明 required bool isUrgent true：紧急； required bool isDisplay true：终端显示器显示
     * required bool isBroadcast true：终端TTS播报 required bool isAdvertiseScreen true：广告屏显示 required bool infoType
     * true:中心导航信息；false：CAN故障码信息；
     *
     * @param showType 显示方式
     * @return
     */
    private LCMessageSign.MessageSign constructMessageSign(Integer showType)
    {
        LCMessageSign.MessageSign.Builder messageSignBuilder = LCMessageSign.MessageSign.newBuilder();
        Constant.DispatchMessageDisplayTypeEnum displayTypeEnum =
            Constant.DispatchMessageDisplayTypeEnum.valueOf(showType);
        switch (displayTypeEnum)
        {
            case URGENT:
                messageSignBuilder.setIsUrgent(true)
                    .setIsDisplay(false)
                    .setIsBroadcast(false)
                    .setIsAdvertiseScreen(false)
                    .setInfoType(false);
                break;
            case TERMINAL_DISPLAY:
                messageSignBuilder.setIsUrgent(false)
                    .setIsDisplay(true)
                    .setIsBroadcast(false)
                    .setIsAdvertiseScreen(false)
                    .setInfoType(false);
                break;
            case TTS:
                messageSignBuilder.setIsUrgent(false)
                    .setIsDisplay(false)
                    .setIsBroadcast(true)
                    .setIsAdvertiseScreen(false)
                    .setInfoType(false);
                break;
            case AD_SCREEN:
                messageSignBuilder.setIsUrgent(false)
                    .setIsDisplay(false)
                    .setIsBroadcast(false)
                    .setIsAdvertiseScreen(true)
                    .setInfoType(false);
                break;
            case CAN_BUS:
                messageSignBuilder.setIsUrgent(false)
                    .setIsDisplay(false)
                    .setIsBroadcast(false)
                    .setIsAdvertiseScreen(false)
                    .setInfoType(false);
                break;
            default:
                logger.info("unsupported show type,showType:" + showType);
                break;
        
        }
        return messageSignBuilder.build();
    }
}
