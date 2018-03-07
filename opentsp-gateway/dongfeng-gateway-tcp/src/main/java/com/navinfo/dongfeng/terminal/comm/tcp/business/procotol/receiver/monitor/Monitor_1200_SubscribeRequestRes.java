package com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.receiver.monitor;

import com.lc.core.protocol.platform.LCRPNodeData;
import com.lc.core.protocol.platform.LCSubscribeRequestRes;
import com.lc.core.protocol.platform.auth.LCMultiServerAuthRes;
import com.navinfo.dongfeng.terminal.comm.cache.RPNodeDataCache;
import com.navinfo.dongfeng.terminal.comm.common.Command;
import com.navinfo.dongfeng.terminal.comm.common.Packet;
import com.navinfo.dongfeng.terminal.comm.common.StringUtils;
import com.navinfo.dongfeng.terminal.comm.common.constant.CacheKeyConstants;
import com.navinfo.dongfeng.terminal.comm.common.constant.Constant;
import com.navinfo.dongfeng.terminal.comm.model.cache.WebServiceIp;
import com.navinfo.dongfeng.terminal.comm.tcp.business.procotol.GpsCommandFactory;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudLinkCache;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订阅请求应答
 * 
 */
@Component
public class Monitor_1200_SubscribeRequestRes extends Command
{
    @Autowired
    private RPNodeDataCache rPNodeDataCache;
    
