package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.entity.HyTerminalLogEntity;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.ITerminalLogService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;


/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Repository
public class TerminalLogService extends BaseService implements ITerminalLogService {
    private static final int DEFAULT_LOG_STATUS = -3;//日志状态默认值，与数据库的保持一致

    @Override
    @Transactional
    public BigInteger addTerminalCommandLog(TerminalLogPojo pojo) {
        if (pojo == null) {
            return null;
        }
        HyTerminalLogEntity entity = pojoToEntity(pojo);
        HyTerminalLogEntity temp = (HyTerminalLogEntity) dao.save(entity);
        return temp.getLogId();
    }

    @Override
    @Transactional
    public BigInteger updateTerminalLog(TerminalLogPojo pojo) {
        if (pojo == null) {
            return null;
        }
        HyTerminalLogEntity entity = pojoToEntity(pojo);
        HyTerminalLogEntity temp = (HyTerminalLogEntity) dao.update(entity);
        return temp.getLogId();
    }

    @Override
    public HyTerminalLogEntity queryTerminalLogById(BigInteger id) {
        if (id == null || id.intValue() == 0) {
            return null;
        }
        HyTerminalLogEntity temp = new HyTerminalLogEntity();
        temp.setLogId(id);
        return (HyTerminalLogEntity) dao.findOne(temp);
    }

    private HyTerminalLogEntity pojoToEntity(TerminalLogPojo pojo) {
        HyTerminalLogEntity entity = new HyTerminalLogEntity();
        if (!StringUtil.isEmpty(pojo.getLogId())) {
            entity.setLogId(pojo.getLogId());
        }
        entity.setLogValue(pojo.getValue() == null ? 1 : pojo.getValue());
        entity.setLogContent(pojo.getDes());
        entity.setLogName(pojo.getType());
        entity.setLogAccountName(pojo.getOperateUser());
        entity.setLogAccountIp(pojo.getOperateIp());
        entity.setChassisNum(pojo.getChassisNum());
        entity.setCarCph(pojo.getCarNum());
        entity.setCompanyName(pojo.getTeam());
        entity.setCarOwner(pojo.getReCustom());
        if (StringUtil.isEmpty(pojo.getStatus())) {
            entity.setLogState(DEFAULT_LOG_STATUS);
        } else {
            entity.setLogState(pojo.getStatus());
        }
        entity.setLogSim(pojo.getSimNo());
        entity.setLogCommon(pojo.getLogCommon());
        if (pojo.getType().contains("8F40")) {
            entity.setModuleType(1);
        }
        entity.setLogDate(StringUtil.getCurrentTimeSeconds());
        if (StringUtil.isEmpty(pojo.getLogType())) {
            entity.setLogType(0);
        } else {
            entity.setLogType(pojo.getLogType());
        }
        return entity;
    }
}
