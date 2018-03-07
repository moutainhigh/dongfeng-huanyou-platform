package com.navinfo.opentsp.dongfeng.report.pojo.market;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-06-06
 * @modify
 * @copyright Navi Tsp
 */
public class ImportScanCodePojo {

    private String id;//序号

    @NotNull(message = "VIN码不能为空")
    @NotBlank(message = "VIN码不能为空")
    @Length(min = 17, max = 17, message = "VIN为17位字符,且后8位与车辆底盘号一致")
    private String vinCode;

    @NotNull(message = "制造公司代码不能为空")
    @NotBlank(message = "制造公司代码不能为空")
    private String companyCode;

    @NotNull(message = "生产工厂代码不能为空")
    @NotBlank(message = "生产工厂代码不能为空")
    private String productionFactoryCode;

    @NotNull(message = "班次代码不能为空")
    @NotBlank(message = "班次代码不能为空")
    private String shiftCode;

    @NotNull(message = "销售资源库代码不能为空")
    @NotBlank(message = "销售资源库代码不能为空")
    private String sellRepositCode;

    @NotNull(message = "物理库代码不能为空")
    @NotBlank(message = "物理库代码不能为空")
    private String physicalLibraryCode;

    @NotNull(message = "仓库库区代码不能为空")
    @NotBlank(message = "仓库库区代码不能为空")
    private String warehouseCode;

    @NotNull(message = "质检员代码不能为空")
    @NotBlank(message = "质检员代码不能为空")
    private String qualityCheckerCode;

    @NotNull(message = "倒车司机代码不能为空")
    @NotBlank(message = "倒车司机代码不能为空")
    private String reverseDriverCode;

    @NotNull(message = "操作员编号不能为空")
    @NotBlank(message = "操作员编号不能为空")
    private String operatorNum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVinCode() {
        return vinCode;
    }

    public void setVinCode(String vinCode) {
        this.vinCode = vinCode;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getProductionFactoryCode() {
        return productionFactoryCode;
    }

    public void setProductionFactoryCode(String productionFactoryCode) {
        this.productionFactoryCode = productionFactoryCode;
    }

    public String getShiftCode() {
        return shiftCode;
    }

    public void setShiftCode(String shiftCode) {
        this.shiftCode = shiftCode;
    }

    public String getSellRepositCode() {
        return sellRepositCode;
    }

    public void setSellRepositCode(String sellRepositCode) {
        this.sellRepositCode = sellRepositCode;
    }

    public String getPhysicalLibraryCode() {
        return physicalLibraryCode;
    }

    public void setPhysicalLibraryCode(String physicalLibraryCode) {
        this.physicalLibraryCode = physicalLibraryCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getQualityCheckerCode() {
        return qualityCheckerCode;
    }

    public void setQualityCheckerCode(String qualityCheckerCode) {
        this.qualityCheckerCode = qualityCheckerCode;
    }

    public String getReverseDriverCode() {
        return reverseDriverCode;
    }

    public void setReverseDriverCode(String reverseDriverCode) {
        this.reverseDriverCode = reverseDriverCode;
    }

    public String getOperatorNum() {
        return operatorNum;
    }

    public void setOperatorNum(String operatorNum) {
        this.operatorNum = operatorNum;
    }

    @Override
    public String toString() {
        return "ImportScanCodePojo{" +
                "id='" + id + '\'' +
                ", vinCode='" + vinCode + '\'' +
                ", companyCode='" + companyCode + '\'' +
                ", productionFactoryCode='" + productionFactoryCode + '\'' +
                ", shiftCode='" + shiftCode + '\'' +
                ", sellRepositCode='" + sellRepositCode + '\'' +
                ", physicalLibraryCode='" + physicalLibraryCode + '\'' +
                ", warehouseCode='" + warehouseCode + '\'' +
                ", qualityCheckerCode='" + qualityCheckerCode + '\'' +
                ", reverseDriverCode='" + reverseDriverCode + '\'' +
                ", operatorNum='" + operatorNum + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + ((vinCode == null) ? 0 : vinCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ImportScanCodePojo)) {
            return false;
        }

        final ImportScanCodePojo scanCodePojo = (ImportScanCodePojo) obj;
        return this.getVinCode().equals(scanCodePojo.getVinCode());
    }
}
