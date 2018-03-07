package com.navinfo.opentsp.dongfeng.system.commands.station;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.common.validation.group.ParameterType;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationBasicInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.StationServiceInfoIndto;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.station.SubStationInfoIndto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

/**
 * @author tushenghong
 * @version 1.0
 * @date 2017-03-23
 * @modify
 * @copyright Navi Tsp
 */
public class UpdateStationCommand extends BaseCommand<HttpCommandResultWithData> {
    @NotNull(message = "stationId 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "stationId 不能为空", groups = {GroupCommand.class})
    private String stationId;
    private String delSubStation; //删除的二级网点
    @NotNull(message = "服务站名称 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站名称 不能为空", groups = {GroupCommand.class})
    @Converter(target = "basicInfoBean")
    protected String basicInfo;
    protected StationBasicInfoIndto basicInfoBean;
    @NotNull(message = "服务站服务类型 不能为空", groups = {GroupCommand.class})
    @NotBlank(message = "服务站服务类型 不能为空", groups = {GroupCommand.class})
    @Converter(target = "serviceInfoBean")
    protected String serviceInfo;
    protected StationServiceInfoIndto serviceInfoBean;
    @Converter(target = "subStationInfoBean", type = ParameterType.LIST)
    protected String subStationInfo;
    protected ArrayList<SubStationInfoIndto> subStationInfoBean;

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getDelSubStation() {
        return delSubStation;
    }

    public void setDelSubStation(String delSubStation) {
        this.delSubStation = delSubStation;
    }

    public String getBasicInfo() {
        return basicInfo;
    }

    public void setBasicInfo(String basicInfo) {
        this.basicInfo = basicInfo;
    }

    public StationBasicInfoIndto getBasicInfoBean() {
        return basicInfoBean;
    }

    public void setBasicInfoBean(StationBasicInfoIndto basicInfoBean) {
        this.basicInfoBean = basicInfoBean;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public StationServiceInfoIndto getServiceInfoBean() {
        return serviceInfoBean;
    }

    public void setServiceInfoBean(StationServiceInfoIndto serviceInfoBean) {
        this.serviceInfoBean = serviceInfoBean;
    }

    public String getSubStationInfo() {
        return subStationInfo;
    }

    public void setSubStationInfo(String subStationInfo) {
        this.subStationInfo = subStationInfo;
    }

    public ArrayList<SubStationInfoIndto> getSubStationInfoBean() {
        return subStationInfoBean;
    }

    public void setSubStationInfoBean(ArrayList<SubStationInfoIndto> subStationInfoBean) {
        this.subStationInfoBean = subStationInfoBean;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }
}