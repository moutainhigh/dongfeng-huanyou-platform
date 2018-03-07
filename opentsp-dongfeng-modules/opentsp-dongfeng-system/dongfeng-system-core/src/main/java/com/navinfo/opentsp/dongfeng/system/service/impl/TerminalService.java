package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.cache.TerminalSubscribeCache;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.dto.BaseData;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.dto.TamperData;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.SynchronousTerminalDataWebService;
import com.navinfo.opentsp.dongfeng.common.service.*;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.common.validation.ValidateUtil;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.system.client.CmdClient;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.terminal.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.SpecialDataParamOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TerminalSettingMenuOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.TerminalSettingParamOutDto;
import com.navinfo.opentsp.dongfeng.system.commands.terminal.*;
import com.navinfo.opentsp.dongfeng.system.constant.TerminalConstant;
import com.navinfo.opentsp.dongfeng.system.converter.TerminalConverter;
import com.navinfo.opentsp.dongfeng.system.entity.HyTerminalEntity;
import com.navinfo.opentsp.dongfeng.system.entity.HyTerminalSettingParamEntity;
import com.navinfo.opentsp.dongfeng.system.model.*;
import com.navinfo.opentsp.dongfeng.system.pojo.*;
import com.navinfo.opentsp.dongfeng.system.service.ITerminalService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.navinfo.opentsp.dongfeng.common.constant.DictionaryConstant.BaseDataType.TERMINAL_PARAM_SETTING;
import static com.navinfo.opentsp.dongfeng.common.result.ReturnCode.IMPORT_TERMINAL_FAILED;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-08
 * @modify
 * @copyright Navi Tsp
 */
@Service
public class TerminalService extends BaseService implements ITerminalService {
    //位置云返回的同步成功标志
    private static final int SYNC_SUCCESS = 1;
    /**
     * 未选中驾驶行为项
     */
    private static final int DRIVER_BEHAVIOR_UN_SELECTED = -1;

    @Value("${rpws.server.auth.type}")
    private int authType;

    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;

    @Autowired
    private GpsInfoCache gpsInfoCache;

    @Autowired
    private CmdClient cmdClient;

    @Autowired
    private ITerminalLogService terminalLogService;

    @Autowired
    private IRedisService redisService;

    @Autowired
    private IBaseDataService baseDataService;

    @Resource
    private TerminalSubscribeCache terminalSubscribeCache;

    @Resource
    private IOperateLogService operateLogService;


    @Autowired
    private MailClient mailClient;

