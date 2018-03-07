package com.navinfo.opentsp.dongfeng.openapi.core.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.openapi.commands.dealer.DealerPositionReportCommand;
import com.navinfo.opentsp.dongfeng.openapi.core.pojo.HyAuditTeamPojo;
import com.navinfo.opentsp.dongfeng.openapi.core.service.IDealerPositionReportService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-07-17
 * @modify
 * @copyright Navi Tsp
 */
@Service
@SuppressWarnings("rawtypes")
public class DealerPositionReportServiceImpl  extends BaseService implements IDealerPositionReportService {

    /**
     * 经销商位置上报
     *
     * @param command
     * @return
     */
    @SuppressWarnings("unchecked")
	@Override
    @Transactional(rollbackFor = { Exception.class })
    public HttpCommandResultWithData dealerPositionReport(DealerPositionReportCommand command) throws Exception{

        HttpCommandResultWithData resultMap = new HttpCommandResultWithData();
        String param = command.getParam();
        HyAuditTeamPojo hyAuditTeamPojo = new HyAuditTeamPojo();
        if(StringUtil.isNotEmpty(param)){
            JSONObject json = JSONObject.fromObject(param);
            hyAuditTeamPojo.setAeraId(new BigInteger((String)json.get("tid")));
            hyAuditTeamPojo.setLng(new BigInteger((String)json.get("tlon")));
            hyAuditTeamPojo.setLat(new BigInteger((String)json.get("tlat")));
            hyAuditTeamPojo.setAddress((String)json.get("address"));
            hyAuditTeamPojo.setAeraType(Integer.valueOf((String)json.get("lv")));
            hyAuditTeamPojo.setAccountId(new BigInteger((String)json.get("userId")));
            hyAuditTeamPojo.setCreateTime(new BigInteger(String.valueOf(StringUtil.getCurrentTimeSeconds())));
            dao.executeUpdate("addDealerAudit", hyAuditTeamPojo);
        }else{
            resultMap.fillResult(ReturnCode.CLIENT_ERROR);
            resultMap.setMessage("参数不能为空");
        }
        return resultMap;
    }
}