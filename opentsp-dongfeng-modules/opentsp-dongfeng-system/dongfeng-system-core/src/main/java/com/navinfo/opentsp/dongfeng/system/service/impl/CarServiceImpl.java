package com.navinfo.opentsp.dongfeng.system.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.lc.core.protocol.common.LCLocationData.LocationData;
import com.lc.core.protocol.dataaccess.terminal.LCLastestLocationDataRes.LastestLocationDataRes;
import com.lc.core.protocol.webservice.originaldata.LCTerminalLocationData.TerminalLocationData;
import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.cache.TerminalSubscribeCache;
import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.configuration.CloudDataRmiClientConfiguration;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.dto.GpsInfoData;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.rmi.SynchronousTerminalDataWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.module.CommonParameterSerializer;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.system.commands.business.AddBusinessCommand;
import com.navinfo.opentsp.dongfeng.system.commands.car.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BasicDataIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BusinessIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.CarIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.*;
import com.navinfo.opentsp.dongfeng.system.converter.DownloadCarConverter;
import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import com.navinfo.opentsp.dongfeng.system.entity.CarDetailEntity;
import com.navinfo.opentsp.dongfeng.system.entity.CarEntity;
import com.navinfo.opentsp.dongfeng.system.repository.CarDetailReporsitory;
import com.navinfo.opentsp.dongfeng.system.repository.CarRepository;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
@Transactional(rollbackFor = {BaseServiceException.class})
@EnableAsync
public class CarServiceImpl implements ICarService
{
    private static Log logger = LogFactory.getLog(CarServiceImpl.class);
    
    @Autowired
    private CommonDao dao;
    
    @Autowired
    private MailClient mailClient;
    
    @Value("${report.module.path}")
    private String targetPath;
    
    @Resource
    private GpsCache gpsCache;
    
    @Resource
    private GpsInfoCache gpsInfoCache;
    
    @Resource
    private TerminalSubscribeCache terminalSubscribeCache;
    
    @Resource
    private CloudDataRmiClientConfiguration cloudDataRmiClientConfiguration;
    
    @Resource
    private IOperateLogService operateLogService;
    
    @Resource
    private CarRepository repository;
    
    @Resource
    private CarDetailReporsitory carDetailReporsitory;

	public static final int STATE = 0xFFFFFFFF;
    
	private static final String MSG = "检测正常";
	private static final int MSG_FLAG = 1;
	private static final String NULL_MSG = "未检测到数据";
	private static final int NULL_MSG_FLAG = 2;
	private static final String NO_MSG = "尚未获得有效位置数据";
	private static final int NO_MSG_FLAG = 3;

    @Override
    public PagingInfo<DealerOutdto> sqlPageDealer(QueryDealerCommand dealerCommand, String pageSize)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        
        // 此处暂时缺少判断用户是经销商类型用户还是系统用户的业务逻辑,后续需要补全.
        int userType = dealerCommand.getUserInfor().getType();
        
        if (userType != UserTypeConstant.SYSTEM_ADMIN.getCode())
        {
            parameter.put("account_id", dealerCommand.getUserInfor().getUserId());
        }
        int pageNumber =
            null == dealerCommand.getPage_number() || "".equals(dealerCommand.getPage_number().trim()) ? 1
                : Integer.valueOf(dealerCommand.getPage_number().trim());
        String fruzzy = null == dealerCommand.getFruzzy() ? "" : dealerCommand.getFruzzy().trim();
        
        if (null != dealerCommand.getGovCodeCity()) {
        	parameter.put("govCodeCity", dealerCommand.getGovCodeCity());
        }
        
