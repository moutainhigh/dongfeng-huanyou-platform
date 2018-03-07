package com.navinfo.opentsp.dongfeng.system.handler.nonf9;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.ExportNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.system.pojo.NonF9VehiclePojo;
import com.navinfo.opentsp.dongfeng.system.service.INonF9VehicleService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 导出非F9车辆
 *
 * @author tushenghong
 * @version 1.0
 * @date 2017-11-28
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ExportNonF9VehicleHandler extends ValidateTokenAndReSetAbstracHandler<ExportNonF9VehicleCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportNonF9VehicleHandler.class);

    public ExportNonF9VehicleHandler() {
        super(ExportNonF9VehicleCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportNonF9VehicleHandler(Class<ExportNonF9VehicleCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }


    @Autowired
    private INonF9VehicleService nonF9VehicleService;
    @Autowired
    private IReportService reportService;

    @Autowired
    private PropertyConfig reportProperty;

    @Override
    protected HttpCommandResultWithData businessHandle(ExportNonF9VehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        boolean isQueryAll = (command.getDownloadFlag() == ReportConfigConstants.EXPORT_ALL_FLAG);
        PagingInfo pagingInfo = nonF9VehicleService.queryNonF9Vehicle(command, !isQueryAll);
        String metadata = command.getMetadata();
        ReportConfig config = new ReportConfig("非F9车辆信息");
        if (!StringUtils.isEmpty(metadata)) {
            JSONArray jsonArray = JSONArray.fromObject(metadata);
            try {
                config.setColumn(jsonArray, NonF9VehiclePojo.class);
            } catch (BaseServiceException e) {
                e.printStackTrace();
            }
        } else {
            config.setColumn("", "序号", 0);
            config.setColumn("chassisNum", "底盘号", 1, "", 16 * 256 + "");
            config.setColumn("carModelCode", "车架号", 2, "", 26 * 256 + "");
            config.setColumn("carModelCode", "车型码", 3, "", 26 * 256 + "");
            config.setColumn("manufactureDate", "下线(制造日期)", 4, "", 16 * 256 + "");
            config.setColumn("businessName", "所属客户", 5, "", 40 * 256 + "");
            config.setColumn("businessCode", "客户证件号", 6, "", 40 * 256 + "");
            config.setColumn("invoiceNumber", "客户发票号", 7, "", 12 * 256 + "");
            config.setColumn("carTypeStr", "车辆型号", 8, "", 26 * 256 + "");
            config.setColumn("engineCode", "发动机号", 9, "", 22 * 256 + "");
            config.setColumn("engineTypeStr", "发动机型号", 10, "", 22 * 256 + "");
            config.setColumn("enginePower", "发动机功率", 11, "", 14 * 256 + "");
            config.setColumn("carModelAnnouncement", "公告车型", 12, "", 26 * 256 + "");
            config.setColumn("aakSaleDate", "AAK日期", 13, "", 16 * 256 + "");
        }
        return reportService.downLoad(pagingInfo.getList(), command, config, reportProperty);
    }


}