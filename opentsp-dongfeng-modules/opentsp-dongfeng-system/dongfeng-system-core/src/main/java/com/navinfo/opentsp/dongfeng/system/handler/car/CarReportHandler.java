package com.navinfo.opentsp.dongfeng.system.handler.car;

import com.navinfo.opentsp.dongfeng.common.cache.GpsCache;
import com.navinfo.opentsp.dongfeng.common.constant.ReportConstants;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.car.DownloadCarCommand;
import com.navinfo.opentsp.dongfeng.system.commands.car.QueryCarCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.outDto.VechicleOutdto;
import com.navinfo.opentsp.dongfeng.system.converter.DownloadCarConverter;
import com.navinfo.opentsp.dongfeng.system.service.ICarService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.List;

@Component
public class CarReportHandler extends
    ValidateTokenAndReSetAbstracHandler<DownloadCarCommand, HttpCommandResultWithData>
{
    
    @Resource
    private ICarService carService;
    
    @Value("${report.module.path}")
    private String path;
    
    @Value("${fservice.url}")
    private String fservice;
    
    @Value("${download.carrecord.size}")
    private String records;
    
    @Resource
    private GpsCache gpsCache;
    
    public CarReportHandler()
    {
        super(DownloadCarCommand.class, HttpCommandResultWithData.class);
    }
    
    protected CarReportHandler(Class<DownloadCarCommand> commandType, Class<HttpCommandResultWithData> resultType)
    {
        super(commandType, resultType);
    }
    
    @Override
    protected HttpCommandResultWithData businessHandle(DownloadCarCommand command)
    {
        HttpCommandResultWithData result = null;
        
        command.setSheetName("车辆列表");
        QueryCarCommand queryVechicleCommand =
            DownloadCarConverter.downloadVechicleCommand2QueryVechicleCommand(command);
        // vechicleService.queryVechicle(command , UserTypeConstant.CAR_FACTORY);
        
        try {

            // 下载当前分页信息
            if (command.getDownloadFlag() == ReportConstants.FileExportFlg.current.getValue())
            {
                PagingInfo<VechicleOutdto> page = null;
                if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType())
                {
                    page = carService.queryCar(queryVechicleCommand, UserTypeConstant.SYSTEM_ADMIN);
                }
                
                if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType())
                {
                    page = carService.queryCar(queryVechicleCommand, UserTypeConstant.CAR_FACTORY);
                }
                
                if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType())
                {
                    page = carService.queryCar(queryVechicleCommand, UserTypeConstant.BUSINESS);
                }

                if (UserTypeConstant.SERVICE_STATION.getCode() == command.getUserInfor().getType()) {
                    page = carService.queryCar(queryVechicleCommand, UserTypeConstant.SERVICE_STATION);
                }

                JSONObject uploadResult = carService.download(fservice, page.getList(), command, path);
                
                result = new HttpCommandResultWithData();
                result.setResultCode(ReturnCode.OK.code());
                
                if (null != uploadResult && !org.springframework.util.StringUtils.isEmpty(uploadResult.getString("data")))
                {
                    JSONObject uploadJson = JSONObject.fromObject(uploadResult.getString("data"));
                    result.setData(uploadJson);
                }
                else
                {
                    result.setData(new JSONObject());
                }
            }
            
            // 下载全部车辆信息
            if (command.getDownloadFlag() == ReportConstants.FileExportFlg.all.getValue())
            {
                int number = StringUtil.isEmpty(records) ? 3000 : Integer.valueOf(records);
                
                if (null == command.getTotalNumber())
                {
                    
                    result = new HttpCommandResultWithData();
                    
                    result.setResultCode(ReturnCode.SERVER_ERROR.code());
                    result.setMessage("必须传递记录总条数 .");
                    result.setData(new JSONObject());
                    return result;
                }
                
                if (command.getTotalNumber() >= number)
                {
                    
                    if (StringUtils.isEmpty(command.getEmail()))
                    {
                        result = new HttpCommandResultWithData();
                        
                        result.setResultCode(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.code());
                        result.setMessage(MessageFormat.format(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.message(), number));
                        result.setData(new JSONObject());
                        return result;
                    }
                    
                    // path表示模板在文件服务器的位置.
                    carService.asyncDonwload(fservice, command, path);
                    
                    result = new HttpCommandResultWithData();
                    result.setResultCode(ReturnCode.OK.code());
                    result.setData(new JSONObject());
                    result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), number , command.getEmail()));
                }
                else
                {
                    List<VechicleOutdto> list = null;
                    if (UserTypeConstant.SYSTEM_ADMIN.getCode() == command.getUserInfor().getType())
                    {
                        list = carService.queryCarList(queryVechicleCommand, UserTypeConstant.SYSTEM_ADMIN);
                    }
                    
                    if (UserTypeConstant.CAR_FACTORY.getCode() == command.getUserInfor().getType())
                    {
                        list = carService.queryCarList(queryVechicleCommand, UserTypeConstant.CAR_FACTORY);
                    }
                    
                    if (UserTypeConstant.BUSINESS.getCode() == command.getUserInfor().getType())
                    {
                        list = carService.queryCarList(queryVechicleCommand, UserTypeConstant.BUSINESS);
                    }
                    
                    JSONObject uploadResult = carService.download(fservice, list, command, path);
                    result = new HttpCommandResultWithData();
                    result.setResultCode(ReturnCode.OK.code());
                    
                    JSONObject uploadJson = JSONObject.fromObject(uploadResult.getString("data"));
                    result.setData(uploadJson);
                }
            }
        } catch (BaseServiceException e) {
        	logger.error("CarReportHandler ===> download : " , e);
        	result = new HttpCommandResultWithData();
        	result.setResultCode(ReturnCode.SERVER_ERROR.code());
        	result.setMessage(e.getExceptionMessage());
        }
        
        return result;
        
    }
}
