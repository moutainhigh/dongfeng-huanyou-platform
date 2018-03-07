package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;

/**
 * 经销商删除接口类
 * @Author zhaoming@mapbar.com
 * @Date 2017/3/10 15:41
 **/

public interface DealerService {

    /**
     * 功能描述:删除经销商
     * @author zhaoming@mapbar.com
     * @date 2017/3/10 15:48
     **/
    public HttpCommandResultWithData deleteDealer(String tid) throws Exception;

    ;


    /**
     * 功能描述:添加经销商
     * @author zhaoming@mapbar.com
     * @date 2017/3/13 18:42
     **/
    public HttpCommandResultWithData addDealer(AddDealerCommand command) throws Exception;

    ;


    /**
     * 功能描述:修改经销商
     * @author zhaoming@mapbar.com
     * @date 2017/3/13 18:44
     **/
    public HttpCommandResultWithData updateDealer(UpdateDealerCommand command) throws Exception;

    ;


    /**
     * 功能描述:车组的新增 更新
     * @author
     * @date 2017/3/16
     **/
    public CommonResult saveOrUpdate(DealerIndto db) throws Exception;;

    /**
     * 功能描述:查询经销商列表
     **/
    HttpCommandResultWithData queryDealerPage(QuerySDealerCommand command,Boolean isAll);

    /**
     * 功能描述:查询单个经销商
     **/
    HttpCommandResultWithData getDealer(GetSDealerCommand command);
    /**
     * 功能描述:查询待审核经销商
     **/
    HttpCommandResultWithData queryAuditDealer(QueryAuditDealerCommand command);
    /**
     * 功能描述:查询待审核经销商
     **/
    CommonResult updateDealerAuditStatus(UpdateDealerAuditStatusCommand command) throws Exception;
    /**
     * 功能描述:修改待审核经销商
     **/
    CommonResult updateBatchDealerAuditStatus(UpdateBatchDealerAuditStatusCommand command) throws Exception;
    /**
     * 功能描述:经销商同步标签系统
     **/
    CommonResult synDealerToSign(SynDealerToSignCommand command) throws Exception;
    /**
     * 功能描述:经销商同步Tboss系统
     **/
    CommonResult synDealerToTboss(SynDealerToTbossCommand command) throws Exception;
}
