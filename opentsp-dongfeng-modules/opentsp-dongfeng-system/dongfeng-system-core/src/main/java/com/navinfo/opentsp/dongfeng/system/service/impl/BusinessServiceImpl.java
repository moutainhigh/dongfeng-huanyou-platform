package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.QueryConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseConveterException;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.business.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.BusinessOutdto;
import com.navinfo.opentsp.dongfeng.system.converter.BusinessConverter;
import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.BusinessPojo;
import com.navinfo.opentsp.dongfeng.system.repository.BusinessRepository;
import com.navinfo.opentsp.dongfeng.system.service.IBusinessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yaocy on 2017/03/14.
 * add crud fengwm
 * 客户相关功能Service
 */
@Service
public class BusinessServiceImpl extends BaseService implements IBusinessService {
    /**
     * Brand Service LOG
     */
    protected static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);

    @Autowired
    private BusinessRepository businessRepository;

    @Override
    public HttpCommandResultWithData queryBusiness(BusinessCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        try {
            //List<BusinessEntity> list = businessRepository.findAll();

            //根据当前登入用户筛选所要查找的客户信息------created by zhangtiantong 2017-10-18----------------------------------------
            Map<String, Object> params = new HashMap<>();
            params.put("accountType", command.getUserInfor().getType());
            params.put("accountId", command.getUserInfor().getUserId());
            List<BusinessEntity> list = dao.sqlFind("queryAllBusinessByAccountIdAndAccountType", params, BusinessEntity.class);
            //------------------------------------------------------------------------------------------------------------------

            List<BusinessOutdto> dtoList = BusinessConverter.listEntityToDto(list);
            result.setData(dtoList);
            result.fillResult(ReturnCode.OK);
        } catch (BaseConveterException e) {
            logger.error("BusinessServiceImpl ====> queryBusiness : ", e);
        }
        return result;
    }

    /**
     * 功能描述:获取客户列表
     */
    @Override
    public HttpCommandResultWithData queryBusinesses(QueryBusinessesCommand command,Boolean isAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        try {
            Map<String, Object> parm = new HashMap<>();
            if (StringUtil.isNotEmpty(command.getBusinessName())) {
                parm.put("businessName", command.getBusinessName());
            }
            if (StringUtil.isNotEmpty(command.getBusinessCode())) {
                parm.put("businessCode", command.getBusinessCode());
            }
            if (StringUtil.isNotEmpty(command.getAddressCode())) {
                parm.put("addressCode", command.getAddressCode());
            }
            if (StringUtil.isNotEmpty(command.getCtype()) && !command.getCtype().equals(QueryConstants.CONDITION_ALL)) {
                parm.put("type", Integer.valueOf(command.getCtype()));
            }

            //查询条件增加当前登录用户的类型和ID------created by zhangtiantong 2017-10-18---------
            parm.put("accountType", command.getUserInfor().getType());
            parm.put("accountId", command.getUserInfor().getUserId().intValue());
            //--------------------------------------------------------------------------------

            if(isAll){
                List<BusinessPojo> lists = dao.sqlFind("queryBusinessesPage", parm, BusinessPojo.class);
                List<BusinessOutdto> dtos = BusinessConverter.listPojoToDto(lists);
                result.setData(dtos);
            }else {
                PagingInfo commonsPageInfo = dao.sqlPagelFind("queryBusinessesPage", parm, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), BusinessPojo.class);
                List<BusinessPojo> lists = commonsPageInfo.getList();
                //POJO>>DTO
                List<BusinessOutdto> dtos = BusinessConverter.listPojoToDto(lists);
                commonsPageInfo.setList(dtos);
                result.setData(commonsPageInfo);
            }

        } catch (BaseConveterException e) {
            logger.error("BusinessServiceImpl ====> queryBusinesses : ", e);
        }

        return result;
    }

    /**
     * 功能描述:获取单个客户
     */
    @Override
    public HttpCommandResultWithData getBusiness(String cid) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        try {

            if (StringUtil.isNotEmpty(cid)) {
                BusinessPojo pojo = new BusinessPojo();
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", new BigInteger(cid));
                pojo = (BusinessPojo) dao.sqlFindObject("getBusiness", map, BusinessPojo.class);
                //POJO>>DTO
                BusinessOutdto dto = BusinessConverter.pojoToDto(pojo);
                result.setData(dto);
                result.fillResult(ReturnCode.OK);
            } else {
                result.fillResult(ReturnCode.SERVER_ERROR);
            }
        } catch (BaseConveterException e) {
            logger.error("BusinessServiceImpl ====> getBusiness : ", e);
        }
        return result;
    }

    /**
     * 功能描述:删除客户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult deleteBusiness(DeleteBusinessCommand command) throws Exception {
        CommonResult result = new CommonResult();
        if (StringUtil.isNotEmpty(command.getCid())) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", new BigInteger(command.getCid()));
            //关联车辆判断
            List<BusinessPojo> count_car = dao.sqlFind("checkBusinessCascadeCar", map, BusinessPojo.class);
            //关联用户判断
            List<BusinessPojo> count_account = dao.sqlFind("checkBusinessCascadeAccount", map, BusinessPojo.class);
            //关联非F9车辆判断
            List<BigInteger> count_noF9car = (List<BigInteger>) dao.sqlFindField("checkBusinessCascadeNoF9Car", map);
            if (!(ListUtil.isEmpty(count_car) && ListUtil.isEmpty(count_account))||(count_noF9car.get(0).longValue()>0)) {
                result = result.fillResult(ReturnCode.BUSINESS_ALREADY_BIND_CAR_OR_ACCOUNT);
                return result;
            } else {
                if (StringUtil.isNotEmpty(command.getCid())) {
                    businessRepository.delete(new BigInteger(command.getCid()));
                    result = result.fillResult(ReturnCode.OK);
                } else {
                    result.fillResult(ReturnCode.BUSINESS_DELETE_FAIL);
                }
            }
        } else {
            result.fillResult(ReturnCode.BUSINESS_DELETE_FAIL);
        }
        return result;
    }

    /**
     * 功能描述:添加客户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public HttpCommandResultWithData addBusiness(AddBusinessCommand command) throws Exception {
    	HttpCommandResultWithData result = new HttpCommandResultWithData();
        //验证证件号码不能为空
    	CommonResult nullResult = checkBusinessCodeIsNull(command.getBusinessCode(), command.getCtype());
        if (nullResult.getResultCode() != 200) {
        	result.setResultCode(nullResult.getResultCode());
            return result;
        }
        // 验证证件号重复
        CommonResult existResult = checkBusinessCodeIsExist(command.getBusinessCode(), command.getCtype());
        if (existResult.getResultCode() != 200) {
        	result.setResultCode(existResult.getResultCode());
        	result.setMessage(existResult.getMessage());
            return result;
        }
        if (command.getCtype().equals("1")) {
            if (StringUtil.isNotEmpty(command.getOrganizationCode())) {
                if (command.getOrganizationCode().length()>18) {
                    result.setResultCode(ReturnCode.ORGANIZATION_CODE_LENGTH_OUT.code());
                    result.setMessage(ReturnCode.ORGANIZATION_CODE_LENGTH_OUT.message());
                    return result;
                }
                //验证道路运输经营许可证号重复
                CommonResult resultO = checkCodeIsExist(command.getOrganizationCode(), "4");
                if (resultO.getResultCode() != 200) {
                	result.setResultCode(resultO.getResultCode());
                    result.setMessage(resultO.getMessage());
                    return result;
                }
            }
            //从业资格证号\机动车行驶证号\道路运输证号setNULL
            command.setCertificateCode("");
            command.setVehicleLicCode("");
            command.setRoadTransLicCode("");
        }
        if (command.getCtype().equals("2")) {
            //验证从业资格证号重复
            if (StringUtil.isNotEmpty(command.getCertificateCode())) {
                CommonResult resultT = checkCodeIsExist(command.getCertificateCode(), "1");
                if (resultT.getResultCode() != 200) {
                	result.setResultCode(resultT.getResultCode());
                    result.setMessage(resultT.getMessage());
                    return result;
                }
            }
            //验证机动车行驶证号重复
            if (StringUtil.isNotEmpty(command.getVehicleLicCode())) {
                CommonResult resultS = checkCodeIsExist(command.getVehicleLicCode(), "2");
                if (resultS.getResultCode() != 200) {
                	result.setResultCode(resultS.getResultCode());
                    result.setMessage(resultS.getMessage());
                    return result;
                }
            }
            //验证道路运输证号重复
            if (StringUtil.isNotEmpty(command.getRoadTransLicCode())) {
                if (command.getRoadTransLicCode().length()>18) {
                    result.setResultCode(ReturnCode.ROADTRANSLIC_CODE_LENGTH_OUT.code());
                    result.setMessage(ReturnCode.ROADTRANSLIC_CODE_LENGTH_OUT.message());
                    return result;
                }
                CommonResult resultF = checkCodeIsExist(command.getRoadTransLicCode(), "3");
                if (resultF.getResultCode() != 200) {
                	result.setResultCode(resultF.getResultCode());
                    result.setMessage(resultF.getMessage());
                    return result;
                }
            }
            //道路运输经营许可证号setNULL
            command.setOrganizationCode("");
        }
        BusinessEntity businessEntity = new BusinessEntity();
        CopyPropUtil.copyProp(command, businessEntity);
        businessEntity.setCType(Short.valueOf(command.getCtype()));

        //增加用户时，获取当前操作人员的Id和type-----create by zhangtiantong 2017-10-18---------
        businessEntity.setAccountId(command.getUserInfor().getUserId().longValue());
        businessEntity.setAccountType(command.getUserInfor().getType());
        //----------------------------------------------------------------------------------

        BusinessEntity resultEntity = (BusinessEntity)dao.save(businessEntity);
        result = result.fillResult(ReturnCode.OK);
        result.setData(resultEntity);
        return result;
    }

    /**
     * 功能描述:修改客户
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CommonResult updateBusiness(UpdateBusinessCommand command) throws Exception {
        CommonResult result = new CommonResult();
        if (null != command && StringUtil.isNotEmpty(command.getCid())) {
            // 获取原始客户信息
            BusinessEntity entity = businessRepository.findOne(new BigInteger(command.getCid()));
            if (null != entity) {
                //验证证件号码不能为空
                CommonResult nullResult = checkBusinessCodeIsNull(command.getBusinessCode(), command.getCtype());
                if (nullResult.getResultCode() != 200) {
                    return nullResult;
                }
                // 验证证件号重复
                if (!command.getBusinessCode().equals(entity.getBusinessCode())) {
                    CommonResult existResult = checkBusinessCodeIsExist(command.getBusinessCode(), command.getCtype());
                    if (existResult.getResultCode() != 200) {
                        return existResult;
                    }
                }
                //验证道路运输经营许可证号重复
                if (command.getCtype().equals("1")) {
                    if (StringUtil.isNotEmpty(command.getOrganizationCode())&&command.getOrganizationCode().length()>18) {
                        result.setResultCode(ReturnCode.ORGANIZATION_CODE_LENGTH_OUT.code());
                        result.setMessage(ReturnCode.ORGANIZATION_CODE_LENGTH_OUT.message());
                        return result;
                    }
                    if (StringUtil.isNotEmpty(command.getOrganizationCode())&&!command.getOrganizationCode().equals(entity.getOrganizationCode())) {
                        CommonResult resultO = checkCodeIsExist(command.getOrganizationCode(), "4");
                        if (resultO.getResultCode() != 200) {
                            return resultO;
                        }
                    }
                    //从业资格证号\机动车行驶证号\道路运输证号setNULL
                    command.setCertificateCode("");
                    command.setVehicleLicCode("");
                    command.setRoadTransLicCode("");
                }
                if (command.getCtype().equals("2")) {
                    //验证从业资格证号重复
                    if (StringUtil.isNotEmpty(command.getCertificateCode())&&!command.getCertificateCode().equals(entity.getCertificateCode())) {
                        CommonResult resultT = checkCodeIsExist(command.getCertificateCode(), "1");
                        if (resultT.getResultCode() != 200) {
                            return resultT;
                        }
                    }
                    //验证机动车行驶证号重复
                    if (StringUtil.isNotEmpty(command.getVehicleLicCode())&&!command.getVehicleLicCode().equals(entity.getVehicleLicCode())) {
                        CommonResult resultS = checkCodeIsExist(command.getVehicleLicCode(), "2");
                        if (resultS.getResultCode() != 200) {
                            return resultS;
                        }
                    }
                    //验证道路运输证号重复
                    if (StringUtil.isNotEmpty(command.getRoadTransLicCode())&&command.getRoadTransLicCode().length()>18) {
                        result.setResultCode(ReturnCode.ROADTRANSLIC_CODE_LENGTH_OUT.code());
                        result.setMessage(ReturnCode.ROADTRANSLIC_CODE_LENGTH_OUT.message());
                        return result;
                    }
                    if (StringUtil.isNotEmpty(command.getRoadTransLicCode())&&!command.getRoadTransLicCode().equals(entity.getRoadTransLicCode())) {
                        CommonResult resultF = checkCodeIsExist(command.getRoadTransLicCode(), "3");
                        if (resultF.getResultCode() != 200) {
                            return resultF;
                        }
                    }
                    //道路运输经营许可证号setNULL
                    command.setOrganizationCode("");
                }
                CopyPropUtil.copyProp(command, entity);
                entity.setCid(new BigInteger(command.getCid()));
                entity.setCType(Short.valueOf(command.getCtype()));
                dao.save(entity);
                result = result.fillResult(ReturnCode.OK);
            } else {
                result.fillResult(ReturnCode.BUSINESS_UPDATE_FAIL);
            }
        } else {
            result.fillResult(ReturnCode.BUSINESS_UPDATE_FAIL);
        }
        return result;
    }

    /**
     * 验证证件号码不能为空
     *
     * @param businessCode
     * @param type
     * @return
     */
    protected CommonResult checkBusinessCodeIsNull(String businessCode, String type) {
        CommonResult result = new CommonResult();
        //验证证件号码不能为空
        if (StringUtil.isEmpty(businessCode)) {
            if (type.equals("1")) {
                result.fillResult(ReturnCode.BUSINESS_CODE_Q_ISNOTNULL);
            }
            if (type.equals("2")) {
                result.fillResult(ReturnCode.BUSINESS_CODE_G_ISNOTNULL);
            }
            return result;
        } else {
            result.fillResult(ReturnCode.OK);
            return result;
        }
    }

    /**
     * 验证证件号码不能重复
     *
     * @param businessCode
     * @param type
     * @return
     */
    protected CommonResult checkBusinessCodeIsExist(String businessCode, String type) {
        CommonResult result = new CommonResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("businessCode", businessCode);
        // 验证证件号重复
        List<BusinessPojo> count_car = dao.sqlFind("checkBusinessCodeIsExist", map, BusinessPojo.class);
        if (!ListUtil.isEmpty(count_car)) {
            if (type.equals("1")) {
                result.fillResult(ReturnCode.BUSINESS_CODE_Q_ALREADY_BIND);
            }
            if (type.equals("2")) {
                result.fillResult(ReturnCode.BUSINESS_CODE_G_ALREADY_BIND);
            }
            return result;
        } else {
            result.fillResult(ReturnCode.OK);
            return result;
        }
    }

    /**
     * 验证证件不能重复
     *
     * @param code
     * @param type
     * @return
     */
    protected CommonResult checkCodeIsExist(String code, String type) {
        CommonResult result = new CommonResult();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", code);
        map.put("type", type);
        // 验证证件号重复
        List<BusinessPojo> count_car = dao.sqlFind("checkCodeIsExist", map, BusinessPojo.class);
        if (!ListUtil.isEmpty(count_car)) {
            if (type.equals("1")) {
                result.fillResult(ReturnCode.CERTIFICATE_CODE_ISNOTNULL);
            }
            if (type.equals("2")) {
                result.fillResult(ReturnCode.VECHICLELIC_CODE_ISNOTNULL);
            }
            if (type.equals("3")) {
                result.fillResult(ReturnCode.ROADTRANSLIC_CODE_ISNOTNULL);
            }
            if (type.equals("4")) {
                result.fillResult(ReturnCode.ORGANIZATION_CODE_ISNOTNULL);
            }
            return result;
        } else {
            result.fillResult(ReturnCode.OK);
            return result;
        }
    }
}
