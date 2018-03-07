package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.entity.HyTerminalLogEntity;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;

import java.math.BigInteger;

/**
 * 终端日志管理
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-10
 * @modify
 * @copyright Navi Tsp
 */
public interface ITerminalLogService {
    /**
     * 新增终端日志
     *
     * @param pojo
     * @return
     */
    BigInteger addTerminalCommandLog(TerminalLogPojo pojo);

    /**
     * 更新终端指令日志
     *
     * @param pojo
     * @return
     */
    BigInteger updateTerminalLog(TerminalLogPojo pojo);

    /**
     * 查询终端日志
     *
     * @param id 日志ID
     * @return
     */
    HyTerminalLogEntity queryTerminalLogById(BigInteger id);
}
