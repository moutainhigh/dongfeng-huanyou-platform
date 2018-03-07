package com.navinfo.dongfeng.terminal.comm.cmd.base;

import com.navinfo.dongfeng.terminal.comm.result.CommonResult;
import com.navinfo.dongfeng.terminal.comm.validator.GroupCommand;
import messaging.AbstractSecuredCommand;
import messaging.Command;
import org.hibernate.validator.constraints.NotBlank;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by zhangyu on 2017/3/27.
 */
public class BaseCmd<T extends Command.Result> extends AbstractSecuredCommand<CommonResult> implements Serializable
{
    
    private static final long serialVersionUID = -8765500906240643869L;
    
    @NotNull(message = "token不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "token不能为空白", groups = {GroupCommand.class})
    private String token;// token
    
    @NotNull(message = "command不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "command不能为空白", groups = {GroupCommand.class})
    private String command;// 消息ID
    
    private String nodeMsg;// 消息体属性
    
    private String uniqueMark;// 唯一标识 SIM/CODE
    
    private String serialNumber = "0000";// 消息流水号
    
    private String content;// 消息体
    
    /**
     * request
     */
    
    private HttpServletRequest request;
    
    // @NotNull(message = "contentForBytes不能为空", groups = {GroupCommand.class})
    // @NotBlank(message = "contentForBytes不能为空白", groups = {GroupCommand.class})
    public String contentForBytes;
    
    public static long getSerialVersionUID()
    {
        return serialVersionUID;
    }
    
    public String getCommand()
    {
        return command;
    }
    
    public void setCommand(String command)
    {
        this.command = command;
    }
    
    public String getNodeMsg()
    {
        return nodeMsg;
    }
    
    public void setNodeMsg(String nodeMsg)
    {
        this.nodeMsg = nodeMsg;
    }
    
    public String getUniqueMark()
    {
        return uniqueMark;
    }
    
    public void setUniqueMark(String uniqueMark)
    {
        this.uniqueMark = uniqueMark;
    }
    
    public String getSerialNumber()
    {
        return serialNumber;
    }
    
    public void setSerialNumber(String serialNumber)
    {
        this.serialNumber = serialNumber;
    }
    
    public String getContent()
    {
        return content;
    }
    
    public void setContent(String content)
    {
        this.content = content;
    }
    
    public String getContentForBytes()
    {
        return contentForBytes;
    }
    
    public void setContentForBytes(String contentForBytes)
    {
        this.contentForBytes = contentForBytes;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    public void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    @Override
    public Class<? extends CommonResult> getResultType()
    {
        return CommonResult.class;
    }
    
}
