package com.navinfo.opentsp.dongfeng.monitor.service.log.impl;

import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.constant.Constants;
import com.navinfo.opentsp.dongfeng.common.constant.TcpCommandEnum;
import com.navinfo.opentsp.dongfeng.common.constant.UserTypeConstant;
import com.navinfo.opentsp.dongfeng.common.pojo.TerminalLogPojo;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IBaseDataService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.ExcelUtil;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.ExportTerminalLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.commands.log.QueryTerminalLogCommand;
import com.navinfo.opentsp.dongfeng.monitor.converter.log.TerminalLogConverter;
import com.navinfo.opentsp.dongfeng.monitor.service.log.IQueryTerminalLogService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-20
 * @modify
 * @copyright Navi Tsp
 */
@Service
@EnableAsync
public class QueryTerminalLogService extends BaseService implements IQueryTerminalLogService {
    protected static final Logger logger = LoggerFactory.getLogger(QueryTerminalLogService.class);
    @Autowired
    private IBaseDataService baseDataService;

    @Autowired
    private MailClient mailClient;

    @Override
    public PagingInfo queryLogOfTerminal(QueryTerminalLogCommand command, boolean paging) {
        String searchLogName = "";
        if (!StringUtil.isEmpty(command.getCommandType()) && !command.getCommandType().equals("-1")) {
            searchLogName = TcpCommandEnum.TERMINAL_SETTING.getMsg();
        }
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("chassisNo", command.getChassisNo());
        param.put("simNo", command.getSimNo());
        param.put("terminalId", command.getTerminalId());
        param.put("operateUser", command.getOperateUser());
        if (StringUtil.isNotEmpty(command.getCommandDir()) && !command.getCommandDir().equals("-1")) {
            param.put("commandDir", command.getCommandDir());
        }
        param.put("logName", searchLogName);
        if (StringUtil.isNotEmpty(command.getCommandStatus()) && !command.getCommandStatus().equals("-4")) {
            param.put("commandStatus", command.getCommandStatus());
        }
        if (StringUtil.isNotEmpty(command.getBeginTime()) && StringUtil.isNotEmpty(command.getEndTime())) {
            param.put("beginTime", DateUtil.strTimeChangeLong(command.getBeginTime()));
            param.put("endTime", DateUtil.strTimeChangeLong(command.getEndTime()));
        }
        param.put("accountId", command.getUserInfor().getUserId());
        param.put("accountType", command.getUserInfor().getType());
        PagingInfo<TerminalLogPojo> pageInfo = new PagingInfo<>();
        String cars = queryVehicleOfUser(command.getUserInfor().getUserId(), command.getUserInfor().getType());
        if (!StringUtil.isEmpty(cars)) {
            cars = Constants.NO_REPLACE_SINGLE_QUOTATION +cars;
            String temp = cars.replaceAll(",", "','");
            param.put("cars", temp);
            if (paging) {
                pageInfo = dao.sqlPagelFind("searchTerminalLogByParam", param, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), TerminalLogPojo.class);
            } else {
                List<TerminalLogPojo> list = dao.sqlFind("searchTerminalLogByParam", param, TerminalLogPojo.class);
                pageInfo.setList(list);
                pageInfo.setPage_total(0);
                pageInfo.setTotal(0);
            }

        }
        page.setList(TerminalLogConverter.toTerminalLogOutDtoList(pageInfo.getList()));
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    @Async
    public void asyncDownload(String fservice, List<? extends Object> list, ExportTerminalLogCommand command, String sourcePath) {
        try {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            //调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("终端日志报表");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导出终端日志异常：" , e);
        }
    }

    @Override
    public JSONObject download(String fservice, List<? extends Object> list, ExportTerminalLogCommand command, String sourcePath) {
        ReportConfig config = new ReportConfig("车辆轨迹");
        config.setColumn("", "序号", 0);
        config.setColumn("chassisNo", "底盘号", 1);
        config.setColumn("carNum", "车牌号", 2);
        config.setColumn("simNo", "SIM卡", 3);
        config.setColumn("terminalId", "终端ID", 4);
        config.setColumn("team", "经销商", 5);
        config.setColumn("reCustom", "所属客户", 6);
        config.setColumn("dir", "指令方向", 7);
        config.setColumn("status", "状态", 8);
        config.setColumn("type", "指令类型", 9);
        config.setColumn("operateUser", "操作人", 10);
        config.setColumn("operateIp", "操作人IP", 11);
        config.setColumn("operateTimeStr", "操作时间", 12);
        config.setColumn("des", "操作描述", 13);

        config.setSourcePath(sourcePath);
        File file = ExcelUtil.getExcel(config, list, null, 0);

        MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
        param.add("account", command.getUserInfor().getUserId());
        param.add("file", new FileSystemResource(file));
        RestTemplate restTemplate = new RestTemplate();
        String httpResult = restTemplate.postForObject(fservice, param, String.class);
        return JSONObject.fromObject(httpResult);
    }

    /**
     * 查询用户的车辆
     *
     * @param userId
     * @return
     */
    private String queryVehicleOfUser(Long userId, int userType) {
        if (StringUtil.isEmpty(userId)) {
            return null;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("accountId", userId);
        List list = new ArrayList();
        if (userType == UserTypeConstant.TRANSPORT.getCode()) {
            list = dao.sqlFindField("queryVehicleOfTransport", param);
        } else if (userType == UserTypeConstant.FINANCE_COMPANY.getCode()) {
            list = dao.sqlFindField("queryVehicleOfFinanceCompany", param);
        } else if (userType != UserTypeConstant.SYSTEM_ADMIN.getCode()) {
            list = dao.sqlFindField("queryVehicleOfNormal", param);
        } else {
            return "ALL";
        }
        if (StringUtil.isEmpty(list)) {
            return null;
        }
        return (String) list.get(0);
    }
}
