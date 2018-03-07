package com.navinfo.opentsp.dongfeng.report.converter.market;

import com.navinfo.opentsp.dongfeng.report.dto.market.QueryRSerpartAppointmentDTo;
import com.navinfo.opentsp.dongfeng.report.pojo.market.RSerpartAppointmentPojo;

import java.util.ArrayList;
import java.util.List;

public class RSerpartAppointmentDto {

    public static List<QueryRSerpartAppointmentDTo> convert(List<RSerpartAppointmentPojo> list) {
        List<QueryRSerpartAppointmentDTo> dtos = new ArrayList<QueryRSerpartAppointmentDTo>();
        for (RSerpartAppointmentPojo pojo : list) {
            QueryRSerpartAppointmentDTo dto = new QueryRSerpartAppointmentDTo();
            dto.setAppointmentId(pojo.getAppointmentId() != null ? pojo.getAppointmentId().longValue() : null);
            dto.setCarId(pojo.getCarId() != null ? pojo.getCarId().toString() : null);
            dto.setChassisNum(pojo.getChassisNum());
            dto.setCarCph(pojo.getCarCph());
            dto.setTerId(pojo.getTerId());
            dto.setCarTerId(pojo.getCarTerId());
            dto.setCompanyName(pojo.getCompanyName());
            dto.setCarOwner(pojo.getCarOwner());
            dto.setCarType(pojo.getCarType());
            dto.setEngineNumber(pojo.getEngineNumber());
            dto.setAppoPhone(pojo.getAppoPhone());
            dto.setStationId(pojo.getStationId() != null ? pojo.getStationId().longValue() : null);
            dto.setAppoStation(pojo.getAppoStation());
            dto.setAppoTime(pojo.getAppoTime() != null ? pojo.getAppoTime() : null);
            dto.setAppoName(pojo.getAppoName());
            dto.setAppoService(pojo.getAppoService());
            dto.setAppoParts(pojo.getAppoParts());
            dto.setAppoTool(pojo.getAppoTool());
            dto.setAppoComment(pojo.getAppoComment());
            dto.setCommTime(pojo.getCommTime() != null ? pojo.getCommTime() : null);
            dto.setCommContent(pojo.getCommContent());
            dto.setCommTechLevel(pojo.getCommTechLevel() != null ? pojo.getCommTechLevel() + "" : null);
            dto.setCommServiceLevel(pojo.getCommServiceLevel() != null ? pojo.getCommServiceLevel() + "" : null);
            dto.setCommTotalLevel(pojo.getCommTotalLevel() != null ? pojo.getCommTotalLevel() + "" : null);
            dto.setAppointmentType(pojo.getAppointmentType());
            dto.setInTime(pojo.getInTime());
            dto.setOutTime(pojo.getOutTime());
            dto.setAppoSubmitTime(pojo.getAppoSubmitTime());
            dto.setAppointmentNum(pojo.getAppointmentNum() != null ? pojo.getAppointmentNum() + "" : null);
            // 预约状态 0:预约待确认，1:已确认未服务，2:已取消，3:正在服务,4:完成服务待评价,5:完成评价,6:已过期
            dto.setAppointmentStatus(pojo.getAppointmentStatus() + "");
            dtos.add(dto);
        }
        return dtos;
    }

}
