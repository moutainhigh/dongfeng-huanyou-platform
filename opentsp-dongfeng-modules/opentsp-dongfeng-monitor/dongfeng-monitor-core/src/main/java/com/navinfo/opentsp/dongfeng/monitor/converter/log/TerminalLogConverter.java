package com.navinfo.opentsp.dongfeng.monitor.converter.log;

import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.util.DateUtils;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.constant.TerminalLogConstant;
import com.navinfo.opentsp.dongfeng.monitor.dto.log.TerminalLogOutdto;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalLogConverter {
    /**
     * 终端日志转换为DTO对象
     *
     * @param pojo
     * @return
     */
    public static TerminalLogOutdto toTerminalLogOutDto(TerminalLogPojo pojo) {
        TerminalLogOutdto dto = new TerminalLogOutdto();
        dto.setLogId(pojo.getLogId());//日志自增id
        dto.setCarId(pojo.getCarId());//车辆自增id
        dto.setChassisNo(pojo.getChassisNum());//底盘号
        dto.setCarNum(pojo.getCarNum());//车牌号
        dto.setSimNo(pojo.getSimNo());//SIM卡
        dto.setTerminalId(pojo.getTerminalId());//终端ID
        dto.setTeam(pojo.getTeam());//经销商
        dto.setReCustom(pojo.getReCustom());//所属客户
        dto.setDir(TerminalLogConstant.CommandDirEnum.valueOf(pojo.getDir()).getMsg());//指令方向
        dto.setType(pojo.getType());//指令类型
        dto.setStatus(TerminalLogConstant.CommandStatusEnum.valueOf(pojo.getStatus()).getDes());//状态
        dto.setOperateUser(pojo.getOperateUser());//操作人
        dto.setOperateIp(pojo.getOperateIp());//操作人IP
        dto.setOperateTime(pojo.getOperateTime().toString());//操作时间
        if (!StringUtil.isEmpty(pojo.getOperateTime())) {
            dto.setOperateTimeStr(DateUtils.formatDate(pojo.getOperateTime().longValue() * 1000));//操作时间字符串
        }
        dto.setDes(pojo.getDes());//操作描述
        return dto;
    }

    public static List<TerminalLogOutdto> toTerminalLogOutDtoList(List<TerminalLogPojo> pojoList) {
        if (StringUtil.isEmpty(pojoList)) {
            return null;
        }
        List<TerminalLogOutdto> dtoList = new ArrayList<>(pojoList.size());
        for (TerminalLogPojo terminalLogPojo : pojoList) {
            dtoList.add(toTerminalLogOutDto(terminalLogPojo));
        }
        return dtoList;
    }

}
