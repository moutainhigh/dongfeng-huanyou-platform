package com.navinfo.opentsp.dongfeng.monitor.converter.risk;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRegionCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.risk.AddRiskRuleCommand;
import com.navinfo.opentsp.dongfeng.monitor.constant.RiskConstant;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.Point;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.QueryRisksPoint;
import com.navinfo.opentsp.dongfeng.monitor.dto.risk.RiskRegionDto;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyDrawMapDataEntity;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyDrawMapEntity;
import com.navinfo.opentsp.dongfeng.monitor.entity.HyRiskEntity;
import com.navinfo.opentsp.dongfeng.monitor.pojo.risk.RiskRegionPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-21
 * @modify
 * @copyright Navi Tsp
 */
public class RiskConverter {

    public static HyDrawMapEntity commandToDrawMapEntity(AddRiskRegionCommand command) {
        HyDrawMapEntity entity = new HyDrawMapEntity();
        entity.setDmType(StringUtil.toInteger(command.getRegionType()));
        entity.setDmName(command.getRegionName());
        entity.setDmGroupId(StringUtil.toBigInteger(command.getRegionParent()));
        entity.setDmDescribe(command.getRemark());
        entity.setDistrictId(1);
        if (StringUtil.isEmpty(command.getCreateTime())) {
            entity.setDmDate(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        } else {
            entity.setDmDate(StringUtil.toBigInteger(DateUtil.parseTimeStrToSecond(command.getCreateTime())));
        }
        entity.setDmNumber(String.valueOf(StringUtil.getCurrentTimeSeconds()));
        if (StringUtil.isEmpty(command.getUserName())) {
            entity.setDmAccountName(command.getUserName());
        } else {
            entity.setDmAccountName(command.getUserInfor().getUsername());
        }
        return entity;
    }


    public static List<HyDrawMapDataEntity> commandToDrawMapDataEntity(AddRiskRegionCommand command, long regionId) {
        List<HyDrawMapDataEntity> list = new ArrayList<>();
        int radius = 0;
        if (StringUtil.toInteger(command.getRegionType()) == RiskConstant.RegionTypeEnum.CIRCLE.getCode()) {
            radius = StringUtil.toInteger(command.getRadius());
        }
        ArrayList<Point> pointArray = command.getPointArray();
        for (int i = 0; i < pointArray.size(); i++) {
            HyDrawMapDataEntity entity = new HyDrawMapDataEntity();
            Point point = pointArray.get(i);
            entity.setDmLat(Math.round(point.getLat() * 1000000));
            entity.setDmLng(Math.round(point.getLng() * 1000000));
            entity.setDmdRoadNum(radius);
            entity.setDmId(regionId);
            list.add(entity);
        }
        return list;
    }

    public static RiskRegionDto toRiskRegionDto(List<RiskRegionPojo> list) {
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        RiskRegionDto dto = new RiskRegionDto();
        RiskRegionPojo temp = list.get(0);
        dto.setRegionId(temp.getRegionId());
        dto.setRegionType(temp.getRegionType());
        dto.setRegionName(temp.getRegionName());
        dto.setParentId(temp.getParentId());
        dto.setParentName(temp.getParentName());
        dto.setRemake(temp.getRemake());
        dto.setRadius(temp.getRadius());
        dto.setModifyUser(temp.getModifyUser());
        dto.setCreateTime(temp.getCreateTime());
        List<QueryRisksPoint> points = new ArrayList<>(list.size());
        for (RiskRegionPojo pojo : list) {
            QueryRisksPoint point = new QueryRisksPoint();
            point.setLat(pojo.getLatitude());
            point.setLng(pojo.getLongitude());
            points.add(point);
        }
        dto.setPoints(points);
        return dto;
    }


    public static HyRiskEntity toRiskEntity(BaseCommand command) {
        HyRiskEntity entity = new HyRiskEntity();
        AddRiskRuleCommand reCommand = (AddRiskRuleCommand) command;
        entity.setRegionId(StringUtil.toBigInteger(reCommand.getRegionId()));
        entity.setVehicleId(StringUtil.toBigInteger(reCommand.getVehicleId()));
        entity.setRiskType(StringUtil.toInteger(reCommand.getType()));
        entity.setRiskValue(StringUtil.toInteger(reCommand.getValue()));
        entity.setOperateUser(reCommand.getUserInfor().getUsername());
        entity.setOperateTime(DateUtil.getCurrentTimestamp());
        entity.setRiskCommon("设置" + RiskConstant.RiskTypeEnum.valueOf(Integer.valueOf(reCommand.getType())).getMsg() + ",值：" + reCommand.getValue());
        return entity;
    }
}
