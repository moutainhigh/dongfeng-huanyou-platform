package com.navinfo.opentsp.dongfeng.authority.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.common.messaging.Command;
import com.navinfo.opentsp.dongfeng.common.command.BaseCommand;
import com.navinfo.opentsp.dongfeng.common.exception.CommandHandlingException;
import com.navinfo.opentsp.dongfeng.common.result.CommonResult;
import com.navinfo.opentsp.dongfeng.common.result.ReturnCode;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentspcore.common.handler.CommandHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Created by zhangyu on 2017/5/18.
 *
 * 统一controller入口，支持post get方式，返回json
 */
@RestController
public class WebController
{
    protected static final Logger logger = LoggerFactory.getLogger(WebController.class);
    
    @Resource
    private Map properties;
    
    @Value("${controller.prefix}")
    private String prefix;
    
    private final Map<Class<?>, CommandHandler<? extends Command<?>, ? extends CommonResult>> handlerByType =
        new IdentityHashMap();
    
    @Autowired
    public void CommandDispatcher(List<CommandHandler<? extends Command<?>, ? extends CommonResult>> handlers)
    {
        Iterator var2 = handlers.iterator();
        
        while (var2.hasNext())
        {
            CommandHandler<? extends Command<?>, ? extends CommonResult> handler = (CommandHandler)var2.next();
            Class<?> commandType = handler.getCommandType();
            this.handlerByType.put(commandType, handler);
            logger.info("Registered {} as handler of {}",
                handler.getClass().getSimpleName(),
                commandType.getSimpleName());
        }
    }
    
    public Map timeout(final HttpServletRequest request)
    {
        Map reMap = new HashMap();
        reMap.put("resultCode", 408);
        reMap.put("message", "请求超时");
        return reMap;
    }
    
    @RequestMapping(value = "/**")
    /*
     * @HystrixCommand(fallbackMethod = "timeout", commandProperties = {
     * 
     * @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "20000") })
     */
    public Map comeOn(final HttpServletRequest request)
    {
        //ADD fengwm 2017.06.30 提供给运维监控模块服务正常
        if(request.getRequestURI().equals("/health")){
            Map reMap = new HashMap();
            reMap.put("resultCode", 200);
            reMap.put("message", "ok");
            return reMap;
        }
        String uri = prefix + request.getRequestURI();
        String method = request.getMethod();
        logger.info("{} uri:{}", method, uri);
        if ("GET".equals(method))
        {
            // get 方式
            String param = getJson4Request(request);
            logger.info("request parm:{}", param);
            return execute(param, uri, request);
        }
        else if ("POST".equals(method))
        {
            // post 方式
            String parambody = getJson4Request(request);
            logger.info("request body:{}", parambody);
            return execute(parambody, uri, request);
        }
        else
        {
            // 不支持的请求方式
            Map reMap = new HashMap();
            reMap.put("resultCode", 405);
            reMap.put("message", "不支持的请求方式");
            return reMap;
        }
        
    }
    
    private Map execute(String param, String uri, HttpServletRequest request)
    {
        long startTime = System.currentTimeMillis();
        Object o;
        final String[] re = new String[1];
        // 初始化返回结果为超时，防止调用超时
        CommonResult result = new CommonResult();
        result.setResultCode(500);
        result.setMessage("服务异常");
        Map reMap = new HashMap();
        reMap.put("resultCode", 500);
        reMap.put("message", "服务异常");
        
        try
        {
            re[0] = JsonUtil.toJson(result);
            List urlList = valueGetKey(properties, uri);
            if (urlList.isEmpty())
            {
                reMap.put("resultCode", 404);
                reMap.put("message", "无该请求路径");
                return reMap;
            }
            Class clazz = Class.forName(urlList.get(0).toString());
            if ("".equals(param))
            {
                o = clazz.newInstance();
            }
            else
            {
                
                o = JsonUtil.fromJson(param, clazz);
                if (o == null)
                {
                    reMap.put("resultCode", 400);
                    reMap.put("message", "参数有误");
                    return reMap;
                }
            }
            
            // 基类设置equest
            BaseCommand cmd = (BaseCommand)o;
            if (cmd != null)
            {
                cmd.setRequest(request);
            }
            
            CommonResult handlerresult = dispatch(cmd);
            CommonResult commonResult = (CommonResult)handlerresult;

            // 服务返回异常，打印错误参数
            if (commonResult.getResultCode() == ReturnCode.SERVER_ERROR.code())
            {
                logger.error("服务端异常，异常入参为 ： " + param);
            }

            logger.info(commonResult.toString());
            re[0] = JsonUtil.toJson(commonResult);
            reMap = JsonUtil.toMap(re[0]);
            long endTime = System.currentTimeMillis();
            float seconds = (endTime - startTime) / 1000F;
            logger.info(uri + " request is:" + Float.toString(seconds) + " seconds.");
        }
        catch (ClassNotFoundException e)
        {
            logger.error(e.getMessage(), e);
        }
        catch (IllegalAccessException e)
        {
            logger.error(e.getMessage(), e);
        }
        catch (InstantiationException e)
        {
            logger.error(e.getMessage(), e);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage(), e);
        }
        return reMap;
    }
    
    private <C extends Command<?>, CR extends CommonResult> CR dispatch(C command)
    {
        CommandHandler<C, CR> commandHandler = (CommandHandler)this.handlerByType.get(command.getClass());
        if (commandHandler == null)
        {
            throw new CommandHandlingException("No handlers for " + command.getClass());
        }
        else
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Dispatching {} to ", command.getClass().getSimpleName(), commandHandler.getClass()
                    .getSimpleName());
            }
            
            return commandHandler.handle(command);
        }
    }
    
    private String getJson4Request(HttpServletRequest request)
    {
        if ("GET".equals(request.getMethod()) || "application/x-www-form-urlencoded".equals(request.getContentType()))
        {
            // GET 方式 或者 post方式的表单提交
            Map<String, String[]> map = request.getParameterMap();
            Map singleMap = new HashMap();
            for (String key : map.keySet())
            {
                if (map.get(key).length != 1)
                {
                    // 参数中有数组
                    singleMap.put(key, map.get(key));
                }
                else
                {
                    // 参数中无数组
                    singleMap.put(key, map.get(key)[0]);
                }
            }
            String json = null;
            try
            {
                json = JsonUtil.toJson(singleMap);
            }
            catch (JsonProcessingException e)
            {
                logger.error(e.getMessage(), e);
            }
            return json;
        }
        else
        {
            // post json 方式提交
            BufferedReader br;
            String str, wholeStr = "";
            try
            {
                br = request.getReader();
                while ((str = br.readLine()) != null)
                {
                    wholeStr += str;
                }
                
            }
            catch (IOException e)
            {
                logger.error(e.getMessage(), e);
            }
            return wholeStr;
        }
    }
    
    private ArrayList valueGetKey(Map map, String value)
    {
        Set set = map.entrySet();// 新建一个不可重复的集合
        ArrayList arr = new ArrayList<>();// 新建一个集合
        Iterator it = set.iterator();// 遍历的类
        while (it.hasNext())
        {
            Map.Entry entry = (Map.Entry)it.next();// 找到所有key-value对集合
            if (entry.getValue().equals(value))
            {// 通过判断是否有该value值
                String s = entry.getKey().toString();// 取得key值
                arr.add(s);
            }
        }
        return arr;
    }
}
