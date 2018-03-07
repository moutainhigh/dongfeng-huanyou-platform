package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

public class CarDetaildto {
	
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
	 * 发动机号
	 */
	private String engineNumber;
	
	/**
	 * 图片路径
	 */
	public String img_path;
	
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
	 * AAK销售日期
	 */
	private String mbSalesDate;
	
	/**
	 * 发票号
	 */
	private String invoiceNumber;
	
	/**
	 * 燃油类型
	 */
	private Integer fuel;

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

	public String getMbSalesDate() {
		return mbSalesDate;
	}

	public void setMbSalesDate(String mbSalesDate) {
		this.mbSalesDate = mbSalesDate;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Integer getFuel() {
		return fuel;
	}

	public void setFuel(Integer fuel) {
		this.fuel = fuel;
	}

}
