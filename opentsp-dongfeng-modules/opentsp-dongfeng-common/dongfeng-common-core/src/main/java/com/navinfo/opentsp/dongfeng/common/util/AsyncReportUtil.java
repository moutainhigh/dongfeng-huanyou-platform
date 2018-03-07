package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.command.BaseReportCommand;
import com.navinfo.opentsp.dongfeng.common.constant.ReportConfigConstants;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 异步导出工具
 *
 * @author wt
 * @version 1.0
 * @date 2017-05-24
 * @modify
 * @copyright Navi Tsp
 */
@EnableAsync
@Component
public class AsyncReportUtil
{
    protected static final Logger logger = LoggerFactory.getLogger(AsyncReportUtil.class);
    
    @Autowired
    private MailClient mailClient;
    
    // 邮件信息的下标值
    private static final String MAIL_SENDOR_INDEX = "1";
    
    @Async
    public void asyncDownload(final List<? extends Object> list, final BaseReportCommand command,
        final ReportConfig config, final ReportProperty property)
    {
        logger.info("asyncDownload start...");
        String uploadResult = upload(list, command, config, property);
        try
        {
            logger.info("send email to user: {}", command.getEmail());
            // 调用邮件接口发送邮件信息
            SendEmailCommand cmd = new SendEmailCommand();
            cmd.setToEmails(command.getEmail());
            cmd.setSubject(config.getFileName());
            cmd.setContent(uploadResult);
            cmd.setWm(MAIL_SENDOR_INDEX);
            mailClient.sendMail(cmd);
        }
        catch (Exception e)
        {
            logger.error("asyncDownload is error", e);
        }
        logger.info("asyncDownload end");
    }
    
    private String upload(final List<? extends Object> list, final BaseReportCommand command,
        final ReportConfig config, final ReportProperty property)
    {
        File file = null;
        String filePath = "";
        try
        {
            config.setSourcePath(property.getPath());
            file = ExcelUtil.getExcel(config, list, config.getFileName(), ReportConfigConstants.EXCEL_START_ROW_NUMBER);
            logger.info("upload report excel file..." + file.getPath());
            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("account", command.getUserInfor().getUserId());
            param.add("file", new FileSystemResource(file));
            RestTemplate restTemplate = new RestTemplate();
            String httpResult = restTemplate.postForObject(property.getFservice(), param, String.class);
            filePath = JSONObject.fromObject(httpResult).getJSONObject("data").getString("fullPath");
        }
        catch (Exception e)
        {
            logger.error("upload excel file is error ", e);
        }
        finally
        {
            try
            {
                if (list != null) {
                    list.clear();
                }
                FileUtils.forceDelete(file);
            }
            catch (IOException ie)
            {
                logger.error("forceDelete excel file is error ", ie);
            }
        }
        return filePath;
    }
}