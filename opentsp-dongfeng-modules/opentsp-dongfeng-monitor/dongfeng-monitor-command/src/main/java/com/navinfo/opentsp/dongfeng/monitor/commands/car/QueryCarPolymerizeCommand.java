package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;

import javax.validation.constraints.NotNull;

/**
 * 
 * @author xltianc-zh
 *
 */
@SuppressWarnings("rawtypes")
public class QueryCarPolymerizeCommand extends BaseCommand<HttpCommandResultWithData> {

	// 搜索类型
	private Integer searchType;
	// 搜索内容
	private String searchText;
	// 发动机类型,默认值 -1(不限)
	private String engineType;
	// 车辆类型,默认值 -1(不限)
	private String carType;
	// 当前所在地
	private String carPlace;
	// STD销售状态
	private Integer stdSalesStatus;
	// AAK销售状态
	private Integer aakSalesStatus;
	// 所属经销商
	private String dealer;
	// 所属客户
	private String customer;
	// 车辆状态
	private Integer carStauts;
	
	@NotNull(message = "地图显示等级不能为空", groups = { GroupCommand.class })
	private Integer level; // 地图显示等级
	@NotNull(message = "左下角经度不能为空", groups = { GroupCommand.class })
	private Double leftLng; // 左下角经度
	@NotNull(message = "左下角纬度不能为空", groups = { GroupCommand.class })
	private Double leftLat; // 左下角纬度
	@NotNull(message = "右上角经度不能为空", groups = { GroupCommand.class })
	private Double rightLng; // 右上角经度
	@NotNull(message = "右上角纬度不能为空", groups = { GroupCommand.class })
	private Double rightLat; // 右上角纬度

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public String getCarPlace() {
		return carPlace;
	}

	public void setCarPlace(String carPlace) {
		this.carPlace = carPlace;
	}

	public Integer getStdSalesStatus() {
		return stdSalesStatus;
	}

	public void setStdSalesStatus(Integer stdSalesStatus) {
		this.stdSalesStatus = stdSalesStatus;
	}

	public Integer getAakSalesStatus() {
		return aakSalesStatus;
	}

	public void setAakSalesStatus(Integer aakSalesStatus) {
		this.aakSalesStatus = aakSalesStatus;
	}

	public String getDealer() {
		return dealer;
	}

	public void setDealer(String dealer) {
		this.dealer = dealer;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Integer getCarStauts() {
		return carStauts;
	}

	public void setCarStauts(Integer carStauts) {
		this.carStauts = carStauts;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getLeftLng() {
		return leftLng;
	}

	public void setLeftLng(Double leftLng) {
		this.leftLng = leftLng;
	}

	public Double getLeftLat() {
		return leftLat;
	}

	public void setLeftLat(Double leftLat) {
		this.leftLat = leftLat;
	}

	public Double getRightLng() {
		return rightLng;
	}

	public void setRightLng(Double rightLng) {
		this.rightLng = rightLng;
	}

	public Double getRightLat() {
		return rightLat;
	}

	public void setRightLat(Double rightLat) {
		this.rightLat = rightLat;
	}

	@Override
	public Class<? extends HttpCommandResultWithData> getResultType() {
		return HttpCommandResultWithData.class;
	}

	@Override
	public String toString() {
		return "QueryCarPolymerizeCommand [level=" + level + ", leftLng=" + leftLng + ", leftLat=" + leftLat
				+ ", rightLng=" + rightLng + ", rightLat=" + rightLat + ", searchType=" + searchType + ", searchText="
				+ searchText + ", engineType=" + engineType + ", carType=" + carType + ", carPlace=" + carPlace
				+ ", stdSalesStatus=" + stdSalesStatus + ", aakSalesStatus=" + aakSalesStatus + ", dealer=" + dealer
				+ ", customer=" + customer + ", carStauts=" + carStauts + "]";
	}

}
