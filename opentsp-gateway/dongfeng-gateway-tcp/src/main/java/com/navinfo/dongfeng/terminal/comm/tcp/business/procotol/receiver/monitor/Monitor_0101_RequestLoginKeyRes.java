package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.monitor;

import com.google.protobuf.InvalidProtocolBufferException;
import com.lc.core.protocol.common.LCPlatformResponseResult;
import com.lc.core.protocol.platform.auth.LCRequestLoginKeyRes;
import com.lc.core.protocol.platform.auth.LCServerLogin;
import com.navinfo.dongfeng.terminal.comm.cache.RPNodeDataCache;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.model.cache.WebServiceIp;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.MonitorCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 平台鉴权请求应答
 * 
 */
@Component
public class Monitor_0101_RequestLoginKeyRes extends Command
{
    @Autowired
    private RPNodeDataCache rPNodeDataCache;
    
    @Override
    public Object processor(Packet packet)
    {
        try
        {
            LCRequestLoginKeyRes.RequestLoginKeyRes reqLoginRes =
                LCRequestLoginKeyRes.RequestLoginKeyRes.parseFrom(packet.getContentForBytes());
            // 云平台返回成功
            if (reqLoginRes.getResults().equals(LCPlatformResponseResult.PlatformResponseResult.success))
            {
                // 流程操作成功标识,caozhen,20151202 新增
                CloudServerStart cloudServerStart =
                    (CloudServerStart)packet.getSession().getAttribute("cloudServerStart");
                if (cloudServerStart != null)
                {
                    cloudServerStart.setIsSucces(1);
                }
                
                long serverIdentifies = reqLoginRes.getServerIdentifies();
                // 应答的对象放入缓存中 确保唯一性
                CloudLinkCache.instance().getCodeCache().clear();
                CloudLinkCache.instance().addCodeCache(reqLoginRes);
                // log.error("鉴权码接收成功：serverIdentifies：" +
                // serverIdentifies+",session: "+packet.getSession().toString());
                // System.out.println("鉴权码接收成功：serverIdentifies：" +
                // serverIdentifies+",session: "+packet.getSession().toString());
                // 发送登录鉴权码
                LCServerLogin.ServerLogin.Builder serverLogin = LCServerLogin.ServerLogin.newBuilder();
                serverLogin.setServerIdentifies(serverIdentifies);
                packet.setContent(serverIdentifies + "");
                // 连接位置云服务器
                connectSever(reqLoginRes, packet);
                // 获取数据同步地址
                String synIp = reqLoginRes.getDataSynchronizeIp();
                Integer synPort = reqLoginRes.getDataSynchronizePort();
                if (synIp != null && synPort != null)
                {
                    // 同步地址存rmi缓存 ，合并后不需要进行rmi缓存同步
                    // Map<String, List<String>> maps = new HashMap<String, List<String>>();
                    List<WebServiceIp> webServiceIpList = new ArrayList<WebServiceIp>();
                    WebServiceIp WebServiceIp = new WebServiceIp();
                    WebServiceIp.setIdentifies(serverIdentifies + "");
                    WebServiceIp.setServiceIp(synIp + ":" + synPort);
                    webServiceIpList.add(WebServiceIp);
                    // maps.put("SynchronizeAddress", webServiceIpList);
                    // BaseDataManage.getITestManageService().rmiTest(maps);
                    
                    rPNodeDataCache.addWebServiceRP(CacheKeyConstants.WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY,
                        webServiceIpList);
                    // log.error("SynchronizeAddress_webService:  "+synIp+":"+synPort);
                    // System.out.println("SynchronizeAddress_webService:  "+synIp+":"+synPort);
                }
            }
            else
            {
                log.error("云平台鉴权请求应答异常：" + reqLoginRes.getResults() + ",session: " + packet.getSession().toString());
                // System.out.println("云平台鉴权请求应答异常："+reqLoginRes.getResults()+",session: "+packet.getSession().toString());
            }
            
        }
        catch (InvalidProtocolBufferException e)
        {
            log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
        }
        catch (Exception e)
        {
            log.error("解析ProtocolBuffer数据异常,原始数据：" + packet, e);
        }
        
        return null;
    }
    
    /**
     * 连接位置云服务器
     * 
     * @param packet
     */
    private void connectSever(LCRequestLoginKeyRes.RequestLoginKeyRes reqLoginRes, Packet packet)
    {
        // 连接主服务器地址
        CloudServerStart cloudServerStart = new CloudServerStart();
        cloudServerStart.setIp(reqLoginRes.getMmMasterNodeIp());
        cloudServerStart.setPort(reqLoginRes.getMmMasterNodePort());
        cloudServerStart.setClouds(true);// 表示是位置云分区主机
        cloudServerStart.setServerType(1);// 分MM
        log.info("分MM>>(主)" + reqLoginRes.getMmMasterNodeIp() + ":" + reqLoginRes.getMmMasterNodePort() + "准备登陆");
        IoSession session = cloudServerStart.connect();
        // 连接成功，进行登录
        if (session != null && cloudServerStart.getConnector() != null)
        {
            loginServer(session, packet);
        }
        else
        {
            // 备用服务器连接
            connectBakServer(reqLoginRes, packet);
        }
    }
    
    /**
     * 备服务器连接
     * 
     * @param reqLoginRes
     * @param packet
     */
    private void connectBakServer(LCRequestLoginKeyRes.RequestLoginKeyRes reqLoginRes, Packet packet)
    {
        CloudServerStart cloudServerStart = new CloudServerStart();
        cloudServerStart.setIp(reqLoginRes.getMmSlaverNodeIp());
        cloudServerStart.setPort(reqLoginRes.getMmSlaverNodePort());
        cloudServerStart.setClouds(true);
        cloudServerStart.setServerType(1);// 分MM
        log.error("分MM(备)>>" + reqLoginRes.getMmMasterNodeIp() + ":" + reqLoginRes.getMmMasterNodePort() + "准备登陆");
        
        IoSession session = cloudServerStart.connect();
        if (session != null && cloudServerStart.getConnector() != null)
            loginServer(session, packet);
    }
    
    /**
     * 登录命令
     * 
     * @param session
     * @param packet
     */
    private void loginServer(IoSession session, Packet packet)
    {
        // 发送登录请求
        packet.setSession(session);
        packet.setCommand(Constant.UP_LOGIN);
        MonitorCommandFactory.processor(packet);
        
        // new Timer().schedule(new TestTasks(), 0, 120*1000);
    }
    
}
