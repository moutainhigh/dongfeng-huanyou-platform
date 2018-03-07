package com.navinfo.dongfeng.terminal.comm.init;

import com.navinfo.dongfeng.terminal.comm.services.IHyInstructLayinService;
import com.navinfo.dongfeng.terminal.comm.tcp.business.server.CloudServerStart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class GpsServerStart implements ApplicationListener<ContextRefreshedEvent>
{
    @Value("${CloudServerIp}")
    private String cloudServerIp;
    
    @Value("${CloudServerPort}")
    private String cloudServerPort;
    
    @Resource
    private IHyInstructLayinService nstructLayinService;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)
    {
        // 配置文件加载
        // 2.配置文件加载
        // Constants.picServer = this.getServletContext().getContextPath()
        // + "/downloads/";
        // Constants.picServerPath = this.getServletContext().getRealPath("")
        // + "\\downloads\\";
        // Constants.userName = Configuration.getReourcesV("userName");
        // Constants.passWord = Configuration.getReourcesV("passWord");
        // Constants.areaCode=Integer.parseInt(Configuration.getReourcesV("areaCode"));
        // Constants.REDIS_IP=Configuration.getReourcesV("redis_ip");
        // Constants.REDIS_PORT=Integer.parseInt(Configuration.getReourcesV("redis_port"));
        // 车厂数据初始化加载
        // new InitializeCache().initialize();
        // TCP类初始化
        // new CommandInitialize().initialize(null);
        // APP接口
        // new AppCommandInitialize().initialize(null);
        
        // TCP服务初始化
        CloudServerStart cloudServerStart = new CloudServerStart();
        cloudServerStart.setIp(cloudServerIp);
        cloudServerStart.setPort(Integer.parseInt(cloudServerPort));
        cloudServerStart.setClouds(false);// 是否为位置云平台鉴权返回
        cloudServerStart.setServerType(0);// 连接位置云总MM
        cloudServerStart.setConnect(false);// 初始设置为未连接
        cloudServerStart.connect();

        // // 启动定时任务
        // ScheduleController scheduleController = ScheduleController.instance();
        // scheduleController.start();
    }
    
    // /***** 启动位置云链路 ******/
    // public static void startCloudServer() {
    // CloudServerStart cloudServerStart = new CloudServerStart();
    // cloudServerStart.setIp(Configuration.getReourcesV("CloudServerIp"));
    // cloudServerStart.setPort(Integer.parseInt(Configuration
    // .getReourcesV("CloudServerPort")));
    // cloudServerStart.setClouds(false);// 是否为位置云平台鉴权返回
    // cloudServerStart.setServerType(0);// 连接位置云总MM
    // cloudServerStart.setConnect(false);// 初始设置为未连接
    // cloudServerStart.connect();
    // }
}