    @Override
    public Object processor(Packet packet)
    {
        
        try
        {
            LCSubscribeRequestRes.SubscribeRequestRes res =
                LCSubscribeRequestRes.SubscribeRequestRes.parseFrom(packet.getContentForBytes());
            List<LCRPNodeData.RPNodeData> listRes = res.getDatasList();
            // 添加RP服务信息与鉴权码之间的缓存
            Map<String, List<String>> maps = new HashMap<String, List<String>>();
            List<WebServiceIp> webServiceIpList = new ArrayList<WebServiceIp>();
            List<LCRPNodeData.RPNodeData> gpsIpList = new ArrayList<LCRPNodeData.RPNodeData>();
            Long webServiceServerIdentifies = null;
            Long gpsServerIdentifies = null;
            LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo serverAuthInfo = null;
            // 获取缓存中各服务的鉴权码
            List<LCMultiServerAuthRes.MultiServerAuthRes.ServerAuthInfo> listCodes = CloudLinkCache.instance().getCodesCache();
            // webservice RP服务信息
            for (int i = 0; i < listCodes.size(); i++)
            {
                serverAuthInfo = listCodes.get(i);
                webServiceServerIdentifies = serverAuthInfo.getServerIdentify();
            }
            for (int i = 0; i < listRes.size(); i++)
            {
                LCRPNodeData.RPNodeData data = listRes.get(i);
                if (data.getTypes().equals(LCRPNodeData.RPNodeData.ServerType.dataQueryRequest))
                {
                    WebServiceIp WebServiceIp = new WebServiceIp();
                    WebServiceIp.setIdentifies(webServiceServerIdentifies + "");
                    WebServiceIp.setServiceIp(data.getRpIp() + ":" + data.getRpPort());
                    webServiceIpList.add(WebServiceIp);
                    log.error("rp_webServicer:  " + data.getRpIp() + ":" + data.getRpPort());
                    System.err.println("rp_webServicer:  " + data.getRpIp() + ":" + data.getRpPort());
                }
            }
            // 将webService服务的鉴权码和RP信息发送至RMI
            // maps.put(webServiceServerIdentifies+"", webServiceIpList);
            // BaseDataManage.getITestManageService().rmiTest(maps);
            // rPNodeDataCache.addWebServiceRP(webServiceServerIdentifies + "", webServiceIpList);
            rPNodeDataCache.addWebServiceRP(CacheKeyConstants.WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY,
                webServiceIpList);
            // gps RP服务信息
            // 获取Gps对应云RP节点
            for (int j = 0; j < listRes.size(); j++)
            {
                LCRPNodeData.RPNodeData data = listRes.get(j);
                if (!data.getTypes().equals(LCRPNodeData.RPNodeData.ServerType.dataQueryRequest))
                {
                    gpsIpList.add(data);
                }
            }
            // 各Gps对应云RP节点缓存更新
            // for (int i = 0; i < gpsServerIp.length; i++) {
            // String gpsIp = gpsServerIp[i];
            serverAuthInfo = null;
            gpsServerIdentifies = null;
            // 获取对应gpsIp地址云鉴权码
            for (int k = 0; k < listCodes.size(); k++)
            {
                serverAuthInfo = listCodes.get(k);
                gpsServerIdentifies = serverAuthInfo.getServerIdentify();
            }
            if (gpsServerIdentifies != null && gpsIpList.size() > 0)
            {
                // 添加gpsRP服务信息缓存前，进行缓存合并，（增量缓存时缓存非空，需要进行合并）
                List<LCRPNodeData.RPNodeData> tempDatas = rPNodeDataCache.getGpsRP(gpsServerIdentifies + "");
                
                if (tempDatas != null && tempDatas.size() > 0)
                {// 已经存在缓存，进行合并
                    boolean isHave = false;
                    for (LCRPNodeData.RPNodeData rpNodeData : gpsIpList)
                    {
                        for (LCRPNodeData.RPNodeData temprp : tempDatas)
                        {
                            // if (StringUtils.isEq(temprp.getRpIp(), rpNodeData.getRpIp())
                            // && temprp.getRpPort() == rpNodeData.getRpPort()
                            // && temprp.getTerminalIdentifyList().size() == rpNodeData.getTerminalIdentifyList()
                            // .size())
                            if (StringUtils.isEq(temprp.getRpIp(), rpNodeData.getRpIp())
                                && temprp.getRpPort() == rpNodeData.getRpPort())
                            {
                                isHave = true;
                                break;
                            }
                        }
                        if (!isHave)
                        {// 不存在
                            tempDatas.add(rpNodeData);
                        }
                    }
                }
                else
                {
                    tempDatas = gpsIpList;
                }
                // 添加gpsRP服务信息缓存，所有的gps对应所有云RP节点
                rPNodeDataCache.addGpsRP(gpsServerIdentifies + "", tempDatas);
            }
            // }
            
            // 对增量RP进行推送,合并不需要推送，caozhen，20151118
            // pushAddRPNodeData(gpsIpList);
            
            for (LCRPNodeData.RPNodeData item : gpsIpList)
            {//
                log.error("RP>>>ServerType:" + item.getTypes() + ",RpIp:" + item.getRpIp() + ",RpPort:"
                    + item.getRpPort() + ",terminalIdentify.size:" + item.getTerminalIdentifyCount());
            }
            log.info("订阅请求应答success!");
            
            // 流程操作成功标识,caozhen,20151202 新增
            CloudServerStart cloudServerStart = (CloudServerStart)packet.getSession().getAttribute("cloudServerStart");
            if (cloudServerStart != null)
            {
                cloudServerStart.setIsSucces(3);
            }
            
            // 返回各Rp后，进行各rp连接链路，20151118，caozhen
            startRp(gpsIpList);
        }
        catch (Exception e)
        {
            log.error("订阅请求应答异常", e);
        }
        
        return null;
    }
    
