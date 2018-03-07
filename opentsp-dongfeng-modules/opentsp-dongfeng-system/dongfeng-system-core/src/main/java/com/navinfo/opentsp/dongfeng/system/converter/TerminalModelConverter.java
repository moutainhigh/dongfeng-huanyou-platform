package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TerminalModelOutdto;
import com.navinfo.opentsp.dongfeng.system.entity.TerminalModelEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalModelPojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 终端型号对象转换类
 *
 * @author zhangyue
 * @version 1.0
 * @date 2017-03-17
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalModelConverter {

    public static TerminalModelOutdto toOutDto(TerminalModelPojo terminalPojo) {
        TerminalModelOutdto dto = new TerminalModelOutdto();
        dto.setTmName(terminalPojo.getTmName());
        dto.setTmModelNum(terminalPojo.getTmModelNum());
        dto.setTmId(terminalPojo.getTmId());

        if( ! StringUtil.isEmpty(terminalPojo.getTmCarMatched()) &&  terminalPojo.getTmCarMatched().split(",").length > 0){
                dto.setTmCarMatched(terminalPojo.getTmCarMatched().split(","));
        }
        if( ! StringUtil.isEmpty(terminalPojo.getTmCommuModel()) &&  terminalPojo.getTmCommuModel().split(",").length > 0){
                dto.setTmCommuModel(terminalPojo.getTmCommuModel().split(","));
        }
        if( ! StringUtil.isEmpty(terminalPojo.getTmLoctionModel()) &&  terminalPojo.getTmLoctionModel().split(",").length > 0){
            dto.setTmLoctionModel(terminalPojo.getTmLoctionModel().split(","));
        }
        dto.setTmFactoryNum(terminalPojo.getTmFactoryNum());
        dto.setTmFactoryName(terminalPojo.getTmFactoryName());
        dto.setTmNotes(terminalPojo.getTmNotes());
        dto.setTmPublicNum(terminalPojo.getTmPublicNum());

        return dto;
    }


    public static List<TerminalModelOutdto> toOutDtoList(List<TerminalModelPojo> terminalModelPojo) {
        if (terminalModelPojo == null || terminalModelPojo.size() == 0) {
            return null;
        }
        List<TerminalModelOutdto> list = new ArrayList<>(terminalModelPojo.size());
        for (TerminalModelPojo terminalPojo : terminalModelPojo) {
            list.add(toOutDto(terminalPojo));
        }
        return list;
    }

    public static TerminalModelOutdto toOutDtoUp(TerminalModelEntity terEntity) {
        TerminalModelOutdto dto = new TerminalModelOutdto();
        dto.setTmModelNum(terEntity.getTmModelNum());
        dto.setTmId(BigInteger.valueOf(terEntity.getTmId()));
        dto.setTmName(terEntity.getTmName());

        if( ! StringUtil.isEmpty(terEntity.getTmCarMatched()) &&  terEntity.getTmCarMatched().split(",").length > 0){
            dto.setTmCarMatched(terEntity.getTmCarMatched().split(","));
        }
        if( ! StringUtil.isEmpty(terEntity.getTmCommuModel()) &&  terEntity.getTmCommuModel().split(",").length > 0){
            dto.setTmCommuModel(terEntity.getTmCommuModel().split(","));
        }
        if( ! StringUtil.isEmpty(terEntity.getTmLoctionModel()) &&  terEntity.getTmLoctionModel().split(",").length > 0){
            dto.setTmLoctionModel(terEntity.getTmLoctionModel().split(","));
        }
        dto.setTmFactoryNum(terEntity.getTmFactoryNum());
        dto.setTmFactoryName(terEntity.getTmFactoryName());
        dto.setTmNotes(terEntity.getTmNotes());
        dto.setTmPublicNum(terEntity.getTmPublicNum());

        return dto;
    }
}
