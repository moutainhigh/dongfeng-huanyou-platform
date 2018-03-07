package com.navinfo.opentsp.dongfeng.common.dao.base;

import com.navinfo.opentsp.dongfeng.common.sqllaber.SqlLaberUtil;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class BaseDao
{
    @PersistenceContext
    protected EntityManager em;
    
    @Autowired
    public SqlLaberUtil sqlLaberUtil;
}