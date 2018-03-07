package com.navinfo.opentsp.dongfeng.system.service;

import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.system.commands.business.AddBusinessCommand;
import com.navinfo.opentsp.dongfeng.system.commands.car.*;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BasicDataIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.BusinessIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.*;
import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import net.sf.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface ICarService {
	
	public PagingInfo<DealerOutdto> sqlPageDealer(QueryDealerCommand dealerCommand , String pageSize);
	
	public PagingInfo<TerminalIndto> sqlPageTerminal(QueryTerminalCommand terminalCommand);
	
	public PagingInfo<BusinessIndto> sqlPageBusiness(QueryBusinessCommand businessCommand , UserTypeConstant userType);
	
	public Map<String , List<BasicDataIndto>> queryBasicData(QueryBasicDataCommand basicCommand);
	
	public void updateCar(UpdateCarCommand vechicle) throws BaseServiceException;
	
	public void deleteCar(DeleteCarCommand carCommand , UserTypeConstant userType) throws BaseServiceException;
	
	public PagingInfo<VechicleOutdto> queryCar(QueryCarCommand command , UserTypeConstant userType);

	public List<VechicleOutdto> queryCarList(QueryCarCommand command , UserTypeConstant userType);

	public void asyncDonwload(String fservice , DownloadCarCommand command , String sourcePath);
	
	public JSONObject download(String fservice , List<? extends Object> list , DownloadCarCommand command , String sourcePath) throws BaseServiceException;
	
	/**
	 * 判断发票号是否存在
	 * @param command
	 * @return
	 */
	public boolean hasInvoiceNumber(QueryInvoiceNumberCommand command);
	
	/**
	 * 新增后装车辆时输入底盘号进行联想查询
	 * @param command
	 * @return
	 */
	public PagingInfo<CarBaseInfoDto> queryCarWithChassis(QueryCarWithRelevanceCommand command);
	
	
	/**
	 * 联想查询经销商
	 * @param command
	 * @return
	 */
	public PagingInfo<DealerOutdto> queryDealerWithName(QueryDealerWithRelevanceCommand command);
	
	/**
	 * 联想查询终端
	 * @return
	 */
	public PagingInfo<TerminalIndto> queryTerminalWithIdOrSim(QueryTerminalWithRelevanceCommand command);
	
	/**
	 * 联想查询客户信息
	 * @param command
	 * @return
	 */
	public PagingInfo<BusinessIndto> queryBusinessWithName(QueryBusinessWithRelevanceCommand command);
	
	/**
	 * 添加客户信息
	 * @param command
	 * @throws BaseServiceException
	 */
	public BusinessEntity addBusiness(AddBusinessCommand command) throws BaseServiceException;
	
	/**
	 * 服务站联想查询(根据名称查询)
	 * @param command
	 * @return
	 */
	public PagingInfo<ServiceStationOutdto> queryServiceStationWithName(QueryServiceStationCommand command);
	
	/**
	 * 新增/修改后装车辆
	 * @param command
	 * @throws BaseServiceException
	 */
	public void updateSaledCar(UpdateSaledCarCommand command) throws BaseServiceException;
	
	/**
	 * 查询发动机类型
	 * @param command
	 * @return
	 */
	public CarModelOutDto queryCarModel(QueryCarModelCommand command);

	/**
	 * 查询最大功率
	 * @param command
	 * @return
	 */
	public CarParamOutDto queryCarParam(CarParamCommand command);
	
	/**
	 * 车辆终端检测
	 * @return
	 */
	public CheckTerminalOutDto checkTerminal(CheckTerminalCommand command) throws BaseServiceException;

	/**
	 * 更新在库状态
	 * @param command
	 * @throws BaseServiceException
	 */
	public void updateInState(Long carId , Integer state) throws BaseServiceException;
	
	/**
	 * 查询车辆类型
	 * @param command
	 * @return
	 */
	public Integer queryCarType(QueryCarTypeCommand command) throws BaseServiceException;
	
	/**
	 * 补充末次有效位置
	 * @param list
	 */
	public void fillLocationData(List<? extends Object> list) throws BaseServiceException;
}
