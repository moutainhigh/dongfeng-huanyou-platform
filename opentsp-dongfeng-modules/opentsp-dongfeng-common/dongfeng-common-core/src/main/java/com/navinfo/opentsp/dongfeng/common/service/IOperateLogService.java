package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;

/**
 * 操作日志服务
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-13
 * @modify
 * @copyright Navi Tsp
 */
public interface IOperateLogService {
    /**
     * 添加操作日志
     *
     * @param result         操作结果
     * @param operateIp      操作ip
     * @param operateUser    操作用户
     * @param operateType    操作类型
     * @param operateContent 操作备注 备注格式：底盘号：123456
     */
    void addOperateLog(OperateLogConstants.OperateResultEnum result, String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent);

    /**
     * @param result         操作结果
     * @param command        command参数
     * @param operateType    操作类型
     * @param operateContent 操作备注
     */
    void addOperateLog(OperateLogConstants.OperateResultEnum result, BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent);

    /**
     * @param command        command参数
     * @param operateType    操作类型
     * @param operateContent 操作备注
     */
    void addOperateLog(BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent);

    /**
     * 添加操作日志
     *
     * @param operateIp      操作ip
     * @param operateUser    操作用户
     * @param operateType    操作类型
     * @param operateContent 操作备注
     */
    void addOperateLog(String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent);

    /**
     * 添加操作日志
     *
     * @param operateIp      操作ip
     * @param operateUser    操作用户
     * @param operateType    操作类型
     * @param operateContent 操作备注
     */
    void addOperateLog(String operateIp, String operateUser, OperateLogConstants.OperateTypeEnum operateType, String operateContent, CommonResult data);

    /**
     * 添加操作日志
     *
     * @param command        command参数
     * @param operateType    操作类型
     * @param operateContent 操作备注
     * @param data           返回的数据
     */
    void addOperateLog(BaseCommand command, OperateLogConstants.OperateTypeEnum operateType, String operateContent, CommonResult data);
}
