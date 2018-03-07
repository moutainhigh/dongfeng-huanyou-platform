package com.navinfo.dongfeng.terminal.comm.task;

import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/4.
 */
@Component
public class ReConnectCloudServerTask {
    private static final Logger log = LoggerFactory
            .getLogger(ReConnectCloudServerTask.class);

    @Value("${CloudServerIp}")
    private String cloudServerIp;

    @Value("${CloudServerPort}")
    private String cloudServerPort;

    @Scheduled(fixedDelay = 30000, initialDelay = 75000)
    public void execute() throws InterruptedException {
        try {
            // if (CloudServerStart.getConnector() == null) {
            String stIp = "";
            String stPort = "";
            Map<String, IoSession> links = CloudLinkCache.instance().getcaches();
            /*******************rp重连操作************************/
            List<String> list = CloudLinkCache.instance().getRpIpPortCache();
            if (null != list && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    stIp = list.get(i).split(":")[0];
                    stPort = list.get(i).split(":")[1];
                    IoSession link = links.get(stIp + ":" + stPort);
                    if (link != null) {//链路不存在则重连
                        CloudServerStart cloudServerStart = (CloudServerStart) link.getAttribute("cloudServerStart");
                        if (!(Boolean) link.getAttribute("isconnect")) {
                            cloudServerStart.connect();//未连接重新连接
                        }
                        if ((Boolean) link.getAttribute("isconnect") && cloudServerStart.getIsSucces() < 1) {//未登录成功,重新连接
                            cloudServerStart.stopServer();
                            cloudServerStart.connect();//重新连接
                        }
                    } else {//session 不存在时rp链路重连
                        CloudServerStart cloudServerStart = new CloudServerStart();
                        cloudServerStart.setIp(stIp);
                        cloudServerStart.setPort(Integer.valueOf(stPort));
                        cloudServerStart.setServerType(2);
                        cloudServerStart.connect();
                    }
                }
            } else {
                log.error("请检测下行链路rp服务IP和端口！");
            }
            // }
            /*******************MM重连操作********************/
            new ReConnectCloudMMTask().executeOnce(cloudServerIp, cloudServerPort);

        } catch (Exception e) {
            log.error("重连rp异常", e);
        }
    }
}
