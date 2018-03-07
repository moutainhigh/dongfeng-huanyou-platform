package com.navinfo.opentsp.dongfeng.system.commands.dealer;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.DealerIndto;

/**
 * Created by Rex on 2017/3/13.
 */
public class AddDealerCommand extends BaseCommand<CommonResult>{

    @Converter(target="dealerBean")
    private String dealermanager;

    private DealerIndto dealerBean;

    @Override
    public Class<? extends CommonResult> getResultType() {
        return CommonResult.class;
    }

    public String getDealermanager() {
        return dealermanager;
    }

    public void setDealermanager(String dealermanager) {
        this.dealermanager = dealermanager;
    }

    public DealerIndto getDealerBean() {
        return dealerBean;
    }

    public void setDealerBean(DealerIndto dealerBean) {
        this.dealerBean = dealerBean;
    }
}