    @Transactional
    @Override
    public HttpCommandResultWithData addTerminal(AddTerminalCommand command, int source) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 唯一性校验
        int re = isTerminalUnique(command);
        if (re != ReturnCode.OK.code()) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.valueOf(re).message());
            return result;
        }
        //是否已删除，如果已删除，恢复删除的终端
        result = deletedTerminalRecover(command);
        if (result.getResultCode() == ReturnCode.TERMINAL_RECOVER.code()) {
            result.setResultCode(ReturnCode.OK.code());
            return result;
        }
        // 同步位置云
        Long autoCId = null;// 自动生成的通讯ID
        re = terminalAddOrUpdateSync(command, null, true);
        if (re != SYNC_SUCCESS) {
            if (re == ReturnCode.COMMUNICATION_ID_ALREADY_EXIST.code()) {// 如果通讯ID已存在则自动生成通讯ID,再次尝试一把，如果失败则提示用户
                autoCId = StringUtil.generalAutoCommunication();
                re = terminalAddOrUpdateSync(command, autoCId, true);
                if (re != SYNC_SUCCESS) {
                    if (re == ReturnCode.COMMUNICATION_ID_ALREADY_EXIST.code()) {
                        result.setResultCode(re);
                        result.setMessage(ReturnCode.valueOf(re).message());
                        return result;
                    }
                }
            } else if (re == ReturnCode.TERMINAL_ID_ALREADY_EXIST.code()) {// 设备id已经存在则重新做修改操作
                re = terminalAddOrUpdateSync(command, null, false);
                if (re != SYNC_SUCCESS) {
                    if (re == ReturnCode.COMMUNICATION_ID_ALREADY_EXIST.code()) {// 如果通讯ID已存在则自动生成通讯ID,再次尝试一把，如果失败则提示用户
                        autoCId = StringUtil.generalAutoCommunication();
                        terminalAddOrUpdateSync(command, autoCId, false);
                        if (re != SYNC_SUCCESS) {
                            if (re == ReturnCode.TERMINAL_ID_ALREADY_EXIST.code()) {
                                result.setResultCode(re);
                                result.setMessage(ReturnCode.valueOf(re).message());
                                return result;
                            }
                        }

                    }
                }
            } else {
                result.setResultCode(re);
                result.setMessage(ReturnCode.SYNC_TERMINAL_FAILED.message());
                return result;
            }

        }
        // 添加终端到数据库
        HyTerminalEntity t = TerminalConverter.commandToEntity(command, autoCId, null);
        t.setImportTime(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
        t.setCreateUser(StringUtil.toBigInteger(command.getUserInfor().getUserId()));
        if (source == 1) {
            t.setAutoFlag(1);
        } else {
            t.setAutoFlag(0);
        }
        HyTerminalEntity entity = (HyTerminalEntity) dao.save(t);
        result.setData(entity);
        // 添加到终端订阅缓存
        terminalSubscribeCache.addSubscribeTerminal(StringUtil.isEmpty(autoCId) ? StringUtil.toLong(command.getCommunicationId())
                : autoCId);
        operateLogService.addOperateLog(command,
                OperateLogConstants.OperateTypeEnum.ADD,
                OperateLogConstants.OperateContentPrefixEnum.TERMINAL.getValue() + command.getTerminalId(),
                result);
        return result;
    }

    @Transactional
    @Override
    public HttpCommandResultWithData updateTerminal(UpdateTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 唯一性校验
        int re = isTerminalUnique(command);
        if (re != ReturnCode.OK.code()) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.valueOf(re).message());
            return result;
        }
        // 检查是否有操作权限
        ReturnCode codeEnum =
                verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getId());
        if (codeEnum != ReturnCode.OK) {
            result.fillResult(codeEnum);
            return result;
        }
        // 获取通讯号
        Long autoCId = null;
        TerminalPojo terminalPojo = queryTerminal(command.getId());
        // 检查是否已和车辆绑定
        boolean isBinding = isTerminalBindToVehicle(command.getId());
        if (isBinding) {
            // 终端已和车辆绑定不能修改分组
            if (!command.getReAgent().equals(String.valueOf(terminalPojo.gettTeamId()))) {
                result.fillResult(ReturnCode.CAN_NOT_UPDATE_TEAM);
                return result;
            }
        }
        if (!StringUtil.isEmpty(terminalPojo.gettAutoCommunicationId())) {
            // 如果有自动生成的通信ID,则用自动生成的通讯ID通讯
            autoCId = terminalPojo.gettAutoCommunicationId().longValue();
        }
        // 同步位置云
        re = terminalAddOrUpdateSync(command, autoCId, false);
        if (re != SYNC_SUCCESS) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.SYNC_TERMINAL_FAILED.message());
            return result;
        }
        TerminalPojo pojo = queryTerminal(command.getId());
        // 更新终端到数据库
        HyTerminalEntity t = TerminalConverter.commandToEntity(command, autoCId, command.getId());
        t.setImportTime(pojo.getImportTime());
        t.setCreateUser(pojo.getCreateUser());
        dao.update(t);
        // 添加到终端订阅缓存
        terminalSubscribeCache.addSubscribeTerminal(StringUtil.isEmpty(autoCId) ? StringUtil.toLong(command.getCommunicationId())
                : autoCId);
        return result;
    }

    /**
     * 查询终端
     *
     * @param command
     * @return
     */
    @Override
    public HttpCommandResultWithData detailQuery(QueryTerminalDetailCommand command) {
        logger.debug("=====  query terminal start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 检查是否有操作权限
        ReturnCode codeEnum =
                verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getId());
        if (codeEnum != ReturnCode.OK) {
            result.fillResult(codeEnum);
            return result;
        }
        TerminalPojo pojo = queryTerminal(command.getId());
        result.setData(TerminalConverter.toOutDto(pojo));
        return result;
    }

    @Override
    public HttpCommandResultWithData queryCompleteInfo(QueryTerminalCompleteInfoCommand command) {
        logger.debug("=====  query Terminal Complete info start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 检查是否有操作权限
        ReturnCode codeEnum =
                verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getId());
        if (codeEnum != ReturnCode.OK) {
            result.fillResult(codeEnum);
            return result;
        }
        // 查询终端完整信息
        TerminalPojo pojo = queryTerminalCompleteInfo(command.getId(), command.getTerminalId());
        result.setData(TerminalConverter.toOutDto(pojo));
        return result;

    }

    /**
     * 搜索
     *
     * @param command
     * @return
     */
    @Override
    public PagingInfo batchQuery(SearchTerminalCommand command, boolean isPaging) {
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("terminalId", command.getTerminalId()); // 终端ID
        param.put("simNo", command.getSimNo());// sim卡
        param.put("type", command.getType());// 终端类型
        param.put("reChassis", command.getReChassis());// 关联底盘号
        param.put("reVehicleNum", command.getReVehicleNum());// 关联车牌号
        param.put("reAgent", command.getReAgent());// 关联经销商
        param.put("devLabelId", command.getDevLabelId());// 设备标签ID
        param.put("accountId", command.getUserInfor().getUserId());
        param.put("accountType", command.getUserInfor().getType());
        param.put("delFlag", Constants.HAVE_DEL);
        param.put("reStatus", command.getReStatus());
        PagingInfo<TerminalPojo> pageInfo = new PagingInfo<>();
        if (isPaging) {
            pageInfo =
                    dao.sqlPagelFind("searchTerminalByParam",
                            param,
                            StringUtil.toInteger(command.getPage_number()),
                            StringUtil.toInteger(command.getPage_size()),
                            TerminalPojo.class);
        } else {
            List<TerminalPojo> list = dao.sqlFind("searchTerminalByParam", param, TerminalPojo.class);
            pageInfo.setList(list);
            pageInfo.setPage_total(0);
            pageInfo.setTotal(0);
        }
        page.setList(TerminalConverter.toOutDtoList(pageInfo.getList()));
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    /**
     * 删除终端
     *
     * @param command
     * @return
     */
    @Transactional
    @Override
    public HttpCommandResultWithData delete(DeleteTerminalCommand command)
            throws Exception {
        logger.debug("=====  delete terminal start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 检查是否有操作权限
        ReturnCode codeEnum =
                verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getId());
        if (codeEnum != ReturnCode.OK) {
            result.fillResult(codeEnum);
            return result;
        }
        // 检查是否已和车辆绑定
        boolean isBinding = isTerminalBindToVehicle(command.getId());
        if (isBinding) {
            result.fillResult(ReturnCode.HAVE_BIND_WITH_VEHICLE);
            return result;
        }
        // 同步到位置云
        int re = terminalDeleteSync(command.getId());
        if (re != SYNC_SUCCESS) {
            result.setResultCode(re);
            result.setMessage(ReturnCode.valueOf(re).message());
            return result;
        }
        // 从数据库中删除(逻辑删除)
        TerminalPojo terminalPojo = queryTerminal(command.getId());
        HyTerminalEntity entity = TerminalConverter.pojoToEntity(terminalPojo);
        entity.setDelFlag(Constants.HAVE_DEL);
        dao.update(entity);
        return result;
    }

    @Override
    @Transactional
    public HttpCommandResultWithData setting(SettingTerminalCommand command)
            throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        // 检查是否有操作权限
        ReturnCode codeEnum =
                verifyAuthToAccess(command.getUserInfor().getUserId(), command.getUserInfor().getType(), command.getTid());
        if (codeEnum != ReturnCode.OK) {
            result.fillResult(codeEnum);
            return result;
        }
        // 检查是否已和车辆绑定
        boolean isBinding = isTerminalBindToVehicle(command.getTid());
        if (!isBinding) {
            result.fillResult(ReturnCode.HAVE_NOT_BIND_WITH_VEHICLE);
            return result;
        }
        String menuSet = command.getMenuSet();
        String driveSet = command.getDriveSet();
        TerminalPojo pojo = queryTerminalCompleteInfo(command.getTid(), command.getTerminalId());

        // 从缓存获取车辆状态
        GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(pojo.getCommunicationId().toString());
        if (StringUtil.isEmpty(gpsInfo)
                || (gpsInfo.getCarStatue().intValue() & 1) != TerminalConstant.TerminalStatusEnum.ONLINE.getCode()) {
            result.fillResult(ReturnCode.TERMINAL_OFFLINE);
            return result;
        } else {
            if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.POSITION_SETTING.getCode() + "")) {
                // 保存日志
                String serialNum =
                        saveTerminalLog(pojo,
                                command.getUserInfor().getUsername(),
                                command.getToken(),
                                TcpCommandEnum.POSITION_SETTING,
                                ClientUtil.getClientIp(command.getRequest()));
                // 下发设置
                positionSetting(pojo.getCommunicationId().toString(),
                        command.getToken(),
                        serialNum,
                        command.getGpsParamBean());

                addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.POSITION_SETTING, command.getGpsParamBean());
            }
            TerminalConstant.TerminalTypeEnum tempType = TerminalConstant.TerminalTypeEnum.valueOf(pojo.gettStyle());
            if (tempType == TerminalConstant.TerminalTypeEnum.FK_CONTROLLER) {// F9类型才进行设置
                if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.CAN_SETTING.getCode() + "")) {
                    String serialNum =
                            saveTerminalLog(pojo,
                                    command.getUserInfor().getUsername(),
                                    command.getToken(),
                                    TcpCommandEnum.CAN_SETTING,
                                    ClientUtil.getClientIp(command.getRequest()));
                    canSetting(pojo.getCommunicationId().toString(),
                            command.getToken(),
                            serialNum,
                            command.getCanParamBean());
                    addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.CAN_SETTING, command.getCanParamBean());
                }
                if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.TERMINAL_INIT.getCode() + "")) {
                    String serialNum =
                            saveTerminalLog(pojo,
                                    command.getUserInfor().getUsername(),
                                    command.getToken(),
                                    TcpCommandEnum.TERMINAL_INIT,
                                    ClientUtil.getClientIp(command.getRequest()));
                    terminalInit(pojo.getCommunicationId().toString(),
                            command.getToken(),
                            serialNum,
                            command.getInitParamBean());

                    addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.TERMINAL_INIT, command.getInitParamBean());
                }
                if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.DRIVING_BEHAVIOR.getCode() + "")) {
                    if (StringUtil.isNotEmpty(driveSet)) {
                        String serialNum =
                                saveTerminalLog(pojo,
                                        command.getUserInfor().getUsername(),
                                        command.getToken(),
                                        TcpCommandEnum.DRIVING_BEHAVIOR,
                                        ClientUtil.getClientIp(command.getRequest()));
                        drivingBehaviorSetting(pojo.getCommunicationId().toString(),
                                command.getToken(),
                                serialNum,
                                driveSet,
                                command.getAlarmParamBean());
                        addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.DRIVING_BEHAVIOR, command.getAlarmParamBean());

                    }
                }
                if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.REAL_TIME.getCode() + "")) {
                    String serialNum =
                            saveTerminalLog(pojo,
                                    command.getUserInfor().getUsername(),
                                    command.getToken(),
                                    TcpCommandEnum.REAL_TIME_DATA_COLLECT,
                                    ClientUtil.getClientIp(command.getRequest()));
                    realTimeDataCollectionSetting(pojo.getCommunicationId().toString(),
                            command.getToken(),
                            serialNum,
                            command.getRealTimeParamBean());
                    addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.REAL_TIME, command.getRealTimeParamBean());
                }
