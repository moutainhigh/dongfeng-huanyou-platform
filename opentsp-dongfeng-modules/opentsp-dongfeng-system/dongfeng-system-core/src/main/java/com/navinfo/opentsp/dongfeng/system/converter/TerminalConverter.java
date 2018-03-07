package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TerminalOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TerminalProtocolOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.AddTerminalCommand;
import com.navinfo.opentsp.dongfeng.system.constant.TerminalConstant;
import com.navinfo.opentsp.dongfeng.system.entity.HyTerminalEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalCompleteInfoPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalProtocolPojo;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * 终端对象转换类
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
public class TerminalConverter {
    private static final int DISTINCT = 1;//分区服务编码

    /**
     * command 命令对象转换为数据库对象
     *
     * @param command
     * @return
     */
    public static HyTerminalEntity commandToEntity(AddTerminalCommand command, Long autoCId, String id) {
        HyTerminalEntity entity = new HyTerminalEntity();
        if (!StringUtil.isEmpty(id)) {
            entity.settId(StringUtil.toBigInteger(id));
        }
        if (!StringUtil.isEmpty(autoCId)) {
            entity.settAutoCommunicationId(StringUtil.toBigInteger(autoCId));
        }
        entity.settCode(command.getTerminalId());
        entity.settSim(command.getSimNo());
        if (!StringUtil.isEmpty(command.getCommunicationId())) {
            entity.settCommunicationId(StringUtil.toBigInteger(command.getCommunicationId(), BigInteger.ONE));
        }
        if (!StringUtil.isEmpty(command.getProtocol())) {
            entity.settTypeId(StringUtil.toInteger(command.getProtocol()));
        }
        entity.settModel(command.getModel());
        if (!StringUtil.isEmpty(command.getType())) {
            entity.settStyle(StringUtil.toInteger(command.getType()));
        }
        if (!StringUtil.isEmpty(command.getReAgent())) {
            entity.settTeamId(StringUtil.toBigInteger(command.getReAgent(), BigInteger.ONE));
            //新增时填充原始分组ID
            if (StringUtil.isEmpty(id)) {
                entity.setoTeamId(entity.gettTeamId());
            }
        }
        entity.settLabelId(command.getDevLabelId());
        entity.settCameraChannel(command.getCamera());
        if (!StringUtil.isEmpty(command.getMic())) {
            entity.settMic(StringUtil.toInteger(command.getMic()));
        }
        entity.settCommon(command.getRemark());
        entity.setDelFlag(Constants.IS_NOT_DEL);
        entity.setDistrict(DISTINCT);
        entity.setApplianceEngineType(BigInteger.ZERO);
        entity.setApplianceCarType(BigInteger.ZERO);
        return entity;
    }

    public static HyTerminalEntity pojoToEntity(TerminalPojo pojo) {
        HyTerminalEntity entity = new HyTerminalEntity();
        entity.settId(pojo.gettId());
        entity.setDistrict(pojo.getDistrict());
        entity.settSim(pojo.gettSim());
        entity.settCommunicationId(pojo.gettCommunicationId());
        entity.settTeamId(pojo.gettTeamId());
        entity.settCode(pojo.gettCode());
        entity.settTypeId(pojo.gettTypeId());
        entity.settSimSign(pojo.gettSimSign());
        entity.setDelFlag(pojo.getDelFlag());
        entity.setAlarmHandle(pojo.getAlarmHandle());
        entity.settAutoCommunicationId(pojo.gettAutoCommunicationId());
        entity.setAutoFlag(pojo.getAutoFlag());
        entity.setImportTime(pojo.getImportTime());
        entity.settStyle(pojo.gettStyle());
        entity.settModel(pojo.gettModel());
        entity.settCommon(pojo.gettCommon());
        entity.settMic(pojo.gettMic());
        entity.settCameraChannel(pojo.gettCameraChannel());
        entity.settLabelId(pojo.gettLabelId());
        entity.setApplianceEngineType(pojo.getApplianceEngineType());
        entity.setApplianceCarType(pojo.getApplianceCarType());
        return entity;
    }

