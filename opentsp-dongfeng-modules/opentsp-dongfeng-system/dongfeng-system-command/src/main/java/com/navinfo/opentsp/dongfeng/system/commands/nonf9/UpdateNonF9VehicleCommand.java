package com.navinfo.opentsp.dongfeng.system.commands.nonf9;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 更新非F9车辆
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
public class UpdateNonF9VehicleCommand extends BaseCommand<HttpCommandResultWithData> {
    /**
     * 车辆ID
     */
    private Long vehicleId;
    /**
     * 结构号
     */
    @NotBlank(message = "车架号 不能为空", groups = {GroupCommand.class})
    private String vin;
    /**
     * 底盘号
     */
    @NotBlank(message = "底盘号 不能为空", groups = {GroupCommand.class})
    private String chassisNum;
    /**
     * 车型码
     */
    @NotBlank(message = "车型码 不能为空", groups = {GroupCommand.class})
    private String carModelCode;

    /**
     * 车辆类型
     */
    @NotBlank(message = "车辆类型 不能为空", groups = {GroupCommand.class})
    private String carType;
    /**
     * 发动机类型
     */
    private String engineType;
    /**
     * 发动机号
     */
    private String engineCode;
    /**
     * 发动机功率
     */
    private String enginePower;

    /**
     * 制造日期
     */
    private String manufactureDate;
    /**
     * 公告车型
     */
    private String carModelAnnouncement;
    /**
     * aak销售日期
     */
    @NotBlank(message = "AAK销售日期 不能为空", groups = {GroupCommand.class})
    private String aakSaleDate;
    /**
     * 所属客户
     */
    @NotBlank(message = "所属客户 不能为空", groups = {GroupCommand.class})
    private String businessId;
    /**
     * 发票号
     */
    private String invoiceNumber;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getChassisNum() {
        return chassisNum;
    }

    public void setChassisNum(String chassisNum) {
        this.chassisNum = chassisNum;
    }

    public String getCarModelCode() {
        return carModelCode;
    }

    public void setCarModelCode(String carModelCode) {
        this.carModelCode = carModelCode;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getCarModelAnnouncement() {
        return carModelAnnouncement;
    }

    public void setCarModelAnnouncement(String carModelAnnouncement) {
        this.carModelAnnouncement = carModelAnnouncement;
    }

    public String getAakSaleDate() {
        return aakSaleDate;
    }

    public void setAakSaleDate(String aakSaleDate) {
        this.aakSaleDate = aakSaleDate;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

}
