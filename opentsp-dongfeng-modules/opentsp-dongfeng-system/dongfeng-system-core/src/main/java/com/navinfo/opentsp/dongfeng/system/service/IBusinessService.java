package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.business.*;

/**
 * Created by yaocy on 2017/03/14.
 * 客户Service接口
 */

public interface IBusinessService {

    public HttpCommandResultWithData queryBusiness(BusinessCommand command);

    /**
     * 功能描述:获取客户列表
     **/
    HttpCommandResultWithData queryBusinesses(QueryBusinessesCommand command,Boolean isAll);
    /**
     * 功能描述:获取单个客户
     **/
    HttpCommandResultWithData getBusiness(String cid);
    /**
     * 功能描述:删除经客户
     **/
    CommonResult deleteBusiness(DeleteBusinessCommand command)throws Exception;
    /**
     * 功能描述:添加客户
     **/
    HttpCommandResultWithData addBusiness(AddBusinessCommand command)throws Exception;
    /**
     * 功能描述:修改客户
     **/
    CommonResult updateBusiness(UpdateBusinessCommand command)throws Exception;
}
