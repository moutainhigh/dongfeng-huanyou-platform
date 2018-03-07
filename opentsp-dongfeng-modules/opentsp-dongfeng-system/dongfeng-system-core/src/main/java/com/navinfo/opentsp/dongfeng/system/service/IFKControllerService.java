package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryFKControllerCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import org.springframework.stereotype.Service;

/**
 * Created by liht on 2017/3/9.
 */
@Service
public interface IFKControllerService {
    public PagingInfo<TerminalIndto> QueryFKController(QueryFKControllerCommand fkCommand);
}
