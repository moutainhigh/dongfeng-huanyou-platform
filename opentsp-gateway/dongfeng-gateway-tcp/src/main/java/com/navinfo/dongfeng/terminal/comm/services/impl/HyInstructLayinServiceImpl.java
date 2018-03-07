package com.navinfo.dongfeng.terminal.comm.services.impl;

import com.navinfo.dongfeng.terminal.comm.common.StringUtils;
import com.navinfo.dongfeng.terminal.comm.mapper.system.instructlayin.HyInstructLayinMapper;
import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;
import com.navinfo.dongfeng.terminal.comm.services.IHyInstructLayinService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class HyInstructLayinServiceImpl implements IHyInstructLayinService
{
    @Resource
    private HyInstructLayinMapper hyInstructLayinMapper;
    
    /**
     * 修改
     * 
     * @param record
     * @return
     */
    public void updateByPrimaryKeySelective(HyInstructLayin record)
        throws Exception
    {
//        hyInstructLayinMapper.updateByPrimaryKeySelective(record);
    }
    
    /**
     * 新增
     * 
     * @param record
     * @return
     */
    public void insertSelective(HyInstructLayin record)
        throws Exception
    {
        Map<String, Object> conMap = new HashMap<String, Object>();
        if (!StringUtils.isEmpty(String.valueOf(record.getId())))
        {
            conMap.put("id", record.getId());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getTerminalId())))
        {
            conMap.put("terminalId", record.getTerminalId());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getCarId())))
        {
            conMap.put("carId", record.getCarId());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getPeopleId())))
        {
            conMap.put("peopleId", record.getPeopleId());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getLogAccountName())))
        {
            conMap.put("logAccountName", record.getLogAccountName());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getTerminalNum())))
        {
            conMap.put("terminalNum", record.getTerminalNum());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getInstructNum())))
        {
            conMap.put("instructNum", record.getInstructNum());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getInstructContent())))
        {
            conMap.put("instructContent", record.getInstructContent());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getInstructDate())))
        {
            conMap.put("instructDate", record.getInstructDate());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getInstructState())))
        {
            conMap.put("instructState", record.getInstructState());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getReciveDate())))
        {
            conMap.put("reciveDate", record.getReciveDate());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getInstructSerialnumber())))
        {
            conMap.put("instructSerialnumber", record.getInstructSerialnumber());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getSessionId())))
        {
            conMap.put("sessionId", record.getSessionId());
        }
        if (!StringUtils.isEmpty(String.valueOf(record.getIssuccessOrfalse())))
        {
            conMap.put("issuccessOrfalse", record.getIssuccessOrfalse());
        }
        // 存在条件判断
        // List<HyInstructLayin> list=hyInstructLayinMapper.selectByPropertyByPage(conMap);
        // int isExist =0;
        // if(null != list &&list.size()>0){
        // isExist=list.size();
        // }
        //
        // /**
        // * 需要封装返回json类
        // */
        // if(isExist>0){
        // json.setSuccess(false);
        // json.setMsg("客户名称已经存在！");
        // }else{
//         hyInstructLayinMapper.insertSelective(record);
        ;
    }
    
}