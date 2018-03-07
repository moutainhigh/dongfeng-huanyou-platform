package com.navinfo.opentsp.dongfeng.monitor.service.log.impl;


import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.pojo.PlatformLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryOperateLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.service.log.IQueryOperateLogService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class QueryOperateLogService extends BaseService implements IQueryOperateLogService {
    @Override
    public HttpCommandResultWithData query(QueryOperateLogCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("accountType", command.getUserInfor().getType());
        param.put("accountName", command.getUserInfor().getUsername());
        param.put("operateUser", command.getOperateUser());
        if (!command.getOperateUserType().equals("-1")) {
            param.put("operateUserType", command.getOperateUserType());
        }
        param.put("operateName", command.getOperateName());
        if (!command.getOperateType().equals("-1")) {
            param.put("operateType", command.getOperateType());
        }
        param.put("operateContent", command.getOperateContent());
        if (StringUtil.isNotEmpty(command.getBeginTime()) && StringUtil.isNotEmpty(command.getEndTime())) {
            param.put("beginTime", DateUtil.strTimeChangeLong(command.getBeginTime()));
            param.put("endTime", DateUtil.strTimeChangeLong(command.getEndTime()));
        }
        PagingInfo<PlatformLogPojo> pageInfo = dao.sqlPagelFind("searchOperateLogByParam", param, StringUtil.toInteger(command.getPage_number()), StringUtil.toInteger(command.getPage_size()), PlatformLogPojo.class);
        if (!StringUtil.isEmpty(pageInfo.getList())) {
            for (PlatformLogPojo pojo : pageInfo.getList()) {
                convertTypeName(pojo);
            }
        }
        page.setList(pageInfo.getList());
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        result.setData(page);
        return result;
    }

    /**
     * 转换操作类型名称
     *
     * @param pojo
     * @return
     */
    private void convertTypeName(PlatformLogPojo pojo) {
        if (pojo == null) {
            return;
        }
        pojo.setTypeName(OperateLogConstants.OperateTypeEnum.valueOf(pojo.getType()).getMsg());
    }


}
