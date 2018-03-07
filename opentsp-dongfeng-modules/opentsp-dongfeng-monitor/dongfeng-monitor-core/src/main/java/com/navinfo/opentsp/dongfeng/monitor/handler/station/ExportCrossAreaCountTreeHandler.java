package com.navinfo.opentsp.dongfeng.monitor.handler.station;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.station.ExportCrossAreaCountTreeCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.CrossAreaCountPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCrossAreaCountTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.station.IQueryCrossAreaCountTreeService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fengwm
 * @version 1.0
 * @date 2017-03-16
 * @modify
 * @copyright Navi Tsp
 */

@Component
public class ExportCrossAreaCountTreeHandler extends ValidateTokenAndReSetAbstracHandler<ExportCrossAreaCountTreeCommand, HttpCommandResultWithData> {

    @Autowired
    IQueryCrossAreaCountTreeService iQueryCrossAreaCountTreeService;
    @Value("${report.module.path}")
    private String path;

    @Value("${fservice.url}")
    private String fservice;

    @Value("${download.record.size:3000}")
    private String records;
    protected static final Logger logger = LoggerFactory.getLogger(ExportCrossAreaCountTreeHandler.class);

    public ExportCrossAreaCountTreeHandler() {
        super(ExportCrossAreaCountTreeCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportCrossAreaCountTreeHandler(Class<ExportCrossAreaCountTreeCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportCrossAreaCountTreeCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        List<QueryCrossAreaCountTreePojo> crossTrees = (List<QueryCrossAreaCountTreePojo>)iQueryCrossAreaCountTreeService.queryCrossAreaCountTree(command).getData();
        List<CrossAreaCountPojo> cacList = new ArrayList<CrossAreaCountPojo>();
        if(!ListUtil.isEmpty(crossTrees)){
            for(QueryCrossAreaCountTreePojo tree : crossTrees){
                if(!tree.getIsParent()){
                    List<CrossAreaCountPojo> list = tree.getCarList();
                    for(int i = 0; i<list.size(); i++){
                        cacList.add(list.get(i));
                    }
                }
            }
        }
        int number = Integer.valueOf(records);
        if (cacList.size() >= number) {

            if (StringUtil.isNull(command.getEmail())&& StringUtil.isNull(command.getUserInfor().getEmail())) {
                result = new HttpCommandResultWithData();

                result.setResultCode(ReturnCode.SERVER_ERROR.code());
                result.setMessage("Email can not be null . ");
                return result;
            }else{
                if(!StringUtil.isNull(command.getUserInfor().getEmail())){
                    command.setEmail(command.getUserInfor().getEmail());
                }
            }
            // path表示模板在文件服务器的位置.
            iQueryCrossAreaCountTreeService.asyncDonwload(fservice, command, path,cacList);

            result = new HttpCommandResultWithData();
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), records, command.getEmail()));
        } else {
            JSONObject uploadResult = iQueryCrossAreaCountTreeService.download(fservice, cacList, command, path);
            result = new HttpCommandResultWithData();
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage("文件下载成功！");
            result.setData(uploadResult.getJSONObject("data").getString("fullPath"));
        }
        return result;
    }
}