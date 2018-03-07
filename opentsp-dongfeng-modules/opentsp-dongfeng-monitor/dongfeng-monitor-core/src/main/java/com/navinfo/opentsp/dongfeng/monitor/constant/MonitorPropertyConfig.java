package com.navinfo.opentsp.dongfeng.monitor.constant;

import com.navinfo.opentsp.dongfeng.common.util.ReportProperty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-25
 * @modify
 * @copyright Navi Tsp
 */
@Configuration
public class MonitorPropertyConfig implements ReportProperty
{
    
    @Value("${report.module.path}")
    private String path;
    
    @Value("${fservice.url}")
    private String fservice;
    
    @Value("${download.record.size:3000}")
    private String records;
    
    public void setPath(String path)
    {
        this.path = path;
    }
    
    public void setFservice(String fservice)
    {
        this.fservice = fservice;
    }
    
    public void setRecords(String records)
    {
        this.records = records;
    }
    
    @Override
    public String getPath()
    {
        return this.path;
    }
    
    @Override
    public String getFservice()
    {
        return this.fservice;
    }
    
    @Override
    public String getRecords()
    {
        return this.records;
    }
}
