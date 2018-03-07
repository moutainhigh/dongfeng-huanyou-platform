package com.navinfo.opentsp.dongfeng.common.constant;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;

/**
 * 消息推送相关的常量类
 * 
 * @author zhangy
 * @version 1.0
 * @date 2017-04-28
 */

public class MessagePushConstants
{
    
    /**
     * 消息推送类型
     */
    public static enum PushType
    {
        
        CMD_SEND_ANSWER(1, "指令下发应答"), REAL_TIME_TRANSIT(2, "实时在途"), OTHER_USER_LOGIN_IN(3, "其他用户登陆通知");
        
        private int code;
        
        private String name;
        
        PushType(int code, String name)
        {
            this.code = code;
            this.name = name;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public void setCode(int code)
        {
            this.code = code;
        }
        
        public String getName()
        {
            return name;
        }
        
        public void setName(String name)
        {
            this.name = name;
        }
    }
    
    /**
     * 用户登陆消息推送内容
     */
    public static enum UserLoginPushMsg
    {
        
        OTHER_USER_LOGIN_MSQ_PUSH(1, "该账号已在其他地方登陆，请确认");
        
        private int code;
        
        private String msg;
        
        UserLoginPushMsg(int code, String msg)
        {
            this.code = code;
            this.msg = msg;
        }
        
        public int getCode()
        {
            return code;
        }
        
        public void setCode(int code)
        {
            this.code = code;
        }
        
        public String getMsg()
        {
            return msg;
        }
        
        public void setMsg(String msg)
        {
            this.msg = msg;
        }
    }
    
    /**
     * 用户登陆消息推送内容
     */
    public static enum UnloadMsg
    {
        
        UNLOAD("unload", "浏览器关闭");
        
        private String key;
        
        private String msg;
        
        UnloadMsg(String key, String msg)
        {
            this.key = key;
            this.msg = msg;
        }
        
        public String getKey()
        {
            return key;
        }
        
        public void setKey(String key)
        {
            this.key = key;
        }
    }

    /**
     * 指令响应前段
     * @param reponseId
     * @return
     */
    public static String reponseMessage(String reponseId) {
        String reponseMessage = "";
        if(StringUtil.isEmpty(reponseId)){
                return "指令下发成功";
        }
        if(StringUtil.isNotEmpty(reponseId)){
            if(reponseId.equals(String.valueOf(TcpCommandEnum.VOICE_MONITORING.getCommand()))){
                reponseMessage = "语音监控指令下发成功";
            }
        }
        return reponseMessage;
    }
}