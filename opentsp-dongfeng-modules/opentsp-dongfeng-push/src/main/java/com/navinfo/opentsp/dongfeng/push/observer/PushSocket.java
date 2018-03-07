package com.navinfo.opentsp.dongfeng.push.observer;

import com.navinfo.opentsp.dongfeng.Application;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.push.service.IPushWatchedService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/dongfeng/push/pushSocket")
@Component
@Scope("prototype")
public class PushSocket
{
    IPushWatchedService pushWatchedService;
    
    private static Log logger = LogFactory.getLog(PushSocket.class);
    
    public PushSocket()
    {
        this.pushWatchedService =
            (IPushWatchedService)Application.getApplicationContext().getBean("pushWatchedService");
    }
    
    @OnOpen
    public void open(Session session)
    {
        try
        {
            String token = session.getQueryString();
            if (StringUtil.isNotEmpty(token))
            {
                token = token.indexOf("=") == -1 ? "" : token.split("=")[1];
                if (StringUtil.isNotEmpty(token))
                {
                    WebSocketContainer.register(token, session);
                    // 使用观察者模式进行服务注册.
                    pushWatchedService.addPushSocket(session, this);
                    logger.debug("与客户端建立连接成功，连接token is : " + token);
                }
            }
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 接收到客户端的消息
     * 
     * @param session
     * @param text
     */
    @OnMessage
    public void onMessage(Session session, String text)
    {
        try
        {
            session.getBasicRemote().sendText(text);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    /**
     * 向客户端发送消息
     * 
     * @param session
     * @param text
     */
    public void sendMessage(Session session, String text)
    {
        String token = session.getQueryString();
        token = token.indexOf("=") == -1 ? "" : token.split("=")[1];
        
        logger.debug("向客户端发送消息，连接token is : " + token + "发送的消息为：" + text);
        try
        {
            session.getBasicRemote().sendText(text);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    @OnClose
    public void close(Session session)
    {
        try
        {
            String token = session.getQueryString();
            token = token.indexOf("=") == -1 ? "" : token.split("=")[1];
            logger.debug("关闭的连接token is : " + token);
            
            // 清空session
            WebSocketContainer.destory(session);
            pushWatchedService.removePushSocket(session, this);
            
        }
        catch (Exception e)
        {
            logger.error(e.getMessage(), e);
        }
    }
    
    @OnError
    public void onError(Throwable e, Session session)
    {
        try
        {
            if (session != null)
            {
                String token = session.getQueryString();
                token = token.indexOf("=") == -1 ? "" : token.split("=")[1];
                logger.debug("错误时关闭的连接token is : " + token);
                
                session.close();
                // 清空session
                WebSocketContainer.destory(session);
                pushWatchedService.removePushSocket(session, this);
            }
        }
        catch (Exception e1)
        {
            logger.error(e.getMessage(), e);
        }
    }
}
