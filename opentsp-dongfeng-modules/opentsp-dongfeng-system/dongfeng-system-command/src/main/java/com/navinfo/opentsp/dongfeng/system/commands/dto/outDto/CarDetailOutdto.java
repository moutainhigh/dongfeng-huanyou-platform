package com.navinfo.opentsp.dongfeng.system.commands.dto.outDto;

public class CarDetailOutdto {
	
	/**
	 * 与车辆ID相同
	 */
	private long id;
	
	/**
	 * 发动机类型
	 */
//    @NotNull(message = "发动机类型(engineType)不能为空", groups = {GroupCommand.class})
//    @NotBlank(message = "发动机类型(engineType)不能为空", groups = {GroupCommand.class})
	private String engineType;
	
	/**
	 * 发动机中文描述
	 */
	private String engineTypeValue;
	/**
	 * 发动机号
	 */
	private String engineNumber;
	
	/**
	 * 图片路径
	 */
	private String img_path;
	
	/**
	 * AAK销售状态描述
	 */
	private String mbSaleStatusValue;
	
	/**
	 * AAK销售状态
	 */
	private Integer mbSalesStatus;
	
	/**
	 * AAK销售时间
	 */
	private String mbSaleTime;
	
	/**
	 * 发动机型号
	 */
	private String engineTypeRear;
	
	/**
	 * 额定功率
	 */
	private String edgl;
	
	/**
	 * 最大功率
	 */
	private String zdgl;
	
	/**
	 * 整备质量
	 */
	private String zbzl;
	
	/**
	 * 总质量
	 */
	private String zzl;
	
	/**
	 * 燃料类型
	 */
	private Integer fuel;
	/**
	 * 燃料类型
	 */
	private String fuelValue;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getEngineTypeValue() {
		return engineTypeValue;
	}

	public void setEngineTypeValue(String engineTypeValue) {
		this.engineTypeValue = engineTypeValue;
	}

	public String getMbSaleStatusValue() {
		return mbSaleStatusValue;
	}

	public void setMbSaleStatusValue(String mbSaleStatusValue) {
		this.mbSaleStatusValue = mbSaleStatusValue;
	}

	public Integer getMbSalesStatus() {
		return mbSalesStatus;
	}

	public void setMbSalesStatus(Integer mbSalesStatus) {
		this.mbSalesStatus = mbSalesStatus;
	}

	public String getMbSaleTime() {
		return mbSaleTime;
	}

	public void setMbSaleTime(String mbSaleTime) {
		this.mbSaleTime = mbSaleTime;
	}

	public String getEngineTypeRear() {
		return engineTypeRear;
	}

	public void setEngineTypeRear(String engineTypeRear) {
		this.engineTypeRear = engineTypeRear;
	}

	public String getEdgl() {
		return edgl;
	}

	public void setEdgl(String edgl) {
		this.edgl = edgl;
	}

	public String getZdgl() {
		return zdgl;
	}

	public void setZdgl(String zdgl) {
		this.zdgl = zdgl;
	}

	public String getZbzl() {
		return zbzl;
	}

	public void setZbzl(String zbzl) {
		this.zbzl = zbzl;
	}

	public String getZzl() {
		return zzl;
	}

	public void setZzl(String zzl) {
		this.zzl = zzl;
	}

	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}

	public String getFuelValue() {
		return fuelValue;
	}

	public void setFuelValue(String fuelValue) {
		this.fuelValue = fuelValue;
	}
}
