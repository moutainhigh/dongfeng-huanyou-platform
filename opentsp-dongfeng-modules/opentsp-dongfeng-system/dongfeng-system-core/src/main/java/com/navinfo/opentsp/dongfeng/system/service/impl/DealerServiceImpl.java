package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseRuntimeException;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.AuditDealerOutdto;
import com.navinfo.opentsp.dongfeng.system.entity.*;
import com.navinfo.opentsp.dongfeng.system.pojo.*;
import com.navinfo.opentsp.dongfeng.system.repository.*;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 经销商服务类
 *
 * @Author zhaoming@mapbar.com
 * @Date 2017/3/10 10:51
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class DealerServiceImpl extends BaseService implements DealerService {

    protected static final Logger logger = LoggerFactory.getLogger(DealerServiceImpl.class);
    // 审核状态 0：待审核  1：拒绝  2：同意
    public static final String AUDIT_RESULT_NO = "1";
    public static final String AUDIT_RESULT_YES = "2";
    // 经销商类型 1：一级经销商  2：二级经销商
    public static final String ONE_DEALER = "1";
    public static final String TWO_DEALER = "2";
    // 经销商同步标签系统常量
    public static final String SYN_SUCCESS = "200";
    public static final String SYN_ADD = "0";
    public static final String SYN_UPDATE = "1";
    public static final String SYN_DELETE = "2";
    public static final String BIG_AERA = "X009003";
    @Autowired
    private TeamMappingRepository teamMappingRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private EmployeesRepository employeesRepository;

    @Autowired
    private SecdteamRepository secdteamRepository;

    @Resource
    private IOperateLogService operateLogService;
    @Autowired
    private ISyncService syncService;
    @Value("${sign.server.syn.switch}")
    private String syncSwitch;
    @Value("${cloud.server.sync.stationTeam.switch}")
    private String syncStationTeamSwitch;

    /**
     * 功能描述:删除经销商即车组
     * 车组删除:还需要做验证,验证当前车组是否还有信息.包括有如下:
     * 1、是否还有子车组
     * 2、是否车组下还有车辆
     * 3、是否车组还有終端
     * 如有以上三个任意一信息存在 刚当前车组不允许删除操作
     *
     * @author zhaoming@mapbar.com
     * @date 2017/3/10 10:56
     */
    @Override
    public HttpCommandResultWithData deleteDealer(String tid) throws Exception {
        logger.info(" ========== deleteDealer start ==========");
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (StringUtil.isNotEmpty(tid)) {
            Long teamId = Long.parseLong(tid);
            int res = -1;
            // 车组ID为1的车组不允许删除
            if (teamId == 1) {
                // 根节点不允许删除
                result.fillResult(ReturnCode.ROOT_TEAM_CANNOT_DEL);
                return result;
            }
            //       Map<String,Object> resultMap = new HashMap<String,Object>();
            result = isExistTeam(teamId, result);
            boolean existTeamResult = false;
            if (result.getResultCode() == ReturnCode.OK.code()) {
                existTeamResult = true;
            }
            TeamEntity paramEntity = new TeamEntity();
            paramEntity.setId(new BigInteger(tid));
            TeamEntity teamEntity = (TeamEntity) dao.findOne(paramEntity);
            if (existTeamResult) {
                //进行标签系统同步,teamEntity.getTtype()!=0：经销商同步、经销商区域不进行同步
                if (Boolean.valueOf(syncSwitch) && teamEntity.getTtype() != 0) {
                    HttpCommandResultWithData resultWithData = synSecTeamToSignSystemForDelete(tid);
                    boolean synResult = false;
                    if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                        synResult = true;
                    } else {
                        synResult = false;
                    }
                    if (!synResult) {
                        throw new BaseRuntimeException("同步电子标签系统失败:" + resultWithData.getMessage() + ",请重新修改或联系管理员！");
                    }
                }
                //进行TBOSS系统同步,teamEntity.getTtype()!=0：经销商同步、经销商区域不进行同步
                if (Boolean.valueOf(syncStationTeamSwitch) && teamEntity.getTtype() != 0) {
                    //新增同步tboss失败，不进行删除同步
                    if (teamEntity.getSyncStatus().intValue() != 1) {
                        String tId = teamEntity.getId().toString();
                        String province = teamEntity.getProvince().toString();
                        String city = teamEntity.getCity().toString();
                        int operateType = 2;
                        HttpCommandResultWithData resultWithData = syncService.syncDealer(tId, province, city, operateType);
                        if (resultWithData.getResultCode() != ReturnCode.OK.code()) {
                            throw new BaseRuntimeException("同步Tboss系统失败:" + result.getMessage() + ",请重新修改或联系管理员！");
                        }
                    }
                }
                HyTeamEntity entity = new HyTeamEntity();
                entity.settId(new BigInteger(tid));
                dao.executeUpdate("deleteDealer", entity);
                //删除用户车组绑定关系
                List<AccountTeamMapping> list = teamMappingRepository.findByCarTeamIdAndAtcType(new BigInteger(tid), 0);
                if (!StringUtil.isEmpty(list)) {
                    dao.batchDelete(list);
                }
            }
        } else {
            result.fillResult(ReturnCode.DEL_FAIL);
        }
        return result;
    }

    /**
     * 功能描述:
     *
     * @author zhaoming@mapbar.com
     * @date 2017/3/13 15:01
     */
    @Override
    public HttpCommandResultWithData addDealer(AddDealerCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result.fillResult(ReturnCode.OK);

        DealerIndto db = command.getDealerBean();
        //省市编码不为0
        if (db.getGovCodePrvc() == 0 || db.getGovCodeCity() == 0) {
            result.fillResult(ReturnCode.DEALER_CITYANDPROVICE_NOZERO);
            return result;
        }
        //验证经销商名称是否唯一
        Long count_name = teamRepository.countByTnameAndDelFlag(db.getTname(), Constants.IS_NOT_DEL);
        if (count_name > 0) {
            result.fillResult(ReturnCode.DEALERNAME_ALREADY_EXISTS);
            return result;
        }
        //验证经销商编码是否唯一
        Long count_code = teamRepository.countByDealerCodeAndDelFlag(db.getDealerCode(), Constants.IS_NOT_DEL);
        if (count_code > 0) {
            result.fillResult(ReturnCode.DEALER_ALREADY_EXISTS);
            return result;
        }
        //如果没有则新增
        if (!(count_code > 0 && count_name > 0)) {
            db.settType(1);
            db.settAccountName(command.getUserInfor().getUsername());
            result = saveOrUpdate(db);
        }
        //进行标签系统同步
        if (Boolean.valueOf(syncSwitch)) {
            HttpCommandResultWithData resultWithData = synSecTeamToSignSystemForAdd(command);
            Integer synStatus = Integer.valueOf(SYN_ADD);
            if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                synStatus = Integer.valueOf(SYN_SUCCESS);
            }
            //修改一级经销商同步状态
            String tId = (String) result.getData();
            TeamEntity teamEntity = new TeamEntity();
            teamEntity.setId(new BigInteger(tId));
            teamEntity.setSyncSignStatus(synStatus);
            dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
            //修改二级经销商同步状态
            if (StringUtil.isNotEmpty(command.getDealerBean().getHySecdTeamList())) {
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("tid", tId);
                param.put("secIds", "");
                //查询二级经销商信息
                List<SecdteamPojo> secTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
                SecdteamEntity secTeamEntity = null;
                if (!ListUtil.isEmpty(secTeamList)) {
                    for (SecdteamPojo forTeam : secTeamList) {
                        secTeamEntity = new SecdteamEntity();
                        secTeamEntity.setId(forTeam.getId());
                        secTeamEntity.setSyncStatus(synStatus);
                        dao.executeUpdate("updateTwoDealerSynSignStatus", secTeamEntity);
                    }
                }
            }
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.DEALER.getValue() + command.getDealerBean().getDealerCode(), result);

        return result;
    }


    /**
     * 功能描述:
     *
     * @author zhaoming@mapbar.com
     * @date 2017/3/13 15:01
     */
    @Override
    public HttpCommandResultWithData updateDealer(UpdateDealerCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        //省市编码不为0
        if (command.getDealerIndto().getGovCodePrvc() == null || command.getDealerIndto().getGovCodeCity() == null) {
            result.fillResult(ReturnCode.DEALER_CITYANDPROVICE_NOZERO);
            return result;
        }
        if (command.getDealerIndto().getGovCodePrvc() == 0 || command.getDealerIndto().getGovCodeCity() == 0) {
            result.fillResult(ReturnCode.DEALER_CITYANDPROVICE_NOZERO);
            return result;
        }
        TeamEntity team = teamRepository.findByIdAndDelFlag(BigInteger.valueOf(command.getDealerIndto().getId()), Constants.IS_NOT_DEL);
        if (!command.getDealerIndto().getTname().equals(team.getTname())) {
            //验证经销商名称是否唯一
            Long count_name = teamRepository.countByTnameAndDelFlag(command.getDealerIndto().getTname(), Constants.IS_NOT_DEL);
            if (count_name > 0) {
                result.fillResult(ReturnCode.DEALERNAME_ALREADY_EXISTS);
                return result;
            }
        }
        DealerIndto db = command.getDealerIndto();
        result = saveOrUpdate(db);
        //进行标签系统同步
        if (Boolean.valueOf(syncSwitch)) {
            HttpCommandResultWithData resultWithData = synSecTeamToSignSystemForUpdate(command);
            boolean synResult = false;
            if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                synResult = true;
            } else {
                synResult = false;
            }
            TeamEntity initteamEntity = new TeamEntity();
            initteamEntity.setId(BigInteger.valueOf(db.getId()));
            TeamEntity queryTeamEntity = (TeamEntity) dao.findOne(initteamEntity);
            if (StringUtil.isNotEmpty(command.getDealerIndto().getDelSecdTeam()) && !synResult && queryTeamEntity.getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD).intValue()) {
                throw new BaseRuntimeException("同步电子标签系统失败:" + resultWithData.getMessage() + ",请重新修改或联系管理员！");
            } else {
                //修改一级经销商同步状态
                Long tId = db.getId();
                TeamEntity teamEntity = new TeamEntity();
                teamEntity.setId(new BigInteger(String.valueOf(db.getId())));
                if (synResult) {
                    teamEntity.setSyncSignStatus(Integer.valueOf(SYN_SUCCESS));
                } else {
                    if (queryTeamEntity.getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD).intValue()) {
                        teamEntity.setSyncSignStatus(Integer.valueOf(SYN_UPDATE));
                    } else {
                        teamEntity.setSyncSignStatus(Integer.valueOf(SYN_ADD));
                    }
                }
                dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                //修改二级经销商同步状态
                if (StringUtil.isNotEmpty(command.getDealerIndto().getHySecdTeamList())) {
                    String secdTeamStr = command.getDealerIndto().getHySecdTeamList();
                    JSONArray jsonArrayList = JSONArray.fromObject(secdTeamStr);
                    List<String> codeList = new ArrayList<String>();
                    for (int i = 0; i < jsonArrayList.size(); i++) {
                        JSONObject jsonObject = jsonArrayList.getJSONObject(i);
                        if (null != jsonObject.get("id")) {
                            codeList.add((String) jsonObject.get("code"));
                        }
                    }
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("tid", String.valueOf(tId));
                    param.put("secIds", "");
                    //查询二级经销商信息
                    List<SecdteamPojo> secTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
                    SecdteamPojo secTeamEntity = null;
                    if (!ListUtil.isEmpty(secTeamList)) {
                        for (SecdteamPojo forTeam : secTeamList) {
                            secTeamEntity = new SecdteamPojo();
                            secTeamEntity.setId(forTeam.getId());
                            if (synResult) {
                                secTeamEntity.setSyncStatus(Integer.valueOf(SYN_SUCCESS));
                            } else {
                                if (codeList.contains(forTeam.getCode())) {
                                    if (forTeam.getSyncStatus().equals(Integer.valueOf(SYN_SUCCESS))) {
                                        secTeamEntity.setSyncStatus(Integer.valueOf(SYN_UPDATE));
                                    } else {
                                        secTeamEntity.setSyncStatus(forTeam.getSyncStatus());
                                    }
                                } else {
                                    secTeamEntity.setSyncStatus(Integer.valueOf(SYN_ADD));
                                }
                            }
                            dao.executeUpdate("updateTwoDealerSynSignStatus", secTeamEntity);
                        }
                    }
                }
            }
        }
        //如果存在二级网点,则根据经销商ID进行关联删除
        delSecdTeam(db.getDelSecdTeam());
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.DEALER.getValue() + command.getDealerIndto().getDealerCode(), result);
        return result;
    }


    /**
     * 功能描述:车组的新增 更新
     *
     * @author zhaoming@mapbar.com
     * @date 2017/3/13 17:02
     */
    @Override
    public HttpCommandResultWithData saveOrUpdate(DealerIndto db) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        result.fillResult(ReturnCode.OK);
        int res = -1;

        // 验证是否新增或修改的车组其父ID被设为0（即该车组成为根节点）,父ID为空时不验证
        if (db.getParentId() != null && db.getParentId() == 0) {
            result.fillResult(ReturnCode.ROOT_CAR_TEAM_CANNOT_SAVEORUPDATE);
            return result;
        }
        //同一分组内不允许出现相同名称的车组
        if (db.getId() == null) {//新增
            long count = teamRepository.countByParentIdAndTnameAndDelFlag(db.getParentId(), db.getTname(), Constants.IS_NOT_DEL);
            if (count > 0) {
                result.fillResult(ReturnCode.DATA_EXIST);
                return result;
            }
        } else { //修改
            TeamEntity team = teamRepository.findByIdAndDelFlag(BigInteger.valueOf(db.getId()), Constants.IS_NOT_DEL);
            if (team.getParentId().longValue() != db.getParentId().longValue() || !team.getTname().equals(db.getTname())) {
                long count = teamRepository.countByParentIdAndTnameAndDelFlag(db.getParentId(), db.getTname(), Constants.IS_NOT_DEL);
                if (count > 0) {
                    result.fillResult(ReturnCode.DATA_EXIST);
                    return result;
                }
            }
        }
        // 判断是新增还是修改
        int insertRe = 0;
        if (db.getId() == null) {//新增
            TeamEntity entity = dtoToEntity(db, new TeamEntity());
            entity.settDate(StringUtil.toBigInteger(StringUtil.getCurrentTimeSeconds()));
            entity.settAccountName(db.gettAccountName());
            entity.setSyncStatus(0);
            entity = (TeamEntity) dao.save(entity);
            if (StringUtil.isNotEmpty(db.getHySecdTeamList())) {

                String secdTeamStr = db.getHySecdTeamList();
                JSONArray jsonArrayList = JSONArray.fromObject(secdTeamStr);
                List<SecdteamEntity> secdTeamList = new ArrayList<SecdteamEntity>();
                JSONObject jsonObject;
                SecdteamEntity hySecdTeamEntity;
                for (int i = 0; i < jsonArrayList.size(); i++) {
                    jsonObject = jsonArrayList.getJSONObject(i);
                    hySecdTeamEntity = (SecdteamEntity) JSONObject.toBean(jsonObject, SecdteamEntity.class);
                    //验证二网经纬度、地址为空
                    if(hySecdTeamEntity.getLatitude()==null || hySecdTeamEntity.getLatitude().longValue() ==0
                            || hySecdTeamEntity.getLongitude()==null || hySecdTeamEntity.getLongitude().longValue() ==0
                            || StringUtil.isEmpty(hySecdTeamEntity.getAddress()))
                    {
                        logger.info("二级经销商的经纬度或者地址不能为空的编码：" + hySecdTeamEntity.getCode());
                        throw new BaseRuntimeException("二级经销商的经纬度或者地址不能为空！");
                    }
                    hySecdTeamEntity.setDealerCode(entity.getDealerCode());
                    hySecdTeamEntity.setDealerId(entity.getId().longValue());
                    secdTeamList.add(hySecdTeamEntity);
                }
                dao.batchSave(secdTeamList, true);

            }

            //新增车组时，父车组拥有权限的用户自动添加权限，caozhen，20160314
            if (db.getParentId() != null && db.getParentId() > 0) {
                Map<String, Object> atcPar = new HashMap<String, Object>();
                atcPar.put("carTeamId", db.getParentId());
                atcPar.put("atcType", 0);
                List<HyAtcMappingEntity> bkList = dao.sqlFind("queryByPIdAndAtcType", atcPar, HyAtcMappingEntity.class);

                List<HyAtcMappingEntity> list = new ArrayList<HyAtcMappingEntity>();

                if (bkList != null && bkList.size() > 0) {
                    for (HyAtcMappingEntity actEntity : bkList) {
                        HyAtcMappingEntity atcEntity = new HyAtcMappingEntity();
                        atcEntity.setCarTeamId(entity.getId());
                        atcEntity.setAtcType(0);
                        atcEntity.setAccountId(actEntity.getAccountId());
                        list.add(atcEntity);
                    }
                    //批量插入用户车组关联关系
                    dao.batchSave(list, true);
                }
            }
            //经销商ID暂时塞到返回结果中
            result.setData(entity.getId().toString());
        } else {//修改
            TeamEntity entity = teamRepository.findByIdAndDelFlag(BigInteger.valueOf(db.getId()), Constants.IS_NOT_DEL);
            entity = dtoToEntity(db, entity);
            dao.update(entity);
            //2017-02-28:东风的二级网点单独存放
            if (StringUtil.isNotEmpty(db.getHySecdTeamList())) {
                String secdTeamStr = db.getHySecdTeamList();
                JSONArray jsonArrayList = JSONArray.fromObject(secdTeamStr);
                List<SecdteamEntity> secdTeamList = new ArrayList<SecdteamEntity>();
                JSONObject jsonObject;
                SecdteamEntity secdTeamEntity = null;
                for (int i = 0; i < jsonArrayList.size(); i++) {
                    jsonObject = jsonArrayList.getJSONObject(i);
                    secdTeamEntity = (SecdteamEntity) JSONObject.toBean(jsonObject, SecdteamEntity.class);
                    //验证二网经纬度、地址为空
                    if(secdTeamEntity.getLatitude()==null || secdTeamEntity.getLatitude().longValue() ==0
                            || secdTeamEntity.getLongitude()==null || secdTeamEntity.getLongitude().longValue() ==0
                            || StringUtil.isEmpty(secdTeamEntity.getAddress()))
                    {
                        logger.info("二级经销商的经纬度或者地址不能为空的编码："+secdTeamEntity.getCode());
                        throw new BaseRuntimeException("二级经销商的经纬度或者地址不能为空！");
                    }
                    secdTeamEntity.setDealerCode(entity.getDealerCode());
                    secdTeamEntity.setDealerId(entity.getId().longValue());
                    if (null != secdTeamEntity.getId()) {
                        SecdteamEntity entityPam = new SecdteamEntity();
                        entityPam.setId(secdTeamEntity.getId());
                        SecdteamEntity reSec = (SecdteamEntity) dao.findOne(entityPam);
                        secdTeamEntity.setSyncStatus(reSec.getSyncStatus());
                    } else {
                        secdTeamEntity.setSyncStatus(Integer.valueOf(SYN_SUCCESS));
                    }
                    secdTeamList.add(secdTeamEntity);
                }
                if (!ListUtil.isEmpty(secdTeamList)) {
                    for (SecdteamEntity forTeam : secdTeamList) {
                        if (null != forTeam.getId()) {
                            dao.update(forTeam);
                        } else {
                            dao.save(forTeam);
                        }
                    }
                }
            }

        }
        return result;
    }

    /**
     * 删除车组需要判断其下是否存在对象或者信息<BR>
     * 1、判断其下是否有子车组<BR>
     * 2、判断其下是否有车辆<BR>
     * 3、判断其下是否有终端<BR>
     * 4、判断其下是否有人员<BR>
     * 5、判断其是否与用户有进行绑定<BR>
     * 6、判断其下是否有二级网点<BR>
     * 注意：<BR>
     * 第5点是否需要进行提示其是否与用户有进行绑定还是在判断完上3点后直接进行删除车组?<BR>
     *
     * @param teamId 车组ID
     * @param result
     * @return CommonResult：
     * result-不存在,允许删除;
     * false：存在，不允许删除;
     * returnInfo-操作提示信息
     * @author zhaoming@mapbar.com
     */
    private HttpCommandResultWithData isExistTeam(Long teamId, HttpCommandResultWithData result) {
        try {
            // 判断当前车组名下是否存在子车组
            List<TeamEntity> teamList = teamRepository.findByParentIdAndDelFlag(teamId, Constants.IS_NOT_DEL);
            if (!teamList.isEmpty()) {
                //todo rex
                result.fillResult(ReturnCode.TEAM_HAS_CHILDREN_CANNOT_DEL);
                return result;
            }
            // 判断当前车组名下是否存在车辆
            List<CarEntity> carList = carRepository.findByDealerIdAndDelFlag(teamId, Constants.IS_NOT_DEL);
            if (!carList.isEmpty()) {
                //todo rex
                result.fillResult(ReturnCode.TEAM_HAS_CAR_CANNOT_DEL);
                return result;
            }
            // 判断当前车组名下是否存在终端
            List<HyTerminalEntity> terminalList = terminalRepository.findByTTeamIdAndDelFlag(BigInteger.valueOf(teamId), Constants.IS_NOT_DEL);
            if (!terminalList.isEmpty()) {
                //todo rex
                result.fillResult(ReturnCode.TEAM_HAS_TERMINAL_CANNOT_DEL);
                return result;
            }
            // 判断当前车组名下是否存在人员
            List<EmployeesEntity> employeesList = employeesRepository.findByTeamId(teamId);
            if (!employeesList.isEmpty()) {
                //todo rex
                result.fillResult(ReturnCode.TEAM_HAS_EMPLOYEE_CANNOT_DEL);
                return result;
            }
            // 东风判断经销商下是否存在二级经销商网点
            List<SecdteamEntity> secdteamList = secdteamRepository.findByDealerId(teamId);
            if (!secdteamList.isEmpty()) {
                //todo rex
                result.fillResult(ReturnCode.TEAM_HAS_SECDTEAM_CANNOT_DEL);
                return result;
            }
        } catch (Exception e) {
            //todo rex
            result.fillResult(ReturnCode.DEL_FAIL);
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
        }
        return result;
    }


    /**
     * dto转Entity
     * 车组的新增 更新时共通
     *
     * @param db
     * @return
     */
    private TeamEntity dtoToEntity(DealerIndto db, TeamEntity entity) {
        entity.setTname(db.getTname());
        entity.setDealerCode(db.getDealerCode());
        entity.setManageBrand(db.getManageBrand());
        entity.setCompanyName(db.getPname());
        entity.setCompanyAddress(db.getCompanyAddress());
        entity.setCompanyLinkman(db.getCompanyLinkman());
        entity.setCompanyLinktel(db.getCompanyLinktel());
        entity.settLinkMan(db.gettLinkMan());
        entity.settLinkTel(db.gettLinkTle());
        if (db.getEnableRadius() != null) {
            entity.setEnableRadius(Integer.parseInt(db.getEnableRadius()));
        } else {
            entity.setEnableRadius(0);
        }
        if (db.getLockRadius() != null) {
            entity.setLockRadius(Integer.parseInt(db.getLockRadius()));
        } else {
            entity.setLockRadius(0);
        }
        entity.setParentId(db.getParentId());
        entity.setTeamLat(db.getTeamLat());
        entity.setTeamLon(db.getTeamLon());
        entity.setCompanyFax(db.getCompanyFax());
        entity.setCompanyCode(db.getCompanyCode());
        entity.setTeamPicture(db.getTeamPicture());
        entity.setTtype(db.gettType());
        entity.setDistrict(1);
        entity.setProvince(db.getGovCodePrvc());
        entity.setCity(db.getGovCodeCity());
        entity.setUpdateTime(new BigInteger(String.valueOf(StringUtil.getCurrentTimeSeconds())));
        return entity;
    }

    @Override
    public HttpCommandResultWithData queryDealerPage(QuerySDealerCommand command, Boolean isAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String tName = command.getTname();
        String tCode = command.getDealerCode();
        String pId = command.getpId();
        Map<String, Object> parm = new HashMap<>();
        parm.put("accountType", command.getUserInfor().getType());
        parm.put("accountId", command.getUserInfor().getUserId());
        if (StringUtil.isNotEmpty(command.getTname())) {
            parm.put("tname", command.getTname());
        }
        if (StringUtil.isNotEmpty(command.getDealerCode())) {
            parm.put("dealerCode", command.getDealerCode());
        }
        if (StringUtil.isNotEmpty(command.getpId())) {
            parm.put("pId", command.getpId());
        }
        if (isAll) {
            List<SDealerPojo> list = dao.sqlFind("queryDealerPage", parm, SDealerPojo.class);
            for (SDealerPojo sDealerPojo : list) {
                sDealerPojo.setEnableRadius(new Integer(sDealerPojo.getEnableRadius()));
                sDealerPojo.setLockRadius(new Integer(sDealerPojo.getLockRadius()));
            }
            result.setData(list);
        } else {
            PagingInfo commonsPageInfo = dao.sqlPagelFind("queryDealerPage", parm, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), SDealerPojo.class);
            result.setData(commonsPageInfo);
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData getDealer(GetSDealerCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        if (StringUtil.isNotEmpty(command.getTid())) {
            SDealerPojo pojo = new SDealerPojo();
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("tid", new BigInteger(command.getTid()));
            map.put("accountId", command.getUserInfor().getUserId());
            pojo = (SDealerPojo) dao.sqlFindObject("getDealer", map, SDealerPojo.class);
            List<SecdteamEntity> secdteam = secdteamRepository.findByDealerId(Long.parseLong(command.getTid()));
            if (!secdteam.isEmpty()) {
                pojo.setSecdTeam(secdteam);
            }
            result.setData(pojo);
            result.fillResult(ReturnCode.OK);
        } else {
            result.fillResult(ReturnCode.SERVER_ERROR);
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData queryAuditDealer(QueryAuditDealerCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("accountType", command.getUserInfor().getType());
            map.put("accountId", command.getUserInfor().getUserId());
            if (StringUtil.isNotEmpty(command.getDealerName())) {
                map.put("dealerName", command.getDealerName());
            }
            if (StringUtil.isNotEmpty(command.getCode())) {
                map.put("code", command.getCode());
            }
            PagingInfo commonsPageInfo = dao.sqlPagelFind("queryAuditDealer", map, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), AuditDealerPojo.class);
            List<AuditDealerPojo> pojos = commonsPageInfo.getList();
            List<AuditDealerOutdto> dtos = new ArrayList<AuditDealerOutdto>();
            AuditDealerOutdto dto = null;
            for (AuditDealerPojo dealerPojo : pojos) {
                dto = new AuditDealerOutdto();
                try {
                    CopyPropUtil.copyProp(dealerPojo, dto);
                    if (null != dealerPojo.getNewLat()) {
                        dto.setNewLat(NumberFormatUtil.getLatitudeOrLongitude(dealerPojo.getNewLat().intValue()));
                    } else {
                        dto.setNewLat(0);
                    }
                    if (null != dealerPojo.getNewLng()) {
                        dto.setNewLng(NumberFormatUtil.getLatitudeOrLongitude(dealerPojo.getNewLng().intValue()));
                    } else {
                        dto.setNewLng(0);
                    }
                    if (null != dealerPojo.getOldLat()) {
                        dto.setOldLat(NumberFormatUtil.getLatitudeOrLongitude(dealerPojo.getOldLat().intValue()));
                    } else {
                        dto.setOldLat(0);
                    }
                    if (null != dealerPojo.getOldLng()) {
                        dto.setOldLng(NumberFormatUtil.getLatitudeOrLongitude(dealerPojo.getOldLng().intValue()));
                    } else {
                        dto.setOldLng(0);
                    }
                    dto.setCreateTime(DateUtil.timeStr(dealerPojo.getCreateTime().longValue()));
                    dto.setRadius(dto.getRadius());
                    dtos.add(dto);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
                }
            }
            commonsPageInfo.setList(dtos);
            result.setData(commonsPageInfo);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("DealerServiceImpl ====> queryAuditDealer : ", e);
        }
        return result;
    }

    @Override
    public CommonResult updateDealerAuditStatus(UpdateDealerAuditStatusCommand command) throws Exception {
        CommonResult result = new CommonResult();
        updateAuditStatus(command);
        //同步电子标签
        if (Boolean.valueOf(syncSwitch)) {
            HttpCommandResultWithData resultWithData = null;
            //同步结果
            boolean synResult = false;
            if (command.getAuditStatus().equals(AUDIT_RESULT_YES)) {
                //查询待同步的二级经销商信息并同步
                if (StringUtil.isNotEmpty(command.getAuditId())) {
                    if (command.getType().equals(TWO_DEALER)) {
                        resultWithData = synSecTeamToSignSystem(command.getAuditId(), "");
                    }
                    if (command.getType().equals(ONE_DEALER)) {
                        resultWithData = synSecTeamToSignSystem("", command.getAuditId());
                    }
                    if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                        synResult = true;
                    } else {
                        synResult = false;
                    }
                } else {
                    synResult = true;
                }
            } else {
                synResult = true;
            }
            //同步失败，修改状态
            if (!synResult) {
                //查询待同步的一级经销商信息
                if (command.getType().equals(ONE_DEALER)) {
                    Map<String, Object> oneParam = new HashMap<String, Object>();
                    oneParam.put("auditIds", command.getAuditId());
                    List<TeamEntity> oneParamList = dao.sqlFind("queryTeamId", oneParam, TeamEntity.class);
                    if (!ListUtil.isEmpty(oneParamList)) {
                        if (oneParamList.get(0).getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD)) {
                            TeamEntity teamEntity = new TeamEntity();
                            teamEntity.setId(oneParamList.get(0).getId());
                            teamEntity.setSyncSignStatus(Integer.valueOf(SYN_UPDATE));
                            dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                        }
                    }
                }
                if (command.getType().equals(TWO_DEALER)) {
                    //查询待同步的二级经销商信息
                    Map<String, Object> param = new HashMap<String, Object>();
                    param.put("auditIds", command.getAuditId());
                    List<SecdteamPojo> twoParamList = dao.sqlFind("querySecTeamId", param, SecdteamPojo.class);
                    if (!ListUtil.isEmpty(twoParamList)) {
                        if (twoParamList.get(0).getSyncStatus().intValue() != Integer.valueOf(SYN_ADD)) {
                            SecdteamEntity secTeamEntity = new SecdteamEntity();
                            secTeamEntity.setId(twoParamList.get(0).getId());
                            secTeamEntity.setSyncStatus(Integer.valueOf(SYN_UPDATE));
                            dao.executeUpdate("updateTwoDealerSynSignStatus", secTeamEntity);
                            //将一级状态也进行修改
                            TeamEntity teamEntity = new TeamEntity();
                            teamEntity.setId(twoParamList.get(0).getDealerId());
                            TeamEntity queryTeamEntity = (TeamEntity) dao.findOne(teamEntity);
                            if (queryTeamEntity.getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD).intValue()) {
                                teamEntity.setSyncSignStatus(Integer.valueOf(SYN_UPDATE));
                                dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CommonResult updateBatchDealerAuditStatus(UpdateBatchDealerAuditStatusCommand command) throws Exception {
        CommonResult result = new CommonResult();
        //审核状态
        String auditStatus = command.getAuditStatus();
        //审核意见
        String opinion = command.getOpinion();
        //待审核经销商字符串
        String dealerAudit = command.getDealerAudit();
        //待审核字符串转集合对象
        if (StringUtil.isNotEmpty(dealerAudit)) {
            JSONArray jsonArrayList = JSONArray.fromObject(dealerAudit);
            List<UpdateDealerAuditStatusCommand> auditDealerList = new ArrayList<UpdateDealerAuditStatusCommand>();
            JSONObject jsonObject = null;
            AuditDealerPojo dealerPojo = null;
            UpdateDealerAuditStatusCommand statusCommand = null;
            //同步字符串
            StringBuffer synSecTeamIds = new StringBuffer();
            StringBuffer synTeamIds = new StringBuffer();
            if (jsonArrayList.size() > 0) {
                for (int i = 0; i < jsonArrayList.size(); i++) {
                    jsonObject = jsonArrayList.getJSONObject(i);
                    dealerPojo = (AuditDealerPojo) JSONObject.toBean(jsonObject, AuditDealerPojo.class);
                    statusCommand = new UpdateDealerAuditStatusCommand();
                    //审核ID
                    statusCommand.setAuditId(String.valueOf(dealerPojo.getAuditId()));
                    //经销商ID
                    statusCommand.setTid(dealerPojo.getDealerId().toString());
                    //经销商类型
                    statusCommand.setType(String.valueOf(dealerPojo.getType()));
                    //经销商编码
                    statusCommand.setDealerCode(dealerPojo.getCode());
                    //审核状态
                    statusCommand.setAuditStatus(auditStatus);
                    //审核意见
                    statusCommand.setOpinion(opinion);
                    if (StringUtil.isNotEmpty(dealerPojo.getProvince())) {
                        statusCommand.setProvince(dealerPojo.getProvince());
                    }
                    if (StringUtil.isNotEmpty(StringUtil.toInteger(dealerPojo.getCity()))) {
                        statusCommand.setCity(dealerPojo.getCity());
                    }
                    auditDealerList.add(statusCommand);
                    //判断审核通过并且经销商类型为二级
                    if (auditStatus.equals(AUDIT_RESULT_YES) && String.valueOf(dealerPojo.getType()).equals(TWO_DEALER)) {
                        synSecTeamIds.append(dealerPojo.getAuditId()).append(",");
                    }
                    //判断审核通过并且经销商类型为一级
                    if (auditStatus.equals(AUDIT_RESULT_YES) && String.valueOf(dealerPojo.getType()).equals(ONE_DEALER)) {
                        synTeamIds.append(dealerPojo.getAuditId()).append(",");
                    }
                }
                for (UpdateDealerAuditStatusCommand scommand : auditDealerList) {
                    //循环审核
                    updateAuditStatus(scommand);
                    operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.DEALER.getValue() + scommand.getDealerCode(), result);
                }
                //同步电子标签
                if (Boolean.valueOf(syncSwitch)) {
                    HttpCommandResultWithData resultWithData = null;
                    //同步结果
                    boolean synResult = false;
                    //经销商信息同步
                    if (StringUtil.isEmpty(synSecTeamIds.toString()) && StringUtil.isEmpty(synTeamIds.toString())) {
                        synResult = true;
                    } else {
                        if (StringUtil.isNotEmpty(synSecTeamIds.toString()) && StringUtil.isEmpty(synTeamIds.toString())) {
                            resultWithData = synSecTeamToSignSystem(synSecTeamIds.toString().substring(0, synSecTeamIds.toString().length() - 1), "");
                        }
                        if (StringUtil.isEmpty(synSecTeamIds.toString()) && StringUtil.isNotEmpty(synTeamIds.toString())) {
                            resultWithData = synSecTeamToSignSystem("", synTeamIds.toString().substring(0, synTeamIds.toString().length() - 1));
                        }
                        if (StringUtil.isNotEmpty(synSecTeamIds.toString()) && StringUtil.isNotEmpty(synTeamIds.toString())) {
                            resultWithData = synSecTeamToSignSystem(synSecTeamIds.toString().substring(0, synSecTeamIds.toString().length() - 1), synTeamIds.toString().substring(0, synTeamIds.toString().length() - 1));
                        }
                        if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                            synResult = true;
                        } else {
                            synResult = false;
                        }
                    }
                    //同步失败，修改状态
                    if (!synResult) {
                        //一级状态修改
                        if (StringUtil.isNotEmpty(synTeamIds.toString())) {
                            //查询待同步的一级经销商信息
                            Map<String, Object> oneParam = new HashMap<String, Object>();
                            oneParam.put("auditIds", synTeamIds.toString().substring(0, synTeamIds.toString().length() - 1));
                            List<TeamEntity> oneParamList = dao.sqlFind("queryTeamId", oneParam, TeamEntity.class);
                            if (!ListUtil.isEmpty(oneParamList)) {
                                for (TeamEntity initTeamEntity : oneParamList) {
                                    if (initTeamEntity.getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD)) {
                                        TeamEntity teamEntity = new TeamEntity();
                                        teamEntity.setId(initTeamEntity.getId());
                                        teamEntity.setSyncSignStatus(Integer.valueOf(SYN_UPDATE));
                                        dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                                    }
                                }
                            }
                        }
                        //二级状态修改
                        if (StringUtil.isNotEmpty(synSecTeamIds.toString())) {
                            //查询待同步的二级经销商信息
                            Map<String, Object> param = new HashMap<String, Object>();
                            param.put("auditIds", synSecTeamIds.toString().substring(0, synSecTeamIds.toString().length() - 1));
                            List<SecdteamPojo> twoParamList = dao.sqlFind("querySecTeamId", param, SecdteamPojo.class);
                            if (!ListUtil.isEmpty(twoParamList)) {
                                for (SecdteamPojo initSecTeamEntity : twoParamList) {
                                    if (initSecTeamEntity.getSyncStatus().intValue() != Integer.valueOf(SYN_ADD).intValue()) {
                                        SecdteamEntity secTeamEntity = new SecdteamEntity();
                                        secTeamEntity.setId(initSecTeamEntity.getId());
                                        secTeamEntity.setSyncStatus(Integer.valueOf(SYN_UPDATE));
                                        dao.executeUpdate("updateTwoDealerSynSignStatus", secTeamEntity);
                                        //将一级状态也进行修改
                                        TeamEntity teamEntity = new TeamEntity();
                                        teamEntity.setId(initSecTeamEntity.getDealerId());
                                        TeamEntity queryTeamEntity = (TeamEntity) dao.findOne(teamEntity);
                                        if (queryTeamEntity.getSyncSignStatus().intValue() != Integer.valueOf(SYN_ADD).intValue()) {
                                            teamEntity.setSyncSignStatus(Integer.valueOf(SYN_UPDATE));
                                            dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    public CommonResult synDealerToSign(SynDealerToSignCommand command) throws Exception {
        CommonResult result = new CommonResult();
        //进行标签系统同步
        if (Boolean.valueOf(syncSwitch)) {
            HttpCommandResultWithData resultWithData = synSecTeamToSignSystemForSelf(command);
            if (resultWithData.getResultCode() == ReturnCode.OK.code()) {
                //修改一级经销商同步状态
                String tId = command.getTid();
                TeamEntity teamEntity = new TeamEntity();
                teamEntity.setId(new BigInteger(tId));
                teamEntity.setSyncSignStatus(Integer.valueOf(SYN_SUCCESS));
                dao.executeUpdate("updateOneDealerSynSignStatus", teamEntity);
                //修改二级经销商同步状态
                Map<String, Object> param = new HashMap<String, Object>();
                param.put("tid", tId);
                param.put("secIds", "");
                //查询二级经销商信息
                List<SecdteamPojo> secTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
                SecdteamEntity secTeamEntity = null;
                if (!ListUtil.isEmpty(secTeamList)) {
                    for (SecdteamPojo forTeam : secTeamList) {
                        secTeamEntity = new SecdteamEntity();
                        secTeamEntity.setId(forTeam.getId());
                        secTeamEntity.setSyncStatus(Integer.valueOf(SYN_SUCCESS));
                        dao.executeUpdate("updateTwoDealerSynSignStatus", secTeamEntity);
                    }
                }
            } else {
                throw new BaseRuntimeException("同步电子标签系统失败:" + resultWithData.getMessage() + ",请重新修改或联系管理员！");
            }
        } else {
            result.fillResult(ReturnCode.SYN_NO_SIGN_SWITH);
        }
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult synDealerToTboss(SynDealerToTbossCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (Boolean.valueOf(syncStationTeamSwitch)) {
            //一级经销商处理
            TeamEntity entity = new TeamEntity();
            entity.setId(new BigInteger(command.getTid()));
            TeamEntity hyTeamEntity = (TeamEntity) dao.findOne(entity);
            //组装参数
            String stationId = String.valueOf(hyTeamEntity.getId());
            String province = String.valueOf(hyTeamEntity.getProvince());
            String city = String.valueOf(hyTeamEntity.getCity());
            int type = hyTeamEntity.getSyncStatus();
            result = syncService.syncDealer(stationId, province, city, type);
            if (null != result) {
                if (result.getResultCode() != ReturnCode.OK.code()) {
                    throw new BaseRuntimeException("同步Tboss系统失败:" + result.getMessage() + ",请重新修改或联系管理员！");
                }
            } else {
                throw new BaseRuntimeException("同步Tboss系统失败:" + result.getMessage() + ",请重新修改或联系管理员！");
            }
        } else {
            result.fillResult(ReturnCode.SYN_NO_BOSS_SWITH);
        }
        return result;
    }

    /**
     * 执行审核操作
     *
     * @param command
     * @throws Exception
     */
    public void updateAuditStatus(UpdateDealerAuditStatusCommand command) throws Exception {
        //审核结果
        String auditResult = command.getAuditStatus();
        if (auditResult.equals(AUDIT_RESULT_NO)) {
            HyAuditTeamEntity entity = setAuditEntity(command);
            dao.save(entity);
        }
        if (auditResult.equals(AUDIT_RESULT_YES)) {
            //一级经销商处理
            if (command.getType().equals(ONE_DEALER)) {
                HyTeamEntity teamEntity = setOneTeamEntity(command);
                dao.executeUpdate("updateOneDealer", teamEntity);
                if (Boolean.valueOf(syncStationTeamSwitch)) {
                    syncService.asyncDealer(command.getTid().toString(), command.getProvince(), command.getCity(), 5);
                }
            }
            //二级经销商处理
            if (command.getType().equals(TWO_DEALER)) {
                SecdteamEntity hySecdteamEntity = setTwoTeamEntity(command);
                if(hySecdteamEntity.getLatitude()==null || hySecdteamEntity.getLatitude().longValue() ==0
                   || hySecdteamEntity.getLongitude()==null || hySecdteamEntity.getLongitude().longValue() ==0
                   || StringUtil.isEmpty(hySecdteamEntity.getAddress()))
                {
                    logger.info("二级经销商的经纬度或者地址不能为空的编码：" + hySecdteamEntity.getCode());
                    throw new BaseRuntimeException("二级经销商的经纬度或者地址不能为空！");
                }
                dao.executeUpdate("updateTwoDealer", hySecdteamEntity);
            }
            HyAuditTeamEntity entity = setAuditEntity(command);
            dao.save(entity);
        }
    }

    /**
     * 审核经销商HyAuditTeamEntity修改组装
     *
     * @param command
     * @return
     */
    public HyAuditTeamEntity setAuditEntity(UpdateDealerAuditStatusCommand command) {
        HyAuditTeamEntity entity = queryAuditEntity(command);
        entity.setAuditStatus(new BigInteger(command.getAuditStatus()));
        entity.setOpition(command.getOpinion());
        //entity.setAuditUser(new BigInteger(String.valueOf(command.getUserInfor().getUserId())));
        entity.setAuditTime(new BigInteger(String.valueOf(System.currentTimeMillis() / 1000)));
        return entity;
    }

    /**
     * 一级经销商HyTeamEntity修改组装
     *
     * @param command
     * @return
     */
    public HyTeamEntity setOneTeamEntity(UpdateDealerAuditStatusCommand command) {
        HyTeamEntity entity = new HyTeamEntity();
        HyAuditTeamEntity auditEntity = queryAuditEntity(command);
        entity.settId(new BigInteger(command.getTid()));
        entity.setTeamLat(auditEntity.getLat());
        entity.setTeamLon(auditEntity.getLng());
        entity.setCompanyAddress(auditEntity.getAddress());
        if (StringUtil.isNotEmpty(command.getProvince())) {
            entity.setProvince(StringUtil.toInteger(command.getProvince()));
        }
        if (StringUtil.isNotEmpty(StringUtil.toInteger(command.getCity()))) {
            entity.setCity(StringUtil.toInteger(command.getCity()));

        }
        return entity;
    }

    /**
     * 二级经销商HyTeamEntity修改组装
     *
     * @param command
     * @return
     */
    public SecdteamEntity setTwoTeamEntity(UpdateDealerAuditStatusCommand command) {
        SecdteamEntity entity = new SecdteamEntity();
        HyAuditTeamEntity auditEntity = queryAuditEntity(command);
        entity.setId(new BigInteger(command.getTid()));
        entity.setLatitude(auditEntity.getLat().longValue());
        entity.setLongitude(auditEntity.getLng().longValue());
        entity.setAddress(auditEntity.getAddress());
        return entity;
    }

    /**
     * 审核经销商HyAuditTeamEntity查询
     *
     * @param command
     * @return
     */
    public HyAuditTeamEntity queryAuditEntity(UpdateDealerAuditStatusCommand command) {
        HyAuditTeamEntity queryEntity = new HyAuditTeamEntity();
        queryEntity.setId(new BigInteger(command.getAuditId()));
        HyAuditTeamEntity entity = (HyAuditTeamEntity) dao.findOne(queryEntity);
        return entity;
    }

    /**
     * 删除二级经销商网点
     *
     * @param delSecdTeam
     */
    private void delSecdTeam(String delSecdTeam) throws Exception {
        if (StringUtil.isEmpty(delSecdTeam)) {
            return;
        }
        List<String> delSubStationList = StringUtil.stringToList(delSecdTeam, ",");
        for (String subId : delSubStationList) {
            SecdteamEntity entity = new SecdteamEntity();
            entity.setId(StringUtil.toBigInteger(subId));
            dao.delete(entity);
        }

    }

    /**
     * 同步二级经销商网点到标签系统
     *
     * @param synSecTeamIds
     */
    private HttpCommandResultWithData synSecTeamToSignSystem(String synSecTeamIds, String synTeamIds) throws Exception {
        HttpCommandResultWithData synResult = new HttpCommandResultWithData();
        //查询待同步的一级经销商信息并同步
        List<TeamSynSignPojo> oneParamList = new ArrayList<TeamSynSignPojo>();
        if (StringUtil.isNotEmpty(synTeamIds)) {
            Map<String, Object> oneParam = new HashMap<String, Object>();
            oneParam.put("auditIds", synTeamIds);
            oneParamList = dao.sqlFind("queryToSynTeam", oneParam, TeamSynSignPojo.class);
            if (!ListUtil.isEmpty(oneParamList)) {
                for (TeamSynSignPojo pojo : oneParamList) {
                    if (pojo.getEditMode().equals(SYN_SUCCESS)) {
                        pojo.setEditMode(SYN_UPDATE);
                    }
                    validateTeamLength(pojo);
                }
            }
        }
        List<SecTeamSynSignPojo> paramList = new ArrayList<SecTeamSynSignPojo>();
        if (StringUtil.isNotEmpty(synSecTeamIds)) {
            //查询待同步的二级经销商信息并同步
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("auditIds", synSecTeamIds);
            paramList = dao.sqlFind("queryToSynSecTeam", param, SecTeamSynSignPojo.class);
            if (!ListUtil.isEmpty(paramList)) {
                for (SecTeamSynSignPojo pojo : paramList) {
                    if (pojo.getEditMode().equals(SYN_SUCCESS)) {
                        pojo.setEditMode(SYN_UPDATE);
                    }
                    validateSecTeamLength(pojo);
                }
            }
        }
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dealerList", oneParamList);
            jsonParam.put("secDealerList", paramList);
            synResult = syncService.syncDealerToSign(jsonParam);
            return synResult;
        } catch (Exception e) {
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
            return new HttpCommandResultWithData().fillResult(ReturnCode.SING_SYSTEM_ERROR);
        }
    }

    /**
     * 同步二级经销商网点到标签系统
     *
     * @param command
     */
    private HttpCommandResultWithData synSecTeamToSignSystemForUpdate(UpdateDealerCommand command) throws Exception {
        HttpCommandResultWithData synResult = new HttpCommandResultWithData();
        //一级经销商处理
        List<TeamSynSignPojo> teamList = new ArrayList<TeamSynSignPojo>();
        if (null != command.getDealerIndto()) {
            TeamSynSignPojo teamSynSignPojo = new TeamSynSignPojo();
            teamSynSignPojo.setJf0200(command.getDealerIndto().getDealerCode());
            teamSynSignPojo.setJf0201(BIG_AERA);
            teamSynSignPojo.setJf0202("1");
            teamSynSignPojo.setJf0203(command.getDealerIndto().getTname());
            teamSynSignPojo.setJf0204(command.getDealerIndto().getTname());
            teamSynSignPojo.setJf0205(command.getDealerIndto().getCompanyAddress());
            teamSynSignPojo.setJf0206(command.getDealerIndto().getCompanyLinkman());
            teamSynSignPojo.setJf0207(command.getDealerIndto().getCompanyLinktel());
            teamSynSignPojo.setEditMode(SYN_UPDATE);
            teamList.add(validateTeamLength(teamSynSignPojo));
        }
        //待添加/修改二级经销商处理
        List<SecTeamSynSignPojo> secdTeamList = new ArrayList<SecTeamSynSignPojo>();
        if (StringUtil.isNotEmpty(command.getDealerIndto().getHySecdTeamList())) {
            String secdTeamStr = command.getDealerIndto().getHySecdTeamList();
            JSONArray jsonArrayList = JSONArray.fromObject(secdTeamStr);
            SecTeamSynSignPojo secTeamSynSignPojo = null;
            SecdteamEntity hySecdTeamEntity = null;
            for (int i = 0; i < jsonArrayList.size(); i++) {
                JSONObject jsonObject = jsonArrayList.getJSONObject(i);
                hySecdTeamEntity = (SecdteamEntity) JSONObject.toBean(jsonObject, SecdteamEntity.class);
                secTeamSynSignPojo = new SecTeamSynSignPojo();
                secTeamSynSignPojo.setJf0300(command.getDealerIndto().getDealerCode());
                if (null != hySecdTeamEntity.getWorkType()) {
                    secTeamSynSignPojo.setJf0301(String.valueOf(hySecdTeamEntity.getWorkType().intValue()));
                } else {
                    secTeamSynSignPojo.setJf0301("1");//默认：收车
                }
                secTeamSynSignPojo.setJf0302(hySecdTeamEntity.getCode());
                if (null != hySecdTeamEntity.getLongitude()) {
                    secTeamSynSignPojo.setJf0303(hySecdTeamEntity.getLongitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0303("0");
                }
                if (null != hySecdTeamEntity.getLatitude()) {
                    secTeamSynSignPojo.setJf0304(hySecdTeamEntity.getLatitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0304("0");
                }
                secTeamSynSignPojo.setJf0305("1");
                secTeamSynSignPojo.setJf0306(hySecdTeamEntity.getWorkRadius());
                secTeamSynSignPojo.setJf0307(hySecdTeamEntity.getName());
                if (null != hySecdTeamEntity.getId()) {
                    secTeamSynSignPojo.setEditMode(SYN_UPDATE);
                } else {
                    secTeamSynSignPojo.setEditMode(SYN_ADD);
                }
                secdTeamList.add(validateSecTeamLength(secTeamSynSignPojo));
            }
        }
        //待删除二级经销商处理
        if (StringUtil.isNotEmpty(command.getDealerIndto().getDelSecdTeam())) {
            //查询待删除二级经销商信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("tid", String.valueOf(command.getDealerIndto().getId()));
            param.put("secIds", command.getDealerIndto().getDelSecdTeam());
            List<SecdteamPojo> delSecTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
            SecTeamSynSignPojo secTeamSynSignPojo = null;
            for (SecdteamPojo hySecdTeamEntity : delSecTeamList) {
                if (hySecdTeamEntity.getSyncStatus().intValue() == Integer.valueOf(SYN_ADD).intValue()) {
                    continue;
                }
                secTeamSynSignPojo = new SecTeamSynSignPojo();
                secTeamSynSignPojo.setJf0300(command.getDealerIndto().getDealerCode());
                if (null != hySecdTeamEntity.getWorkType()) {
                    secTeamSynSignPojo.setJf0301(String.valueOf(hySecdTeamEntity.getWorkType().intValue()));
                } else {
                    secTeamSynSignPojo.setJf0301("1");//默认：收车
                }
                secTeamSynSignPojo.setJf0302(hySecdTeamEntity.getCode());
                if (null != hySecdTeamEntity.getLongitude()) {
                    secTeamSynSignPojo.setJf0303(hySecdTeamEntity.getLongitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0303("0");
                }
                if (null != hySecdTeamEntity.getLatitude()) {
                    secTeamSynSignPojo.setJf0304(hySecdTeamEntity.getLatitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0304("0");
                }
                secTeamSynSignPojo.setJf0305("1");
                secTeamSynSignPojo.setJf0306(hySecdTeamEntity.getWorkRadius());
                secTeamSynSignPojo.setJf0307(hySecdTeamEntity.getName());
                secTeamSynSignPojo.setEditMode(SYN_DELETE);
                secdTeamList.add(validateSecTeamLength(secTeamSynSignPojo));
            }
        }
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dealerList", teamList);
            jsonParam.put("secDealerList", secdTeamList);
            synResult = syncService.syncDealerToSign(jsonParam);
            return synResult;
        } catch (Exception e) {
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
            return new HttpCommandResultWithData().fillResult(ReturnCode.SING_SYSTEM_ERROR);
        }
    }

    /**
     * 同步二级经销商网点到标签系统
     *
     * @param command
     */
    private HttpCommandResultWithData synSecTeamToSignSystemForAdd(AddDealerCommand command) throws Exception {
        HttpCommandResultWithData synResult = new HttpCommandResultWithData();
        //一级经销商处理
        List<TeamSynSignPojo> teamList = new ArrayList<TeamSynSignPojo>();
        if (null != command.getDealerBean()) {
            TeamSynSignPojo teamSynSignPojo = new TeamSynSignPojo();
            teamSynSignPojo.setJf0200(command.getDealerBean().getDealerCode());
            teamSynSignPojo.setJf0201(BIG_AERA);
            teamSynSignPojo.setJf0202("1");
            teamSynSignPojo.setJf0203(command.getDealerBean().getTname());
            teamSynSignPojo.setJf0204(command.getDealerBean().getTname());
            teamSynSignPojo.setJf0205(command.getDealerBean().getCompanyAddress());
            teamSynSignPojo.setJf0206(command.getDealerBean().getCompanyLinkman());
            teamSynSignPojo.setJf0207(command.getDealerBean().getCompanyLinktel());
            teamSynSignPojo.setEditMode(SYN_ADD);
            teamList.add(validateTeamLength(teamSynSignPojo));
        }
        //二级经销商处理
        List<SecTeamSynSignPojo> secdTeamList = new ArrayList<SecTeamSynSignPojo>();
        if (StringUtil.isNotEmpty(command.getDealerBean().getHySecdTeamList())) {
            String secdTeamStr = command.getDealerBean().getHySecdTeamList();
            JSONArray jsonArrayList = JSONArray.fromObject(secdTeamStr);
            SecTeamSynSignPojo secTeamSynSignPojo = null;
            SecdteamEntity hySecdTeamEntity = null;
            for (int i = 0; i < jsonArrayList.size(); i++) {
                JSONObject jsonObject = jsonArrayList.getJSONObject(i);
                hySecdTeamEntity = (SecdteamEntity) JSONObject.toBean(jsonObject, SecdteamEntity.class);
                secTeamSynSignPojo = new SecTeamSynSignPojo();
                secTeamSynSignPojo.setJf0300(command.getDealerBean().getDealerCode());
                if (null != hySecdTeamEntity.getWorkType()) {
                    secTeamSynSignPojo.setJf0301(String.valueOf(hySecdTeamEntity.getWorkType().intValue()));
                } else {
                    secTeamSynSignPojo.setJf0301("1");//默认：收车
                }
                secTeamSynSignPojo.setJf0302(hySecdTeamEntity.getCode());
                if (null != hySecdTeamEntity.getLongitude()) {
                    secTeamSynSignPojo.setJf0303(hySecdTeamEntity.getLongitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0303("0");
                }
                if (null != hySecdTeamEntity.getLatitude()) {
                    secTeamSynSignPojo.setJf0304(hySecdTeamEntity.getLatitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0304("0");
                }
                secTeamSynSignPojo.setJf0305("1");
                secTeamSynSignPojo.setJf0306(hySecdTeamEntity.getWorkRadius());
                secTeamSynSignPojo.setJf0307(hySecdTeamEntity.getName());
                secTeamSynSignPojo.setEditMode(SYN_ADD);
                secdTeamList.add(validateSecTeamLength(secTeamSynSignPojo));
            }
        }
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dealerList", teamList);
            jsonParam.put("secDealerList", secdTeamList);
            synResult = syncService.syncDealerToSign(jsonParam);
            return synResult;
        } catch (Exception e) {
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
            return new HttpCommandResultWithData().fillResult(ReturnCode.SING_SYSTEM_ERROR);
        }
    }

    /**
     * 同步二级经销商网点到标签系统
     *
     * @param tid
     */
    private HttpCommandResultWithData synSecTeamToSignSystemForDelete(String tid) throws Exception {
        HttpCommandResultWithData synResult = new HttpCommandResultWithData();
        //一级经销商处理
        TeamEntity entity = new TeamEntity();
        entity.setId(new BigInteger(tid));
        TeamEntity hyTeamEntity = (TeamEntity) dao.findOne(entity);
        if (hyTeamEntity.getSyncSignStatus().intValue() == Integer.valueOf(SYN_ADD).intValue()) {
            synResult.fillResult(ReturnCode.OK);
            return synResult;
        }
        //一级经销商处理
        List<TeamSynSignPojo> teamList = new ArrayList<TeamSynSignPojo>();
        if (null != hyTeamEntity) {
            TeamSynSignPojo teamSynSignPojo = new TeamSynSignPojo();
            teamSynSignPojo.setJf0200(hyTeamEntity.getDealerCode());
            teamSynSignPojo.setJf0201(BIG_AERA);
            teamSynSignPojo.setJf0202("1");
            teamSynSignPojo.setJf0203(hyTeamEntity.getTname());
            teamSynSignPojo.setJf0204(hyTeamEntity.getTname());
            teamSynSignPojo.setJf0205(hyTeamEntity.getCompanyAddress());
            teamSynSignPojo.setJf0206(hyTeamEntity.getCompanyLinkman());
            teamSynSignPojo.setJf0207(hyTeamEntity.getCompanyLinktel());
            teamSynSignPojo.setEditMode(SYN_DELETE);
            teamList.add(validateTeamLength(teamSynSignPojo));
        }
        //二级经销商处理(有二级经销商一级不允许删除)
        List<SecTeamSynSignPojo> secdTeamList = new ArrayList<SecTeamSynSignPojo>();
//        if(StringUtil.isNotEmpty(tid)){
//            //查询待删除二级经销商信息
//            Map<String, Object> param = new HashMap<String, Object>();
//            param.put("tid", tid);
//            param.put("secIds", "");
//            List<SecdteamPojo> delSecTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
//            SecTeamSynSignPojo secTeamSynSignPojo = null;
//            for (SecdteamPojo hySecdTeamEntity: delSecTeamList) {
//                secTeamSynSignPojo = new SecTeamSynSignPojo();
//                secTeamSynSignPojo.setJf0300(hyTeamEntity.getDealerCode());
//                secTeamSynSignPojo.setJf0301(hySecdTeamEntity.getWorkType());
//                secTeamSynSignPojo.setJf0302(hySecdTeamEntity.getCode());
//                secTeamSynSignPojo.setJf0303(new BigInteger(String.valueOf(hySecdTeamEntity.getLongitude())));
//                secTeamSynSignPojo.setJf0304(new BigInteger(String.valueOf(hySecdTeamEntity.getLatitude())));
//                secTeamSynSignPojo.setJf0305(new BigInteger("1"));
//                secTeamSynSignPojo.setJf0306(hySecdTeamEntity.getWorkRadius());
//                secTeamSynSignPojo.setJf0307(hySecdTeamEntity.getName());
//                secTeamSynSignPojo.setEditMode(Integer.valueOf(SYN_DELETE));
//                secdTeamList.add(secTeamSynSignPojo);
//            }
//        }
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dealerList", teamList);
            jsonParam.put("secDealerList", secdTeamList);
            synResult = syncService.syncDealerToSign(jsonParam);
            return synResult;
        } catch (Exception e) {
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
            return new HttpCommandResultWithData().fillResult(ReturnCode.SING_SYSTEM_ERROR);
        }
    }

    /**
     * 同步二级经销商网点到标签系统
     *
     * @param command
     */
    private HttpCommandResultWithData synSecTeamToSignSystemForSelf(SynDealerToSignCommand command) throws Exception {

        String tid = command.getTid();
        HttpCommandResultWithData synResult = new HttpCommandResultWithData();
        //一级经销商处理
        TeamEntity entity = new TeamEntity();
        entity.setId(new BigInteger(tid));
        TeamEntity hyTeamEntity = (TeamEntity) dao.findOne(entity);
        //一级经销商处理
        List<TeamSynSignPojo> teamList = new ArrayList<TeamSynSignPojo>();
        if (null != hyTeamEntity) {
            TeamSynSignPojo teamSynSignPojo = new TeamSynSignPojo();
            teamSynSignPojo.setJf0200(hyTeamEntity.getDealerCode());
            teamSynSignPojo.setJf0201(BIG_AERA);
            teamSynSignPojo.setJf0202("1");
            teamSynSignPojo.setJf0203(hyTeamEntity.getTname());
            teamSynSignPojo.setJf0204(hyTeamEntity.getTname());
            teamSynSignPojo.setJf0205(hyTeamEntity.getCompanyAddress());
            teamSynSignPojo.setJf0206(hyTeamEntity.getCompanyLinkman());
            teamSynSignPojo.setJf0207(hyTeamEntity.getCompanyLinktel());
            if (hyTeamEntity.getSyncSignStatus().intValue() == Integer.valueOf(SYN_SUCCESS).intValue()) {
                teamSynSignPojo.setEditMode(SYN_UPDATE);
            } else {
                teamSynSignPojo.setEditMode(String.valueOf(hyTeamEntity.getSyncSignStatus()));
            }
            teamList.add(validateTeamLength(teamSynSignPojo));
        }
        //二级经销商处理
        List<SecTeamSynSignPojo> secdTeamList = new ArrayList<SecTeamSynSignPojo>();
        if (StringUtil.isNotEmpty(tid)) {
            //查询待同步二级经销商信息
            Map<String, Object> param = new HashMap<String, Object>();
            param.put("tid", tid);
            param.put("secIds", "");
            List<SecdteamPojo> delSecTeamList = dao.sqlFind("querySecTeam", param, SecdteamPojo.class);
            SecTeamSynSignPojo secTeamSynSignPojo = null;
            for (SecdteamPojo hySecdTeamEntity : delSecTeamList) {
                secTeamSynSignPojo = new SecTeamSynSignPojo();
                secTeamSynSignPojo.setJf0300(hyTeamEntity.getDealerCode());
                if (null != hySecdTeamEntity.getWorkType()) {
                    secTeamSynSignPojo.setJf0301(String.valueOf(hySecdTeamEntity.getWorkType().intValue()));
                } else {
                    secTeamSynSignPojo.setJf0301("1");//默认：收车
                }
                secTeamSynSignPojo.setJf0302(hySecdTeamEntity.getCode());
                if (null != hySecdTeamEntity.getLongitude()) {
                    secTeamSynSignPojo.setJf0303(hySecdTeamEntity.getLongitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0303("0");
                }
                if (null != hySecdTeamEntity.getLatitude()) {
                    secTeamSynSignPojo.setJf0304(hySecdTeamEntity.getLatitude().toString());
                } else {
                    secTeamSynSignPojo.setJf0304("0");
                }
                secTeamSynSignPojo.setJf0305("1");
                secTeamSynSignPojo.setJf0306(hySecdTeamEntity.getWorkRadius());
                secTeamSynSignPojo.setJf0307(hySecdTeamEntity.getName());
                if (hySecdTeamEntity.getSyncStatus().intValue() == Integer.valueOf(SYN_SUCCESS).intValue()) {
                    secTeamSynSignPojo.setEditMode(SYN_UPDATE);
                } else {
                    secTeamSynSignPojo.setEditMode(String.valueOf(hySecdTeamEntity.getSyncStatus().intValue()));
                }
                secdTeamList.add(validateSecTeamLength(secTeamSynSignPojo));
            }
        }
        try {
            JSONObject jsonParam = new JSONObject();
            jsonParam.put("dealerList", teamList);
            jsonParam.put("secDealerList", secdTeamList);
            //发起标签系统调用
            return syncService.syncDealerToSign(jsonParam);
        } catch (Exception e) {
            logger.error(DealerServiceImpl.class.getSimpleName() + " Exception:", e);
            return new HttpCommandResultWithData().fillResult(ReturnCode.SING_SYSTEM_ERROR);
        }
    }

    /**
     * 校验一级经销商传值长度（括号内长度为实际测试长度，其他为文档提供长度）
     *
     * @param pojo
     * @return
     */
    public TeamSynSignPojo validateTeamLength(TeamSynSignPojo pojo) {
        if (null != pojo) {
            //经销商代码必填 长度20 （东风库为主键）jf0200
            if (StringUtil.isNotEmpty(pojo.getJf0200()) && StringUtil.length(pojo.getJf0200()) > 20) {
                pojo.setJf0200(StringUtil.subChinseseStr(pojo.getJf0200(), 20));
            }
            //单位简称 选填 长度30(20) （和单位名称同，超长截取）jf0203
            if (StringUtil.isNotEmpty(pojo.getJf0203()) && StringUtil.length(pojo.getJf0203()) > 20) {
                pojo.setJf0203(StringUtil.subChinseseStr(pojo.getJf0203(), 20));
            }
            //单位名称 选填 长度80 超长截取 jf0204
            if (StringUtil.isNotEmpty(pojo.getJf0204()) && StringUtil.length(pojo.getJf0204()) > 80) {
                pojo.setJf0204(StringUtil.subChinseseStr(pojo.getJf0204(), 80));
            }
            //地址 选填 长度100(80) 超长截取 jf0205
            if (StringUtil.isNotEmpty(pojo.getJf0205()) && StringUtil.length(pojo.getJf0205()) > 80) {
                pojo.setJf0205(StringUtil.subChinseseStr(pojo.getJf0205(), 80));
            }
            //联系人选填 长度20 jf0206
            if (StringUtil.isNotEmpty(pojo.getJf0206()) && StringUtil.length(pojo.getJf0206()) > 20) {
                pojo.setJf0206(StringUtil.subChinseseStr(pojo.getJf0206(), 20));
            }
            //联系电话 选填 长度30 jf0207
            if (StringUtil.isNotEmpty(pojo.getJf0207()) && StringUtil.length(pojo.getJf0207()) > 30) {
                pojo.setJf0207(StringUtil.subChinseseStr(pojo.getJf0207(), 30));
            }
        }
        return pojo;
    }

    /**
     * 校验二级经销商传值长度
     *
     * @param pojo
     * @return
     */
    public SecTeamSynSignPojo validateSecTeamLength(SecTeamSynSignPojo pojo) {
        if (null != pojo) {
            //经销商代码必填 长度20 （东风库为主键）jf0300
            if (StringUtil.isNotEmpty(pojo.getJf0300()) && StringUtil.length(pojo.getJf0300()) > 20) {
                pojo.setJf0300(StringUtil.subChinseseStr(pojo.getJf0300(), 20));
            }
            //到货地代码	必填 长度10 （东风库为主键）jf0302
            if (StringUtil.isNotEmpty(pojo.getJf0302()) && StringUtil.length(pojo.getJf0302()) > 10) {
                pojo.setJf0302(StringUtil.subChinseseStr(pojo.getJf0302(), 10));
            }
            //到货地名称	选填 长度20 超长截取 jf0307
            if (StringUtil.isNotEmpty(pojo.getJf0307()) && StringUtil.length(pojo.getJf0307()) > 20) {
                pojo.setJf0307(StringUtil.subChinseseStr(pojo.getJf0307(), 20));
            }
        }
        return pojo;
    }
}