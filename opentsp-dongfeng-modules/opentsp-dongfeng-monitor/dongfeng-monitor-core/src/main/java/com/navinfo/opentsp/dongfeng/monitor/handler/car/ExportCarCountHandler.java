package com.navinfo.opentsp.dongfeng.monitor.handler.car;

import com.navinfo.opentsp.dongfeng.common.handler.ValidateTokenAndReSetAbstracHandler;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.CopyPropUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.ExportCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.car.QueryCarCountCommand;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.CrossAreaCountPojo;
import com.navinfo.opentsp.dongfeng.monitor.pojo.car.QueryCrossAreaCountTreePojo;
import com.navinfo.opentsp.dongfeng.monitor.service.car.IQueryCarCountService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 导出车次详情
 *
 * @wenya
 * @create 2017-04-18 9:40
 **/
@Component
public class ExportCarCountHandler extends ValidateTokenAndReSetAbstracHandler<ExportCarCountCommand, HttpCommandResultWithData> {
    @Autowired
    private IQueryCarCountService queryCarCountService;

    @Value("${report.module.path}")
    private String path;

    @Value("${fservice.url}")
    private String fservice;

    @Value("${download.record.size}")
    private String records;

    public ExportCarCountHandler() {
        super(ExportCarCountCommand.class, HttpCommandResultWithData.class);
    }

    protected ExportCarCountHandler(Class<ExportCarCountCommand> commandType, Class<HttpCommandResultWithData> resultType) {
        super(commandType, resultType);
    }

    @Override
    protected HttpCommandResultWithData businessHandle(ExportCarCountCommand command) {
        HttpCommandResultWithData result = null;
        QueryCarCountCommand queryCarCountCommand = new QueryCarCountCommand();
        try {
            CopyPropUtil.copyProp(command, queryCarCountCommand);
            queryCarCountCommand.setUserInfor(command.getUserInfor());
            queryCarCountCommand.setToken(command.getToken());
            queryCarCountCommand.setRequest(command.getRequest());
            queryCarCountCommand.setPage_number(command.getPage_number());
            queryCarCountCommand.setPage_size(command.getPage_size());
        } catch (Exception e) {
            logger.error(e.getMessage(),e);
        }
        List<QueryCrossAreaCountTreePojo> reuslt = (List<QueryCrossAreaCountTreePojo>) queryCarCountService.queryCarCount(queryCarCountCommand).getData();
        List<CrossAreaCountPojo> cacList = new ArrayList<CrossAreaCountPojo>();
        for (QueryCrossAreaCountTreePojo tree : reuslt) {
            if (!tree.getIsParent()) {
                List<CrossAreaCountPojo> list = tree.getCarList();
                for (int i = 0; i < list.size(); i++) {
                    cacList.add(list.get(i));
                }
            }
        }
        int number = Integer.valueOf(records);
        if (cacList.size() >= number) {

            if (StringUtil.isNull(command.getMail())) {
                result = new HttpCommandResultWithData();

                result.setResultCode(ReturnCode.SERVER_ERROR.code());
                result.setMessage("Email can not be null . ");
                return result;
            }
            // path表示模板在文件服务器的位置.
            queryCarCountService.asyncDonwload(fservice, command, path);

            result = new HttpCommandResultWithData();
            result.setMessage("请登录邮箱查看下载结果 . ");
        } else {
            JSONObject uploadResult = queryCarCountService.download(fservice, cacList, command, path);
            result = new HttpCommandResultWithData();
            result.setData(uploadResult.getJSONObject("data").getString("fullPath"));
            result.setMessage("导出车次成功");
        }
        return result;
    }
}
