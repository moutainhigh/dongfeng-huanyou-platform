package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.cache.GpsInfoCache;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.CarOutdto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.VechicleOutdto;
import com.navinfo.opentsp.dongfeng.system.converter.CarConverter;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Component
public class QueryCarHandler extends ValidateTokenAndReSetAbstracHandler<QueryCarCommand, HttpCommandResultWithData> {

    @Resource
    private ICarService carService;

    @Autowired
    private GpsInfoCache gpsCache;

    public QueryCarHandler() {
        super(QueryCarCommand.class, HttpCommandResultWithData.class);
    }

    protected QueryCarHandler(Class<QueryCarCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(QueryCarCommand command) {

        HttpCommandResultWithData result = null;

        try {
        	PagingInfo<VechicleOutdto> page = null;//vechicleService.queryVechicle(command , UserTypeConstant.CAR_FACTORY);
            if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType()) {
                result = new HttpCommandResultWithData();
                page = carService.queryCar(command, UserTypeConstant.SYSTEM_ADMIN);
                result.setResultCode(ReturnCode.OK.code());
            }

            if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType()) {
                result = new HttpCommandResultWithData();
                page = carService.queryCar(command, UserTypeConstant.CAR_FACTORY);
                result.setResultCode(ReturnCode.OK.code());
            }

            if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType()) {
                result = new HttpCommandResultWithData();
                page = carService.queryCar(command, UserTypeConstant.BUSINESS);
                result.setResultCode(ReturnCode.OK.code());
            }

            if (UserTypeConstant.SERVICE_STATION.getCode() == command.getUserInfor().getType()) {
                result = new HttpCommandResultWithData();
                page = carService.queryCar(command, UserTypeConstant.SERVICE_STATION);
                result.setResultCode(ReturnCode.OK.code());
            }
        	
            List<CarOutdto> resultList = new ArrayList<CarOutdto>();
            if (null != page.getList() && !page.getList().isEmpty()) {

                carService.fillLocationData(page.getList());
                for (VechicleOutdto vechicle : page.getList()) {
                    resultList.add(CarConverter.convertVechicleOutdto2CarOutdto(vechicle));
                }
            }

            PagingInfo<CarOutdto> carOutPage = new PagingInfo<CarOutdto>();

            carOutPage.setPage_total(page.getPage_total());
            carOutPage.setTotal(page.getTotal());
            carOutPage.setList(resultList);
            result.setData(carOutPage);
        } catch (BaseServiceException e) {
        	
        	logger.error("QueryCarHandler ====> querycar : " , e);
        	result = new HttpCommandResultWithData();
        	result.setResultCode(ReturnCode.SERVER_ERROR.code());
        	result.setMessage(e.getExceptionMessage());
        }

        return result;
    }

}
