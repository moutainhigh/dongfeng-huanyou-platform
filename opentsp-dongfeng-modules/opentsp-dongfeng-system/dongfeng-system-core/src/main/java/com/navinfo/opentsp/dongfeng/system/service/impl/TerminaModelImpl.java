package com.navinfo.opentsp.dongfeng.system.service.impl;

import com.navinfo.opentsp.dongfeng.common.dao.CommonDao;
import com.navinfo.opentsp.dongfeng.common.result.HttpCommandResultWithData;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.ListUtil;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.system.commands.termodel.*;
import com.navinfo.opentsp.dongfeng.system.converter.TerminalModelConverter;
import com.navinfo.opentsp.dongfeng.system.entity.TerminalModelEntity;
import com.navinfo.opentsp.dongfeng.system.pojo.BasicdataPojo;
import com.navinfo.opentsp.dongfeng.system.pojo.TerminalModelPojo;
import com.navinfo.opentsp.dongfeng.system.service.TerminalModeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangyue on 2017/03/09.
 * 终端型号管理
 */
@Service
@Transactional(rollbackFor = {Exception.class})
public class TerminaModelImpl implements TerminalModeService {
    /**
     *
     */
    @Autowired
    private CommonDao dao;

    /**
     * 添加终端型号
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData addTerminaModel(AddTerminaModelCommand command) throws Exception {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        TerminalModelEntity modelEntity = new TerminalModelEntity();
        Map<String, String> parm = new HashMap<>();
        parm.put("tmName", command.getTmName());
        List<BigInteger> list = dao.sqlFindField("QueryRepeatTmname", parm);
        //判断终端型号名称是否已存在
        if (list != null && list.get(0) != null && list.get(0).signum() > 0) {
            result.fillResult(ReturnCode.TERMINA_MODEL_ALREADY_EXISTS);

        } else {
            modelEntity.setTmCommuModel(command.getTmCommuModel());
            modelEntity.setTmFactoryName(command.getTmFactoryName());
            modelEntity.setTmCarMatched(command.getTmCarMatched());
            modelEntity.setTmFactoryNum(command.getTmFactoryNum());
            modelEntity.setTmLoctionModel(command.getTmLoctionModel());
            modelEntity.setTmName(command.getTmName());
            modelEntity.setTmNotes(command.getTmNotes());
            modelEntity.setTmPublicNum(command.getTmPublicNum());
            modelEntity.setTmModelNum(command.getTmModelNum());
            dao.save(modelEntity);
            result.fillResult(ReturnCode.OK);

        }
        return result;
    }


    /**
     * 按ID号查询一条要删除终端型号
     *
     * @param terminalId
     * @return HttpCommandResultWithData
     */
    public int queryIdData(String terminalId) {
        Map<String, Object> parm = new HashMap<String, Object>();
        parm.put("terminalId", terminalId);
        List<TerminalModelPojo> list = dao.sqlFind("queryIdBdData", parm, TerminalModelPojo.class);
        //判断终端型号是否绑定
        if (!ListUtil.isEmpty(list)) {
            //如果为1说明已经绑定
            return 1;

        }
        return 0;
    }


