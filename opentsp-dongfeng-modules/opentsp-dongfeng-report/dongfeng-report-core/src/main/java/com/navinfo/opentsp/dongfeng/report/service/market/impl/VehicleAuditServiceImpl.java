package com.navinfo.opentsp.dongfeng.report.service.market.impl;

import java.math.BigInteger;
import java.util.*;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.service.BaseService;
import com.navinfo.opentsp.dongfeng.common.service.IRestTemplateService;
import com.navinfo.opentsp.dongfeng.common.util.DateUtil;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryAuditReasonCommand;
import com.navinfo.opentsp.dongfeng.report.commands.market.QueryVehicleAuditCommand;
import com.navinfo.opentsp.dongfeng.report.commands.market.VehicleAuditCommand;
import com.navinfo.opentsp.dongfeng.report.converter.market.VehicleAuditCvt;
import com.navinfo.opentsp.dongfeng.report.dto.market.VehicleAuditId;
import com.navinfo.opentsp.dongfeng.report.entity.market.VehicleAuditEntity;
import com.navinfo.opentsp.dongfeng.report.pojo.market.VehicleAuditPojo;
import com.navinfo.opentsp.dongfeng.report.repository.VehicleAuditRepository;
import com.navinfo.opentsp.dongfeng.report.service.market.IVehicleAuditService;

/**
 * 代收车辆审批
 * @author wt
 * @version 1.0
 * @date 2017/11/28
 * @modify
 * @copyright
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class VehicleAuditServiceImpl extends BaseService implements IVehicleAuditService {
    private static final Logger logger = LoggerFactory.getLogger(VehicleAuditServiceImpl.class);

    @Value("${sync.audit.enable}")
    private boolean syncauditEnable;

    @Value("${sync.audit.url}")
    private String auditUrl;


    @Autowired
    private IRestTemplateService restTemplateService;

    @Autowired
    private VehicleAuditRepository repository;

    @Override
    public HttpCommandResultWithData query(final QueryVehicleAuditCommand command, final boolean isQueryAll) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        Map<String, Object> parameters = new HashMap<>(16);
        if (StringUtil.isNotEmpty(command.getStartTime()) && StringUtil.isNotEmpty(command.getEndTime())) {
            parameters.put("startTime", command.getStartTime());
            parameters.put("endTime", DateUtil.formatDate(DateUtil.addDay(DateUtil.parseDate(command.getEndTime()), 1)));
        }
        parameters.put("chassisNum", command.getChassisNum());
        parameters.put("status", StringUtil.nvl(command.getStatus()));

        PagingInfo<VehicleAuditPojo> datas = new PagingInfo<>();
        if (isQueryAll){
            List<VehicleAuditPojo> vehicles = dao.sqlFind("queryVehicleAuditInfos", parameters, VehicleAuditPojo.class);
            datas.setList(vehicles);
        } else {
            datas = dao.sqlPagelFind("queryVehicleAuditInfos", parameters,
                    NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), VehicleAuditPojo.class);
        }

        PagingInfo page = new PagingInfo();
        page.setList(VehicleAuditCvt.convert(datas.getList()));
        page.setPage_total(datas.getPage_total());
        page.setTotal(datas.getTotal());
        result.setData(page);
        result.fillResult(ReturnCode.OK);
        return result;
    }

    @Override
    public HttpCommandResultWithData queryApplyReason(final QueryAuditReasonCommand command) {
        HttpCommandResultWithData result = new HttpCommandResultWithData();
        VehicleAuditEntity entity = repository.findById(BigInteger.valueOf(command.getId()));
        result.setData(entity.getApplyReason());
        return result;
    }

    @Override
    public HttpCommandResultWithData audit(final VehicleAuditCommand command) throws Exception {
        HttpCommandResultWithData result = new HttpCommandResultWithData();

        List<Integer> auditIds = new ArrayList<>();
        for (VehicleAuditId audit : command.getAuditBean()) {
            auditIds.add(Integer.parseInt(audit.getId()));
            VehicleAuditEntity entity = repository.findById(BigInteger.valueOf(Long.parseLong(audit.getId())));
            entity.setStatus(command.getStatus());
            entity.setReason(command.getReason());
            dao.update(entity);
        }

        if (syncauditEnable)
        {
            StringBuffer url = new StringBuffer(auditUrl);
            url.append("?id=").append(ListUtil.list2String(auditIds));
            url.append("&status=").append(command.getStatus());
            url.append("&reason=").append(command.getReason());
            url.append("&accountName=").append(command.getUserInfor().getUsername());
            url.append("&accountId=").append(command.getUserInfor().getUserId());
            ResponseEntity<String> response = restTemplateService.getForEntity(url.toString(), String.class);
            logger.info("audit vehicle result {}", response.toString());
            Map m = JsonUtil.toMap(response.getBody());
            if (Integer.parseInt(Objects.toString(m.get("resultCode"))) != ReturnCode.OK.code())
            {
                throw new BaseServiceException(ReturnCode.AUDIT_FAILED.message());
            }
        }

        return result;
    }
}
