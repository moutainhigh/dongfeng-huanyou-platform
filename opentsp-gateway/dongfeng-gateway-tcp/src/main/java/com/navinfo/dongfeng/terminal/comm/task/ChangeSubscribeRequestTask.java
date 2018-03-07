package com.navinfo.dongfeng.terminal.comm.task;

import com.navinfo.dongfeng.terminal.comm.cache.TerminalSubscribeCache;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.model.cache.TerminalSubscribeInfo;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.monitor.Monitor_0200_SubscribeRequest;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/*****
 * 增量订阅请求定时任务
 *
 * @author Administrator
 */
@Component
public class ChangeSubscribeRequestTask {
    private final static Logger log = LoggerFactory.getLogger(ChangeSubscribeRequestTask.class);
    @Resource
    private TerminalSubscribeCache terminalSubscribeCache;
    @Resource
    private Monitor_0200_SubscribeRequest monitor_0200_subscribeRequest;
    @Value("${CloudServerIp}")
    private String cloudServerIp;

    @Value("${CloudServerPort}")
    private String cloudServerPort;

    @Scheduled(fixedDelay = 60000, initialDelay = 75000)
    public void execute() {
        log.info("增量订阅定时任务运行开始");
        try {
            List<TerminalSubscribeInfo> allSubscribeTerminal = terminalSubscribeCache.getAllSubscribeTerminal();
            List<Long> oldList = new ArrayList<Long>();
            List<Long> newList = new ArrayList<Long>();
            for (TerminalSubscribeInfo terminalSubscribeInfo : allSubscribeTerminal) {
                if (System.currentTimeMillis() - terminalSubscribeInfo.getTimestamp() > 300000) {
                    oldList.add(terminalSubscribeInfo.getCommunicationId());
                } else {
                    newList.add(terminalSubscribeInfo.getCommunicationId());

                }
            }
            terminalAddSubscribe(newList);
            removeSubscribeTerminal(oldList);
        } catch (Exception e) {
            log.error("增量订阅定时任务运行异常：" , e);

        }
        log.info("增量订阅定时任务运行结束");
    }

    /**
     * 增量订阅
     *
     * @param commmunicationIds
     */
    private void terminalAddSubscribe(List<Long> commmunicationIds) {
        CloudServerStart temp = null;
        IoSession ioSession = null;
        Map<String, IoSession> sessions = CloudLinkCache.instance().getcaches();
        String CloudConfig = cloudServerIp + ":" + cloudServerPort;
        for (String ipPort : sessions.keySet()) {
            if (!ipPort.equals(CloudConfig)) {//不为位置云主服务器（即配置文件拍照的链路）发送数据订阅
                ioSession = sessions.get(ipPort);
                temp = (CloudServerStart) ioSession.getAttribute("cloudServerStart");
                if (temp.getServerType() == 1) {//分MM重连
                    Packet sendPacket = getSendPacket(commmunicationIds);
                    sendPacket.setSession(ioSession);
                    monitor_0200_subscribeRequest.processor(sendPacket);
                }
            }
        }
        monitor_0200_subscribeRequest.processor(getSendPacket(commmunicationIds));

    }

    /**
     * 删除过期的缓存
     *
     * @param commmunicationIds
     */
    private void removeSubscribeTerminal(List<Long> commmunicationIds) {
        for (Long commmunicationId : commmunicationIds) {
            terminalSubscribeCache.deleteSubscribeTerminal(commmunicationId);
        }
    }


    /**
     * 入参转换
     *
     * @param communicationIds
     * @return
     */
    private Packet getSendPacket(List<Long> communicationIds) {
        Packet packet = new Packet();
        packet.setCommand("0200");
        packet.addParameter("Add", "true");  //添加参数ADD为true表示增量
        packet.addObject("carTerminals", communicationIds);//将增量车辆终端添加入参数中
        return packet;
    }
}

