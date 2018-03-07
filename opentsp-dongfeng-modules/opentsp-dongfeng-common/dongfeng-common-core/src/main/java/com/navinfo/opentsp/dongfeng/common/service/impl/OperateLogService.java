package com.navinfo.opentsp.dongfeng.common.service.impl;


import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.entity.HyPlatformLogEntity;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.ClientUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
@Repository
public class OperateLogService extends BaseService implements IOperateLogService {


    @Override
    public void addOperateLog(OperateLogConstants.OperateResultEnum result, String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent) {
        HyPlatformLogEntity entity = new HyPlatformLogEntity();
        entity.setLogContent(operateContent);
        entity.setLogAccountName(operateUser);
        entity.setLogType(operateType.getValue());
        entity.setLogValue(result.getCode());
        entity.setLogAccountIp(operateIp);
        entity.setLogDate(StringUtil.getCurrentTimeSeconds());
        dao.save(entity);
    }

    @Override
    @Transactional
    public void addOperateLog(OperateLogConstants.OperateResultEnum result, BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent) {
        addOperateLog(result, ClientUtil.getClientIp(command.getRequest()), command.getUserInfor().getUsername(), operateType, operateContent);
    }

    @Override
    @Transactional
    public void addOperateLog(BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent) {
        addOperateLog(OperateLogConstants.OperateResultEnum.SUCCESS, ClientUtil.getClientIp(command.getRequest()), command.getUserInfor().getUsername(), operateType, operateContent);
    }

    @Override
    @Transactional
    public void addOperateLog(String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent) {
        addOperateLog(OperateLogConstants.OperateResultEnum.SUCCESS, operateIp, operateUser, operateType, operateContent);
    }

    @Override
    @Transactional
    public void addOperateLog(String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent, CommonResult data) {
        if (data.getResultCode() != ReturnCode.OK.code()) {
            addOperateLog(OperateLogConstants.OperateResultEnum.FAILED, operateIp, operateUser, operateType, operateContent);
        } else {
            addOperateLog(OperateLogConstants.OperateResultEnum.SUCCESS, operateIp, operateUser, operateType, operateContent);
        }
    }

    @Override
    @Transactional
    public void addOperateLog(BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent, CommonResult data) {
        if (data.getResultCode() != ReturnCode.OK.code()) {
            addOperateLog(OperateLogConstants.OperateResultEnum.FAILED, ClientUtil.getClientIp(command.getRequest()), command.getUserInfor().getUsername(), operateType, operateContent);
        } else {
            addOperateLog(OperateLogConstants.OperateResultEnum.SUCCESS, ClientUtil.getClientIp(command.getRequest()), command.getUserInfor().getUsername(), operateType, operateContent);
        }
    }

}
