package com.navinfo.opentsp.dongfeng.common.pojo;

import java.math.BigInteger;
import java.util.List;

public class HyBasicdataPojo implements java.io.Serializable{
    /**
	 * 数据字典值
	 * @author baitao
	 * @date 2016.7.14
	 */
	private static final long serialVersionUID = 0L;
	// 主键
	private BigInteger dataId;
	// 编码
    private String dataCode;
    // 值
    private String dataValue;
    // datetype主键
    private Integer dataType;


	public BigInteger getDataId() {
        return dataId;
    }

    public void setDataId(BigInteger dataId) {
        this.dataId = dataId;
    }

    public String getDataCode() {
        return dataCode;
    }

    public void setDataCode(String dataCode) {
        this.dataCode = dataCode;
    }

    public String getDataValue() {
        return dataValue;
    }

    public void setDataValue(String dataValue) {
        this.dataValue = dataValue == null ? null : dataValue.trim();
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }
    
    /**
	 * 根据编码找数据字典对象
	 * @param list
	 * @param code
	 * @return
	 */
	public static HyBasicdataPojo getValueByCode(List<HyBasicdataPojo> list,String code){
		for(HyBasicdataPojo hyBasicdata : list){
			if(hyBasicdata.getDataCode().equals(code)){
				return hyBasicdata;
			}
		}
		return null;
	}
	
}