        parameter.put("fruzzy", fruzzy);
        return dao.sqlPagelFind("queryDealerPageInCarManager",
            parameter,
            pageNumber,
            Integer.valueOf(pageSize),
            DealerOutdto.class);
    }

    @Override
    public PagingInfo<TerminalIndto> sqlPageTerminal(QueryTerminalCommand terminalCommand)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        
        parameter.put("dealerId", null == terminalCommand.getDealerIndto().getId() ? 1
            : terminalCommand.getDealerIndto().getId());
        
        parameter.put("tstyle", null == terminalCommand.getTerminalIndto()
            || null == terminalCommand.getTerminalIndto().getTstyle() ? 1 : terminalCommand.getTerminalIndto()
            .getTstyle());
        
        String fruzzy = null == terminalCommand.getFruzzy() ? "" : terminalCommand.getFruzzy().trim();
        
        parameter.put("fruzzy", fruzzy.trim());
        
        Integer tstyle = (Integer)parameter.get("tstyle");
        
        if (null == tstyle)
        {
            throw new NullPointerException("终端类型不允许为空 .");
        }
        
        if (1 == tstyle)
        {
            return dao.sqlPagelFind("queryTerminalPage",
                parameter,
                Integer.valueOf(terminalCommand.getPage_number()),
                Integer.valueOf(terminalCommand.getPage_size()),
                TerminalIndto.class);
        }
        else
        {
            return dao.sqlPagelFind("queryControllerPage",
                parameter,
                Integer.valueOf(terminalCommand.getPage_number()),
                Integer.valueOf(terminalCommand.getPage_size()),
                TerminalIndto.class);
        }
    }

    @Override
    public PagingInfo<BusinessIndto> sqlPageBusiness(QueryBusinessCommand businessCommand, UserTypeConstant userType)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        String fruzzy = null == businessCommand.getFruzzy() ? "" : businessCommand.getFruzzy().trim();
        parameter.put("fruzzy", fruzzy);
        return dao.sqlPagelFind("queryBusinessPage",
            parameter,
            Integer.valueOf(businessCommand.getPage_number()),
            Integer.valueOf(businessCommand.getPage_size()),
            BusinessIndto.class);
    }

    @Override
    public Map<String, List<BasicDataIndto>> queryBasicData(QueryBasicDataCommand basicCommand)
    {
        
        Map<String, List<BasicDataIndto>> resultMap = new HashMap<String, List<BasicDataIndto>>();
        Map<String, Object> parameter = new HashMap<String, Object>();
        
        if (StringUtil.isEmpty(basicCommand.getCarType()))
        {
            return resultMap;
        }
        parameter.put("dataType", basicCommand.getCarType());
        
        List<BasicDataIndto> carTypeList = dao.sqlFind("queryBasicData", parameter, BasicDataIndto.class);
        
        // 车辆类型
        List<BasicDataIndto> resultCarTypeList = new ArrayList<BasicDataIndto>();
//        List<BasicDataIndto> searchEngineTypeList = new ArrayList<BasicDataIndto>();
        
        // 发动机类型
        List<BasicDataIndto> resultEngineTypeList = new ArrayList<BasicDataIndto>();

        for (BasicDataIndto carTypeBasicData : carTypeList)
        {
            resultCarTypeList.add(carTypeBasicData);
        }

        // switch (basicCommand.getServerVersion())
        // {
        // case 1:
        // {
        // for (BasicDataIndto item : carTypeList)
        // {
        // if (Integer.parseInt(item.getDataCode()) <= 129)
        // {
        // resultCarTypeList.add(item);
        // }
        // }
        // break;
        // }
        // case 3:
        // {
        // for (BasicDataIndto carTypeBasicData : carTypeList)
        // {
        // // if (basicCommand.getInstalType() == 1) {
        // //
        // // if (carTypeBasicData.getDataValue().contains("天V")
        // // || carTypeBasicData.getDataValue().contains("悍V")
        // // || carTypeBasicData.getDataValue().contains("虎V")
        // // || carTypeBasicData.getDataValue().contains("龙V")
        // // || carTypeBasicData.getDataValue().contains("JH6")
        // // || carTypeBasicData.getDataValue().contains("J6F")
        // // || carTypeBasicData.getDataValue().contains("J6L")
        // // || carTypeBasicData.getDataValue().contains("J6M")
        // // || carTypeBasicData.getDataValue().contains("J6P"))
        // // {
        // // resultCarTypeList.add(carTypeBasicData);
        // // }
        // // } else {
        // // resultCarTypeList.add(carTypeBasicData);
        // // }
        // if(basicCommand.getCarTypeFlag() == 1) {
        // if (carTypeBasicData.getDataValue().contains("天V")
        // || carTypeBasicData.getDataValue().contains("悍V")
        // || carTypeBasicData.getDataValue().contains("虎V")
        // || carTypeBasicData.getDataValue().contains("龙V")
        // || carTypeBasicData.getDataValue().contains("JH6")
        // || carTypeBasicData.getDataValue().contains("J6F")
        // || carTypeBasicData.getDataValue().contains("J6L")
        // || carTypeBasicData.getDataValue().contains("J6M")
        // || carTypeBasicData.getDataValue().contains("J6P")) {
        // resultCarTypeList.add(carTypeBasicData);
        // }
        // } else {
        // resultCarTypeList.add(carTypeBasicData);
        // }
        // }
        // break;
        // }
        // case 2:
        // case 4:
        // {
        // for (BasicDataIndto item : carTypeList)
        // {
        // if (basicCommand.getCarTypeFlag() == 1) {
        //
        // // if (Integer.parseInt(item.getDataCode()) == 36 || Integer.parseInt(item.getDataCode()) == 90
        // // || Integer.parseInt(item.getDataCode()) > 129)
        // // {
        // resultCarTypeList.add(item);
        // // }
        // } else {
        // resultCarTypeList.add(item);
        // }
        // }
        // break;
        // }
        // default:
        // {
        // break;
        // }
        // }
        resultMap.put("carType", resultCarTypeList);
        
        if (StringUtil.isEmpty(basicCommand.getEngineType()))
        {
            return resultMap;
        }
        parameter.put("dataType", basicCommand.getEngineType());
        List<BasicDataIndto> engineTypeList = dao.sqlFind("queryBasicData", parameter, BasicDataIndto.class);
        
        if (null != engineTypeList && !engineTypeList.isEmpty())
        {
            for (BasicDataIndto item : engineTypeList)
            {
                resultEngineTypeList.add(item);
            }
            // switch (basicCommand.getServerVersion())
            // {
            // case 1:
            // case 3:
            // {
            // for (BasicDataIndto item : engineTypeList)
            // {
            // if (Integer.parseInt(item.getDataCode()) != 51 && Integer.parseInt(item.getDataCode()) != 52)
            // {
            // resultEngineTypeList.add(item);
            // }
            // }
            // break;
            // }
            // case 2:
            // case 4:
            // {
            // for (BasicDataIndto item : engineTypeList)
            // {
            // if (Integer.parseInt(item.getDataCode()) == 45)
            // {
            // resultEngineTypeList.add(item);
            // }
            // else if (Integer.parseInt(item.getDataCode()) != 43
            // && Integer.parseInt(item.getDataCode()) != 45)
            // {
            // resultEngineTypeList.add(item);
            // }
            // }
            // break;
            // }
            // }
        }
        resultMap.put("engineType", resultEngineTypeList);
        // 此处查询蓄电池
        if (!StringUtil.isEmpty(basicCommand.getBattery()))
        {
            parameter.put("dataType", basicCommand.getBattery());
            resultMap.put("battery", dao.sqlFind("queryBasicData", parameter, BasicDataIndto.class));
        }
        
        if (!StringUtil.isEmpty(basicCommand.getBatteryBatch()))
        {
            parameter.put("dataType", basicCommand.getBatteryBatch());
            resultMap.put("batteryBatch", dao.sqlFind("queryBasicData", parameter, BasicDataIndto.class));
        }
        
        if (!StringUtil.isEmpty(basicCommand.getAutoFlag()))
        {
            parameter.put("dataType", basicCommand.getAutoFlag());
            resultMap.put("autoFlag", dao.sqlFind("queryBasicData", parameter, BasicDataIndto.class));
        }
        
        return resultMap;
    }
    
    @Override
    public void updateCar(UpdateCarCommand carCommand)
        throws BaseServiceException
    {
        
    	int optionType = 0;
        try
        {
            Map<String, Object> parameter = new HashMap<String, Object>();
            SimpleDateFormat saleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            if (null == carCommand.getCarIndto() || StringUtil.isEmpty(carCommand.getCarIndto().getChassisNum()))
            {
                throw new BaseServiceException("底盘号不允许为空 .");
            }
            
            if (null == carCommand.getCarIndto() || StringUtils.isEmpty(carCommand.getCarIndto().getCarModelCode()))
            {
                throw new BaseServiceException("车型码不允许为空 .");
            }
            
            if (null == carCommand.getDealerIndto() || null == carCommand.getDealerIndto().getId())
            {
                throw new BaseServiceException("经销商不允许为空 .");
            }
            
            if (null == carCommand.getCarDetailBean() || null == carCommand.getCarDetailBean().getFuel()) {
            	throw new BaseServiceException("燃料类型不允许为空 .");
            }
            
            if (null == carCommand.getCarIndto() || null == carCommand.getCarIndto().getVfactory()) {
            	throw new BaseServiceException("载重类型不允许为空 .");
            }
            
            if (null == carCommand.getCarIndto() || null == carCommand.getCarIndto().getCarType())
            {
                throw new BaseServiceException("车辆类型不允许为空 .");
            }
            if (null == carCommand.getCarIndto() || StringUtil.isEmpty(carCommand.getCarIndto().getStructureNum()))
            {
                throw new BaseServiceException("结构号不允许为空 .");
            }
            
            if (null == carCommand.getCarDetailBean()
                || StringUtil.isEmpty(carCommand.getCarDetailBean().getEngineType()))
            {
                throw new BaseServiceException("发动机类型不允许为空 .");
            }
            
            if (null == carCommand.getCarIndto() || StringUtil.isEmpty(carCommand.getCarIndto().getOilCapacity()))
            {
                throw new BaseServiceException("油量不允许为空 .");
            }
            
            /*
             * AAK销售状态如果为未销售(MB_SALES_STATUS标志位为1)则AAK销售日期、车牌号、车牌颜色、所属客户、客户证件号、发票号、购车总金额（万）、贷款总金额（万）、剩余未还款（万）为空,AAK销售状态为已销售
             * (MB_SALES_STATUS标志位为0)则 AAK销售日期、车牌号、车牌颜色、所属客户、客户证件号、发票号字段必填
             */
            if (null == carCommand.getSaleIndto())
            {
                throw new BaseServiceException("销售信息不允许为空 .");
            }
            
            /*
             * STD销售状态为未销售(状态位为1)则STD销售日期可以为空,STD销售状态为已销售(状态位为0)则STD销售日期则为必填项.
             */
            Integer stdStatus =
                null == carCommand.getSaleIndto() || null == carCommand.getSaleIndto().getSaleStatus() ? 1
                    : carCommand.getSaleIndto().getSaleStatus();
            
            switch (stdStatus)
            {
                case 0:
                {
                    
                    if (null == carCommand.getSaleIndto()
                        || StringUtils.isEmpty(carCommand.getSaleIndto().getSaleDate()))
                    {
                        throw new BaseServiceException("销售日期不允许为空 .");
                    }
                    break;
                }
                case 1:
                {
                    break;
                }
                default:
                {
                    throw new BaseServiceException("STD销售状态不正确 .");
                }
            }
            
            /*
             * STD为已销售状态时才进行AAK销售状态以及必填项的校验
             */
            Integer mbSalesStatus = null;
            if (0 == stdStatus)
            {
                
                mbSalesStatus =
                    null == carCommand.getSaleIndto() || null == carCommand.getSaleIndto().getMbSalesStatus() ? 1
                        : carCommand.getSaleIndto().getMbSalesStatus();
                
                switch (mbSalesStatus)
                {
                    case 1:
                    {// AAK未售状态
                        break;
                    }
                    case 0:
                    { // AAK已售状态
                    
                        if (StringUtils.isEmpty(carCommand.getSaleIndto().getMbSalesDate()))
                        {
                            throw new BaseServiceException("AAK销售日期不允许为空 .");
                        }
                        
//                        if (null == carCommand.getCarIndto() || null == carCommand.getCarIndto().getColor())
//                        {
//                            throw new BaseServiceException("车牌颜色不允许为空 .");
//                        }
                        
                        if (null == carCommand.getBusinessIndto() || null == carCommand.getBusinessIndto().getId())
                        {
                            throw new BaseServiceException("所属客户不允许为空 .");
                        }
                        
                        if (null == carCommand.getSaleIndto()
                            || StringUtils.isEmpty(carCommand.getSaleIndto().getInvoiceNumber()))
                        {
                            throw new BaseServiceException("发票号不允许为空 .");
                        }
                        
                        break;
                    }
                    default:
                    {
                        throw new BaseServiceException("销售状态不正确 .");
                    }
                }
            }
            
            CarEntity entity = new CarEntity();
            CarDetailEntity detail = new CarDetailEntity();
            
            //设置更新时间
            Timestamp syncTime = new Timestamp(Calendar.getInstance().getTime().getTime());
            entity.setSync_time(syncTime);
            
            // 设置底盘号
            entity.setChassisNum(carCommand.getCarIndto().getChassisNum());
            
            // 设置二维码
            entity.setQrCode(carCommand.getCarIndto().getQrCode());

            // 设置车型码
            entity.setCarModelCode(carCommand.getCarIndto().getCarModelCode());
            
            // 设置经销商
            entity.setDealerId(carCommand.getDealerIndto().getId().longValue());
            
            // 设置北斗一体机
            if (null != carCommand.getTerminalIndto() && null != carCommand.getTerminalIndto().getId())
            {
                entity.setTerminalId(carCommand.getTerminalIndto().getId().longValue());
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("terminalId", carCommand.getTerminalIndto().getId().longValue());
                map.put("dealerId", carCommand.getDealerIndto().getId());
                dao.executeUpdate("updateTerminal", map);
            }
            
            // 设置FK控制器
            if (null != carCommand.getControllerIndto() && null != carCommand.getControllerIndto().getId())
            {
                entity.setControllerId(carCommand.getControllerIndto().getId().longValue());
                
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("terminalId", carCommand.getControllerIndto().getId());
                map.put("dealerId", carCommand.getDealerIndto().getId());
                dao.executeUpdate("updateTerminal", map);
            }
            
            // 设置车辆类型
            entity.setCarType(carCommand.getCarIndto().getCarType());
            
            // 设置结构号
            entity.setStructureNum(carCommand.getCarIndto().getStructureNum());
            //设置载重类型
//            entity.setZzType(carCommand.getCarIndto().getZzType());
            
            if (null != carCommand.getCarIndto() && !StringUtils.isEmpty(carCommand.getCarIndto().getVfactory())) {
            	
            	//设置正确载重类型
            	entity.setVfactory(carCommand.getCarIndto().getVfactory());
            }
            
            // 修改发动机类型
            detail.setEngineType(carCommand.getCarDetailBean().getEngineType());
            
            // 设置发动机型号
            if (null != carCommand.getCarDetailBean() && !StringUtils.isEmpty(carCommand.getCarDetailBean().getEngineTypeRear())) {
            	detail.setEngineTypeRear(carCommand.getCarDetailBean().getEngineTypeRear());
            }
            
            // 设置发动机号
            detail.setEngineNumber(carCommand.getCarDetailBean().getEngineNumber());
            
            //设置燃料类型
            detail.setFuel(carCommand.getCarDetailBean().getFuel());
            
            // 油箱容量
            entity.setOilCapacity(carCommand.getCarIndto().getOilCapacity());
            
            // 设置下线时间
            if (!StringUtils.isEmpty(carCommand.getCarIndto().getOnline()))
            {
                SimpleDateFormat offlineTimeForamt = new SimpleDateFormat("yyyy-MM-dd");
                Date offlineTime = offlineTimeForamt.parse(carCommand.getCarIndto().getOnline());
                entity.setOnlineTime(offlineTime.getTime() / 1000);
            }
            
            // 出库时间
            if (null != carCommand.getCarIndto() && !StringUtil.isEmpty(carCommand.getCarIndto().getRemoveTime()))
            {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                long time = format.parse(carCommand.getCarIndto().getRemoveTime()).getTime();
                entity.setRemovalTime(time / 1000);
            }
            
            // 设置STD销售状态
            detail.setSaleStatus(stdStatus);
            
            // 设置STD销售日期
            if (null != stdStatus && 0 == stdStatus)
            {
                if (null == carCommand.getSaleIndto() || StringUtil.isEmpty(carCommand.getSaleIndto().getSaleDate()))
                {
                    throw new BaseServiceException("销售日期不允许为空.");
                }
                long time = saleDateFormat.parse(carCommand.getSaleIndto().getSaleDate()).getTime();
                detail.setSaleDate(time / 1000);
            }
            
            // 设置AAK销售日期
            detail.setMbSalesStatus(null == mbSalesStatus ? 1 : mbSalesStatus);
            
            if (null != mbSalesStatus)
            {
                
                switch (mbSalesStatus)
                {
                    case 0:
                    {
                        
                        if (!StringUtils.isEmpty(carCommand.getSaleIndto().getMbSalesDate()))
                        {
                            SimpleDateFormat mbSalesDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            Date mbSalesDate = mbSalesDateFormat.parse(carCommand.getSaleIndto().getMbSalesDate());
                            detail.setMbSalesDate(mbSalesDate.getTime()/1000 );
                        }
                        
                        // 车牌号
                        entity.setCarCph(carCommand.getCarIndto().getCarCph());
                        
                        // 设置车牌颜色
                        entity.setCarColor(carCommand.getCarIndto().getColor());
                        
                        // 设置所属客户
                        detail.setCarOwner(Long.toString(carCommand.getBusinessIndto().getId().longValue()));
                        
                        // 发票号
                        detail.setInvoiceNumber(carCommand.getSaleIndto().getInvoiceNumber());
                        
                        break;
                    }
                    case 1:
                    {
                        break;
                    }
                    default:
                    {
                        throw new BaseServiceException("销售状态不正确");
                    }
                }
            }
            
            // 车辆总额
            if (null != carCommand.getSaleIndto() && null != carCommand.getSaleIndto().getCarAmount())
            {
                detail.setCarAmount(carCommand.getSaleIndto().getCarAmount());
            }
            
            // 贷款总数
            if (null != carCommand.getSaleIndto() && null != carCommand.getSaleIndto().getLoanAmount())
            {
                detail.setLoanAmount(carCommand.getSaleIndto().getLoanAmount());
            }
            
            // 剩余未还款数
            if (null != carCommand.getSaleIndto() && null != carCommand.getSaleIndto().getSurplus())
            {
                detail.setSurplus(carCommand.getSaleIndto().getSurplus());
            }
            
            // 图片路径
            detail.setImgPath(carCommand.getCarDetailBean().getImg_path());
            
            // 1.根据底盘号查询车辆是否存在.如果存在执行更新操作,如果不存在执行新增操作.
            parameter.put("chassis", carCommand.getCarIndto().getChassisNum());
            BigInteger carId = carCommand.getCarIndto().getId();
            
            //二维码唯一校验
            if (null != carCommand.getCarIndto() && !StringUtils.isEmpty(carCommand.getCarIndto().getQrCode())) {
            	hasDuplicateQr(carCommand.getCarIndto().getQrCode(), null == carId ? null : carId.longValue());
            }
            
//            //车型码唯一性校验
//            if (null != carCommand.getCarIndto() && !StringUtils.isEmpty(carCommand.getCarIndto().getCarModelCode())) {
//            	hasDuplicateCarModel(carCommand.getCarIndto().getCarModelCode() , null == carId ? null : carId.longValue());
//            }

            boolean isSave = true;
            // 判断底盘号是否存在
            if (null == carId){
                List<CarIndto> carList = dao.sqlFind("queryCarByChassis", parameter, CarIndto.class);
                if (null != carList && !carList.isEmpty())
                {
                    if (carList.get(0).getDelFlag() == 1) {
                        entity.setDelFlag(0);
                        carId = carList.get(0).getId();
                        isSave = false;
                        parameter.put("id", carId);
                    } else {
                        throw new BaseServiceException("底盘号已存在 .");
                    }
                }
            }else{
                isSave = false;
                parameter.put("id", carId);
                List<CarIndto> carList = dao.sqlFind("queryCarOtherChassis", parameter, CarIndto.class);
                if (null != carList && !carList.isEmpty())
                {
                    if (carList.get(0).getDelFlag() == 1) {
                        entity.setDelFlag(0);
                    } else {
                        throw new BaseServiceException("底盘号已存在 .");
                    }
                }
            }


            
            // 判断是否增加操作.
            if (isSave)
            {
            	optionType = 0;
                // 录入方式:平台录入
                entity.setAutoFlag(9101);
                
//                entity.setAssembleTime(Calendar.getInstance().getTime().getTime());
                CarEntity car = (CarEntity)dao.save(entity);
                detail.setId(car.getCarId());
                dao.save(detail);
                CommonResult result = new CommonResult();
    			result.setResultCode(ReturnCode.OK.code());
    			result.setMessage("车辆保存成功.");
                operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + carCommand.getCarIndto().getChassisNum(), result);
            }
            else
            {
            	optionType = 1;
                List<VechicleOutdto> originalCar = dao.sqlFind("queryCarById", parameter, VechicleOutdto.class);
                
                if (null != originalCar && !originalCar.isEmpty())
                {
                    String firstGpsTimeStr =
                        StringUtils.isEmpty(originalCar.get(0).getFirstGpsTimeStr()) ? null : originalCar.get(0)
                            .getFirstGpsTimeStr();
                    
                    SimpleDateFormat gpsDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                    
                    if (!StringUtils.isEmpty(firstGpsTimeStr))
                    {
                        Long firstTime = gpsDateFormat.parse(firstGpsTimeStr).getTime() / 1000;
                        entity.setNettingTime(firstTime);
                    }
                    
                    String firstGpsLng =
                        StringUtils.isEmpty(originalCar.get(0).getFirstGpsLng()) ? null : originalCar.get(0)
                            .getFirstGpsLng();
                    
                    if (!StringUtils.isEmpty(firstGpsLng))
                    {
                        Double lng = Double.parseDouble(firstGpsLng) * Math.pow(10, 6);
                        entity.setNettingLog(lng.longValue());
                    }
                    
                    String firstGpsLat =
                        StringUtils.isEmpty(originalCar.get(0).getFirstGpsLat()) ? null : originalCar.get(0)
                            .getFirstGpsLat();
                    
                    if (!StringUtils.isEmpty(firstGpsLat))
                    {
                        Double lat = Double.parseDouble(firstGpsLat) * Math.pow(10, 6);
                        entity.setNettingLat(lat.longValue());
                    }
                    
                    String orderNumber =
                            StringUtils.isEmpty(originalCar.get(0).getOrderNumber()) ? null : originalCar.get(0)
                                .getOrderNumber();
                    if (!StringUtils.isEmpty(orderNumber))
                    {
                        entity.setOrderNumber(orderNumber);
                    }
                    
                }
                
                Integer autoFlag = originalCar.get(0).getAutoFlag();
                
                //设置录入方式
                if (null != autoFlag) {
                	entity.setAutoFlag(autoFlag);
                }
                
                entity.setCarId(carId.longValue());
                dao.update(entity);
                detail.setId(carId.longValue());
                dao.update(detail);

                CommonResult result = new CommonResult();
    			result.setResultCode(ReturnCode.OK.code());
    			result.setMessage("车辆修改成功.");
                operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + carCommand.getCarIndto().getChassisNum(), result);
            }
            //start ---删除车辆hy_car_mesdata中vin码相同的数据  add fwm 2017.12.05
            Map<String,Object> updateParam = new HashMap<String,Object>();
            updateParam.put("vin", carCommand.getCarIndto().getStructureNum());
            dao.executeUpdate("updateNonF9VehicleDelStatus", updateParam);
            //end ---删除车辆hy_car_mesdata中vin码相同的数据
            // 绑定的终端信息加入缓存，进行增量订阅
            BigInteger tid =
                null == carCommand.getTerminalIndto() || null == carCommand.getTerminalIndto().getId() ? null == carCommand.getControllerIndto()
                    || null == carCommand.getControllerIndto().getId() ? null : carCommand.getControllerIndto()
                    .getId() : carCommand.getTerminalIndto().getId();
            
            if (null != tid)
            {
                BigInteger communicationId = queryTerminalCommunicationId(tid);
                if (null != communicationId)
                {
                    terminalSubscribeCache.addSubscribeTerminal(communicationId.longValue());
                }
            }
        }
        catch (Exception e)
        {
            logger.error("CarServiceImpl ====> updateCar : ", e);
            
            if (optionType == 0) {

            	CommonResult result = new CommonResult();
            	result.setResultCode(ReturnCode.SERVER_ERROR.code());
            	result.setMessage("车辆保存失败.");
            	operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + carCommand.getCarIndto().getChassisNum(), result);
            } else {
            	CommonResult result = new CommonResult();
            	result.setResultCode(ReturnCode.SERVER_ERROR.code());
            	result.setMessage("车辆修改失败.");
            	operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + carCommand.getCarIndto().getChassisNum(), result);
            }
            
            throw new BaseServiceException(e.getMessage());
        }
    }
    
    @Override
    public void deleteCar(DeleteCarCommand carCommand, UserTypeConstant userType)
        throws BaseServiceException
    {
        
        // /QueryCarCommand command , UserTypeConstant userType
        try
        {
            if (null == carCommand.getCarId())
            {
                throw new Exception("carId can not be null .");
            }
            
            Map<String, Object> parameter = new HashMap<String, Object>();
            switch (userType)
            {
                case SYSTEM_ADMIN:
                {
                    // 系统管理员可以看到所有车辆
                    break;
                }
                case CAR_FACTORY:
                case SERVICE_STATION:
                {
                    parameter.put("account_id", carCommand.getUserInfor().getUserId());
                    break;
                }
                default:
                {
                }
            }
            
            parameter.put("car_id", carCommand.getCarId());
            List<VechicleOutdto> list = dao.sqlFind("carPage", parameter, VechicleOutdto.class);
            
            if (null == list || list.isEmpty())
            {
                return;
            }
            
            VechicleOutdto vechicle = list.get(0);
            
            if (!StringUtils.isEmpty(vechicle.getTerminalCode()))
            {
                parameter.put("terminalId", vechicle.getTerminalCode());
                dao.executeUpdate("logicalDeleteTerminal", parameter);
            }
            
            if (!StringUtils.isEmpty(vechicle.getFkCode()))
            {
                parameter.put("terminalId", vechicle.getFkCode());
                dao.executeUpdate("logicalDeleteTerminal", parameter);
            }
            
            CarEntity car = repository.findByCarId(carCommand.getCarId());
            CarDetailEntity detail = carDetailReporsitory.findById(carCommand.getCarId());
            
            Timestamp time = new Timestamp(Calendar.getInstance().getTime().getTime());
            car.setDelFlag(1);
            car.setSync_time(time);
            car.setCarId(carCommand.getCarId());
            car.setTerminalId(null);
            car.setControllerId(null);
            
            car.setStationId(null);
            
            detail.setCarOwner(null);
            dao.update(car);
            dao.update(detail);
            
            //获取终端id
            Long tid = StringUtils.isEmpty(vechicle.getFkCode()) ? StringUtils.isEmpty(vechicle.getTerminalCode()) ? null : Long.parseLong(vechicle.getTerminalCode()) : Long.parseLong(vechicle.getFkCode());
            
            List<TerminalOutdto> terminals = null;
			if (null != tid) {
				parameter.put("orgTerminalId", tid);
				terminals = dao.sqlFind("queryTerminalByTid", parameter, TerminalOutdto.class);
			}
			
			if (null != terminals && !terminals.isEmpty()) {
				BigInteger communicationId = null != terminals.get(0).getAutoCommunicationId() ? terminals.get(0).getAutoCommunicationId() : null != terminals.get(0).getCommunicationId() ? terminals.get(0).getCommunicationId() : null;
				
				SynchronousTerminalDataWebService stdw = cloudDataRmiClientConfiguration.getSynchronousTerminalDataWebService();
				
				if (null != stdw && null != communicationId) {
					stdw.terminalDelete(communicationId.longValue());
				}
			}
            
            CommonResult result = new CommonResult();
			result.setResultCode(ReturnCode.OK.code());
			result.setMessage("车辆删除成功.");
            operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + vechicle.getChassisNum(), result);
        }
        catch (Exception e)
        {
            CommonResult result = new CommonResult();
			result.setResultCode(ReturnCode.SERVER_ERROR.code());
			result.setMessage("车辆删除失败.");
            operateLogService.addOperateLog(carCommand, OperateLogConstants.OperateTypeEnum.DELETE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + carCommand.getCarId(), result);
        	throw new BaseServiceException(e);
        }
    }
    
    @Override
    public PagingInfo<VechicleOutdto> queryCar(QueryCarCommand command, UserTypeConstant userType)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        PagingInfo<VechicleOutdto> page = null;
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        try
        {
            
            switch (userType)
            {
                case SYSTEM_ADMIN:
                {
                    // 系统管理员可以看到所有车辆
                    break;
                }
                case CAR_FACTORY:
                case BUSINESS:
                case SERVICE_STATION:
                {
                    parameter.put("account_id", command.getUserInfor().getUserId());
                    break;
                }
                default:
                {
                    return new PagingInfo<VechicleOutdto>();
                }
            }
            if (null != command.getCarBean())
            {
                
                if (null != command.getCarBean().getId())
                {
                    parameter.put("car_id", command.getCarBean().getId());
                }
                parameter.put("chassis", StringUtil.isEmpty(command.getCarBean().getChassisNum()) ? ""
                    : command.getCarBean().getChassisNum());
                parameter.put("cph", StringUtil.isEmpty(command.getCarBean().getCarCph()) ? "" : command.getCarBean()
                    .getCarCph());
                if (!StringUtil.isEmpty(command.getCarBean().getCarType())) {
                	parameter.put("carType", command.getCarBean().getCarType());
                }
                if (!StringUtil.isEmpty(command.getCarBean().getRegisterDate()))
                {
                    String[] register = command.getCarBean().getRegisterDate().split(" - ");
                    
                    Date beginRegisterTime = format.parse(register[0]);
                    Date endRegisterTime = format.parse(register[1]);
                    parameter.put("beginRegDate", beginRegisterTime.getTime() / 1000);
                    parameter.put("endRegDate", endRegisterTime.getTime() / 1000 + 3600*24 - 1);
                }
                
                if (!StringUtils.isEmpty(command.getCarBean().getStructureNum())) {
                	parameter.put("structureNum", command.getCarBean().getStructureNum());
                }
                
                if (!StringUtil.isEmpty(command.getCarBean().getNettingDate()))
                {
                    
                    String[] nettingDates = command.getCarBean().getNettingDate().split(" - ");
                    Date beginNetDate = format.parse(nettingDates[0]);
                    Date endNetDate = format.parse(nettingDates[1]);
                    parameter.put("beginNetDate", beginNetDate.getTime() / 1000);
                    parameter.put("endNetDate", endNetDate.getTime() / 1000 + 3600*24 - 1);
                }
            }
            
            if (null != command.getCarDetailBean())
            {
                
                parameter.put("engineNumber", StringUtil.isEmpty(command.getCarDetailBean().getEngineNumber()) ? ""
                    : command.getCarDetailBean().getEngineNumber());
                if (!StringUtil.isEmpty(command.getCarDetailBean().getEngineType())) {
                    parameter.put("engineType", command.getCarDetailBean().getEngineType());	
                }




                // 动力类型
                if (!StringUtil.isEmpty(command.getCarDetailBean().getFuel()) && !command.getCarDetailBean().getFuel().equals("-1"))
                {
                    parameter.put("fuel", command.getCarDetailBean().getFuel());
                }
            }
            
            if (null != command.getDealerBean())
            {
                parameter.put("companyName", StringUtil.isEmpty(command.getDealerBean().getTname()) ? ""
                    : command.getDealerBean().getTname());
            }
            
            if (null != command.getBusinessBean())
            {
                parameter.put("businessName", StringUtil.isEmpty(command.getBusinessBean().getBusinessName()) ? ""
                    : command.getBusinessBean().getBusinessName());
            }
            
            if (null != command.getTerminalBean())
            {
                parameter.put("tcode", StringUtil.isEmpty(command.getTerminalBean().gettCode()) ? ""
                    : command.getTerminalBean().gettCode());
            }
            
            if (null != command.getControllerBean())
            {
                parameter.put("fkCode", StringUtil.isEmpty(command.getControllerBean().gettCode()) ? ""
                    : command.getControllerBean().gettCode());
            }
            
            // 安装单位模糊查询
            if (null != command.getCarBean())
            {
                parameter.put("stationName", StringUtils.isEmpty(command.getCarBean().getStationName()) ? ""
                    : command.getCarBean().getStationName().trim());
            }
            
            // 前装/后装
            if (null != command.getCarBean() && null != command.getCarBean().getInstalType())
            {
                parameter.put("instalType", command.getCarBean().getInstalType());
            }
            
            if (null != command.getCarBean() && !StringUtils.isEmpty(command.getCarBean().getOnline()))
            {
                String[] offlineTime = command.getCarBean().getOnline().split(" - ");
                String begin = offlineTime[0];
                String end = offlineTime[1];

                Date beginOffline = format.parse(begin);
                Date endOffline = format.parse(end);
                parameter.put("beginOffline", beginOffline.getTime() / 1000);
                parameter.put("endOffline", endOffline.getTime() / 1000 + 3600 * 24 -1);
            }
            
            if (null != command.getCarBean() && !StringUtils.isEmpty(command.getCarBean().getCarDate()))
            {
                String[] assembleTime = command.getCarBean().getCarDate().split(" - ");
                
                String begin = assembleTime[0];
                String end = assembleTime[1];
                Date beginOffline = format.parse(begin);
                Date endOffline = format.parse(end);
                parameter.put("beginAssembleTime", beginOffline.getTime() / 1000);
                parameter.put("endAssembleTime", endOffline.getTime() / 1000 + 3600 * 1000 *24 -1);
            }
            
            if (null != command.getCarBean() && null != command.getCarBean().getStdStatus())
            {
                
                Integer stdStatus = command.getCarBean().getStdStatus();
                switch (stdStatus)
                {
                    case 0:
                    {
                        if (null != command.getCarBean())
                        {
                            
                            if (null == command.getCarBean().getAakStatus())
                            {
                                parameter.put("stdStatus", stdStatus);
                            }
                            else
                            {
                                if(null != command.getCarBean().getStdStatus()){
                                    parameter.put("stdStatus", command.getCarBean().getStdStatus());
                                }
                                parameter.put("aakStatus", command.getCarBean().getAakStatus());
                            }
                        }
                        break;
                    }
                    case 1:
                    {
                        parameter.put("stdStatus", command.getCarBean().getStdStatus());
                        break;
                    }
                }
            } else {
            	
            	if (null != command.getCarBean() && null != command.getCarBean().getAakStatus()) {
            		parameter.put("aakStatus", command.getCarBean().getAakStatus());
            	}
            	
            }

            // 工厂代码
            if (null != command.getCarBean() && !StringUtil.isEmpty(command.getCarBean().getVfactory()) && !command.getCarBean().getVfactory().equals("-1"))
            {
                parameter.put("vfactory", command.getCarBean().getVfactory());
            }

            page = dao.sqlPageFind("carPage", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), "queryCarCount", VechicleOutdto.class);
        }
        catch (Exception e)
        {
            logger.error("CarServiceImpl`s queryCar has error : ", e);
            return new PagingInfo<VechicleOutdto>();
        }
        return page;
    }
    
    @Override
    public List<VechicleOutdto> queryCarList(QueryCarCommand command, UserTypeConstant userType)
    {
        

        Map<String, Object> parameter = new HashMap<String, Object>();
        
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        
        
        try
        {

            
            switch (userType)
            {
                case SYSTEM_ADMIN:
                {
                    // 系统管理员可以看到所有车辆
                    break;
                }
                case CAR_FACTORY:
                case BUSINESS:
                case SERVICE_STATION:
                {
                    parameter.put("account_id", command.getUserInfor().getUserId());
                    break;
                }
                default:
                {
                    return new ArrayList<VechicleOutdto>();
                }
            }
            if (null != command.getCarBean())
            {
                
                if (null != command.getCarBean().getId())
                {
                    parameter.put("car_id", command.getCarBean().getId());
                }
                parameter.put("chassis", StringUtil.isEmpty(command.getCarBean().getChassisNum()) ? ""
                    : command.getCarBean().getChassisNum());
                parameter.put("cph", StringUtil.isEmpty(command.getCarBean().getCarCph()) ? "" : command.getCarBean()
                    .getCarCph());
                parameter.put("carType", command.getCarBean().getCarType());
                
                if (!StringUtil.isEmpty(command.getCarBean().getRegisterDate()))
                {
                    String[] register = command.getCarBean().getRegisterDate().split(" - ");
                    
                    Date beginRegisterTime = format.parse(register[0]);
                    Date endRegisterTime = format.parse(register[1]);
                    parameter.put("beginRegDate", beginRegisterTime.getTime() / 1000);
                    parameter.put("endRegDate", endRegisterTime.getTime() / 1000 + 3600*24 - 1);
                }
                
                if (!StringUtil.isEmpty(command.getCarBean().getNettingDate()))
                {
                    
                    String[] nettingDates = command.getCarBean().getNettingDate().split(" - ");
                    Date beginNetDate = format.parse(nettingDates[0]);
                    Date endNetDate = format.parse(nettingDates[1]);
                    parameter.put("beginNetDate", beginNetDate.getTime() / 1000);
                    parameter.put("endNetDate", endNetDate.getTime() / 1000 + 3600*24 - 1);
                }
            }
            
            if (null != command.getCarDetailBean())
            {
                
                parameter.put("engineNumber", StringUtil.isEmpty(command.getCarDetailBean().getEngineNumber()) ? ""
                    : command.getCarDetailBean().getEngineNumber());
                if (!StringUtil.isEmpty(command.getCarDetailBean().getEngineType())) {
                    parameter.put("engineType", command.getCarDetailBean().getEngineType());	
                }
            }
            
            if (null != command.getDealerBean())
            {
                parameter.put("companyName", StringUtil.isEmpty(command.getDealerBean().getTname()) ? ""
                    : command.getDealerBean().getTname());
            }
            
            if (null != command.getBusinessBean())
            {
                parameter.put("businessName", StringUtil.isEmpty(command.getBusinessBean().getBusinessName()) ? ""
                    : command.getBusinessBean().getBusinessName());
            }
            
            if (null != command.getTerminalBean())
            {
                parameter.put("tcode", StringUtil.isEmpty(command.getTerminalBean().gettCode()) ? ""
                    : command.getTerminalBean().gettCode());
            }
            
            if (null != command.getControllerBean())
            {
                parameter.put("fkCode", StringUtil.isEmpty(command.getControllerBean().gettCode()) ? ""
                    : command.getControllerBean().gettCode());
            }
            
            // 安装单位模糊查询
            if (null != command.getCarBean())
            {
                parameter.put("stationName", StringUtils.isEmpty(command.getCarBean().getStationName()) ? ""
                    : command.getCarBean().getStationName().trim());
            }
            
            // 前装/后装
            if (null != command.getCarBean() && null != command.getCarBean().getInstalType())
            {
                parameter.put("instalType", command.getCarBean().getInstalType());
            }
            
            if (null != command.getCarBean() && !StringUtils.isEmpty(command.getCarBean().getOnline()))
            {
                String[] offlineTime = command.getCarBean().getOnline().split(" - ");
                String begin = offlineTime[0];
                String end = offlineTime[1];

                Date beginOffline = format.parse(begin);
                Date endOffline = format.parse(end);
                parameter.put("beginOffline", beginOffline.getTime() / 1000);
                parameter.put("endOffline", endOffline.getTime() / 1000 + 3600 * 24 -1);
            }
            
            if (null != command.getCarBean() && !StringUtils.isEmpty(command.getCarBean().getCarDate()))
            {
                String[] assembleTime = command.getCarBean().getCarDate().split(" - ");
                
                String begin = assembleTime[0];
                String end = assembleTime[1];
                Date beginOffline = format.parse(begin);
                Date endOffline = format.parse(end);
                parameter.put("beginAssembleTime", beginOffline.getTime() / 1000);
                parameter.put("endAssembleTime", endOffline.getTime() / 1000 + 3600 * 1000 *24 -1);
            }
            
            if (null != command.getCarBean() && null != command.getCarBean().getStdStatus())
            {
                
                Integer stdStatus = command.getCarBean().getStdStatus();
                switch (stdStatus)
                {
                    case 0:
                    {
                        if (null != command.getCarBean())
                        {
                            
                            if (null == command.getCarBean().getAakStatus())
                            {
                                parameter.put("stdStatus", stdStatus);
                            }
                            else
                            {
                                parameter.put("aakStatus", command.getCarBean().getAakStatus());
                            }
                        }
                        
                        break;
                    }
                    case 1:
                    {
                        parameter.put("stdStatus", command.getCarBean().getStdStatus());
                        break;
                    }
                }
            } else {
            	
            	if (null != command.getCarBean() && null != command.getCarBean().getAakStatus()) {
            		parameter.put("aakStatus", command.getCarBean().getAakStatus());
            	}
            	
            }
        }
        catch (Exception e)
        {
            logger.error("CarServiceImpl ====> queryCarList has error : ", e);
            return new ArrayList<VechicleOutdto>();
        }
        return dao.sqlFind("carPage", parameter, VechicleOutdto.class);
    }
    
    @Async
    @Override
    public void asyncDonwload(String fservice, DownloadCarCommand command, String sourcePath)
    {
        
        List<VechicleOutdto> list = null;
        try
        {
            
            QueryCarCommand queryVechicleCommand =
                DownloadCarConverter.downloadVechicleCommand2QueryVechicleCommand(command);
            if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType())
            {
                list = queryCarList(queryVechicleCommand, UserTypeConstant.SYSTEM_ADMIN);
            }
            
            if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType())
            {
                list = queryCarList(queryVechicleCommand, UserTypeConstant.CAR_FACTORY);
            }
            
            if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType())
            {
                list = queryCarList(queryVechicleCommand, UserTypeConstant.BUSINESS);
            }

            if (null != list && !list.isEmpty()) {
            	for (VechicleOutdto v : list) {
            		String engineTypeValue = StringUtils.isEmpty(v.getEngine_TYPE_VALUE()) ? "" : v.getEngine_TYPE_VALUE();
            		String engineTypeRearValue = StringUtils.isEmpty(v.getEngineTypeRear()) ? "" : v.getEngineTypeRear();
            		v.setEngineTypeRear(null != v.getInstalType() && 2 == v.getInstalType().intValue() ? engineTypeValue+engineTypeRearValue : engineTypeRearValue);
            	}
            }
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            
            if (StringUtil.isEmpty(command.getEmailSubject()))
            {
                command.setEmailSubject("车辆列表文件下载路径路径");
            }
            mailCommand.setSubject(command.getEmailSubject());
            
            String fileServerDataStr = uploadResult.getString("data");
            if (StringUtil.isEmpty(fileServerDataStr))
            {
                mailCommand.setContent("");
                logger.debug("=========== email info =========== : " + mailCommand.toString());
                mailClient.sendMail(mailCommand);
                // HttpUtil.get(mailServer, mailCommand);
            }
            else
            {
                JSONObject fileServerDataJson = uploadResult.getJSONObject("data");
                if (null == fileServerDataJson || fileServerDataJson.isEmpty())
                {
                    mailCommand.setContent("");
                    logger.debug("=========== email info =========== : " + mailCommand.toString());
                    mailClient.sendMail(mailCommand);
                    // HttpUtil.get(mailServer, mailCommand);
                }
                else
                {
                    mailCommand.setContent(fileServerDataJson.getString("fullPath"));
                    logger.debug("=========== email info =========== : " + mailCommand.toString());
                    mailClient.sendMail(mailCommand);
                    // HttpUtil.get(mailServer, mailCommand);
                }
            }
            // mailCommand.setContent(uploadResult.getString("fullPath"));
            // String jsonStr = JSONObject.fromObject(mailCommand).toString();
            //
            // HttpUtil.postJson(mailServer, jsonStr, "");
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("CarServiceImpl ====> asyncDonwload has error : ", e);
        }
    }
    
    @Override
    public JSONObject download(String fservice, List<? extends Object> list, DownloadCarCommand command,
        String sourcePath) throws BaseServiceException
    {
        
        String httpResult = null;
        File file = null;
        try
        {
            
            ReportConfig config = new ReportConfig("车辆列表");
            
            config.setTargetPath(targetPath);
            
            String metadata = command.getMetadata();
            
            if (!StringUtils.isEmpty(metadata)) {
            	
            	JSONArray jsonArray = JSONArray.fromObject(metadata);
            	
            	for (int index = 0 , length = jsonArray.size() ; index < length ; index++) {
            		JSONObject json = jsonArray.getJSONObject(index);
            		if (json.containsKey("CAR_TYPE_VALUE")) {
            			String desc = json.getString("CAR_TYPE_VALUE");
            			json.remove("CAR_TYPE_VALUE");
            			json.accumulate("car_TYPE_VALUE", desc);
            		}
            		
            		if (json.containsKey("ENGINE_TYPE_VALUE")) {
            			String desc = json.getString("ENGINE_TYPE_VALUE");
            			json.remove("ENGINE_TYPE_VALUE");
            			json.accumulate("engine_TYPE_VALUE", desc);
            			
            		}

            	}
            	
            	config.setColumn(jsonArray, VechicleOutdto.class);
            } else {
                
                config.setColumn("", "序号", 0);
                config.setColumn("chassisNum", "底盘号", 1);
                config.setColumn("cph", "车牌号", 2);
                config.setColumn("sturctureNum", "车架号" , 3);
                //增加二维码
                config.setColumn("qrCode", "二维码" , 4);
                //增加车型码
                config.setColumn("carModelCode", "车型码" , 5);
                //增加安装类型
                config.setColumn("instalTypeDesc", "安装类型", 6);
                //增加安装单位
                config.setColumn("stationName", "安装单位", 7);
                
                config.setColumn("onlineTime", "下线(制造)日期" , 8 , ColumnStyle.DATE , TimePrecision.SECOND , FormatStyle.DATE);
                //增加车辆下线日期
                config.setColumn("carDate", "安装时间", 9 , ColumnStyle.DATE, TimePrecision.MILLISECOND , FormatStyle.TIME);
                config.setColumn("terminalCode", "北斗一体机ID", 10);
                config.setColumn("terminalSim", "北斗一体机SIM卡", 11);
                config.setColumn("fkCode", "FK控制器ID", 12);
                config.setColumn("fkSim", "FK控制器SIM卡", 13);
                config.setColumn("companyName", "所属经销商", 14);
                config.setColumn("dealerCode", "经销商代码", 15);
                config.setColumn("businessName", "所属客户", 16);
                config.setColumn("businessCode", "客户证件号" , 17);
                config.setColumn("invoiceNumber", "发票号" , 18);
                config.setColumn("CAR_TYPE_VALUE", "车辆型号", 19);
                config.setColumn("engineTypeRear", "发动机型号", 20);
                config.setColumn("ENGINE_TYPE_VALUE", "发动机类型", 21);
                config.setColumn("registerTimeStr", "注册时间", 22, ColumnStyle.DATE, TimePrecision.SECOND , FormatStyle.DATE);
                config.setColumn("salesDateStr", "STD销售时间", 23, ColumnStyle.DATE, TimePrecision.SECOND , FormatStyle.DATE);
                config.setColumn("salesStatusValue", "STD销售状态", 24);
                config.setColumn("mbSalesDate", "AAK销售日期", 25 , ColumnStyle.DATE, TimePrecision.SECOND , FormatStyle.DATE);
                config.setColumn("aakStatusValue", "AAK销售状态", 26);
                config.setColumn("autoFlagStr", "录入方式", 27);
                config.setColumn("removalTimeStr", "出库时间", 28, ColumnStyle.DATE, TimePrecision.SECOND , FormatStyle.DATE);
                config.setColumn("firstGpsTimeStr", "首次有效时间", 29, ColumnStyle.DATE, TimePrecision.SECOND , FormatStyle.DATE);
                
                //首次有效位置
                config.setColumn("firstAvalidPosition", "首次有效位置", 30);
                
                config.setColumn("lastGpsDate", "末次有效时间", 31, ColumnStyle.DATE, TimePrecision.MILLISECOND , FormatStyle.TIME);
                config.setColumn("addressDetail", "末次有效位置", 32);
            }
            config.setSourcePath(sourcePath);
            
            fillLocationData(list);
            file = ExcelUtil.getExcel(config, list, command.getSheetName(), 0);
            
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("account", command.getUserInfor().getUserId());
            param.add("file", new FileSystemResource(file));
            logger.info("******************************生成Excel（createExcel）-E**************************************");
            
            RestTemplate restTemplate = new RestTemplate();
            httpResult = restTemplate.postForObject(fservice, param, String.class);
            
        }
        catch (Exception e)
        {
            // e.printStackTrace();
            logger.error("CarServiceImpl ====> asyncDonwload has error : ", e);
            throw new BaseServiceException(e);
        }
        finally
        {
            
            if (null != file && file.exists())
            {
                file.delete();
            }
        }
        return JSONObject.fromObject(httpResult);
        
    }

    @Override
    public void fillLocationData(List<? extends Object> list) throws BaseServiceException
    {
        if (null != list && !list.isEmpty())
        {

        	Map<String, GpsInfoData> map = gpsInfoCache.getAllGpsInfoMap();
            for (int index = 0, length = list.size(); index < length; index++)
            {
                VechicleOutdto vechicle = (VechicleOutdto)list.get(index);
				String fkCommunication = !StringUtils.isEmpty(vechicle.getFkSim()) ? vechicle.getFkSim() : StringUtils.isEmpty(vechicle.getTerminalSim()) ? null : vechicle.getTerminalSim();
                if (null == gpsCache)
                {
                    break;
                }
                try
                {
    				if (!StringUtils.isEmpty(fkCommunication)) {
    					
    					GpsInfoData locationData = map.get(fkCommunication.toString());
    					if(locationData!=null){
    						
    						if (null != locationData.getLng() && null != locationData.getLat()) {
        	                    String lng =
        	                        Double.toString(NumberFormatUtil.getLatitudeOrLongitude(locationData.getLng()));
        	                    String lat =
        	                        Double.toString(NumberFormatUtil.getLatitudeOrLongitude(locationData.getLat()));
        	                    vechicle.setAddressDetail(lng + ";" + lat);
        	                    
        	                    vechicle.setLastGpsLat(lat);
        	                    vechicle.setLastGpsLon(lng);
    						}
    						
    						if (null != locationData.getGpsDate()) {
    							vechicle.setLastGpsDate(locationData.getGpsDate());
    						}
    					}
    				}
                }
                catch (Exception e)
                {
                    logger.error("fillLocationData ====> fillLocationData has error : ", e);
                    throw new BaseServiceException(e);
                }
                if (!StringUtils.isEmpty(vechicle.getFirstGpsLng()) && !StringUtils.isEmpty(vechicle.getFirstGpsLat())) {
                	vechicle.setFirstAvalidPosition(vechicle.getFirstGpsLng()+";"+vechicle.getFirstGpsLat());
                }
            }
        }
    }
    
    @Override
    public boolean hasInvoiceNumber(QueryInvoiceNumberCommand command)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        parameter.put("invoiceNumber", command.getInvoiceNum());
        
        if (null != command.getCarId())
        {
            parameter.put("carId", command.getCarId().longValue());
        }
        List<CarDetailEntity> list = dao.sqlFind("queryInvoiceNumber", parameter, CarDetailEntity.class);
        return null == list || list.isEmpty() ? true : false;
    }
    
    /**
     * 查询终端通讯号
     *
     * @param tId 终端自增ID
     */
    private BigInteger queryTerminalCommunicationId(BigInteger tId)
    {
        // 查询终端
        Map<String, Object> param = new HashMap<>();
        param.put("id", tId);
        param.put("delFlag", Constants.HAVE_DEL);
        List list = dao.sqlFindField("queryCommunicationId", param);
        if (StringUtil.isEmpty(list))
        {
            return null;
        }
        return (BigInteger)list.get(0);
    }
    
    @Override
    public PagingInfo<CarBaseInfoDto> queryCarWithChassis(QueryCarWithRelevanceCommand command)
    {
        
        Map<String, Object> parameter = new HashMap<String, Object>();
        
        if (null != command && !StringUtils.isEmpty(command.getChassis()))
        {
            
            parameter.put("chassis", command.getChassis());
            
            return dao.sqlPagelFind("queryCarWithChassisNum",parameter,Integer.valueOf(command.getPage_number()),Integer.valueOf(command.getPage_size()),CarBaseInfoDto.class);
        }
        else
        {
            return new PagingInfo<CarBaseInfoDto>();
        }
    }
	@Override
	public PagingInfo<DealerOutdto> queryDealerWithName(QueryDealerWithRelevanceCommand command) {

		Map<String , Object> parameter = new HashMap<String , Object>();

		command.setPage_size("5");
        // 此处暂时缺少判断用户是经销商类型用户还是系统用户的业务逻辑,后续需要补全.
        int userType = command.getUserInfor().getType();
		if (null != command && !StringUtils.isEmpty(command.getName())) {
			parameter.put("name", command.getName());

	        if (userType != UserTypeConstant.SYSTEM_ADMIN.getCode())
	        {
	            parameter.put("account_id", command.getUserInfor().getUserId());
	        }
			return dao.sqlPagelFind("queryDealerPageInCarManager", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), DealerOutdto.class);
		} else {
			return new PagingInfo<DealerOutdto>();
		}
	}

	@Override
	public PagingInfo<TerminalIndto> queryTerminalWithIdOrSim(QueryTerminalWithRelevanceCommand command) {

		Map<String , Object> parameter = new HashMap<String , Object>();
		
		command.setPage_size("5");
		Long dealerId = null == command.getDealerId() ? null : command.getDealerId();
		
		if (null == dealerId) {
			return new PagingInfo<TerminalIndto>();
		}
		parameter.put("dealerId", dealerId);
		if (null != command && !StringUtils.isEmpty(command.getFruzzy())) {
			parameter.put("fruzzy", command.getFruzzy());
			return dao.sqlPagelFind("queryTerminalWithIdOrSim", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), TerminalIndto.class);
		} else {
			return new PagingInfo<TerminalIndto>();
		}
	}

	@Override
	public PagingInfo<BusinessIndto> queryBusinessWithName(QueryBusinessWithRelevanceCommand command) {
		
		command.setPage_size("5");
		Map<String , Object> parameter = new HashMap<String , Object>();
		if (null != command && !StringUtils.isEmpty(command.getName())) {
			parameter.put("name", command.getName());
			return dao.sqlPagelFind("queryBusinessPage", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), BusinessIndto.class);
		}
		return new PagingInfo<BusinessIndto>();
	}

	@Override
	public BusinessEntity addBusiness(AddBusinessCommand command) throws BaseServiceException {
		
		if (null == command) {
			throw new BaseServiceException("客户信息不允许为空 .");
		}
		Integer ctype = Integer.valueOf(command.getCtype());
		
		List<BusinessIndto> list = null;
		try {
			
			if (StringUtils.isEmpty(command.getBusinessCode())) {
				throw new BaseServiceException("企业许可证号/身份证驾驶证号不允许为空 .");
			}
			
			
			Map<String , Object> parameter = new HashMap<String , Object>();
			
			parameter.put("businessCode", command.getBusinessCode());
			list = dao.sqlFind("hasDoubleCode", parameter, BusinessEntity.class);
			
			if (null != list && !list.isEmpty()) {
				throw new BaseServiceException("企业许可证号重复 .");
			}
			
			switch (ctype) {
			case 1 :{//判断企业许可证号是否重复
				break;
			}
			case 2 :{//判断个人驾驶证号是否重复
				
				if (!StringUtils.isEmpty(command.getVehicleLicCode())) {
					parameter.put("drivingLicense", command.getVehicleLicCode());
					list = dao.sqlFind("hasDoubleCode", parameter, BusinessEntity.class);
					if (null != list && !list.isEmpty()) {
						throw new BaseServiceException("驾驶证号重复 .");
					}
				}
				break;
			}
			default :{
				throw new BaseServiceException("客户类型不正确 .");
			}
			}

			BusinessEntity businessEntity = new BusinessEntity();
	        CopyPropUtil.copyProp(command, businessEntity);
			return (BusinessEntity)dao.save(businessEntity);
		} catch (Exception e) {
			throw new BaseServiceException(e);
		}
	}

	@Override
	public PagingInfo<ServiceStationOutdto> queryServiceStationWithName(QueryServiceStationCommand command) {
		
		command.setPage_size("5");
		if (null != command && !StringUtils.isEmpty(command.getStationName())) {
			Map<String , Object> parameter = new HashMap<String , Object>();
			parameter.put("stationName", command.getStationName());
			
			return dao.sqlPagelFind("queryServiceStationWithName", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), ServiceStationOutdto.class);
		}
		
		if (null != command && null != command.getId()) {
			Map<String , Object> parameter = new HashMap<String , Object>();
			parameter.put("stationId", command.getId());
			return dao.sqlPagelFind("queryServiceStationWithName", parameter, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), ServiceStationOutdto.class);
		}
		return new PagingInfo<ServiceStationOutdto>();
	}

	/* (non-Javadoc)
	 * @see com.navinfo.opentsp.dongfeng.system.service.ICarService#updateSaledCar(com.navinfo.opentsp.dongfeng.system.commands.car.UpdateSaledCarCommand)
	 */
	@Override
	public void updateSaledCar(UpdateSaledCarCommand command) throws BaseServiceException {
		
		int optionType = 0;
		try {

            Map<String, Object> parameter = new HashMap<String, Object>();
            SimpleDateFormat saleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            
            if (null == command.getCarIndto() || StringUtil.isEmpty(command.getCarIndto().getChassisNum()))
            {
                throw new BaseServiceException("底盘号不允许为空 .");
            }

            if (null == command.getCarIndto() || StringUtils.isEmpty(command.getCarIndto().getCarModelCode()))
            {
                throw new BaseServiceException("车型码不允许为空 .");
            }
            
            if (null == command.getCarIndto() || StringUtils.isEmpty(command.getCarIndto().getVechicleVin())) {
            	throw new BaseServiceException("车辆VIN码不允许为空 .");
            }

            if (null == command.getCarIndto() || null == command.getCarIndto().getCarType())
            {
                throw new BaseServiceException("车辆类型不允许为空 .");
            }

            if (null == command.getCarIndto() || StringUtils.isEmpty(command.getCarIndto().getCarCph()))
            {
                throw new BaseServiceException("车牌号不允许为空 .");
            }
            
//            if (null == command.getCarIndto() || null == command.getCarIndto().getColor()) {
//            	throw new BaseServiceException("车牌颜色不允许为空 .");
//            }
            
            if (null == command.getDealerIndto() || null == command.getDealerIndto().getId())
            {
                throw new BaseServiceException("经销商不允许为空 .");
            }
            
            if (null == command.getCarDetailBean() || null == command.getCarDetailBean().getFuel()) {
            	throw new BaseServiceException("燃料类型不允许为空 .");
            }
            
            if (null == command.getCarIndto() || null == command.getCarIndto().getVfactory()) {
            	throw new BaseServiceException("载重类型不允许为空 .");
            }
            
            if (null == command.getBusinessIndto() || null == command.getBusinessIndto().getId())
            {
                throw new BaseServiceException("所属客户不允许为空 .");
            }
            
            if (null == command.getStationBean() || null == command.getStationBean().getId()) {
            	throw new BaseServiceException("服务站不允许为空");
            }
            
            if (null == command.getCarDetailBean() || StringUtils.isEmpty(command.getCarDetailBean().getEngineType())) {
            	throw new BaseServiceException("请选择发动机类型 .");
            }

            CarEntity car = new CarEntity();
            CarDetailEntity detail = new CarDetailEntity();

            //设置更新时间
            Timestamp syncTime = new Timestamp(Calendar.getInstance().getTime().getTime());
            car.setSync_time(syncTime);
            
            //设置底盘号
            car.setChassisNum(command.getCarIndto().getChassisNum());
            
            //车型码
            car.setCarModelCode(command.getCarIndto().getCarModelCode());
            
            //车辆类型
            car.setCarType(command.getCarIndto().getCarType());
            
            // 设置下线时间
            if (!StringUtils.isEmpty(command.getCarIndto().getOnline()))
            {
                SimpleDateFormat offlineTimeForamt = new SimpleDateFormat("yyyy-MM-dd");
                Date offlineTime = offlineTimeForamt.parse(command.getCarIndto().getOnline());
                car.setOnlineTime(offlineTime.getTime() / 1000);
            }
            //发动机型号
            if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getEngineTypeRear())) {
//            	String engineTypeRear = parseEngineTypeRear(command.getCarDetailBean().getEngineTypeRear());
            	detail.setEngineTypeRear(command.getCarDetailBean().getEngineTypeRear());
            }

            //设置STD为已售状态
            car.setSaleStatus(0);
            //设置AAK为已售状态
            detail.setMbSalesStatus(0);
            
            //设置二维码
            car.setQrCode(command.getCarIndto().getQrCode());
            
            //设置VIN码
            car.setStructureNum(command.getCarIndto().getVechicleVin());
            //设置安装类型
            BigInteger instalType = new BigInteger(command.getCarIndto().getInstalType().toString());
            car.setInstalType(instalType);
            //设置载重类型
            car.setVfactory(command.getCarIndto().getVfactory());
            
            if (null != command.getCarIndto() && !StringUtils.isEmpty(command.getCarIndto().getVfactory())) {
            	
            	//设置载重类型
            	car.setVfactory(command.getCarIndto().getVfactory());
            }
            
            //设置额定功率
            if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getEdgl())) {
            	
            	String edgl = command.getCarDetailBean().getEdgl();//.substring(0, command.getCarDetailBean().getEdgl().indexOf("KW"));
            	edgl = edgl.substring(0 , -1 != edgl.indexOf("KW") ? edgl.indexOf("KW") : edgl.length());
            	detail.setEdgl(edgl);
            }

            //最大功率            
            if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getZdgl())) {
            	String zdgl = command.getCarDetailBean().getZdgl();//.substring(0, command.getCarDetailBean().getZdgl().indexOf("KW"));
            	zdgl = zdgl.substring(0 , -1 != zdgl.indexOf("KW") ? zdgl.indexOf("KW") : zdgl.length());
                detail.setZdgl(zdgl);
            }
            
            //设置整备质量
            detail.setZbzl(command.getCarDetailBean().getZbzl());
            
            //设置总质量
            detail.setZzl(command.getCarDetailBean().getZzl());
            
            //设置燃料类型
            detail.setFuel(command.getCarDetailBean().getFuel());
            
            //设置车牌号码
            car.setCarCph(command.getCarIndto().getCarCph());
            
            //设置车牌颜色
            car.setCarColor(command.getCarIndto().getCphColor());
            
            //设置经销商
            car.setDealerId(command.getDealerIndto().getId());
            
            if (null != command.getBusinessIndto() && null != command.getBusinessIndto().getId()) {
            	detail.setCarOwner(Long.toString(command.getBusinessIndto().getId().longValue()));
            }
            
            //AAK销售日期
            if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getMbSalesDate())) {
            	Long aakSaleTime = saleDateFormat.parse(command.getCarDetailBean().getMbSalesDate()).getTime();
                detail.setMbSalesDate(aakSaleTime / 1000);
            }
            
            //服务站ID
            car.setStationId(command.getStationBean().getId().longValue());

            //设置车牌颜色
            if (null != command.getCarIndto() && null != command.getCarIndto().getColor()) {
            	car.setCarColor(command.getCarIndto().getColor());
            }
            
            //设置客户ID
            if (null != command.getBusinessIndto()) {
            	detail.setCarOwner(command.getBusinessIndto().getId().toString());
            }
            
            //设置发动机号
            detail.setEngineNumber(command.getCarDetailBean().getEngineNumber());
            
            //设置发动机类型
            detail.setEngineType(command.getCarDetailBean().getEngineType());
            
            Long carId = null;
            if (null != command.getCarIndto().getId()) {
            	carId = command.getCarIndto().getId().longValue();
            }
            // 1.根据底盘号查询车辆是否存在.如果存在执行更新操作,如果不存在执行新增操作.
            parameter.put("chassis", command.getCarIndto().getChassisNum());

            List<CarIndto> carList = dao.sqlFind("queryCarByChassis", parameter, CarIndto.class);
            
            if (null != carList && !carList.isEmpty() ) {
            	
            	if (carList.get(0).getDelFlag() == 1) {
            		carId = carList.get(0).getId().longValue();
            	}
            }
            
            //二维码唯一校验
            if (null != command.getCarIndto() && !StringUtils.isEmpty(command.getCarIndto().getQrCode())) {
            	hasDuplicateQr(command.getCarIndto().getQrCode(), carId);
            }

            //设置FK终端
        	if (null != command.getControllerIndto() && null != command.getControllerIndto().getId()) {
        		car.setControllerId(command.getControllerIndto().getId().longValue());
        		bindTeam(command.getControllerIndto().getId() , command.getDealerIndto().getId());
        	}
        	
        	//设置一体机终端
        	if (null != command.getTerminalIndto() && null != command.getTerminalIndto().getId()) {
        		car.setTerminalId(command.getTerminalIndto().getId().longValue());
        		bindTeam(command.getTerminalIndto().getId() , command.getDealerIndto().getId());
        	}
            
            //设置发票号
            if (null != command.getSaleIndto() && !StringUtils.isEmpty(command.getSaleIndto().getInvoiceNumber())) {
            	detail.setInvoiceNumber(command.getSaleIndto().getInvoiceNumber());
            }
            
            if (null == carId) {//新增后装车辆
            	// 录入方式:平台录入
            	car.setAutoFlag(command.getCarIndto().getAutoFlag());

            	optionType = 0;
                if (null != carList && !carList.isEmpty())
                {
                    throw new BaseServiceException("底盘号已经存在 .");
                }
            	
                if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getImg_path())) {
                    detail.setImgPath(command.getCarDetailBean().getImg_path());
                }

                //设置平台录入
                car.setAutoFlag(9101);
                
                //设置安装时间
