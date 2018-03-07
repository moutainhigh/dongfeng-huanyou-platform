package com.navinfo.opentsp.dongfeng.monitor.commands.car;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;

/**
 * @Author liusanhu@aerozhonghuan.com
 * @Date 2017/3/10
 */
public class QuerySearchTreeCommand extends BaseCommand<HttpCommandResultWithData> {
    //搜索类型
    private Integer searchType;
    //搜索内容
    private String searchText;
    //发动机类型,默认值 -1(不限)
    private String engineType;
    //车辆类型,默认值 -1(不限)
    private String carType;
    //当前所在地
    private String carPlace;
    //STD销售状态
    private Integer stdSalesStatus;
    //AAK销售状态
    private Integer aakSalesStatus;
    //所属经销商
    private String dealer;
    //所属客户
    private String customer;
    //车辆状态
    private Integer carStauts;

    public Integer getSearchType() {
        return searchType;
    }

    public void setSearchType(Integer searchType) {
        this.searchType = searchType;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarPlace() {
        return carPlace;
    }

    public void setCarPlace(String carPlace) {
        this.carPlace = carPlace;
    }

    public Integer getStdSalesStatus() {
        return stdSalesStatus;
    }

    public void setStdSalesStatus(Integer stdSalesStatus) {
        this.stdSalesStatus = stdSalesStatus;
    }

    public Integer getAakSalesStatus() {
        return aakSalesStatus;
    }

    public void setAakSalesStatus(Integer aakSalesStatus) {
        this.aakSalesStatus = aakSalesStatus;
    }

    public String getDealer() {
        return dealer;
    }

    public void setDealer(String dealer) {
        this.dealer = dealer;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public Integer getCarStauts() {
        return carStauts;
    }

    public void setCarStauts(Integer carStauts) {
        this.carStauts = carStauts;
    }

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    @Override
    public String toString() {
        return "QueryStationTreeCommand{}";
    }
}
