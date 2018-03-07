package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.client.MailClient;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.util.ExcelUtil;
import com.navinfo.opentsp.dongfeng.common.util.ReportConfig;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.mail.commands.SendEmailCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.ExportNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.QueryNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.QueryNonF9VehicleDetailCommand;
import com.navinfo.opentsp.dongfeng.system.commands.nonf9.UpdateNonF9VehicleCommand;
import com.navinfo.opentsp.dongfeng.system.pojo.NonF9VehiclePojo;
import com.navinfo.opentsp.dongfeng.system.service.INonF9VehicleService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.math.BigInteger;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: tushenghong
 * @version: 1.0
 * @since: 2017-11-28
 **/
@Service
public class NonF9VehicleServiceImpl extends BaseService implements INonF9VehicleService {

    @Value("${report.module.path}")
    private String path;

    @Value("${fservice.url}")
    private String fservice;

    @Value("${download.carrecord.size}")
    private String records;

    @Autowired
    private MailClient mailClient;

    @Override
    public PagingInfo queryNonF9Vehicle(QueryNonF9VehicleCommand command, boolean isPaging) {
        PagingInfo page = new PagingInfo();
        Map<String, Object> param = new HashMap<>();
        param.put("chassisNum", command.getChassisNum());
        param.put("carType", command.getCarType());
        param.put("engineType", command.getEngineType());
        param.put("engineCode", command.getEngineCode());
        if (StringUtil.isNotEmpty(command.getManufactureDate())) {
            String manufactureDate = command.getManufactureDate();
            param.put("manufactureDateStart", manufactureDate.substring(0, 10));
            param.put("manufactureDateEnd", manufactureDate.substring(manufactureDate.indexOf(" - ") + 3));
        }
        PagingInfo<NonF9VehiclePojo> pageInfo = new PagingInfo<>();
        if (isPaging) {
            pageInfo = dao.sqlPagelFind("queryNonF9VehilceByParam",
                    param,
                    StringUtil.toInteger(command.getPage_number()),
                    StringUtil.toInteger(command.getPage_size()),
                    NonF9VehiclePojo.class);
        } else {
            List<NonF9VehiclePojo> list = dao.sqlFind("queryNonF9VehilceByParam", param, NonF9VehiclePojo.class);
            pageInfo.setList(list);
            pageInfo.setPage_total(0);
            pageInfo.setTotal(0);
        }
        page.setList(pageInfo.getList());
        page.setPage_total(pageInfo.getPage_total());
        page.setTotal(pageInfo.getTotal());
        return page;
    }

    @Override
    public NonF9VehiclePojo queryNonF9VehicleDetail(QueryNonF9VehicleDetailCommand command) {
        Map<String, Object> param = new HashMap<>();
        param.put("vehicleId", command.getVehicleId());
        return (NonF9VehiclePojo) dao.sqlFindObject("queryNonF9VehilceByParam", param, NonF9VehiclePojo.class);
    }

    @Override
    @Transactional
    public HttpCommandResultWithData updateNonF9Vehicle(UpdateNonF9VehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String,Object> cparam = new HashMap<String,Object>();
        cparam.put("chassisNum",command.getChassisNum());
        cparam.put("carId",command.getVehicleId());
        List<BigInteger> chaCount = (List<BigInteger>) dao.sqlFindField("queryNonF9VehicleCountByWhere", cparam);
        if(chaCount.get(0).longValue()>0){
            result.fillResult(ReturnCode.CHASSIS_NUM_EXIST);
            return result;
        }
        Map<String,Object> vparam = new HashMap<String,Object>();
        vparam.put("vin",command.getVin());
        vparam.put("carId",command.getVehicleId());
        List<BigInteger> vinCount = (List<BigInteger>) dao.sqlFindField("queryNonF9VehicleCountByWhere", vparam);
        if(vinCount.get(0).longValue()>0){
            result.fillResult(ReturnCode.VIN_NUM_EXIST);
            return result;
        }
        dao.executeUpdate("updateNonF9Vehicle", command);
        return result;
    }

