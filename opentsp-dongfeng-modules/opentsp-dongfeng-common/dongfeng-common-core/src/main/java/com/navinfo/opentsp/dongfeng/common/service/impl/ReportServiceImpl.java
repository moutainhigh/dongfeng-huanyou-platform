package com.navinfo.opentsp.dongfeng.common.service.impl;

import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.IReportService;
import com.navinfo.opentsp.dongfeng.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.text.MessageFormat;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author wt
 * @version 1.0
 * @date 2017-04-24
 * @modify
 * @copyright Navi Tsp
 */
@Component
public class ReportServiceImpl implements IReportService {
    @Autowired
    private AsyncReportUtil asyncReport;

    protected static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Override
    public HttpCommandResultWithData downLoad(final List<? extends Object> list, final BaseReportCommand command, final ReportConfig config, final ReportProperty property) {
        logger.info("downLoad start, data size is {}" , CollectionUtils.size(list));
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        switch (command.getDownloadFlag()) {
            case ReportConfigConstants.EXPORT_CURRENT_FLAG: {
                result = exportCurrent(list, command, config, property);
                break;
            }
            case ReportConfigConstants.EXPORT_ALL_FLAG: {
                result = exportAll(list, command, config, property);
                break;
            }
            default:
                // do nothing
        }
        logger.info("downLoad end");
        return result;
    }

    private HttpCommandResultWithData exportCurrent(final List<? extends Object> list, final BaseReportCommand command, final ReportConfig config, final ReportProperty property) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        String uploadResult = upload(list, command, config, property);
        result.setResultCode(ReturnCode.OK.code());
        result.setMessage(ReturnCode.EXPORT_FILE_SUCCESS.message());
        result.setData(uploadResult);
        return result;
    }

    private HttpCommandResultWithData exportAll(final List<? extends Object> list, final BaseReportCommand command, final ReportConfig config, final ReportProperty property) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        if (CollectionUtils.size(list) > NumberUtils.toInt(property.getRecords())) {
            final String email = StringUtil.isEmpty(command.getUserInfor().getEmail()) ? command.getEmail() : command.getUserInfor().getEmail();
            if (StringUtil.isEmpty(email)) {
                result.setResultCode(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.code());
                result.setMessage(MessageFormat.format(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.message(), property.getRecords()));
                return result;
            }
            command.setEmail(email);
            asyncReport.asyncDownload(list, command, config, property);
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), property.getRecords(), email));
        } else {
            String uploadResult = upload(list, command, config, property);
            result.setResultCode(ReturnCode.OK.code());
            result.setMessage(ReturnCode.EXPORT_FILE_SUCCESS.message());
            result.setData(uploadResult);
        }
        return result;
    }

    private String upload(final List<? extends Object> list, final BaseReportCommand command, final ReportConfig config, final ReportProperty property) {
        File file = null;
        String filePath = "";
        try {
            config.setSourcePath(property.getPath());
            logger.info("create report excel file...");
            file = ExcelUtil.getExcel(config, list, config.getFileName(), ReportConfigConstants.EXCEL_START_ROW_NUMBER);
            logger.info("upload report excel file..." + file.getPath());
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("account", command.getUserInfor().getUserId());
            param.add("file", new FileSystemResource(file));
            RestTemplate restTemplate = new RestTemplate();
            String httpResult = restTemplate.postForObject(property.getFservice(), param, String.class);
            filePath = JSONObject.fromObject(httpResult).getJSONObject("data").getString("fullPath");
        } catch (Exception e) {
            logger.error("upload excel file is error ", e);
        } finally {
            if (null != file && file.exists())
            {
                file.delete();
            }
        }
        return filePath;
    }
}
