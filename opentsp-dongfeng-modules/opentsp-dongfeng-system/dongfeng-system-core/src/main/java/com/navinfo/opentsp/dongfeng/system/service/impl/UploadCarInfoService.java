package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.util.httpUtil.HttpUtil;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarCommand;
import com.navinfo.opentsp.dongfeng.system.commands.car.UploadCarInfoCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.QueryCarIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.VechicleOutdto;
import com.navinfo.opentsp.dongfeng.system.configuration.RedisConfigurationFactory;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import com.navinfo.opentsp.dongfeng.system.service.IUploadCarInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class UploadCarInfoService implements IUploadCarInfoService {

	private final static Log logger = LogFactory.getLog(UploadCarInfoService.class);
	
	@Value("${cloud.upload.url}")
	private String saveCarInfoUrl;

	@Value("${cloud.server.sync.car.switch}")
	private String syncCarSwitch;

	@Value("${cloud.upload.username:zhonghuan}")
	private String userName;
	
	@Value("${cloud.upload.password:123456}")
	private String password;

	@Resource
	private ICarService carService;
	
	@Resource
	private RedisConfigurationFactory redisConfigurationFactory;
	
    @Autowired
    public StringRedisTemplate redisTemplate;
	
	@Override
	public void uploadCarInfo(UploadCarInfoCommand command) throws BaseServiceException {

		try {

			QueryCarCommand carCommand = new QueryCarCommand();
			carCommand.setUserInfor(command.getUserInfor());
			
			QueryCarIndto carBean = new QueryCarIndto();
			carBean.setId(command.getCarId());
			carCommand.setCarBean(carBean);
			
			carCommand.setPage_size("10");
			carCommand.setPage_number("0");
			
			PagingInfo<VechicleOutdto> page = null;
			if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType()) {
				page = carService.queryCar(carCommand , UserTypeConstant.SYSTEM_ADMIN);
			}

			if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType()) {
				page = carService.queryCar(carCommand , UserTypeConstant.CAR_FACTORY);
			}

			if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType()) {
				page = carService.queryCar(carCommand , UserTypeConstant.BUSINESS);
			}

			if (UserTypeConstant.SERVICE_STATION.getCode() == command.getUserInfor().getType()) {
				page = carService.queryCar(carCommand , UserTypeConstant.SERVICE_STATION);
			}
			
			if (null == page || null == page.getList() || page.getList().isEmpty()) {
				throw new BaseServiceException("车辆信息不存在或用户类型不存在,车辆云端同步失败 .");
			}
			if (Boolean.valueOf(syncCarSwitch)) {
				//向云端上传车辆信息
				uploadCarInfo(saveCarInfoUrl, page.getList().get(0));
			}
		} catch (BaseServiceException e) {
			logger.error("UploadCarInfoService ====> uploadCarInfo : ", e);
			throw e;
		}
	}
	
	/**
	 * 云端同步车辆信息
	 * @param url
	 * @param car
	 * @throws BaseServiceException
	 */
	private void uploadCarInfo(String url , VechicleOutdto car) throws BaseServiceException {

		if (StringUtils.isEmpty(url)) {
			throw new BaseServiceException("同步车辆信息的URl不允许为空 .");
		}
		
		JSONObject param = new JSONObject();
		
		param.accumulate("id", car.getCarId().longValue());
		param.accumulate("carVin", car.getSturctureNum());
		param.accumulate("chassissNum", car.getChassisNum());
		
		//控制器ID
		BigInteger controllerId = car.getControllerId();
		
		//一体机ID
		BigInteger terminalId = car.gettId();
		
		BigInteger tid = null == controllerId ? null == terminalId ? null : terminalId : controllerId;
		
		param.accumulate("terminalId", null == tid ? "" : tid.toString());



		BigInteger comm = null;
		//FK通讯号
		BigInteger fkAutoCommunication = car.getFkAutoCommunication();

		//FK通讯号
		BigInteger fkCommunication = car.getFkCommunication();

		//一体机通讯号
		BigInteger autoCommunication = car.getAutoCommunication();

		//一体机通讯号
		BigInteger communication = car.getCommunication();
		
		//BigInteger comm = null == autoCommunication ? null == communication ? null : communication : autoCommunication;

		//FK优先一体机
		comm = (fkAutoCommunication == null ? fkCommunication : fkAutoCommunication);
		if(comm == null){
			comm = (autoCommunication == null ? communication : autoCommunication);
		}
		param.accumulate("autoTerminal", null == comm ? "" : comm.toString());

		//车型码
		if (StringUtils.isEmpty(car.getCarModelCode())) {
			throw new BaseServiceException("车型码不允许为空 .");
		}
		param.accumulate("carModelCode", car.getCarModelCode());
		
		//证件号
		param.accumulate("businessCode", StringUtils.isEmpty(car.getBusinessCode()) ? "" : car.getBusinessCode());
		
		//零售发票号
		param.accumulate("invoiceNumber", StringUtils.isEmpty(car.getInvoiceNumber()) ? "" : car.getInvoiceNumber());
		
		//AAK销售日期 (见全局时间格式)
		param.accumulate("mbSalesDate", StringUtils.isEmpty(car.getMbSalesDate()) ? "" : car.getMbSalesDate());
		
		//AAK销售状态
		param.accumulate("mbSalesStatus", null == car.getMbSalesStatus() ? "" : car.getMbSalesStatus());
		
		//STD销售状态
		param.accumulate("salesStatus", null == car.getSaleSatusId() ? "" : car.getSaleSatusId());
		
		//STD销售日期(见全局时间格式)
		param.accumulate("salesDate", StringUtils.isEmpty(car.getSalesDateStr()) ? "" : car.getSalesDateStr());
		
		//订单号
		param.accumulate("orderNumber", StringUtils.isEmpty(car.getOrderNumber()) ? "" : car.getOrderNumber());
		
		//经销商id
		param.accumulate("carTeamId", null == car.getDealerId() ? "" : car.getDealerId().toString());
		
		//创建时间(见全局时间格式)
		param.accumulate("createTime", StringUtils.isEmpty(car.getOnlineTime()) ? "" : car.getOnlineTime());
		
		//更新时间(见全局时间格式)
		SimpleDateFormat syncFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String syncTimeStr = null;
		if (null != car.getSyncTime()) {
			syncTimeStr = syncFormat.format(new Date(car.getSyncTime().getTime()));
		}
		param.accumulate("updateTime", StringUtils.isEmpty(syncTimeStr) ? "" : syncTimeStr);

		param.put("loginName", userName);
		param.put("loginPwd", password);
		String uploadJson = null;
		try{
			uploadJson = HttpUtil.postRequest(url, param);
		}catch (Exception e){
			logger.error("云端地址访问失败", e);
			throw new BaseServiceException("云端地址访问失败 .");
		}

		
		if (StringUtils.isEmpty(uploadJson)) {
			throw new BaseServiceException("云端同步无返回信息 .");
		}
		
		JSONObject json = JSONObject.fromObject(uploadJson);
		
		if (null == json || json.isEmpty()) {
			throw new BaseServiceException("云端同步无返回信息.");
		}
		
		Integer resultCode = json.getInt("resultCode");
		
		if (200 != resultCode) {
			throw new BaseServiceException(StringUtils.isEmpty(json.getString("message")) ? "云端没有返回错误信息 ." : json.getString("message"));
		}
	}
}