    private void asyncDownload(String fservice, List<? extends Object> list, ExportNonF9VehicleCommand command, String sourcePath) {
        try {
            JSONObject uploadResult = download(fservice, list, command, sourcePath);
            // 调用邮件接口发送邮件信息
            SendEmailCommand mailCommand = new SendEmailCommand();
            mailCommand.setToEmails(command.getEmail());
            mailCommand.setSubject("非F9车辆数据");
            mailCommand.setContent(uploadResult.getJSONObject("data").getString("fullPath"));
            mailClient.sendMail(mailCommand);
        } catch (Exception e) {
            logger.error("异步导出非F9车辆报表异常：", e);
        }
    }

    private JSONObject download(String fservice, List<? extends Object> list, ExportNonF9VehicleCommand command, String sourcePath) throws BaseServiceException {
        String httpResult = null;
        File file = null;
        try {
            ReportConfig config = new ReportConfig("车辆轨迹");
            String metadata = command.getMetadata();
            if (!StringUtils.isEmpty(metadata)) {
                JSONArray jsonArray = JSONArray.fromObject(metadata);
                config.setColumn(jsonArray, NonF9VehiclePojo.class);
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

            config.setSourcePath(sourcePath);
            file = ExcelUtil.getExcel(config, list, null, 0);

            MultiValueMap<String, Object> param = new LinkedMultiValueMap<String, Object>();
            param.add("account", command.getUserInfor().getUserId());
            param.add("file", new FileSystemResource(file));
            RestTemplate restTemplate = new RestTemplate();
            httpResult = restTemplate.postForObject(fservice, param, String.class);
        } catch (Exception e) {
            logger.error("Non f9 vehicle asyncDonwload has error : ", e);
            throw new BaseServiceException(e);
        } finally {

            if (null != file && file.exists()) {
                file.delete();
            }
        }
        return JSONObject.fromObject(httpResult);
    }

    @Override
    public HttpCommandResultWithData exportCurrentPage(ExportNonF9VehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = this.queryNonF9Vehicle(command, true);
            JSONObject uploadResult = this.download(fservice, pagingInfo.getList(), command, path);
            result = new HttpCommandResultWithData();
            result.setResultCode(ReturnCode.OK.code());
            if (null != uploadResult && !StringUtil.isEmpty(uploadResult.getString("data"))) {
                JSONObject uploadJson = JSONObject.fromObject(uploadResult.getString("data"));
                result.setData(uploadJson);
            } else {
                result.setData(new JSONObject());
            }
            return result;
        } catch (Exception e) {
            logger.error("export non f9 vehicle failed,", e);
            result.fillResult(ReturnCode.EXPORT_NON_F9_VEHICLE_FAILED);
        }
        return result;
    }

    @Override
    public HttpCommandResultWithData exportAll(ExportNonF9VehicleCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        try {
            PagingInfo pagingInfo = this.queryNonF9Vehicle(command, false);
            if (StringUtil.isEmpty(pagingInfo) || StringUtil.isEmpty(pagingInfo.getList())) {
                result.setMessage("没有可导出的数据");
                return result;
            }
            int number = Integer.valueOf(records);
            if (pagingInfo.getList().size() >= number) {
                final String email = StringUtil.isEmpty(command.getUserInfor().getEmail()) ? command.getEmail() : command.getUserInfor().getEmail();
                if (StringUtil.isEmpty(email)) {
                    result.setResultCode(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.code());
                    result.setMessage(MessageFormat.format(ReturnCode.EXPORT_EMAIL_ISNOT_EMPTY.message(), number));
                    return result;
                }
                command.setEmail(email);
                // path表示模板在文件服务器的位置.
                this.asyncDownload(fservice, pagingInfo.getList(), command, path);
                result.setMessage(MessageFormat.format(ReturnCode.EXPORT_ASYNC_SUCCESS.message(), number, email));
            } else {
                JSONObject uploadResult = this.download(fservice, pagingInfo.getList(), command, path);
                result.setData(uploadResult.getJSONObject("data").getString("fullPath"));
            }
            return result;
        } catch (Exception e) {
            logger.error("export non f9 vehicle failed,", e);
            result.fillResult(ReturnCode.EXPORT_NON_F9_VEHICLE_FAILED);
        }
        return result;
    }
}
