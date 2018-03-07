package com.navinfo.opentsp.dongfeng.system.commands.dto.inDto;

/**
 * 销售区域Bean
 *
 * @author Sunyu
 */
public class DistrictIndto {

    /**
     * 销售区域ID
     */
    private java.math.BigInteger tid;
    /**
     * 分区服务编码
     */
    private Integer district;
    /**
     * 分组名称
     */
    private String tname;
    /**
     * 是否是公司
     */
    private Integer ttype;
    /**
     * 分组创建时间
     */
    private Long tdate;
    /**
     * 创建用户
     */
    private String taccountName;
    /**
     * 上级分组
     */
    private Long parentId;
    /**
     * 联系人
     */
    private String tlinkman;
    /**
     * 联系电话
     */
    private String tlinktel;
    /**
     * 描述
     */
    private String tdesc;
    /**
     * 公司名称
     */
    private String companyName;
    /**
     * 公司经营范围
     */
    private String companyBusinessScope;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 邮编
     */
    private String companyCode;
    /**
     * 公司电话
     */
    private String companyTel;
    /**
     * 传真
     */
    private String companyFax;
    /**
     * 公司联系人
     */
    private String companyLinkman;
    /**
     * 公司联系人电话
     */
    private String companyLinktel;
    /**
     * 公司营业执照号
     */
    private String licenceNumber;
    /**
     * 逻辑删除标记(1:删除，0：正常，default：0)
     */
    private Byte delFlag;
    /**
     * 经销商编码
     */
    private String dealerCode;
    /**
     * 以下为新增字段 2016-4-19 10:09:06
     * 经营品牌
     */
    private String manageBrand;
    /**
     * 经销商经度
     */
    private Double teamLat;
    /**
     * 经销商纬度
     */
    private Double teamLon;
    /**
     * 激活半径（M）
     */
    private Integer enableRadius;
    /**
     * 锁车半径（M）
     */
    private Integer lockRadius;
    /**
     * 二级网点信息
     */
    private String secdNameLatLon;
    /**
     * 图片地址
     */
    private String teamPicture;
    /**
     * 所在区域
     */
    private String pname;

    public java.math.BigInteger getTid() {
        return tid;
    }

    public void setTid(java.math.BigInteger tid) {
        this.tid = tid;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public Integer getTtype() {
        return ttype;
    }

    public void setTtype(Integer ttype) {
        this.ttype = ttype;
    }

    public Long getTdate() {
        return tdate;
    }

    public void setTdate(Long tdate) {
        this.tdate = tdate;
    }

    public String getTaccountName() {
        return taccountName;
    }

    public void setTaccountName(String taccountName) {
        this.taccountName = taccountName;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getTlinkman() {
        return tlinkman;
    }

    public void setTlinkman(String tlinkman) {
        this.tlinkman = tlinkman;
    }

    public String getTlinktel() {
        return tlinktel;
    }

    public void setTlinktel(String tlinktel) {
        this.tlinktel = tlinktel;
    }

    public String getTdesc() {
        return tdesc;
    }

    public void setTdesc(String tdesc) {
        this.tdesc = tdesc;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyBusinessScope() {
        return companyBusinessScope;
    }

    public void setCompanyBusinessScope(String companyBusinessScope) {
        this.companyBusinessScope = companyBusinessScope;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyFax() {
        return companyFax;
    }

    public void setCompanyFax(String companyFax) {
        this.companyFax = companyFax;
    }

    public String getCompanyLinkman() {
        return companyLinkman;
    }

    public void setCompanyLinkman(String companyLinkman) {
        this.companyLinkman = companyLinkman;
    }

    public String getCompanyLinktel() {
        return companyLinktel;
    }

    public void setCompanyLinktel(String companyLinktel) {
        this.companyLinktel = companyLinktel;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public String getDealerCode() {
        return dealerCode;
    }

    public void setDealerCode(String dealerCode) {
        this.dealerCode = dealerCode;
    }

    public String getManageBrand() {
        return manageBrand;
    }

    public void setManageBrand(String manageBrand) {
        this.manageBrand = manageBrand;
    }

    public Double getTeamLat() {
        return teamLat;
    }

    public void setTeamLat(Double teamLat) {
        this.teamLat = teamLat;
    }

    public Double getTeamLon() {
        return teamLon;
    }

    public void setTeamLon(Double teamLon) {
        this.teamLon = teamLon;
    }

    public Integer getEnableRadius() {
        return enableRadius;
    }

    public void setEnableRadius(Integer enableRadius) {
        this.enableRadius = enableRadius;
    }

    public Integer getLockRadius() {
        return lockRadius;
    }

    public void setLockRadius(Integer lockRadius) {
        this.lockRadius = lockRadius;
    }

    public String getSecdNameLatLon() {
        return secdNameLatLon;
    }

    public void setSecdNameLatLon(String secdNameLatLon) {
        this.secdNameLatLon = secdNameLatLon;
    }

    public String getTeamPicture() {
        return teamPicture;
    }

    public void setTeamPicture(String teamPicture) {
        this.teamPicture = teamPicture;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }
}