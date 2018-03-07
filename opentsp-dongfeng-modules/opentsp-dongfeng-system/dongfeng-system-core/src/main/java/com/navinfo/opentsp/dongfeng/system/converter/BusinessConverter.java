package com.navinfo.opentsp.dongfeng.system.converter;

import com.navinfo.opentsp.dongfeng.common.exception.BaseConveterException;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.BusinessOutdto;
import com.navinfo.opentsp.dongfeng.system.entity.BusinessEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.BusinessPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaocy on 2017/03/14.
 */
public class BusinessConverter {

    public static BusinessOutdto entityToDto (BusinessEntity entity) throws BaseConveterException {
        BusinessOutdto dto = new BusinessOutdto();
        try {
            CopyPropUtil.copyProp(entity, dto);
        } catch (Exception e) {
        	throw new BaseConveterException(e);
        }
        return dto;
    }

    public static List<BusinessOutdto> listEntityToDto (List<BusinessEntity> list) throws BaseConveterException {

        List<BusinessOutdto> listDto = new ArrayList<BusinessOutdto>();
    	try {
    		if (list != null) {
                for (BusinessEntity entity: list) {
                    BusinessOutdto dto = entityToDto(entity);
                    listDto.add(dto);
                }
            }
    	} catch (BaseConveterException e) {
    		throw e;
    	}
        return listDto;
    }
    public static BusinessOutdto pojoToDto (BusinessPojo pojo) throws BaseConveterException{
        BusinessOutdto dto = new BusinessOutdto();
        
        try {
        	//客户类型特殊处理
            dto.setCType(pojo.getCtype());
            CopyPropUtil.copyProp(pojo, dto);
            	
        } catch (Exception e) {
        	throw new BaseConveterException(e);
        }
        
        return dto;
    }

    public static List<BusinessOutdto> listPojoToDto (List<BusinessPojo> list) throws BaseConveterException{
        List<BusinessOutdto> listDto = new ArrayList<BusinessOutdto>();
        
        try {

            if (list != null) {
                for (BusinessPojo pojo: list) {
                    BusinessOutdto dto = pojoToDto(pojo);
                    listDto.add(dto);
                }
            }
        } catch (BaseConveterException e) {
        	throw e;
        }
        return listDto;
    }
}
