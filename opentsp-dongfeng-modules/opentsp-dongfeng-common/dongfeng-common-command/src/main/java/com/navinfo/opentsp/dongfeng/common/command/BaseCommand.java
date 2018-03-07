package com.navinfo.opentsp.dongfeng.common.command;

import com.navinfo.opentsp.common.messaging.AbstractSecuredCommand;
import com.navinfo.opentsp.common.messaging.Command;
import com.navinfo.opentsp.dongfeng.common.dto.UserInfoDto;
import com.navinfo.opentsp.dongfeng.common.util.RegexpUtils;
import com.navinfo.opentsp.dongfeng.common.validation.group.GroupCommand;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Pattern;

/**
 * General BaseCommand
 *
 * @author zhangy
 * @date 2016-03-02
 * @modify
 * @copyright Navi Tsp
 */
public abstract class BaseCommand<T extends Command.Result> extends AbstractSecuredCommand<T>
{
    /**
     * 当前登录用户的token
     */
    private String token;
    
    /**
     * 当前登录用户
     */
    private UserInfoDto userInfor;
    
    /**
     * 排序字段
     */
    private String sort;
    
    /**
     * 排序方式
     */
    private Integer sortType;
    
    /**
     * request
     */
    
    private HttpServletRequest request;
    
    /**
     * 每页行数
     */
    @Pattern(regexp = RegexpUtils.POSITIVE_NUMBER_BLANK_REGEXP, message = "page_size应为正整数", groups = {GroupCommand.class})
    private String page_size = "20";
    
    /**
     * 当前页
     */
    @Pattern(regexp = RegexpUtils.POSITIVE_NUMBER_BLANK_REGEXP, message = "page_number应为正整数", groups = {GroupCommand.class})
    private String page_number = "1";
    
    /**
     * 总页数
     */
    @Pattern(regexp = RegexpUtils.POSITIVE_NUMBER_BLANK_REGEXP, message = "page_total应为正整数", groups = {GroupCommand.class})
    private String page_total;
    
    /**
     * 获取当前登录用户token
     *
     * @return token
     */
    public String getToken()
    {
        return token;
    }
    
    /**
     * 设置当前登录用户token
     *
     * @param token
     */
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public UserInfoDto getUserInfor()
    {
        return userInfor;
    }
    
    public void setUserInfor(UserInfoDto userInfor)
    {
        this.userInfor = userInfor;
    }
    
    /**
     * 获取排序字段
     *
     * @return sort
     */
    public String getSort()
    {
        return sort;
    }
    
    /**
     * 设置排序字段
     *
     * @param sort 排序字段
     */
    public void setSort(String sort)
    {
        this.sort = sort;
    }
    
    /**
     * 获取排序类型
     *
     * @return sortType
     */
    public Integer getSortType()
    {
        if (sortType == null)
        {
            this.sortType = 1;
        }
        return sortType;
    }
    
    /**
     * 设置排序类型
     *
     * @param sortType 排序类型
     */
    public void setSortType(Integer sortType)
    {
        this.sortType = sortType;
    }
    
    public String getPage_size()
    {
    if (StringUtils.isEmpty(page_size))
    {
        page_size = "20";
    }
    return page_size;
}
    
    public void setPage_size(String page_size)
    {
        this.page_size = page_size;
    }
    
    public String getPage_number()
    {
        if (StringUtils.isEmpty(page_number))
        {
            return "1";
        }
        return page_number;
    }
    
    public void setPage_number(String page_number)
    {
        this.page_number = page_number;
    }
    
    public String getPage_total()
    {
        return page_total;
    }
    
    public void setPage_total(String page_total)
    {
        this.page_total = page_total;
    }
    
    public HttpServletRequest getRequest()
    {
        return request;
    }
    
    public void setRequest(HttpServletRequest request)
    {
        this.request = request;
    }
    
    @Override
    public String toString()
    {
        return "BaseCommand{" + "token='" + token + '\'' + ", userInfor=" + userInfor + ", sort='" + sort + '\''
            + ", sortType=" + sortType + ", page_size='" + page_size + '\'' + ", page_number='" + page_number + '\''
            + ", page_total='" + page_total + '\'' + '}';
    }
}