    public void startRp(List<LCRPNodeData.RPNodeData> gpsIpList)
    {
        if (gpsIpList != null)
        {
            for (LCRPNodeData.RPNodeData rpNodeData : gpsIpList)
            {//
             // 将所有RP服务器下终端ID与对应IP放入缓存
                List<Long> terminals = rpNodeData.getTerminalIdentifyList();
                String rpIp = rpNodeData.getRpIp();
                int rpPort = rpNodeData.getRpPort();
                for (Long terminal : terminals)
                {
                    CloudLinkCache.instance().addCarIpCache(terminal, rpIp + ":" + rpPort);
                }
                // 将得到的IP及其端口放入缓存
                CloudLinkCache.instance().addRpIpPortCache(rpIp + ":" + rpPort);
                log.info("9008>>RP服务器: " + rpIp + ":" + rpPort);
                
                // 如果与rp连接链路存在则进行增量订阅，否则进行连接RP服务器,caozhen,20141218
                IoSession ioSession = CloudLinkCache.instance().getcache(rpIp + ":" + rpPort);
                if (ioSession != null)
                {
                    // 进行增量订阅
                    addDataSubscribe(rpIp, rpPort + "", terminals);
                }
                else
                {
                    // 启动客户端连接各RP服务器
                    CloudServerStart cloudServerStart = new CloudServerStart();
                    cloudServerStart.setIp(rpNodeData.getRpIp());
                    cloudServerStart.setPort(rpNodeData.getRpPort());
                    cloudServerStart.setServerType(2);
                    cloudServerStart.connect();
                }
            }
        }
    }
    
    /*****
     * 增量数据订阅
     */
    public void addDataSubscribe(String rpIp, String rpPort, List<Long> sims)
    {
        // 数据订阅请求发送
        Packet packet1 = new Packet();
        packet1.setTo(rpIp + ":" + rpPort);
        packet1.setCommand(Constant.DATASUBSCRIBE);
        packet1.addParameter("Add", "true");
        packet1.addObject("sims", sims);
        GpsCommandFactory.processor(packet1);
    }
    /****
     * 对增量RP进行推送
     * 
     * @param rpNodeDatas 增量云RP节点
     */
    // public void pushAddRPNodeData(List<RPNodeData> rpNodeDatas){
    // // 获取缓存中各服务Ip的鉴权码
    // List<ServerAuthInfo> codesList = CloudLinkCache.instance().getCodesCache();
    // //获取数据中心链路缓存
    // Map<ServerAddress, IoSession> pMap=ProcessSessionCache.getInstance().getCache();
    // if(pMap==null||pMap.size()<=0){//链路缓存不存在则返回
    // return ;
    // }
    // //循环链路缓存推送9007，908
    // for(ServerAddress address:pMap.keySet()){
    // List<RPNodeData> gpsRPList =rpNodeDatas;
    // Packet packetGps9007 = new Packet();
    // Packet packetGps9008 = new Packet();
    // // 如果新的数据中心是原有的数据中心（如断线重连），则直接向数据中心发送9007
    // IoSession session = pMap.get(address);
    // if(null != gpsRPList && gpsRPList.size() > 0&&session!=null){
    // // 构造9007对象
    // LCMultiServerAuthRes.MultiServerAuthRes.Builder resBuilder9007 = LCMultiServerAuthRes.MultiServerAuthRes
    // .newBuilder();
    // resBuilder9007.setResults(com.lc.core.protocol.common.LCPlatformResponseResult.PlatformResponseResult.success);
    // resBuilder9007.setSerialNumber(0);
    // resBuilder9007.addAllInfos(codesList);
    // packetGps9007.setContentForBytes(resBuilder9007.build().toByteArray());
    // packetGps9007.setCommand(constant.MULTI_SERVER_AUTH_RES);
    // packetGps9007.setMsgType(constant.MONITOR_MESSAGE_TYPE);
    // session.write(packetGps9007);
    //
    // // 构造9008对象
    // LCSubscribeRequestRes.SubscribeRequestRes.Builder resBuilder9008 = LCSubscribeRequestRes.SubscribeRequestRes
    // .newBuilder();
    // resBuilder9008.setSerialNumber(0);
    // resBuilder9008.addAllDatas(gpsRPList);
    // packetGps9008.setContentForBytes(resBuilder9008.build().toByteArray());
    // packetGps9008.setCommand(constant.SUBSCRIBE_REQUEST_RES);
    // packetGps9008.setMsgType(constant.MONITOR_MESSAGE_TYPE);
    //
    // session.write(packetGps9008);
    // }
    // }
    // }
}
