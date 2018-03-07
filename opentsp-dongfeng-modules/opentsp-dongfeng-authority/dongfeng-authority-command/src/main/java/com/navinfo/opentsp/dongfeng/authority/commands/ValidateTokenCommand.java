package com.navinfo.opentsp.dongfeng.authority.commands;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * Created by zhangy on 2017/03/09.token校验
 */
public class ValidateTokenCommand extends BaseCommand<HttpCommandResultWithData>
{
    
    /**
     * 是否重置token的失效时间
     */
    public String reSetTokenLiveTimeFlg;
    
    public String getReSetTokenLiveTimeFlg()
    {
        return reSetTokenLiveTimeFlg;
    }
    
    public void setReSetTokenLiveTimeFlg(String reSetTokenLiveTimeFlg)
    {
        this.reSetTokenLiveTimeFlg = reSetTokenLiveTimeFlg;
    }
    
    @Override
    public Class<? extends HttpCommandResultWithData> getResultType()
    {
        return HttpCommandResultWithData.class;
    }
    
}
