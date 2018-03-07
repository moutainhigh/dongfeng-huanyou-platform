package com.navinfo.opentsp.dongfeng.push.service.impl;

import com.navinfo.opentsp.dongfeng.common.dto.CheckOnline;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.dto.PushAllMessage;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.push.observer.PushSocket;
import com.navinfo.opentsp.dongfeng.push.observer.WebSocketContainer;
import com.navinfo.opentsp.dongfeng.push.service.IPushWatchedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.concurrent.ConcurrentHashMap;

@Service(value = "pushWatchedService")
public class PushWatchedServiceImpl implements IPushWatchedService
{
    private ConcurrentHashMap<Session, PushSocket> watches = new ConcurrentHashMap<Session, PushSocket>();
    
    private static Log logger = LogFactory.getLog(PushWatchedServiceImpl.class);

    /**
     * 添加连接
     * 
     * @param session
     * @param push
     */
    @Override
    public void addPushSocket(Session session, PushSocket push)
    {
        logger.debug("添加连接成功，连接token is : " + session.getQueryString());
        watches.put(session, push);
    }
    
    /**
     * 删除连接
     * 
     * @param session
     * @param push
     */
    @Override
    public void removePushSocket(Session session, PushSocket push)
    {
        logger.debug("删除连接成功，删除连接token is : " + session.getQueryString());
        watches.remove(session, push);
    }
    
    /**
     * 消息推送
     * 
     * @param result
     */
    @Override
    public void notifyWatchers(Message msg, HttpCommandResultWithData result)
    {
        // 没有与客户端建立连接
        if (watches.isEmpty())
        {
            result.fillResult(ReturnCode.MASSAGE_PUSH_CLIENT_CON_ERROR);
            return;
        }
        Session session = WebSocketContainer.getSession(msg.getToken());
        
        // 根据传入的token没有取得连接信息
        if (session == null)
        {
            result.fillResult(ReturnCode.MASSAGE_PUSH_TOKEN_ERROR);
            return;
        }
        
        // 消息推送
        PushSocket push = watches.get(session);
        push.sendMessage(session, msg.getData());
    }
    
    /**
     * 消息推送(广播到车辆所属用户)
     *
     * @param result
     */
    @Override
    public void notifyAllWatchers(PushAllMessage msg, HttpCommandResultWithData result)
    {
        // 没有与客户端建立连接
        if (watches.isEmpty())
        {
            result.fillResult(ReturnCode.MASSAGE_PUSH_CLIENT_CON_ERROR);
            return;
        }
        
        Session[] sessionArray = WebSocketContainer.get();
        
        for (Session session : sessionArray)
        {
            PushSocket push = watches.get(session);
            push.sendMessage(session, msg.getData());
        }
    }
    
    /**
     * 校验用户是否在线
     * 
     * @param result
     */
    public void checkOnline(CheckOnline cmd, CommonResult result)
    {
        String token = cmd.getToken();
        
        Session session = WebSocketContainer.getSession(token);
        
        // 用户不在线
        if (session == null)
        {
            result.fillResult(ReturnCode.USER_NOT_ONLINE);
        }
        
    }
}
