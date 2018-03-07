package com.navinfo.opentsp.dongfeng.common.service;

import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.sqllaber.SqlLaberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhangy on 2015/12/31.
 */
public class BaseService
{
    protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    @Autowired
    public SqlLaberUtil sqlLaberUtil;
    
    @Autowired
    public CommonDao dao;

    @Autowired
    public IRedisService redisService;
}
