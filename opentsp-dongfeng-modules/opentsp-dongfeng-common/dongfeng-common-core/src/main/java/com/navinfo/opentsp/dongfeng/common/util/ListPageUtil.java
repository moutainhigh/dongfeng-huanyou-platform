package com.navinfo.opentsp.dongfeng.common.util;

/**
 * List分页  
 * 实现：利用List的获取子List方法，实现对List的分页  
 * @author zhangy  
 * @date 2017-03-13 16:27:31  
 *  
 */

import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.result.PagingInfo;
import org.apache.commons.lang.math.NumberUtils;

import java.util.Collections;
import java.util.List;

public class ListPageUtil
{
    /**
     * 内存分页
     * @param command
     * @param list
     * @param <T>
     * @return
     */
    public static <T> PagingInfo getPagingInfo(final BaseCommand command, final List<T> list){
        return getPagingInfo(NumberUtils.toInt(command.getPage_number()), NumberUtils.toInt(command.getPage_size()), list);
    }

    /**
     * 获取第几页的内容
     * 
     * @param page 当前页
     * @param pageSize 每页行数
     * @param list 需要分页的集合
     * @return
     */
    public static <T> PagingInfo getPagingInfo(int page, int pageSize, List<T> list)
    {
        int pageStartRow = 0;// 开始条数
        int pageEndRow = 0;// 结束条数
        PagingInfo<T> pageList = new PagingInfo<T>();
        
        // 如果传入list为空返回空pageList
        if (StringUtil.isEmpty(list))
        {
            pageList.setList(Collections.<T>emptyList());
            return pageList;
        }
        // 如果没传当前页默认第一页
        if (page == 0)
        {
            page = 1;
        } else {
        	page += 1;
        }
        int count = list.size(); // 总条数
        int totalPage = (count + pageSize - 1) / pageSize; // 总页数
        pageList.setTotal(count);// 设置总条数
        pageList.setPage_total(totalPage);// 设置总页数
        
        if (page * pageSize <= count)// 判断是否为最后一页
        {
            pageEndRow = page * pageSize;
            pageStartRow = pageEndRow - pageSize;
        }
        else
        {
            pageEndRow = count;
            pageStartRow = pageSize * (totalPage - 1);
        }
        
        // 分页结果集
        pageList.setList(list.subList(pageStartRow, pageEndRow));
        return pageList;
    }
}
