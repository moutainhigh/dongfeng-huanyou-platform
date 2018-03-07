package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.dto.UserInfoDto;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationBasicInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationServiceInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.SubStationInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.*;
import com.navinfo.opentsp.dongfeng.system.commands.station.AddStationCommand;
import com.navinfo.opentsp.dongfeng.system.commands.station.UpdateStationCommand;
import com.navinfo.opentsp.dongfeng.system.entity.HySecdstationEntity;
import com.navinfo.opentsp.dongfeng.system.entity.HyServiceStationEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.PovinceTreePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.StationPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.StationTreePojo;
import com.navinfo.opentsp.dongfeng.system.pojo.SubStationPojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/14.
 */
public class StationConverter {

    private static final String IS_NOT_CORE_STATION_FLAG = "0";// 非核心站

    public static StationTreeOutdto pojoToDto(StationTreePojo pojo) {
        StationTreeOutdto dto = new StationTreeOutdto();
        try {
            CopyPropUtil.copyProp(pojo, dto);
        } catch (Exception e) {

        }
        return dto;
    }

    public static List<StationTreeOutdto> listPojoToDto(List<StationTreePojo> list) {
        List<StationTreeOutdto> listDto = new ArrayList<StationTreeOutdto>();
        if (!StringUtil.isEmpty(list)) {
            for (StationTreePojo pojo : list) {
                StationTreeOutdto dto = pojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static StationTreeOutdto povincePojoToDto(PovinceTreePojo pojo) {
        StationTreeOutdto dto = new StationTreeOutdto();
        dto.setId(StringUtil.toBigInteger(pojo.getId().toString()));
        dto.setName(pojo.getName());
        dto.setPid(pojo.getPid().intValue());
        return dto;
    }

    public static List<StationTreeOutdto> listPovincePojoToDto(List<PovinceTreePojo> list) {
        List<StationTreeOutdto> listDto = new ArrayList<StationTreeOutdto>();
        if (!StringUtil.isEmpty(list)) {
            for (PovinceTreePojo pojo : list) {
                StationTreeOutdto dto = povincePojoToDto(pojo);
                listDto.add(dto);
            }
        }
        return listDto;
    }

    public static StationBasicInfoOutdto pojoToOutDto(StationPojo pojo) {
        StationBasicInfoOutdto basicInfo = new StationBasicInfoOutdto();
        basicInfo.setStationName(pojo.getStationName());
        basicInfo.setStationShortName(pojo.getStationShortName());
        basicInfo.setStationCode(pojo.getStationCode());
        basicInfo.setAddress(pojo.getAddress());
        basicInfo.setLongitude(pojo.getLongitude());
        basicInfo.setLatitude(pojo.getLatitude());
        basicInfo.setStarLever(pojo.getStarLever());
        basicInfo.setRadius(pojo.getRadius());
        basicInfo.setServiceRadius(pojo.getServiceRadius());
        basicInfo.setStrandedMaxTime(pojo.getStrandedMaxTime());
        basicInfo.setTelephone(pojo.getTelephone());
        basicInfo.setMobilePhone(pojo.getMobilePhone());
        basicInfo.setServiceManager(pojo.getServiceManager());
        basicInfo.setProvince(pojo.getProvince());
        basicInfo.setCity(pojo.getCity());
        basicInfo.setPicture(pojo.getPicture());
        basicInfo.setIsCore(pojo.getIsCore());
        basicInfo.setSwitchStatus(pojo.getSwitchStatus());
        basicInfo.setSwitchStatusStr(pojo.getSwitchStatusStr());
        basicInfo.setAndNet(pojo.getAndNet());
        basicInfo.setRemark(pojo.getRemark());
        return basicInfo;
    }


    public static SubStationOutdto pojoToSubStationOutDto(SubStationPojo pojo) {
        SubStationOutdto outDto = new SubStationOutdto();
        outDto.setId(pojo.getId());
        outDto.setName(pojo.getName());
        outDto.setCode(pojo.getCode());
        outDto.setLongitude(pojo.getLongitude());
        outDto.setLatitude(pojo.getLatitude());
        outDto.setAddress(pojo.getAddress());
        outDto.setWorkRadius(pojo.getWorkRadius());
        return outDto;
    }

    public static List<SubStationOutdto> pojoToSubStationOutDtoList(List<SubStationPojo> list) {
        List<SubStationOutdto> result = new ArrayList<>(list.size());
        for (SubStationPojo subStationPojo : list) {
            result.add(pojoToSubStationOutDto(subStationPojo));
        }
        return result;
    }


    public static StationOutdto toStationOutDto(StationPojo pojo, List<SubStationPojo> subStation, List<StationServiceItemOutdto> itms) {
        StationOutdto outDto = new StationOutdto();
        outDto.setStationId(pojo.getId());
        outDto.setBasicInfo(pojoToOutDto(pojo));
        outDto.setSubStation(pojoToSubStationOutDtoList(subStation));
        outDto.setServiceItem(itms);
        return outDto;
    }

    public static HyServiceStationEntity inDtoToEntity(Object object) {
        HyServiceStationEntity entity = new HyServiceStationEntity();
        UpdateStationCommand command = new UpdateStationCommand();
        UserInfoDto userInfoDto = new UserInfoDto();
        if (object instanceof AddStationCommand) {
            AddStationCommand commandAdd = (AddStationCommand) object;
            userInfoDto = commandAdd.getUserInfor();
            try {
                CopyPropUtil.copyProp(object, command);
            } catch (Exception e) {

            }
            entity.setCreatDate(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
            entity.setUpdateTime(entity.getCreatDate());
        } else {
            command = (UpdateStationCommand) object;
            userInfoDto = command.getUserInfor();
            entity.setUpdateTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        }
        StationBasicInfoIndto basicInfo = command.getBasicInfoBean();
        StationServiceInfoIndto serviceInfo = command.getServiceInfoBean();
        entity.setStationName(basicInfo.getStationName());
        entity.setNameForshort(basicInfo.getStationShortName());
        entity.setAddress(basicInfo.getAddress());
        if (!StringUtil.isEmpty(basicInfo.getLatitude()) && !StringUtil.isEmpty(basicInfo.getLongitude())) {
            entity.setLongitude(StringUtil.toBigInteger(basicInfo.getLongitude()));
            entity.setLatitude(StringUtil.toBigInteger(basicInfo.getLatitude()));
        }
        entity.setRadius(StringUtil.toInteger(basicInfo.getRadius()));
        entity.setPovince(StringUtil.toInteger(basicInfo.getProvince()));
        entity.setCity(StringUtil.toInteger(basicInfo.getCity()));
        entity.setServiceManager(basicInfo.getServiceManager());
        entity.setPhone(basicInfo.getMobilePhone());
        entity.setTeamId(BigInteger.valueOf(1));
        if (!StringUtil.isEmpty(basicInfo.getIsCore())) {
            entity.setIsCore(StringUtil.toBigInteger(basicInfo.getIsCore()));
        } else {
            entity.setIsCore(StringUtil.toBigInteger(IS_NOT_CORE_STATION_FLAG));
        }
        if (!StringUtil.isEmpty(basicInfo.getStrandedMaxTime())) {
            entity.setStrandedMaxTime(StringUtil.toBigInteger(basicInfo.getStrandedMaxTime()));
        }
        entity.setStarLevel(StringUtil.toInteger(basicInfo.getStarLever()));
        entity.setPicture(basicInfo.getPicture());
        entity.setServiceRadius(StringUtil.toInteger(basicInfo.getServiceRadius()));
        entity.setServiceCode(basicInfo.getStationCode());
        if (!StringUtil.isEmpty(userInfoDto.getUserId())) {
            entity.setAccountId(StringUtil.toBigInteger(userInfoDto.getUserId().toString()));
        }
        entity.setFixedTelephone(basicInfo.getTelephone());
        //主营业务信息填充
        if (!StringUtil.isEmpty(serviceInfo.getServiceType())) {
            entity.setServiceType(StringUtil.toInteger(serviceInfo.getServiceType()));
            entity.setPartsContent(serviceInfo.getSpareParts());
            entity.setServiceContent(serviceInfo.getServiceContent());
            entity.setToolContent(serviceInfo.getPromoteTool());
        }
        entity.setDelFlag(0);
        entity.setAndNet(basicInfo.getAndNet());
        entity.setRemark(basicInfo.getRemark());
        return entity;
    }

    /**
     * 数据参数转换为二级服务站
     *
     * @param pid
     * @param pCode
     * @param subStation
     * @return
     */
    public static HySecdstationEntity inDtoToSubStation(BigInteger pid, String pCode, SubStationInfoIndto subStation) {
        HySecdstationEntity entity = new HySecdstationEntity();
        if (!StringUtil.isEmpty(subStation.getStationId())) {//更新的
            entity.setId(StringUtil.toBigInteger((subStation.getStationId())));
        } else {//新增的
            entity.setTagSyncStatus(ReturnCode.OK.code());
        }
        entity.setName(subStation.getName());
        entity.setCode(subStation.getCode());
        if (!StringUtil.isEmpty(subStation.getLatitude()) && !StringUtil.isEmpty(subStation.getLongitude())) {
            entity.setLongitude(StringUtil.toBigInteger(subStation.getLongitude()));
            entity.setLatitude(StringUtil.toBigInteger(subStation.getLatitude()));
        }
        entity.setAddress(subStation.getAddress());
        entity.setWorkRadius(StringUtil.toInteger(subStation.getWorkRadius()));
        entity.setStationCode(pCode);
        entity.setStationId(pid);
        return entity;
    }

    public static List<HySecdstationEntity> inDtoToSubStationList(BigInteger pid, String pCode, List<SubStationInfoIndto> list) {
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        List<HySecdstationEntity> result = new ArrayList<>(list.size());
        for (SubStationInfoIndto subStationInfoIndto : list) {
            result.add(inDtoToSubStation(pid, pCode, subStationInfoIndto));
        }
        return result;
    }


}
