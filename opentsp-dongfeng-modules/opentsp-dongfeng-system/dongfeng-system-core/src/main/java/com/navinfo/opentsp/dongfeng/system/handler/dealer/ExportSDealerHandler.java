package com.navinfo.opentsp.dongfeng.system.handler.dealer;

import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.system.commands.dealer.ExportSDealerCommand;
import com.navinfo.opentsp.dongfeng.system.constant.PropertyConfig;
import com.navinfo.opentsp.dongfeng.system.pojo.SDealerPojo;
import com.navinfo.opentsp.dongfeng.system.service.DealerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportSDealerHandler extends ValidateTokenAndReSetAbstracHandler<ExportSDealerCommand, HttpCommandResultWithData> {

    protected static final Logger logger = LoggerFactory.getLogger(ExportSDealerHandler.class);

    @Resource
    private DealerService dealerService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private PropertyConfig reportProperty;

    public ExportSDealerHandler() {
        super(ExportSDealerCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportSDealerHandler(Class<ExportSDealerCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportSDealerCommand command) {
        HttpCommandResultWithData rs = dealerService.queryDealerPage(command,true);
        List<SDealerPojo> list = null;
        if(rs.getData() instanceof PagingInfo){
            PagingInfo pagingInfo = (PagingInfo)rs.getData();
            list = pagingInfo.getList();
        }else{
            list = (List<SDealerPojo>)rs.getData();
        }
        command.setDownloadFlag(ReportConfigConstants.EXPORT_ALL_FLAG);
        //半径米换千米
        for(SDealerPojo kmPojo :list){
            kmPojo.setEnableRadius(kmPojo.getEnableRadius()/1000);
            kmPojo.setLockRadius(kmPojo.getLockRadius()/1000);
        }
        //导出数据
        return reportService.downLoad(list, command, ReportConfigConstants.dealersConfig, reportProperty);
    }
}