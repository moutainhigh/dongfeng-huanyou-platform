package com.navinfo.opentsp.dongfeng.push.service;

import com.navinfo.opentsp.dongfeng.common.dto.CheckOnline;
import com.navinfo.opentsp.dongfeng.common.dto.Message;
import com.navinfo.opentsp.dongfeng.common.dto.PushAllMessage;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.push.observer.PushSocket;

import javax.websocket.Session;

public interface IPushWatchedService
{
    
    public void addPushSocket(Session session, PushSocket push);
    
    public void removePushSocket(Session session, PushSocket push);
    
    public void notifyWatchers(Message msg, HttpCommandResultWithData result);
    
    public void notifyAllWatchers(PushAllMessage msg, HttpCommandResultWithData result);
    
    public void checkOnline(CheckOnline cmd, CommonResult result);
    
}
