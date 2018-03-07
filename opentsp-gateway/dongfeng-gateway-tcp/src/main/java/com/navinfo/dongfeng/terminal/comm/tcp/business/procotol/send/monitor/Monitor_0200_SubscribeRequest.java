package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.send.monitor;

import com.lc.core.protocol.platform.LCSubscribeRequest;
import com.lc.core.protocol.platform.auth.LCRequestLoginKeyRes;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.mapper.system.car.HyTerminalMapper;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 订阅请求 分增量订阅和全量订阅
 */
@Component
public class Monitor_0200_SubscribeRequest extends Command {

    @Autowired
    private HyTerminalMapper hyTerminalMapper;

    @Override
    public Object processor(Packet packet) {

        try {
            LCSubscribeRequest.SubscribeRequest.Builder builder = LCSubscribeRequest.SubscribeRequest.newBuilder();
            // 增量订阅
            if (packet.getParameter("Add") != null && packet.getParameter("Add").equals("true")) {
                List<Long> communications = (List<Long>) packet.getObject("carTerminals");
                for (Long c : communications) {
                    builder.addTerminalIdentify(c);
                }
            } else {// 全量订阅
                // 查询缓存中的sim卡
                // List<HyTerminal> ts = TerminalCache.instance().getAll();
                /*List<HyTerminal> ts = hyTerminalMapper.selectAllForCache();
                if (ts != null) {
                    for (HyTerminal e : ts) {
                        if (e.gettAutoCommunicationId() != null && e.gettAutoCommunicationId() > 0) {
                            builder.addTerminalIdentify(Long.valueOf(e.gettAutoCommunicationId()));
                        } else if (e.gettCommunicationId() != null && e.gettCommunicationId() > 0) {
                            builder.addTerminalIdentify(Long.valueOf(e.gettCommunicationId()));
                        }
                    }
                }*/

                List<String> commIds = hyTerminalMapper.selectAll();
                if (commIds != null) {
                    for (String commId : commIds) {
                        if (commId != null && !commId.trim().equals("0")) {
                            builder.addTerminalIdentify(Long.valueOf(commId));
                        }
                    }
                }

            }
            // 获取鉴权码
            long serverIdentifies = 0;
            List<LCRequestLoginKeyRes.RequestLoginKeyRes> list = CloudLinkCache.instance().getCodeCache();
            if (null != list && list.size() > 0) {
                LCRequestLoginKeyRes.RequestLoginKeyRes res = list.get(0);
                serverIdentifies = res.getServerIdentifies();
            }
            packet.setUniqueMark(serverIdentifies + "");
            packet.setMsgType(Constant.CLOUD_MESSAGE_TYPE);
            packet.setContentForBytes(builder.build().toByteArray());
            sendMsgToCloudMM(packet, "订阅请求");
            log.info("0200>>serverIdentifies:" + serverIdentifies + ",Terminalsize:"
                    + builder.getTerminalIdentifyCount());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("订阅请求异常", e);
        }

        return null;
    }

}
