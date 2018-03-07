package com.navinfo.dongfeng.terminal.comm.services;

import com.navinfo.dongfeng.terminal.comm.model.system.instructlayin.HyInstructLayin;

public interface IHyInstructLayinService
{
    
    /**
     * 修改
     * 
     * @param record
     * @return
     */
	void updateByPrimaryKeySelective(HyInstructLayin record)
        throws Exception;

	/**
	 * 新增
	 * @param record
	 * @return
	 */
	void insertSelective(HyInstructLayin record) throws Exception;
    
}