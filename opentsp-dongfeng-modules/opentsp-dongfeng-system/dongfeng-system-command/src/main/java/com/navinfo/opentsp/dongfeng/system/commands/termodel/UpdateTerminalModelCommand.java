package com.navinfo.opentsp.dongfeng.system.commands.termodel;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.validation.group.Converter;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import com.navinfo.opentsp.dongfeng.system.commands.dto.inDto.TerminalModelIndto;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * 更新终端型号
 *
 * @author: zhangyue
 * @version: 1.0
 * @since: 2017-03-09
 **/
public class UpdateTerminalModelCommand extends BaseCommand<HttpCommandResultWithData> {

    @Converter(target="terminalmodelBean")
    private String terminalmode;

    private TerminalModelIndto terminalmodelBean;

    @NotNull(message = "tmName 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmName 不能为空", groups = {GroupCommand.class})
    private String tmId;
    //终端型号
    @NotNull(message = "tmName 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmName 不能为空", groups = {GroupCommand.class})
    private String tmName;

    //厂家名称
    @NotNull(message = "tmFactoryName 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmFactoryName 不能为空", groups = {GroupCommand.class})
    private String tmFactoryName;

    //厂家编号
    @NotNull(message = "tmFactoryNum 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmFactoryNum 不能为空", groups = {GroupCommand.class})
    private String tmFactoryNum;

    //交通部公告批次
    @NotNull(message = "tmPublicNum 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmPublicNum 不能为空", groups = {GroupCommand.class})
    private String tmPublicNum;

    //定位模式GPS,北斗,GLANOSS,Galileo
    @NotNull(message = "tmLoctionModel 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmLoctionModel 不能为空", groups = {GroupCommand.class})
    private String tmLoctionModel;

    //通信模式GSM,CDMA,WCDMA,TD-SCDMA,CDMA2000,TD-LTE,LTE FDD
    @NotNull(message = "tmCommuModel 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmCommuModel 不能为空", groups = {GroupCommand.class})
    private String tmCommuModel;

    //适用车型客,货,危
    @NotNull(message = "tmCarMatched 不能为NULL", groups = {GroupCommand.class})
    @NotBlank(message = "tmCarMatched 不能为空", groups = {GroupCommand.class})
    private String tmCarMatched;

    //型号编号
    private String tmModelNum;

    //备注
    private String tmNotes;

    @Override
    public Class<? extends HttpCommandResultWithData> getResultType() {
        return HttpCommandResultWithData.class;
    }

    public String getTerminalmode() {
        return terminalmode;
    }

    public void setTerminalmode(String terminalmode) {
        this.terminalmode = terminalmode;
    }
    public TerminalModelIndto getTerminalBean() {
        return terminalmodelBean;
    }

    public String getTmId() {
        return tmId;
    }

    public void setTmId(String tmId) {
        this.tmId = tmId;
    }
    public String getTmName() {
        return tmName;
    }

    public void setTmName(String tmName) {
        this.tmName = tmName;
    }

    public String getTmFactoryName() {
        return tmFactoryName;
    }

    public void setTmFactoryName(String tmFactoryName) {
        this.tmFactoryName = tmFactoryName;
    }

    public String getTmFactoryNum() {
        return tmFactoryNum;
    }

    public void setTmFactoryNum(String tmFactoryNum) {
        this.tmFactoryNum = tmFactoryNum;
    }

    public String getTmPublicNum() {
        return tmPublicNum;
    }

    public void setTmPublicNum(String tmPublicNum) {
        this.tmPublicNum = tmPublicNum;
    }

    public String getTmLoctionModel() {
        return tmLoctionModel;
    }

    public void setTmLoctionModel(String tmLoctionModel) {
        this.tmLoctionModel = tmLoctionModel;
    }

    public String getTmCommuModel() {
        return tmCommuModel;
    }

    public void setTmCommuModel(String tmCommuModel) {
        this.tmCommuModel = tmCommuModel;
    }

    public String getTmCarMatched() {
        return tmCarMatched;
    }

    public void setTmCarMatched(String tmCarMatched) {
        this.tmCarMatched = tmCarMatched;
    }

    public String getTmModelNum() {
        return tmModelNum;
    }

    public void setTmModelNum(String tmModelNum) {
        this.tmModelNum = tmModelNum;
    }

    public String getTmNotes() {
        return tmNotes;
    }

    public void setTmNotes(String tmNotes) {
        this.tmNotes = tmNotes;
    }
}
