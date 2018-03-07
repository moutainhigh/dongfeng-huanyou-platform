package com.navinfo.opentsp.dongfeng.common.rmi;


import com.lc.rp.webservice.service.CommonParameter;
import com.navinfo.opentsp.dongfeng.common.rmi.module.HyPoint;

import java.util.List;

public interface SynchronousTerminalDataWebService {

	public int terminalAddOrUpdate(Long terminalId, int tTypeId, String tCode, Boolean isAdd, int businessId);
	
	public int terminalDelete(Long terminalId);
	
	public List<HyPoint> getDouglasPoint(List<HyPoint> points);
	
	public byte[] getTest(CommonParameter commonParameter);
}
