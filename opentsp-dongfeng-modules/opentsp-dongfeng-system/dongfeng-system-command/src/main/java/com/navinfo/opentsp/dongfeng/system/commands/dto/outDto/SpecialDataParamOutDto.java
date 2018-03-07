package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.CanBusParam;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.SpecialDataParam;

import java.util.List;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-10-20
 **/
public class SpecialDataParamOutDto extends SpecialDataParam {

    private List<CanBusParam> canBusList;

    public List<CanBusParam> getCanBusList() {
        return canBusList;
    }

    public void setCanBusList(List<CanBusParam> canBusList) {
        this.canBusList = canBusList;
    }
}
