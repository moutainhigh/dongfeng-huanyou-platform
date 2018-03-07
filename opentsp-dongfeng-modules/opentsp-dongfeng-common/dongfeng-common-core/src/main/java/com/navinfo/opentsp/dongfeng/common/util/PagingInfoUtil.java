package com.navinfo.opentsp.dongfeng.common.util;

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;

import java.util.ArrayList;
import java.util.List;

public class PagingInfoUtil {

    /**
     * 得到手动分页信息
     * @param list 需要分页的list
     * @param pageIndex 当前页码
     * @param pageSize 每页大小
     * @param <T> 实体类
     * @return PagingInfo
     */
    public static <T> PagingInfo<T> getPagingInfo(List<T> list, int pageIndex,int pageSize) {
        //===============================================================================
        // 设置分页信息
        //===============================================================================
        PagingInfo<T> dtoPage = new PagingInfo<>();

        if (list==null||list.size()==0) {
            dtoPage.setList(new ArrayList<T>());
            return dtoPage;
        }

        // 总记录
        dtoPage.setTotal(list.size());
        // 总页数
        int iTotalPage = list.size()%pageSize == 0 ? list.size()/pageSize : (list.size()/pageSize+1);
        dtoPage.setPage_total(iTotalPage);
        // 每页显示的体检次数信息
        if (pageIndex >= iTotalPage) {
            pageIndex = iTotalPage - 1;
        }
        Integer startIndex = pageIndex * pageSize;
        Integer endIndex = startIndex + pageSize;
        if(endIndex > list.size()){
            endIndex = list.size();
        }
        dtoPage.setList(list.subList(startIndex, endIndex));
        return dtoPage;
    }

    /**
     * 得到手动分页信息
     * @param list 需要分页的list
     * @param command command
     * @param <T> 实体类
     * @return PagingInfo
     */
    public static <T> PagingInfo<T> getPagingInfo(List<T> list, BaseCommand command) {
        return getPagingInfo(list, Integer.parseInt(command.getPage_number()), Integer.parseInt(command.getPage_size()));
    }





}