    /**
     * 删除终端型号
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData deleteTerminaModel(DeleteTerminalModelCommand command) throws Exception {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        TerminalModelEntity modelEntity = new TerminalModelEntity();
        int te = this.queryIdData(command.getTerminalId());
        //如果为1则表示终端型号已绑定
        if (te == 1) {
            result.fillResult(ReturnCode.TERMINA_MODEL_ALREADY_BIND);

        } else {
            modelEntity.setTmId(Long.parseLong(command.getTerminalId()));
            dao.delete(modelEntity);
            result.fillResult(ReturnCode.OK);

        }
        return result;
    }


    /**
     * 修改端型号数据
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData updateTerminaModel(UpdateTerminalModelCommand command) throws Exception {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Long> parm = new HashMap<>();
        TerminalModelEntity modelEntity = new TerminalModelEntity();
        modelEntity.setTmId(Long.parseLong(command.getTmId()));
        TerminalModelEntity terEntity = (TerminalModelEntity) dao.findOne(modelEntity);

        //判断是否取到了终端号
        if (terEntity != null) {
            //判断终端号名称是否为空
            if (!StringUtil.isEmpty(terEntity.getTmName()) &&
                    !command.getTmName().equals(terEntity.getTmName())) {
                result.fillResult(ReturnCode.ERROR_TERMINA_MODEL_TMNAME);

            } else {
                modelEntity.setTmId(Long.parseLong(command.getTmId()));
                modelEntity.setTmCommuModel(command.getTmCommuModel());
                modelEntity.setTmFactoryName(command.getTmFactoryName());
                modelEntity.setTmCarMatched(command.getTmCarMatched());
                modelEntity.setTmFactoryNum(command.getTmFactoryNum());
                modelEntity.setTmLoctionModel(command.getTmLoctionModel());
                modelEntity.setTmName(command.getTmName());
                modelEntity.setTmPublicNum(command.getTmPublicNum());
                modelEntity.setTmModelNum(command.getTmModelNum());
                modelEntity.setTmNotes(command.getTmNotes());
                dao.update(modelEntity);
                result.fillResult(ReturnCode.OK);
            }

        }
        return result;
    }


    /**
     * 查询修改端型号数据
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData queryUpdateData(QueryUpTerminalModelCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        TerminalModelEntity modelEntity = new TerminalModelEntity();
        modelEntity.setTmId(Long.parseLong(command.getTerminalId()));
        TerminalModelEntity terEntity = (TerminalModelEntity) dao.findOne(modelEntity);
        //判断终端型号数据是否已存在
        if (terEntity != null) {
            result.fillResult(ReturnCode.OK);
            result.setData(TerminalModelConverter.toOutDtoUp(terEntity));

        }
        return result;
    }


    /**
     * 查询（定位模式，通信模式，适用车型）type 数据
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData queryTerminaModel(QueryTerminalModelCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> param = new HashMap<>();
        param.put("dataType", command.getDataType());
        List<BasicdataPojo> listPojo = dao.sqlFind("queryDataType", param, BasicdataPojo.class);
        //判断终端型号是否已存在
        if (listPojo != null && listPojo.size() > 0) {
            result.setData(listPojo);

        }
        result.fillResult(ReturnCode.OK);
        return result;
    }


    /**
     * 查询终端型号分页数据
     *
     * @param command
     * @return HttpCommandResultWithData
     */
    @Override
    public HttpCommandResultWithData listTerminalModelPage(ListTerminalModelCommand command) {

        HttpCommandResultWithData result = new HttpCommandResultWithData();
        Map<String, Object> param = new HashMap<>();
        PagingInfo page = new PagingInfo();
        if(StringUtil.isNotEmpty(command.getTmName())){
            param.put("tmName", command.getTmName());
        }
        if(StringUtil.isNotEmpty(command.getTmFactoryName())){
            param.put("tmFactoryName",command.getTmFactoryName());
        }
        if(StringUtil.isNotEmpty(command.getTmFactoryNum())){
            param.put("tmFactoryNum", command.getTmFactoryNum());
        }
        PagingInfo<TerminalModelPojo> pageInfo = dao.sqlPagelFind("queryDataDetail", param, Integer.valueOf(command.getPage_number()), Integer.valueOf(command.getPage_size()), TerminalModelPojo.class);
        List<TerminalModelPojo> list = pageInfo.getList();
        //判断分页查询是否有内容
        if (list != null && list.size() > 0) {

            page.setList(TerminalModelConverter.toOutDtoList(list));
            page.setPage_total(pageInfo.getPage_total());
            page.setTotal(pageInfo.getTotal());
            result.setData(page);
            result.fillResult(ReturnCode.OK);

        }
        result.fillResult(ReturnCode.OK);
        return result;
    }

}
