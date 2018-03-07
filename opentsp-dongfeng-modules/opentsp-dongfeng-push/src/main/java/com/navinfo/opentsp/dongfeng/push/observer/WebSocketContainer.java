package com.navinfo.opentsp.dongfeng.push.observer;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.springframework.util.StringUtils;

import javax.websocket.Session;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketContainer
{
    
    private static ConcurrentHashMap<String, Session> webSocketContainer = new ConcurrentHashMap<String, Session>();
    
    private static ConcurrentHashMap<Session, String> sessionContainer = new ConcurrentHashMap<Session, String>();
    
    public static void register(String token, Session session)
    {
        
        if (StringUtils.isEmpty(token))
        {
            return;
        }
        
        if (null == session || !session.isOpen())
        {
            return;
        }
        webSocketContainer.put(token, session);
        sessionContainer.put(session, token);
    }
    
    public static Session getSession(String token)
    {
        return webSocketContainer.get(token);
    }
    
    public static String getToken(Session session)
    {
        return sessionContainer.get(session);
    }
    
    public static Session[] get()
    {
        
        Session[] sessions = new Session[webSocketContainer.size()];
        Collection<Session> collection = webSocketContainer.values();
        return collection.toArray(sessions);
    }
    
    public static void destory(Session session)
    {
        if (null != session)
        {
            String token = sessionContainer.get(session);
            
            // 如果token不为空
            if (!StringUtil.isEmpty(token))
            {
                webSocketContainer.remove(token);
            }
            sessionContainer.remove(session);
        }
    }
}