//                car.setAssembleTime(Calendar.getInstance().getTime().getTime());
                car.setCarDate(Calendar.getInstance().getTime().getTime() / 1000);
                CarEntity savedCar = (CarEntity)dao.save(car);
                detail.setId(savedCar.getCarId());
                dao.save(detail);
                CommonResult result = new CommonResult();
    			result.setResultCode(ReturnCode.OK.code());
    			result.setMessage("车辆保存成功.");
                operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
            } else {//修改后装车辆

            	optionType = 1;
        		parameter.put("id", carId);
        		List<CarIndto> list = dao.sqlFind("queryCarOtherChassis", parameter, CarIndto.class);
        		if (null != list && !list.isEmpty()) {
        			throw new BaseServiceException("底盘号已经存在 .");
        		}
        		car.setDelFlag(0);
                
                if (null != command.getCarDetailBean() && !StringUtils.isEmpty(command.getCarDetailBean().getImg_path())) {
                    detail.setImgPath(command.getCarDetailBean().getImg_path());	
                }
            	
                List<VechicleOutdto> oldCar =  dao.sqlFind("queryCarById", parameter, VechicleOutdto.class);
                
                //设置平台录入方式
                car.setAutoFlag(9101);
                
            	//判断终端的归属
            	BigInteger terminalOrgId = null;

            	BigInteger tid = carList.get(0).getTerminalId();
            	BigInteger fkId = carList.get(0).getFkCode();
            	if(null != tid) {
            		terminalOrgId = tid;
            	}
            	
            	if (null != fkId) {
            		terminalOrgId = fkId;
            	}
            	if (null != terminalOrgId) {
                	BigInteger terminalId = null == command.getTerminalIndto() ? null : command.getTerminalIndto().getId();
                	BigInteger controllerId = null == command.getControllerIndto() ? null : command.getControllerIndto().getId();
                	
                	if (null != terminalId) {
                		updateTerminalTeam(terminalOrgId , terminalId , command.getDealerIndto().getId());
                	}
                	
                	if (null != controllerId) {
                		updateTerminalTeam(terminalOrgId , controllerId , command.getDealerIndto().getId());
                	}
                	
            	}

        		car.setCarDate(Calendar.getInstance().getTime().getTime() / 1000);
            	car.setCarId(carId);
            	detail.setId(carId);
            	dao.update(car);
            	dao.update(detail);
            	CommonResult result = new CommonResult();
    			result.setResultCode(ReturnCode.OK.code());
    			result.setMessage("车辆修改成功.");
                operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
            }
            //start ---删除车辆hy_car_mesdata底盘号和vin码相同的数据  add fwm 2017.12.05
            Map<String,Object> updateParam = new HashMap<String,Object>();
            updateParam.put("vin",command.getCarIndto().getVechicleVin());
            dao.executeUpdate("updateNonF9VehicleDelStatus", updateParam);
            //end ---删除车辆hy_car_mesdata底盘号和vin码相同的数据
            // 绑定的终端信息加入缓存，进行增量订阅
            BigInteger tid =
                null == command.getTerminalIndto() || null == command.getTerminalIndto().getId() ? null == command.getControllerIndto()
                    || null == command.getControllerIndto().getId() ? null : command.getControllerIndto()
                    .getId() : command.getTerminalIndto().getId();
            if (null != tid)
            {
                BigInteger communicationId = queryTerminalCommunicationId(tid);
                if (null != communicationId)
                {
                    terminalSubscribeCache.addSubscribeTerminal(communicationId.longValue());
                }
            }
            
		} catch (Exception e) {
			logger.error("CarServiceImpl ====> updateSaledCar ====> ", e);
            if (optionType == 0) {

            	CommonResult result = new CommonResult();
            	result.setResultCode(ReturnCode.SERVER_ERROR.code());
            	result.setMessage("后装车辆保存失败.");
            	operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
            } else {
            	CommonResult result = new CommonResult();
            	result.setResultCode(ReturnCode.SERVER_ERROR.code());
            	result.setMessage("后装车辆修改失败.");
            	operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.UPDATE, OperateLogConstants.OperateContentPrefixEnum.VEHICLE.getValue() + command.getCarIndto().getChassisNum(), result);
            }
			throw new BaseServiceException(e.getMessage());
		}
	}
	
	/**
	 * 二维码唯一性校验
	 * @param qrCode
	 * @param carId
	 * @throws BaseServiceException
	 */
	private void hasDuplicateQr(String qrCode , Long carId) throws BaseServiceException{
		
    	Map<String , Object> qrParameter = new HashMap<String , Object>();
    	List<CarIndto> list = null;
    	if (null == carId) {
    		
    		qrParameter.put("qr", qrCode);
    		list = dao.sqlFind("hasQrCode", qrParameter, CarIndto.class);
    		
    		if (null != list && !list.isEmpty()) {
    			throw new BaseServiceException("二维码已存在 .");
    		}
    	} else {
    		qrParameter.put("qr", qrCode);
    		qrParameter.put("id", carId);
    		list = dao.sqlFind("hasQrCode", qrParameter, CarIndto.class);
    		
    		if (null != list && !list.isEmpty()) {
    			throw new BaseServiceException("二维码已存在 .");
    		}
    	}
	}
	
	private void hasDuplicateCarModel(String carModelCode , Long carId) throws BaseServiceException {
    	Map<String , Object> qrParameter = new HashMap<String , Object>();
    	List<CarIndto> list = null;
    	if (null == carId) {
    		
    		qrParameter.put("carModelCode", carModelCode);
    		list = dao.sqlFind("hasCarModelCode", qrParameter, CarIndto.class);
    		
    		if (null != list && !list.isEmpty()) {
    			throw new BaseServiceException("车型码已存在 .");
    		}
    	} else {
    		qrParameter.put("carModelCode", carModelCode);
    		qrParameter.put("id", carId);
    		list = dao.sqlFind("hasCarModelCode", qrParameter, CarIndto.class);
    		
    		if (null != list && !list.isEmpty()) {
    			throw new BaseServiceException("车型码已存在 .");
    		}
    	}
	}
	
	private void bindTeam(BigInteger currentTerminalId , Long teamId) throws BaseServiceException {
		try {

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("terminalId", currentTerminalId);
            map.put("dealerId", teamId);
            dao.executeUpdate("updateTerminal", map);
		} catch (Exception e) {
			throw new BaseServiceException(e.getMessage());
		}
	}
	
	/**
	 * 修改终端归属
	 * @param orgTerminalId
	 * @param currentTerminalId
	 * @param teamId
	 * @throws BaseServiceException
	 */
	private void updateTerminalTeam(BigInteger orgTerminalId , BigInteger currentTerminalId , Long teamId) throws BaseServiceException {
		
		Map<String , Object> parameter = new HashMap<String , Object>();
		
		if (null == orgTerminalId || null == currentTerminalId) {
			throw new BaseServiceException("原始终端ID或当前绑定终端ID均不可为空 .");
		}
		
		if (orgTerminalId.longValue() != currentTerminalId.longValue()) {
			
			parameter.put("orgTerminalId", orgTerminalId.longValue());
			List<TerminalOutdto> list = dao.sqlFind("queryTerminalByTid", parameter, TerminalOutdto.class);
			
			if (null != list && !list.isEmpty()) {
				
				parameter.put("orgTerminalId", orgTerminalId);
				parameter.put("orgTeamId", list.get(0).getOrgTeamId());
				dao.executeUpdate("updateTerminalTeam", parameter);
			} else {
				parameter.put("orgTerminalId", orgTerminalId);
				parameter.put("orgTeamId", teamId);
				dao.executeUpdate("updateTerminalTeam", parameter);
			}
		}
	}

	@Override
	public CarModelOutDto queryCarModel(QueryCarModelCommand command){
		
		if (null == command || StringUtils.isEmpty(command.getCarModel())) {
			return null;
		}
		
		Map<String , Object> parameter = new HashMap<String , Object>();
		parameter.put("car_model", command.getCarModel());
		List<CarModelOutDto> list = dao.sqlFind("selectVehicleTypeAndBasicIdByTypeCode", parameter, CarModelOutDto.class);
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public CarParamOutDto queryCarParam(CarParamCommand command) {

		if (null == command || StringUtils.isEmpty(command.getCarModel())) {
			return null;
		}
		
		Map<String , Object> parameter = new HashMap<String , Object>();
		parameter.put("car_model", command.getCarModel());
		List<CarParamOutDto> list = dao.sqlFind("selectCarParamByVehicleTypeCode", parameter, CarParamOutDto.class);
		if (null == list || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	@Override
	public CheckTerminalOutDto checkTerminal(CheckTerminalCommand command) throws BaseServiceException{
		
		CheckTerminalOutDto result = new CheckTerminalOutDto();
		try {

			if (null == command || null == command.getCarId()) {
				throw new BaseServiceException("车辆ID不允许为空 .");
			}
			
			//1.查询终端通讯号
			Map<String , Object> parameter = new HashMap<String , Object>();
			parameter.put("id", command.getCarId());

			List<VechicleOutdto> list =  dao.sqlFind("queryCarById", parameter, VechicleOutdto.class);
			
			if (null == list || list.isEmpty()) {
				return getDefaultResult();
			}
			
			//获取终端ID
			Long tid = StringUtils.isEmpty(list.get(0).getFkCode()) ? StringUtils.isEmpty(list.get(0).getTerminalCode()) ? null : Long.parseLong(list.get(0).getTerminalCode()) : Long.parseLong(list.get(0).getFkCode());
			List<TerminalOutdto> terminals = null;
			if (null != tid) {
				parameter.put("orgTerminalId", tid);
				terminals = dao.sqlFind("queryTerminalByTid", parameter, TerminalOutdto.class);
			}
			
			if (null == terminals || terminals.isEmpty()) {
				return getDefaultResult();
			}
			
			BigInteger communicationId = terminals.get(0).getCommunicationId();
//			BigInteger communicationId = null != terminals.get(0).getAutoCommunicationId() ? terminals.get(0).getAutoCommunicationId() : null != terminals.get(0).getCommunicationId() ? terminals.get(0).getCommunicationId() : null;
			
			if (null == communicationId) {
				return getDefaultResult();
			}
			//2.获取车辆的在线状态
			 GpsInfoData gpsInfo = gpsInfoCache.getGpsInfo(communicationId.toString());
			 
			 //获取末次有效位置
			 LocationData locationData = gpsCache.getLastGps(communicationId.toString());

			 TerminalDataOutDto terminalData = new TerminalDataOutDto();
			 Integer carStatus = null == gpsInfo || null == gpsInfo.getCarStatue() || (null != gpsInfo.getCarStatue() && 0 == (gpsInfo.getCarStatue() & 1)) ? 0 : 1;

			 //车辆不在线
			 if (0 == carStatus) {
				 if (null == locationData) {
					 terminalData.setTer(NULL_MSG);
					 terminalData.setFlag(NULL_MSG_FLAG);
				 } else {
					 terminalData.setTer(MSG);
					 terminalData.setTertime(changeDate(locationData.getGpsDate()));
					 terminalData.setFlag(MSG_FLAG);
					 terminalData.setLat(Integer.toString(locationData.getLatitude()));
					 terminalData.setLon(Integer.toString(locationData.getLongitude()));
				 }
			 } else {
				 terminalData.setTer(MSG);
				 terminalData.setTertime(changeDate(locationData.getGpsDate()));
				 terminalData.setFlag(MSG_FLAG);
				 terminalData.setLat(Long.toString(locationData.getLatitude()));
				 terminalData.setLon(Long.toString(locationData.getLongitude()));
			 }
			 result.setTerminalData(terminalData);
			 LocationData validLocationData = gpsCache.getLastGpsVlid(communicationId.toString());
			 
			 List<Long> terminalList = new ArrayList<Long>();
			 
			 if (null == terminals.get(0).getCommunicationId() || 0 == terminals.get(0).getCommunicationId().longValue()) {
				 return null;
			 }
			 terminalList.add(Long.parseLong(terminals.get(0).getSimNo()));
			 
			 logger.info("调用WebService获取CAN数据  ====> 通讯号 : " + terminals.get(0).getSimNo() + " , 类型值 : " + 1);
			 LocationData canLocationData = getLastGpsByCloud(terminalList , 1);
			 
			 GpsDataOutDto gpsData = new GpsDataOutDto();
			 AccDataOutDto accData = new AccDataOutDto();
			 CanDataOutDto canData = new CanDataOutDto();
			 
			 if (null == locationData) {

				 gpsData.setGps(NULL_MSG);
				 gpsData.setFlag(NULL_MSG_FLAG);
				 
				 accData.setAcc(NULL_MSG);
				 accData.setFlag(NULL_MSG_FLAG);
				 
				 canData.setCan(NULL_MSG);
				 canData.setFlag(NULL_MSG_FLAG);
			 } else {
				 
				 if (null == validLocationData) {
					 gpsData.setGps(NO_MSG);
					 gpsData.setFlag(NO_MSG_FLAG);
				 } else {
					 gpsData.setGps(MSG);
					 gpsData.setFlag(MSG_FLAG);
					 gpsData.setGpstime(changeDate(validLocationData.getGpsDate()));
					 gpsData.setLocation(getAddressByLatLog(
							validLocationData.getLatitude() * 0.000001,
							validLocationData.getLongitude() * 0.000001));
				 }
			 }
			 
			 if (null != locationData) {
				 
				 if (null == canLocationData) {
					 canData.setCan(NO_MSG);
					 canData.setFlag(NO_MSG_FLAG);
					 
					 accData.setAcc(NO_MSG);
					 accData.setFlag(NO_MSG_FLAG);
				 } else {
					 canData.setCan(MSG);
					 canData.setFlag(MSG_FLAG);
					 canData.setCantime(changeDate(canLocationData.getGpsDate()));
					 
					 accData.setAcc(MSG);
					 accData.setFlag(MSG_FLAG);
					 accData.setAcctime(changeDate(canLocationData.getGpsDate()));
				 }
			 }
			 
			 result.setAccData(accData);
			 result.setCanData(canData);
			 result.setGpsData(gpsData);
			 
			 return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServiceException(e.getMessage());
		}
	}
	/**
	 * 根据经纬度获取地址信息
	 * 
	 */
	public static String getAddressByLatLog(double lat, double log) {
		URLConnection httpsConn = null;
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			String url = String
					.format("http://www.aerohuanyou.com/SoftwareCategory/open/gpsWeathers.htm?lat="
							+ lat + "&lng=" + log);
			URL myURL = new URL(url);
			httpsConn = (URLConnection) myURL.openConnection();// 不使用代理
			insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
			br = new BufferedReader(insr);
			String data = null;
			StringBuilder json = new StringBuilder();
			while ((data = br.readLine()) != null) {
				json.append(data);
			}
			JSONObject obj = JSONObject.fromObject(json.toString());
			String address = obj.getString("detailAddress");
			return address;
		} catch (Exception e) {
			return null;
		} finally {
			if (insr != null) {
				try {
					insr.close();
					br.close();
				} catch (Exception e) {
				}
			}
		}
	}
	
	private String changeDate(Long date) {
		
		Date d = new Date(date * 1000);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(d);
	}
	
	private LocationData getLastGpsByCloud(List<Long> terminalList , int type) throws BaseServiceException{
		
		LocationData gpsdata = null;
		try {

			CommonParameterSerializer para = new CommonParameterSerializer();
            para.setMultipage(false);
            
            if (null == cloudDataRmiClientConfiguration.getBasicDataQueryWebService()) {
            	return null;
            }
			byte[] data = cloudDataRmiClientConfiguration.getBasicDataQueryWebService().getLastestLocationData(terminalList , "" , type , para);
			if (data != null) {
                LastestLocationDataRes gps = null;
                gps = LastestLocationDataRes.parseFrom(data);
                List<TerminalLocationData> lastgps = gps.getDatasList();
                if (lastgps.size() != 0) {
                    gpsdata = lastgps.get(0).getLocationData();
                }
                return gpsdata;
            } else {
                logger.error("调用位置云轨迹接口返回为空，参数为：" + terminalList.get(0));
            }
		} catch (Exception e) {
			e.printStackTrace();
			throw new BaseServiceException(e.getMessage());
		}
		return null;
	}
	
	private CheckTerminalOutDto getDefaultResult() {

		CheckTerminalOutDto check = new CheckTerminalOutDto();
		
		AccDataOutDto accData = new AccDataOutDto();
		accData.setFlag(NO_MSG_FLAG);
		accData.setAcc(NO_MSG);
		check.setAccData(accData);
		
		CanDataOutDto canData = new CanDataOutDto();
		canData.setFlag(NO_MSG_FLAG);
		canData.setCan(NO_MSG);
		
		GpsDataOutDto gpsData = new GpsDataOutDto();
		gpsData.setFlag(NO_MSG_FLAG);
		gpsData.setGps(NO_MSG);
		
		TerminalDataOutDto terminalData = new TerminalDataOutDto();
		terminalData.setFlag(NO_MSG_FLAG);
		terminalData.setTer(NO_MSG);
		
		check.setAccData(accData);
		check.setCanData(canData);
		check.setGpsData(gpsData);
		check.setTerminalData(terminalData);
		return check;
	}
	
	/**
	 * 解析发动机类型
	 * @param engine
	 * @return
	 */
	private Integer parseEngineType(String engine) {
		
		if(StringUtils.isEmpty(engine)) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();

		Pattern p = Pattern.compile("[a-zA-Z0-9|-]");
		for(int i=0 , l=engine.length() ; i<l ; i++) {
			Character c = engine.charAt(i);
			String temp = c.toString();
			Matcher m = p.matcher(temp);
			
			if (!m.find()) {
				builder.append(c);
			} else {
				break;
			}
		}
		Map<String , Object> parameter = new HashMap<String , Object>();
		parameter.put("engineType", builder.toString());
		
		List<BasicDataIndto> list = dao.sqlFind("queryEngineType", parameter, BasicDataIndto.class);
		
		return null != list && !list.isEmpty() ? Integer.parseInt(list.get(0).getDataCode()) : null;
	}
	
	private String parseEngineTypeRear(String engine) {
		
		if(StringUtils.isEmpty(engine)) {
			return null;
		}
		
		StringBuilder builder = new StringBuilder();

		Pattern p = Pattern.compile("[a-zA-Z0-9|-]");
		for(int i=0 , l=engine.length() ; i<l ; i++) {
			Character c = engine.charAt(i);
			String temp = c.toString();
			Matcher m = p.matcher(temp);
			
			if (!m.find()) {
				builder.append(c);
			} else {
				break;
			}
		}
		if (StringUtils.isEmpty(builder.toString())) {
			return engine;
		}
		
		return engine.substring(engine.indexOf(builder.toString())+builder.toString().length());
	}
	

	@Override
	public void updateInState(Long carId, Integer state) throws BaseServiceException {
		
		try {
			//修改车辆在库状态
            CarEntity car = repository.findByCarId(carId);
            car.setState(state);
            dao.update(car);
		} catch (Exception e) {
			throw new BaseServiceException(e.getMessage() , e);
		}
		
	}

	@Override
	public Integer queryCarType(QueryCarTypeCommand command) throws BaseServiceException{
		
		if (null == command || StringUtils.isEmpty(command.getCarTypeRear())) {
			throw new BaseServiceException("查询条件不允许为空 .");
		}
		
		return parseEngineType(command.getCarTypeRear());
	}
	
}