    public static TerminalOutdto toOutDto(TerminalPojo terminalPojo) {
        TerminalOutdto dto = new TerminalOutdto();
        dto.setId(terminalPojo.gettId());
        dto.setTerminalId(terminalPojo.gettCode());
        dto.setSimNo(terminalPojo.gettSim());
        dto.setCommunicationId(terminalPojo.gettCommunicationId());
        dto.setType(terminalPojo.gettStyle());
        if (!StringUtil.isEmpty(terminalPojo.gettStyle())) {
            dto.setTypeName(TerminalConstant.TerminalTypeEnum.valueOf(terminalPojo.gettStyle()).getMsg());
        }
        dto.setModel(terminalPojo.gettModel());
        dto.setModelName(terminalPojo.getModelName());
        dto.setProtocol(terminalPojo.gettTypeId());
        dto.setProtocolName(terminalPojo.getProtocol());
        dto.setReAgent(terminalPojo.gettTeamId());
        dto.setReAgentName(terminalPojo.getTeamName());
        dto.setVehicleId(terminalPojo.getCarId());
        dto.setChassisNo(terminalPojo.getChassisNum());
        dto.setCarNum(terminalPojo.getCarNum());
        dto.setDevLabelId(terminalPojo.gettLabelId());
        dto.setRemark(terminalPojo.gettCommon());
        dto.setMic(terminalPojo.gettMic());
        dto.setCamera(terminalPojo.gettCameraChannel());
        dto.setCarOwnerId(terminalPojo.getCarOwnerId());
        dto.setCarOwnerName(terminalPojo.getCarOwnerName());
        dto.setEngineType(terminalPojo.getEngineType());
        dto.setInitEngineType(convertToInitEngineType(terminalPojo.getEngineType()));
        dto.setCreateTime(terminalPojo.getCreateTime());
        dto.setCreateUser(terminalPojo.getCreateUserName());
        return dto;
    }


    public static List<TerminalOutdto> toOutDtoList(List<TerminalPojo> terminalPojoList) {
        if (StringUtil.isEmpty(terminalPojoList)) {
            return null;
        }
        List<TerminalOutdto> list = new ArrayList<>(terminalPojoList.size());
        for (TerminalPojo terminalPojo : terminalPojoList) {
            list.add(toOutDto(terminalPojo));
        }
        return list;
    }

    public static TerminalProtocolOutdto toTerminalProtocolOutDto(TerminalProtocolPojo protocolPojo) {
        TerminalProtocolOutdto dto = new TerminalProtocolOutdto();
        dto.setTypeId(protocolPojo.getTypeId());
        dto.setTypeName(protocolPojo.getTypeName());
        return dto;
    }

    /**
     * 终端协议转换为dto对象
     *
     * @param protocolPojoList
     * @return
     */
    public static List<TerminalProtocolOutdto> toProtocolDtoList(List<TerminalProtocolPojo> protocolPojoList) {
        if (StringUtil.isEmpty(protocolPojoList)) {
            return null;
        }
        List<TerminalProtocolOutdto> list = new ArrayList<>(protocolPojoList.size());
        for (TerminalProtocolPojo terminalProtocolPojo : protocolPojoList) {
            list.add(toTerminalProtocolOutDto(terminalProtocolPojo));
        }
        return list;
    }


    /**
     * 终端完整信息
     *
     * @param pojo
     * @return
     */
    public static TerminalOutdto completeToOutdto(TerminalCompleteInfoPojo pojo) {
        TerminalOutdto outdto = new TerminalOutdto();
        outdto.setTerminalId(pojo.getTerminalId());
        outdto.setSimNo(pojo.getSimNo());
        outdto.setTypeName(TerminalConstant.TerminalTypeEnum.valueOf(pojo.gettType()).getMsg());
        outdto.setVehicleId(pojo.getCid());
        outdto.setCarNum(pojo.getCarNum());
        outdto.setChassisNo(pojo.getChassisNum());
        return outdto;
    }

    /**
     * 终端设置-初始化设置-对应的发动机类型
     *
     * @param engineType
     * @return
     */
    public static Integer convertToInitEngineType(String engineType) {
        if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 43) {//杭发
            return TerminalConstant.SupportEngineEnum.HANG_FA.getCode();
        } else if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 46) {//玉柴
            return TerminalConstant.SupportEngineEnum.YU_CHAI.getCode();
        } else if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 44) {//锡柴
            return TerminalConstant.SupportEngineEnum.XI_CHAI.getCode();
        } else if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 45) {//潍柴
            return TerminalConstant.SupportEngineEnum.WEI_CHAI.getCode();
        } else if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 51) {//沃能
            return TerminalConstant.SupportEngineEnum.WO_NENG.getCode();
        } else if (!StringUtil.isEmpty(engineType) && Integer.parseInt(engineType) == 52) {//汽研
            return TerminalConstant.SupportEngineEnum.QI_YAN.getCode();
        } else {
            return TerminalConstant.SupportEngineEnum.WEI_CHAI.getCode();
        }
    }
}