//                if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.SPECIAL_DATA.getCode() + "")) {
//                    String serialNum =
//                            saveTerminalLog(pojo,
//                                    command.getUserInfor().getUsername(),
//                                    command.getToken(),
//                                    TcpCommandEnum.CAN_DATA_CYCLE_UPLOAD_SETTING,
//                                    ClientUtil.getClientIp(command.getRequest()));
//                    List<CanBusDataPojo> canBusDataPojos = queryCanBusDataBypids(command.getSpecialDataParamBean().getPidStr());
//                    List<CanBusParam> canBusParams = new ArrayList<>(canBusDataPojos.size());
//                    for (CanBusDataPojo canBusDataPojo : canBusDataPojos) {
//                        CanBusParam canBusParam = new CanBusParam();
//                        canBusParam.setPid(canBusDataPojo.getPid().intValue());
//                        canBusParam.setCanId(canBusDataPojo.getCanId());
//                        canBusParam.setBegin(canBusDataPojo.getBeginIndex());
//                        canBusParam.setOffset(canBusDataPojo.getDataLength());
//                        canBusParams.add(canBusParam);
//                    }
//                    specialDataCollectionSetting(pojo.getCommunicationId().toString(),
//                            command.getToken(),
//                            serialNum,
//                            command.getSpecialDataParamBean(), canBusParams);
//
//                    SpecialDataParamOutDto specialDataParamOutDto = new SpecialDataParamOutDto();
//                    specialDataParamOutDto.setDataCollectionLifeCycle(command.getSpecialDataParamBean().getDataCollectionLifeCycle());
//                    specialDataParamOutDto.setCanWay(command.getSpecialDataParamBean().getCanWay());
//                    specialDataParamOutDto.setCollectionWay(command.getSpecialDataParamBean().getCollectionWay());
//                    specialDataParamOutDto.setDataFormat(command.getSpecialDataParamBean().getDataFormat());
//                    specialDataParamOutDto.setFrameType(command.getSpecialDataParamBean().getFrameType());
//                    specialDataParamOutDto.setBaudRate(command.getSpecialDataParamBean().getBaudRate());
//                    specialDataParamOutDto.setDataCollectionInterval(command.getSpecialDataParamBean().getDataCollectionInterval());
//                    specialDataParamOutDto.setDataReportInterval(command.getSpecialDataParamBean().getDataReportInterval());
//                    specialDataParamOutDto.setCanBusList(canBusParams);
//                    addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.SPECIAL_DATA, specialDataParamOutDto);
//                }
            }
            if (menuSet.contains(TerminalConstant.TerminalSettingMenuEnum.TERMINAL_UPGRADE.getCode() + "")) {
                String serialNum =
                        saveTerminalLog(pojo,
                                command.getUserInfor().getUsername(),
                                command.getToken(),
                                TcpCommandEnum.TERMINAL_UPGRADE,
                                ClientUtil.getClientIp(command.getRequest()));
                terminalUpgrade(pojo.getCommunicationId().toString(),
                        command.getToken(),
                        serialNum,
                        command.getUpgradeParamBean());
                addOrUpdateTerminalSettingParam(command.getTid(), TerminalConstant.TerminalSettingMenuEnum.TERMINAL_UPGRADE, command.getUpgradeParamBean());

            }
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryProtocolOfTerminal(TerminalProtocolCommand command) {
        logger.debug("=====  query terminal protocol start  =====");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> parm = new HashMap<>();
        parm.put("typeName", command.getTypeName());
        parm.put("producerId", command.getProducerId());
        parm.put("travelVersion", command.getTravelVersion());
        parm.put("protocolVersion", command.getProtocolVersion());
        parm.put("gpsModule", command.getGpsModule());
        List<TerminalProtocolPojo> list = dao.sqlFind("queryTerminalProtocol", parm, TerminalProtocolPojo.class);
        if (!StringUtil.isEmpty(list)) {
            result.setData(TerminalConverter.toProtocolDtoList(list));
        } else {
            result.fillResult(ReturnCode.NO_SUPPORT_TERMINAL_PROTOCOL);
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryTerminalSettingParam(QueryTerminalSettingParamCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        List<BaseData> baseDatas = baseDataService.queryList(TERMINAL_PARAM_SETTING.getType());
        List<TerminalSettingMenuOutdto> list = new ArrayList<>();
        list.add(new TerminalSettingMenuOutdto(TERMINAL_PARAM_SETTING.getType().toString(),
                TERMINAL_PARAM_SETTING.getValue()));
        for (BaseData baseData : baseDatas) {
            TerminalSettingMenuOutdto outdto = new TerminalSettingMenuOutdto();
            outdto.setId(baseData.getCode());
            outdto.setName(baseData.getValue());
            outdto.setPid(TERMINAL_PARAM_SETTING.getType().toString());
            if (command.getType().equals(TerminalConstant.TerminalTypeEnum.BEI_DOU_INTEGRATED_MACHINE.getCode() + "")) {
                if (Integer.valueOf(baseData.getCode()) <= TerminalConstant.TerminalSettingMenuEnum.CAN_SETTING.getCode()) {
                    list.add(outdto);
                }
            } else {
                list.add(outdto);
            }
        }
        Map<String, TerminalSettingMenuOutdto> menuMap = list.stream().collect(Collectors.toMap(TerminalSettingMenuOutdto::getId, Function.identity()));
        TerminalSettingParamOutDto data = new TerminalSettingParamOutDto();
        List<HyTerminalSettingParamEntity> hyTerminalSettingParamEntities = queryTerminalSettingParam(command.getTid());
        for (HyTerminalSettingParamEntity entity : hyTerminalSettingParamEntities) {
            TerminalConstant.TerminalSettingMenuEnum terminalSettingMenuEnum = TerminalConstant.TerminalSettingMenuEnum.valueOf(entity.getParamType());
            switch (terminalSettingMenuEnum) {
                case POSITION_SETTING:
                    GpsParam gpsParam = JsonUtil.fromJson(entity.getParamJsonValue(), GpsParam.class);
                    data.setGpsParam(gpsParam);
                    break;
                case CAN_SETTING:
                    CanParam canParam = JsonUtil.fromJson(entity.getParamJsonValue(), CanParam.class);
                    data.setCanParam(canParam);
                    break;
                case DRIVING_BEHAVIOR:
                    AlarmParam alarmParam = JsonUtil.fromJson(entity.getParamJsonValue(), AlarmParam.class);
                    data.setAlarmParam(alarmParam);
                    break;
                case TERMINAL_INIT:
                    InitParam initParam = JsonUtil.fromJson(entity.getParamJsonValue(), InitParam.class);
                    data.setInitParam(initParam);
                    break;
                case TERMINAL_UPGRADE:
                    UpgradeParam upgradeParam = JsonUtil.fromJson(entity.getParamJsonValue(), UpgradeParam.class);
                    data.setUpgradeParam(upgradeParam);
                    break;
                case REAL_TIME:
                    RealTimeParam realTimeParam = JsonUtil.fromJson(entity.getParamJsonValue(), RealTimeParam.class);
                    data.setRealTimeDataParam(realTimeParam);
                    break;
                case SPECIAL_DATA:
                    SpecialDataParamOutDto specialDataParamOutDto = JsonUtil.fromJson(entity.getParamJsonValue(), SpecialDataParamOutDto.class);
                    data.setSpecialDataParam(specialDataParamOutDto);
                    break;
                default:
                    break;
            }
            if (menuMap.get(entity.getParamType().toString()) != null) {
                menuMap.get(entity.getParamType().toString()).setChecked(true);
            }
        }
        data.setMenu(new ArrayList<>(menuMap.values()));
        result.setData(data);
        return result;
    }

    @Async
    @Override
    public void asyncDownload(String fservice, List<? extends Object> list, ExportTerminalCommand command,
                              String sourcePath) {
        try {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("终端报表");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导出终端报表异常：", e);
        }
    }

    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportTerminalCommand command,
                               String sourcePath) {
        ReportConfig config = new ReportConfig("车辆轨迹");
        config.setColumn("", "序号", 0);
        config.setColumn("terminalId", "终端ID", 1);
        config.setColumn("simNo", "终端SIM卡", 2);
        config.setColumn("typeName", "终端类型", 3);
        config.setColumn("modelName", "终端型号", 4);
        config.setColumn("protocolName", "终端协议", 5);
        config.setColumn("reAgentName", "所属经销商", 6);
        config.setColumn("chassisNo", "所属车辆", 7);
        config.setColumn("devLabelId", "设备标签ID", 8);
        config.setColumn("createUser", "创建人", 9);
        config.setColumn("createTime", "创建时间", 10);
        config.setColumn("remark", "备注", 11);

        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, null, 0);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }

    @Override
    @Transactional
    public HttpCommandResultWithData importTerminal(List<Object> datas, ImportTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        for (int i = 1; i < datas.size(); i++) {
            // 终端型号、终端类型、终端协议、经销商信息转换
            TerminalTemplePojo pojo = (TerminalTemplePojo) datas.get(i);
            String s = ValidateUtil.validateBean(pojo);
            if (StringUtil.isNotEmpty(s)) {
                return new HttpCommandResultWithData(IMPORT_TERMINAL_FAILED.code(), "第" + i + "行数据有误，" + s);
            }
            result = constructAddTerminalCommand(pojo, command);
            if (result.getResultCode() != ReturnCode.OK.code()) {
                result.setMessage("第" + i + "行数据有误，" + result.getMessage());
                return result;
            } else {
                AddTerminalCommand addCommand = (AddTerminalCommand) result.getData();
                // 調用新增終端接口
                result = addTerminal(addCommand, 1);
                if (result.getResultCode() != ReturnCode.OK.code()) {
                    result.setMessage("第" + i + "行数据:" + result.getMessage());
                    return result;
                }
            }
        }
        return result;
    }

    @Async
    @Override
    @Transactional
    public void asyncImportTerminal(List<Object> datas, ImportTerminalCommand command) {
        try {
            String operateTime = "";
            if (StringUtil.isNotEmpty(command.getImportTime())) {
                operateTime = command.getImportTime();
            } else {
                operateTime = DateUtils.formatDate(StringUtil.getCurrentTimeMillis());
            }
            HttpCommandResultWithData importResult = importTerminal(datas, command);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject(operateTime + "终端导入结果");
            if (importResult.getResultCode() == ReturnCode.OK.code()) {
                mailCommand.setContent("终端导入成功");
            } else {
                mailCommand.setContent(importResult.getMessage());
            }
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导入终端异常：", e);
        }
    }

    @Override
    public HttpCommandResultWithData queryCanBusData(String pids) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result.setData(queryCanBusDataBypids(pids));
        return result;
    }


    /**
     * 检查是否有操作权限
     *
     * @param userId 用户ID
     * @param tId    终端自增ID
     * @return
     */
    private ReturnCode verifyAuthToAccess(Long userId, int userType, String tId) {
        // 查询分组
        List<Long> teamList = queryTeamByUserId(userId, userType);
        if (StringUtil.isEmpty(teamList)) {
            return ReturnCode.NO_TEAM_AUTH_OF_TERMINAL;
        }
        // 查询终端
        TerminalPojo pojo = queryTerminal(tId);
        if (StringUtil.isEmpty(pojo)) {
            return ReturnCode.TERMINAL_NOT_EXISTED;
        }
        if (!teamList.contains(pojo.gettTeamId())) {
            return ReturnCode.NO_AUTH_TO_ACCESS_TERMINAL;
        }
        return ReturnCode.OK;

    }

    /**
     * 查询用户所有分组
     *
     * @param userId 用户id
     * @return 分组id list
     */
    private List<Long> queryTeamByUserId(Long userId, int userType) {
        Map<String, Object> parm = new HashMap<>();
        parm.put("userId", userId);
        parm.put("userType", userType);
        return dao.sqlFindField("selectTerminalCarTeamIdByAccount", parm);
    }

    /**
     * 获取用户管理的所有车里的id
     *
     * @param userId 用户id
     * @return 车辆id list
     */
    private List<Long> queryVehicleIdByUserId(Long userId, int userType) {
        Map<String, Object> param = new HashMap<>();
        param.put("userId", userId);
        param.put("userType", userType);
        param.put("delFlag", Constants.HAVE_DEL);
        return dao.sqlFindField("terminalSelectVehicleId", param);
    }

    /**
     * 验证终端唯一性
     *
     * @param command
     * @return
     */
    private int isTerminalUnique(BaseCommand command) {
        String id = null;
        if (command instanceof UpdateTerminalCommand) {
            id = ((UpdateTerminalCommand) command).getId();
        }
        AddTerminalCommand terminalCommand = (AddTerminalCommand) command;
        if (!isTerminalIdUnique(terminalCommand.getTerminalId(), id)) {
            return ReturnCode.EXISTED_TERMINAL_ID.code();
        }
        if (!isSimNoUnique(terminalCommand.getSimNo(), id)) {
            return ReturnCode.EXISTED_SIM_NO.code();
        }
        if (!isCommunicationIdUnique(terminalCommand.getCommunicationId(), id)) {
            return ReturnCode.EXISTED_COMMUNICATION_ID.code();
        }
        if (!StringUtil.isEmpty(terminalCommand.getDevLabelId())) {
            if (!isDeviceLabelIDUnique(terminalCommand.getDevLabelId(), id)) {
                return ReturnCode.EXISTED_DEVICE_LABEL_ID.code();
            }
        }
        return ReturnCode.OK.code();

    }

    /**
     * 终端ID是否唯一
     *
     * @param tCode 终端ID
     * @param tId   终端自增ID
     * @return true 唯一 false 不唯一
     */
    private boolean isTerminalIdUnique(String tCode, String tId) {
        if (StringUtil.isEmpty(tCode)) {
            return false;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("tCode", tCode);
        param.put("tId", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryTIdByParam", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 终端通讯号是否唯一
     *
     * @param simNo sim卡号
     * @param tId   终端自增ID
     * @return true 唯一 false 不唯一
     */
    private boolean isSimNoUnique(String simNo, String tId) {
        if (StringUtil.isEmpty(simNo)) {
            return false;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("simNo", simNo);
        param.put("tId", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryTIdByParam", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 终端通讯号是否唯一
     *
     * @param tCommunicationId 终端通讯号
     * @param tId              终端自增ID
     * @return true 唯一 false 不唯一
     */
    private boolean isCommunicationIdUnique(String tCommunicationId, String tId) {
        if (StringUtil.isEmpty(tCommunicationId)) {
            return false;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("communicationId", tCommunicationId);
        param.put("tId", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryTIdByParam", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 设备标签ID是否唯一
     *
     * @param labelId 设备标签ID
     * @param tId     终端自增ID
     * @return true 唯一 false 不唯一
     */
    private boolean isDeviceLabelIDUnique(String labelId, String tId) {
        Map<String, Object> param = new HashMap<>();
        param.put("labelId", labelId);
        param.put("tId", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryTIdByParam", param);
        return StringUtil.isEmpty(list);
    }

    /**
     * 终端是否已经和车辆绑定
     *
     * @param tId 终端自增ID
     * @return true 应该绑定 false 未绑定
     */
    private boolean isTerminalBindToVehicle(String tId) {
        Map<String, Object> param = new HashMap<>();
        param.put("tId", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryCarIdByParam", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 查询终端信息
     *
     * @param tId 终端自增ID
     * @return true 存在 false 不存在
     */
    private TerminalPojo queryTerminal(String tId) {
        // 查询终端
        Map<String, Object> param = new HashMap<>();
        param.put("id", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        return (TerminalPojo) dao.sqlFindObject("queryTerminalById", param, TerminalPojo.class);
    }

    /**
     * 查询终端完整详细信息
     *
     * @param tId
     * @param terminalId
     * @return
     */
    private TerminalPojo queryTerminalCompleteInfo(String tId, String terminalId) {
        // 查询终端
        Map<String, Object> param = new HashMap<>();
        param.put("id", tId);
        param.put("terminalId", terminalId);
        param.put("delFlag", Constants.HAVE_DEL);
        return (TerminalPojo) dao.sqlFindObject("queryTerminalCompleteInfo", param, TerminalPojo.class);
    }


    /**
     * 新增或更新终端同步到位置云
     *
     * @param command
     * @param autoCommunicationId 自动生成的通讯ID
     * @param isAdd               是否是新增
     * @return
     */
    private int terminalAddOrUpdateSync(BaseCommand command, Long autoCommunicationId, boolean isAdd) {
        AddTerminalCommand c = (AddTerminalCommand) command;
        try {
            // 有效的通讯ID
            Long effectiveCId =
                    StringUtil.isEmpty(autoCommunicationId) ? Long.valueOf(c.getCommunicationId()) : autoCommunicationId;
            SynchronousTerminalDataWebService terminalDataWebService =
                    cloudDataRmiClientConfiguration.getSynchronousTerminalDataWebService();
            logger.info("终端同步位置云:effectiveCId=" + effectiveCId + ",c.getProtocol()=" + c.getProtocol() + ",c.getTerminalId()=" + c.getTerminalId() + ",isAdd=" + isAdd + ",authType=" + authType);
            return terminalDataWebService.terminalAddOrUpdate(effectiveCId,
                    Integer.valueOf(c.getProtocol()),
                    c.getTerminalId(),
                    isAdd,
                    authType);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ReturnCode.SYNC_TERMINAL_FAILED.code();
    }

    /**
     * 删除终端同步到位置云
     *
     * @param tId
     * @return
     */
    private int terminalDeleteSync(String tId) {
        try {
            TerminalPojo pojo = queryTerminal(tId);
            if (StringUtil.isEmpty(pojo)) {
                return ReturnCode.TERMINAL_NOT_EXISTED.code();
            }
            Long effectiveCId;
            if (!StringUtil.isEmpty(pojo.gettAutoCommunicationId())) {
                effectiveCId = pojo.gettAutoCommunicationId().longValue();
            } else {
                effectiveCId = pojo.gettCommunicationId().longValue();
            }
            SynchronousTerminalDataWebService terminalDataWebService =
                    cloudDataRmiClientConfiguration.getSynchronousTerminalDataWebService();
            return terminalDataWebService.terminalDelete(effectiveCId);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ReturnCode.SYNC_TERMINAL_FAILED.code();
    }

    /**
     * 位置汇报设置
     *
     * @param communicationId 终端通讯号
     * @param token           token
     * @param token           serialNum
     * @param gpsParam        位置汇报参数
     * @return
     */
    private CommonResult positionSetting(String communicationId, String token, String serialNum, GpsParam gpsParam) {
        CommonResult result = new CommonResult();
        Gps_2253_Cmd gps_2253_cmd = new Gps_2253_Cmd();
        gps_2253_cmd.setProgram(gpsParam.getReportPlan());// 位置汇报策略 0:定时 1:定距 2：定时定距
        gps_2253_cmd.setTactics(gpsParam.getReportStrategy());// 位置汇报方案 0:Acc状态 1:登陆和Acc状态
        gps_2253_cmd.setDefaultTime(gpsParam.getDefaultTime());// 缺省时间间隔
        gps_2253_cmd.setDefaultDistance(gpsParam.getDefaultDistance());// 缺省距离间隔
        gps_2253_cmd.setInflectionAngle(gpsParam.getInflectionAngle());// 拐点补偿设置(<180度)
        gps_2253_cmd.setNotLoginDistance(gpsParam.getNotLoginDistance());// 未登录距离间隔
        gps_2253_cmd.setNotLoginTime(gpsParam.getNotLoginTime());// 未登录时间间隔
        gps_2253_cmd.setSleepingDistance(gpsParam.getSleepingDistance());// 休眠距离间隔
        if (gpsParam.getSleepingTime() != null) {
            gps_2253_cmd.setSleepingTime(gpsParam.getSleepingTime());// 休眠时间间隔
        }
        gps_2253_cmd.setUrgentDistance(gpsParam.getUrgentDistance());// 紧急报警距离间隔
        if (gpsParam.getUrgentTime() != null) {
            gps_2253_cmd.setUrgentTime(gpsParam.getUrgentTime());// 紧急报警时间间隔
        }
        gps_2253_cmd.setIllegalDrivingTime(gpsParam.getIllegalDrivingTime());
        gps_2253_cmd.setUniqueMark(communicationId);
        gps_2253_cmd.setCommand(TcpCommandEnum.POSITION_SETTING.getCommand());
        gps_2253_cmd.setSerialNumber(serialNum);
        gps_2253_cmd.setToken(token);
        try {
            result = cmdClient.call_2253(gps_2253_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.POSITION_SETTING_FAILED);
        }
        return result;
    }

    /**
     * can汇报设置
     *
     * @param communicationId 终端通讯号
     * @param token           token
     * @param canParam        can汇报设置参数
     * @return
     */
    private CommonResult canSetting(String communicationId, String token, String serialNum, CanParam canParam) {
        CommonResult result = new CommonResult();

        Gps_2267_Cmd gps_2267_cmd = new Gps_2267_Cmd();
        gps_2267_cmd.setPassages(canParam.getCanReportWay());
        gps_2267_cmd.setTimingInterval(canParam.getDataCollectionInterval());
        gps_2267_cmd.setUploadInterval(canParam.getCanReportTimeWindow());

        gps_2267_cmd.setUniqueMark(communicationId);
        gps_2267_cmd.setSerialNumber(serialNum);
        gps_2267_cmd.setCommand(TcpCommandEnum.CAN_SETTING.getCommand());
        gps_2267_cmd.setToken(token);
        try {
            result = cmdClient.call_2267(gps_2267_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.CAN_SETTING_FAILED);
        }
        return result;
    }

    /**
     * 驾驶行为设置
     *
     * @param alarmParam
     */
    private CommonResult drivingBehaviorSetting(String communicationId, String token, String serialNum,
                                                String driveSetting, AlarmParam alarmParam) {
        CommonResult result = new CommonResult();
        Gps_2270_Cmd gps_2270_cmd = new Gps_2270_Cmd();
        // 处理驾驶行为参数选中与否，选中与否决定是否下发改参数
        List<String> driverSettingList = StringUtil.splitString(driveSetting, ",");
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_RAPIDLY_SPEEDING_UP.getCode())) {// 急加速阀值Acceleration
            gps_2270_cmd.setAcceleration(alarmParam.getAcceleration());
        } else {
            gps_2270_cmd.setAcceleration(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_RAPIDLY_SPEEDING_DOWN.getCode())) {// 急减速阀值Deceleration
            gps_2270_cmd.setDeceleration(alarmParam.getDeceleration());
        } else {
            gps_2270_cmd.setDeceleration(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_LOW_OIL_VOLUME.getCode())) {// 低油量阀值参数LowOil
            gps_2270_cmd.setLowOil(alarmParam.getLowOil());
        } else {
            gps_2270_cmd.setLowOil(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_COASTING_TIME_PARAM.getCode())) {// 空挡滑行时间参数SlideNeutral
            gps_2270_cmd.setSlideNeutral(alarmParam.getSlideNeutral());
        } else {
            gps_2270_cmd.setSlideNeutral(DRIVER_BEHAVIOR_UN_SELECTED);

        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_STEP_ON_THE_ACCELERATOR.getCode())) {// 猛踩油门阀值Throttle
            gps_2270_cmd.setThrottle(alarmParam.getThrottle());
        } else {
            gps_2270_cmd.setThrottle(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_LONG_TIME_BRAKE.getCode())) {// 长时间刹车阀值LongTimeBreaking
            gps_2270_cmd.setLongTimeBreaking(alarmParam.getLongTimeBreaking());
        } else {
            gps_2270_cmd.setLongTimeBreaking(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.THRESHOLD_VALUE_LONG_TIME_CLUTCH.getCode())) {// 长时间离合阀值LongTimeClutch
            gps_2270_cmd.setLongTimeClutch(alarmParam.getLongTimeClutch());
        } else {
            gps_2270_cmd.setLongTimeClutch(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.PARKING_STALL_IMMEDIATELY.getCode())) {// 停车立即熄火ParkedTime
            gps_2270_cmd.setParkedTime(alarmParam.getParkedTime());
        } else {
            gps_2270_cmd.setParkedTime(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.ENGINE_COLD_START.getCode())) {// 发动机冷启动ColdBootTime
            gps_2270_cmd.setColdBootTime(alarmParam.getColdBootTime());
        } else {
            gps_2270_cmd.setColdBootTime(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.LONG_IDLE.getCode())) {// 过长怠速IdlingTime
            gps_2270_cmd.setIdlingTime(alarmParam.getIdlingTime());
        } else {
            gps_2270_cmd.setIdlingTime(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        if (driverSettingList.contains(TerminalConstant.DrivingBehaviorEnum.TIGHT_BEND_ANGLE.getCode())) {// 急转弯设置
            gps_2270_cmd.setSharpTurning(alarmParam.getTightBendAngle());
        } else {
            gps_2270_cmd.setSharpTurning(DRIVER_BEHAVIOR_UN_SELECTED);
        }
        // web无对应项，暂时设置为-1
        gps_2270_cmd.setRpmThreshold(DRIVER_BEHAVIOR_UN_SELECTED);
        gps_2270_cmd.setDisparitySpeed(DRIVER_BEHAVIOR_UN_SELECTED);
        gps_2270_cmd.setBrakePressure(DRIVER_BEHAVIOR_UN_SELECTED);
        gps_2270_cmd.setUniqueMark(communicationId);
        gps_2270_cmd.setSerialNumber(serialNum);
        gps_2270_cmd.setCommand(TcpCommandEnum.DRIVING_BEHAVIOR.getCommand());
        gps_2270_cmd.setToken(token);
        try {
            result = cmdClient.call_2270(gps_2270_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.DRIVING_BEHAVIOR_FAILED);
        }
        return result;
    }

    /**
     * 终端初始化参数设置
     *
     * @param initParam
     */
    private CommonResult terminalInit(String communicationId, String token, String serialNum, InitParam initParam) {
        CommonResult result = new CommonResult();
        Gps_2273_Cmd gps_2273_cmd = new Gps_2273_Cmd();
        gps_2273_cmd.setEngineType(initParam.getEngineType());// 发动机类型
        gps_2273_cmd.setVehType(initParam.getVehType());// 适用车辆类型
        gps_2273_cmd.setUniqueMark(communicationId);
        gps_2273_cmd.setCommand(TcpCommandEnum.TERMINAL_INIT.getCommand());
        gps_2273_cmd.setToken(token);
        gps_2273_cmd.setSerialNumber(serialNum);
        try {
            result = cmdClient.call_2273(gps_2273_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.TERMINAL_INIT_FAILED);
        }
        return result;
    }

    /**
     * 终端升级设置
     *
     * @param upgradeParam
     */
    private CommonResult terminalUpgrade(String communicationId, String token, String serialNum,
                                         UpgradeParam upgradeParam) {
        CommonResult result = new CommonResult();
        Gps_2405_Cmd gps_2405_cmd = new Gps_2405_Cmd();
        gps_2405_cmd.setUrlAddress(upgradeParam.getUrlAddress());// url地址UrlAddress
        gps_2405_cmd.setDialName(upgradeParam.getDialPointName());// 拨号点名称DialPointName
        gps_2405_cmd.setUsername(upgradeParam.getUserName());// 拨号用户名UserName
        gps_2405_cmd.setPassword(upgradeParam.getUserPassword());// 拨号密码UserPassword
        gps_2405_cmd.setServerIp(upgradeParam.getIpAddress());// 服务器地址，ip或域名IpAddress
        // 位置云要求，不主动推送0，位置云自己处理
        if (upgradeParam.getTcpPort() != null && upgradeParam.getTcpPort() != 0) {
            gps_2405_cmd.setTcpPort(upgradeParam.getTcpPort());// tcp端口TcpPort
        }
        // 位置云要求，不主动推送0，位置云自己处理
        if (upgradeParam.getUdpPort() != null && upgradeParam.getUdpPort() != 0) {
            gps_2405_cmd.setUdpPort(upgradeParam.getUdpPort());// udp端口UdpPort
        }
        gps_2405_cmd.setProductId(upgradeParam.getMadeId());// 制造商ID MadeId
        gps_2405_cmd.setHardwareVersion(upgradeParam.getHardwareVersion());// 硬件版本HardwareVersion
        gps_2405_cmd.setFirmwareVersion(upgradeParam.getSoftVersion());// 固件版本（软件版本）SoftVersion
        gps_2405_cmd.setConnectTime(upgradeParam.getLinkTimeLimit());// 连接到指定服务器时限，单位秒
        gps_2405_cmd.setUniqueMark(communicationId);
        gps_2405_cmd.setCommand(TcpCommandEnum.TERMINAL_UPGRADE.getCommand());
        gps_2405_cmd.setToken(token);
        gps_2405_cmd.setSerialNumber(serialNum);
        try {
            result = cmdClient.call_2405(gps_2405_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.TERMINAL_UPGRADE_FAILED);
        }
        return result;
    }

    /**
     * 实时数据采集汇报设置
     *
     * @param communicationId
     * @param token
     * @param serialNum
     * @param realTimeParam
     * @return
     */
    private CommonResult realTimeDataCollectionSetting(String communicationId, String token, String serialNum,
                                                       RealTimeParam realTimeParam) {
        CommonResult result = new CommonResult();
        Gps_2274_Cmd gps_2274_cmd = new Gps_2274_Cmd();
        gps_2274_cmd.setCollectCycle(realTimeParam.getDataCollectionInterval());
        gps_2274_cmd.setUploadCycle(realTimeParam.getDataReportInterval());
        if (realTimeParam.getDataCollectionLifeCycle() == -1) {
            gps_2274_cmd.setCollectionTime(65535);
        } else {
            gps_2274_cmd.setCollectionTime(realTimeParam.getDataCollectionLifeCycle() * 60);
        }

        gps_2274_cmd.setUniqueMark(communicationId);
        gps_2274_cmd.setCommand(TcpCommandEnum.REAL_TIME_DATA_COLLECT.getCommand());
        gps_2274_cmd.setToken(token);
        gps_2274_cmd.setSerialNumber(serialNum);
        try {
            result = cmdClient.call_2274(gps_2274_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.REAL_TIME_DATA_COLLECT_SETTING_FAILED);
        }
        return result;
    }

    /**
     * 特殊数据采集汇报设置
     *
     * @param communicationId
     * @param token
     * @param serialNum
     * @param param
     * @return
     */
    private CommonResult specialDataCollectionSetting(String communicationId, String token, String serialNum,
                                                      SpecialDataParam param, List<CanBusParam> canBusParams) {
        CommonResult result = new CommonResult();
        Gps_2275_Cmd gps_2275_cmd = new Gps_2275_Cmd();
        gps_2275_cmd.setCanChannel(param.getCanWay());
        gps_2275_cmd.setFrameType(param.getFrameType());
        gps_2275_cmd.setCollectionMode(param.getCollectionWay());
        gps_2275_cmd.setBaudRate(param.getBaudRate());
        gps_2275_cmd.setUploadCycle(param.getDataReportInterval());
        gps_2275_cmd.setCollectionTime(param.getDataCollectionInterval());
        if (param.getDataCollectionLifeCycle() == -1) {
            gps_2275_cmd.setCollectCycle(65535);
        } else {
            gps_2275_cmd.setCollectCycle(param.getDataCollectionLifeCycle() * 60);
        }
        gps_2275_cmd.setAlarmLimit(0);
        gps_2275_cmd.setCanBusParamList(canBusParams);

        gps_2275_cmd.setUniqueMark(communicationId);
        gps_2275_cmd.setCommand(TcpCommandEnum.CAN_DATA_CYCLE_UPLOAD_SETTING.getCommand());
        gps_2275_cmd.setToken(token);
        gps_2275_cmd.setSerialNumber(serialNum);
        try {
            result = cmdClient.call_2275(gps_2275_cmd);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result.fillResult(ReturnCode.SPECIAL_DATA_COLLECT_SETTING_FAILED);
        }
        return result;
    }

    /**
     * 构建增加终端参数
     *
     * @param pojo
     * @return
     */
    private HttpCommandResultWithData constructAddTerminalCommand(TerminalTemplePojo pojo,
                                                                  ImportTerminalCommand oCommand) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        AddTerminalCommand command = new AddTerminalCommand();
        // 终端型号转换
        if (StringUtil.isEmpty(pojo.getModel())) {
            result.fillResult(ReturnCode.TERMINAL_MODEL_NOT_EXISTED);
            return result;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("tmName", pojo.getModel());
        TerminalModelPojo terminalModelPojo =
                (TerminalModelPojo) dao.sqlFindObject("queryDataDetail", param, TerminalModelPojo.class);
        if (terminalModelPojo != null) {
            command.setModel(terminalModelPojo.getTmId().toString());
        } else {
            result.fillResult(ReturnCode.TERMINAL_MODEL_NOT_EXISTED);
            return result;
        }
        // 终端类型转换

        if (StringUtil.isEmpty(pojo.getType())) {
            result.fillResult(ReturnCode.TERMINAL_TYPE_IS_NULL);
            return result;
        }
        if (pojo.getType().equals(TerminalConstant.TerminalTypeEnum.FK_CONTROLLER.getMsg())) {
            command.setType(TerminalConstant.TerminalTypeEnum.FK_CONTROLLER.getCode() + ""); // 防拆盒
        } else if (pojo.getType().equals(TerminalConstant.TerminalTypeEnum.BEI_DOU_INTEGRATED_MACHINE.getMsg())) {
            command.setType(TerminalConstant.TerminalTypeEnum.BEI_DOU_INTEGRATED_MACHINE.getCode() + ""); // 一体机
        }
        // 终端协议装换
        if (StringUtil.isEmpty(pojo.getProtocol())) {
            result.fillResult(ReturnCode.NO_SUPPORT_TERMINAL_PROTOCOL);
            return result;
        }
        if (pojo.getProtocol().equals("部标2013（2012行驶记录仪）")) {
            command.setProtocol("200110");
        } else if (pojo.getProtocol().equals("F9")) {
            command.setProtocol("200130");
        }

        // 经销商转换
        if (StringUtil.isEmpty(pojo.getDealerCode())) {
            result.fillResult(ReturnCode.DEALER_CODE_NOT_EXISTED);
            return result;
        }
        Map<String, Object> param_temp = new HashMap<>();

        param_temp.put("accountType", oCommand.getUserInfor().getType());
        param_temp.put("accountId", oCommand.getUserInfor().getUserId());
        param_temp.put("dealerCode", pojo.getDealerCode());
        param_temp.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryTeamIdByParam", param_temp);
        if (StringUtil.isEmpty(list)) {
            result.fillResult(ReturnCode.DEALER_CODE_NOT_EXISTED);
            return result;
        }
        command.setReAgent(list.get(0).toString());
        command.setTerminalId(pojo.getTerminalId());
        command.setSimNo(pojo.getSimNo());
        command.setCommunicationId(pojo.getSimNo());
        command.setDevLabelId(pojo.getLabelId());
        command.setUserInfor(oCommand.getUserInfor());
        command.setRequest(oCommand.getRequest());
        result.setData(command);
        return result;

    }

    /**
     * 保存终端指令日志
     *
     * @param pojo
     * @param userName
     * @param token
     * @param tcpCommandEnum
     * @param operateIp
     * @throws JsonProcessingException
     */
    @Transactional
    public String saveTerminalLog(TerminalPojo pojo, String userName, String token, TcpCommandEnum tcpCommandEnum,
                                  String operateIp)
            throws JsonProcessingException {
        TerminalLogPojo terminalLogPojo = new TerminalLogPojo();
        terminalLogPojo.setOperateUser(userName);
        if (!StringUtil.isEmpty(pojo.gettSim())) {
            terminalLogPojo.setSimNo(BigInteger.valueOf(Long.valueOf(pojo.gettSim())));
        }
        terminalLogPojo.setChassisNum(pojo.getChassisNum());
        terminalLogPojo.setCarNum(pojo.getCarNum());
        terminalLogPojo.setTeam(pojo.getTeamName());
        terminalLogPojo.setReCustom(pojo.getCarOwnerName());
        terminalLogPojo.setDes(tcpCommandEnum.getMsg() + "操作");
        terminalLogPojo.setType(TcpCommandEnum.TERMINAL_SETTING.getMsg());
        terminalLogPojo.setOperateIp(operateIp);
        BigInteger logId = terminalLogService.addTerminalCommandLog(terminalLogPojo);
        TamperData data = new TamperData();
        data.setCommanId(tcpCommandEnum.getCommand());
        data.setLogId(logId.toString());
        data.setToken(token);
        // 获取序列号
        String serialNum = IDGeneralUtil.builderID(IDGeneralUtil.IDTypeEnum.SerialNumber).toString();
        // 保存日志
        redisService.saveObjectToJson(Constants.MSG_KEY_PREFIX + "_" + serialNum + "_"
                + tcpCommandEnum.getCommand().substring(0, 4), data);
        return serialNum;
    }

    /**
     * 终端是否已删除恢复
     *
     * @param command
     * @return
     */
    private HttpCommandResultWithData deletedTerminalRecover(AddTerminalCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> param = new HashMap<>();
        param.put("tCode", command.getTerminalId());
        param.put("delFlag", Constants.IS_NOT_DEL);
        param.put("simNo", command.getSimNo());
        param.put("communicationId", command.getCommunicationId());
        List list = dao.sqlFindField("queryTIdByParam", param);
        if (StringUtil.isEmpty(list)) {
            return result;
        } else {
            Map<String, Object> param1 = new HashMap<>();
            param1.put("id", list.get(0).toString());
            param1.put("delFlag", Constants.IS_NOT_DEL);
            TerminalPojo terminalPojo = (TerminalPojo) dao.sqlFindObject("queryTerminalById", param1, TerminalPojo.class);
            // 添加终端到数据库
            HyTerminalEntity t = TerminalConverter.commandToEntity(command, null, null);
            t.setDelFlag(Constants.IS_NOT_DEL);
            t.settId(terminalPojo.gettId());
            t.settAutoCommunicationId(terminalPojo.gettAutoCommunicationId());
            t.setAutoFlag(terminalPojo.getAutoFlag());
            t.setImportTime(terminalPojo.getImportTime());
            t.setCreateUser(terminalPojo.getCreateUser());
            HyTerminalEntity entity = (HyTerminalEntity) dao.update(t);
            result.setData(entity);
            operateLogService.addOperateLog(command,
                    OperateLogConstants.OperateTypeEnum.UPDATE,
                    OperateLogConstants.OperateContentPrefixEnum.TERMINAL.getValue() + command.getTerminalId(),
                    result);
            result.fillResult(ReturnCode.TERMINAL_RECOVER);
            return result;

        }
    }

    /**
     * 更新终端设置参数
     *
     * @param tId
     * @param menuEnum
     * @param baseTerminalParam
     * @throws JsonProcessingException
     */
    public void updateTerminalSettingParam(String tId, TerminalConstant.TerminalSettingMenuEnum menuEnum, BaseTerminalParam baseTerminalParam) throws JsonProcessingException {
        HyTerminalSettingParamEntity entity = new HyTerminalSettingParamEntity();
        entity.setTerminalId(StringUtil.toBigInteger(tId));
        entity.setParamType(menuEnum.getCode());
        entity.setParamJsonValue(JsonUtil.toJson(baseTerminalParam));
        entity.setSettingTime(BigInteger.valueOf(StringUtil.getCurrentTimeSeconds()));
        dao.executeUpdate("updateTerminalSettingParam", entity);
    }

    /**
     * 新增终端设置参数
     *
     * @param tId
     * @param menuEnum
     * @param baseTerminalParam
     * @throws JsonProcessingException
     */
    public void addOrUpdateTerminalSettingParam(String tId, TerminalConstant.TerminalSettingMenuEnum menuEnum, BaseTerminalParam baseTerminalParam) throws JsonProcessingException {
        if (hasSettingParam(tId, menuEnum.getCode())) {
            updateTerminalSettingParam(tId, menuEnum, baseTerminalParam);
        } else {
            HyTerminalSettingParamEntity entity = new HyTerminalSettingParamEntity();
            entity.setTerminalId(StringUtil.toBigInteger(tId));
            entity.setParamType(menuEnum.getCode());
            entity.setParamJsonValue(JsonUtil.toJson(baseTerminalParam));
            entity.setSettingTime(BigInteger.valueOf(StringUtil.getCurrentTimeSeconds()));
            dao.save(entity);
        }
    }

    /**
     * 判断是否已经设置该参数
     *
     * @param tId       终端ID
     * @param paramType 参数类型
     * @return
     */
    public boolean hasSettingParam(String tId, int paramType) {
        Map<String, Object> param = new HashMap<>();
        param.put("terminalId", tId);
        param.put("paramType", paramType);
        List list = dao.sqlFindField("hasSettingParam", param);
        return !StringUtil.isEmpty(list);
    }

    /**
     * 查询终端设置参数
     *
     * @param terminalId 终端ID
     * @return
     */
    private List<HyTerminalSettingParamEntity> queryTerminalSettingParam(String terminalId) {
        // 查询终端
        Map<String, String> param = new HashMap<>();
        param.put("terminalId", terminalId);
        return dao.sqlFind("queryTerminalSettingByTerminalId", param, HyTerminalSettingParamEntity.class);
    }

    /**
     * 查询CAN 总线数据
     *
     * @param pids
     * @return
     */
    private List<CanBusDataPojo> queryCanBusDataBypids(String pids) {
        Map<String, Object> param = new HashMap<>();
        param.put("pids", pids);
        return dao.sqlFind("queryCanBusDataByPids", param, CanBusDataPojo.class);
    }
}
