package com.navinfo.opentsp.dongfeng.openapi.dto.day;

/**
 * 提供图吧接口 ，关于查询最近30天内新增或更新的车辆信息 提供的数据model
 * 
 * @author xltianc.zh
 */
public class CarInfoYqDto {

	private Long id; // 车辆id
	private String car_vin; // 车辆vin
	private String car_model; // 车型码
	private Long terminal_id; // 车辆终端ID
	private Long auto_terminal; // 终端通信号
	private String create_time; // 车辆下线时间
	private String update_time;// 车辆最后同步更新时间
	private String chassiss_num;// 底盘号
	private Long car_team_id;// 经销商ID
	private String order_number;// 订单号
	private Long sales_status;// 销售状态
	private Integer del_flag;
	private String invoice_number;// 发票号
	private String business_code;// 证件代码
	private String sales_date;// 销售日期
	private Integer mb_sales_status;
	private String mb_sales_date;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCar_vin() {
		return car_vin;
	}

	public void setCar_vin(String car_vin) {
		this.car_vin = car_vin;
	}

	public String getCar_model() {
		return car_model;
	}

	public void setCar_model(String car_model) {
		this.car_model = car_model;
	}

	public Long getTerminal_id() {
		return terminal_id;
	}

	public void setTerminal_id(Long terminal_id) {
		this.terminal_id = terminal_id;
	}

	public Long getAuto_terminal() {
		return auto_terminal;
	}

	public void setAuto_terminal(Long auto_terminal) {
		this.auto_terminal = auto_terminal;
	}

	public String getCreate_time() {
		return create_time;
	}

	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}

	public String getOrder_number() {
		return order_number;
	}

	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}

	public String getInvoice_number() {
		return invoice_number;
	}

	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}

	public String getBusiness_code() {
		return business_code;
	}

	public void setBusiness_code(String business_code) {
		this.business_code = business_code;
	}

	public String getChassiss_num() {
		return chassiss_num;
	}

	public void setChassiss_num(String chassiss_num) {
		this.chassiss_num = chassiss_num;
	}

	public Long getCar_team_id() {
		return car_team_id;
	}

	public void setCar_team_id(Long car_team_id) {
		this.car_team_id = car_team_id;
	}

	public Long getSales_status() {
		return sales_status;
	}

	public void setSales_status(Long sales_status) {
		this.sales_status = sales_status;
	}

	public Integer getDel_flag() {
		return del_flag;
	}

	public void setDel_flag(Integer del_flag) {
		this.del_flag = del_flag;
	}

	public String getSales_date() {
		return sales_date;
	}

	public void setSales_date(String sales_date) {
		this.sales_date = sales_date;
	}

	public Integer getMb_sales_status() {
		return mb_sales_status;
	}

	public void setMb_sales_status(Integer mb_sales_status) {
		this.mb_sales_status = mb_sales_status;
	}

	public String getMb_sales_date() {
		return mb_sales_date;
	}

	public void setMb_sales_date(String mb_sales_date) {
		this.mb_sales_date = mb_sales_date;
	}

}
