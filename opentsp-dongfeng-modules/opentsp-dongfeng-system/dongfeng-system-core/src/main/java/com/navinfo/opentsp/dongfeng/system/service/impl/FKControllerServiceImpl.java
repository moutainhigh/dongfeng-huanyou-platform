package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryFKControllerCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.service.IFKControllerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liht on 2017/3/12.
 */
@Service
@Transactional(rollbackFor={Exception.class})
public class FKControllerServiceImpl extends BaseService implements IFKControllerService {

    @Override
    public PagingInfo<TerminalIndto> QueryFKController(QueryFKControllerCommand fkCommand){

        Map<String , Object> mapKf = new HashMap<String , Object>();

        int pageNumber = null == fkCommand.getPage_number() || "".equals(fkCommand.getPage_number().trim()) ? 1 : Integer.valueOf(fkCommand.getPage_number().trim());

        mapKf.put("dealerId", fkCommand.getDealerId());

        if(fkCommand.getTerminalBea() != null){
            mapKf.put("tSim", fkCommand.getTerminalBea().getSim()  == null ? "" : fkCommand.getTerminalBea().getSim());
        }
        return dao.sqlPagelFind("queryFKControllerList", mapKf, pageNumber ,Integer.valueOf(fkCommand.getPage_size()) , TerminalIndto.class);

    }
}
