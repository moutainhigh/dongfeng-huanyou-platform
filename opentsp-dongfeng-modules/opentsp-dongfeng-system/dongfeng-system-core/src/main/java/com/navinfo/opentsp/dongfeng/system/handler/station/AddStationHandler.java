package com.navinfo.opentsp.dongfeng.system.handler.station;

import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.OperateLogConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IOperateLogService;
import com.navinfo.opentsp.dongfeng.system.commands.station.AddStationCommand;
import com.navinfo.opentsp.dongfeng.system.service.IStationService;
import com.navinfo.opentsp.dongfeng.system.service.ISyncService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class AddStationHandler extends ValidateTokenAndReSetAbstracHandler<AddStationCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(AddStationHandler.class);

    public AddStationHandler() {
        super(AddStationCommand.class, HttpCommandResultWithData.class);
    }

    protected AddStationHandler(Class<AddStationCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Autowired
    private IStationService stationService;
    @Resource
    private IOperateLogService operateLogService;
    @Autowired
    private ISyncService syncService;

    /**
     * 1.主营业务-》服务类型 -》字典58  SELECT * FROM HY_BASICDATA WHERE DATA_TYPE = 42
     * 1.主营业务-》服务类型-》备件-》字典42
     * 1.主营业务-》服务类型-》服务-》服务内容-》字典41
     * 1.主营业务-》服务类型-》服务-》提升工具-》字典59
     *
     * @param command
     * @return
     */
    @Override
    protected HttpCommandResultWithData businessHandle(AddStationCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            result = stationService.addStation(command);
            //新增成功，同步到云端
            if (result.getResultCode() == ReturnCode.OK.code()) {
                String stationId = (String) result.getData();
                String province = command.getBasicInfoBean().getProvince();
                String city = command.getBasicInfoBean().getCity();
                syncService.asyncStation(stationId, province, city, Constants.SyncOperateEnum.ADD.value());
                result.setData(null);
            }
        } catch (Exception e) {
            logger.error("addStation failed", e);
            result.fillResult(ReturnCode.ADD_STATION_FAILED);
        }
        operateLogService.addOperateLog(command, OperateLogConstants.OperateTypeEnum.ADD, OperateLogConstants.OperateContentPrefixEnum.STATION.getValue() + command.getBasicInfoBean().getStationCode(), result);
        return result;
    }

}