package com.navinfo.opentsp.dongfeng.common.configuration;

import com.navinfo.opentsp.dongfeng.common.rmi.BasicDataQueryWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.DataAnalysisWebService;
import com.navinfo.opentsp.dongfeng.common.rmi.SynchronousTerminalDataWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerRequest;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;

@Component
public class CloudDataRmiClientConfiguration
{
    
    @Value("${rmi.eureka.registerName:rpws}")
    private String SERVICEID;
    
    @Value("${rpws.client.rmi.port:1199}")
    private int port;
    
    @Autowired
    private LoadBalancerClient balancerClient;
    
    public BasicDataQueryWebService getBasicDataQueryWebService()
        throws Exception
    {
        
        BasicDataQueryWebService client =
            balancerClient.execute(SERVICEID, new LoadBalancerRequest<BasicDataQueryWebService>()
            {
                
                @Override
                public BasicDataQueryWebService apply(ServiceInstance instance)
                    throws Exception
                {
                    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
                    rmiProxyFactoryBean.setServiceUrl(buildRmiUrl(instance.getHost(), port, "basicDataQueryWebService"));
                    rmiProxyFactoryBean.setServiceInterface(BasicDataQueryWebService.class);
                    rmiProxyFactoryBean.afterPropertiesSet();
                    BasicDataQueryWebService client = (BasicDataQueryWebService)rmiProxyFactoryBean.getObject();
                    
                    return client;
                }
            });
        return client;
    }
    
    public DataAnalysisWebService getDataAnalysisWebService()
        throws Exception
    {
        
        DataAnalysisWebService client =
            balancerClient.execute(SERVICEID, new LoadBalancerRequest<DataAnalysisWebService>()
            {
                
                @Override
                public DataAnalysisWebService apply(ServiceInstance instance)
                    throws Exception
                {
                    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
                    rmiProxyFactoryBean.setServiceUrl(buildRmiUrl(instance.getHost(), port, "dataAnalysisWebService"));
                    rmiProxyFactoryBean.setServiceInterface(DataAnalysisWebService.class);
                    rmiProxyFactoryBean.afterPropertiesSet();
                    DataAnalysisWebService client = (DataAnalysisWebService)rmiProxyFactoryBean.getObject();
                    
                    return client;
                }
            });
        return client;
    }
    
    public SynchronousTerminalDataWebService getSynchronousTerminalDataWebService()
        throws Exception
    {
        
        SynchronousTerminalDataWebService client =
            balancerClient.execute(SERVICEID, new LoadBalancerRequest<SynchronousTerminalDataWebService>()
            {
                
                @Override
                public SynchronousTerminalDataWebService apply(ServiceInstance instance)
                    throws Exception
                {
                    RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
                    rmiProxyFactoryBean.setServiceUrl(buildRmiUrl(instance.getHost(),
                        port,
                        "synchronousTerminalDataWebService"));
                    rmiProxyFactoryBean.setServiceInterface(SynchronousTerminalDataWebService.class);
                    rmiProxyFactoryBean.afterPropertiesSet();
                    SynchronousTerminalDataWebService client =
                        (SynchronousTerminalDataWebService)rmiProxyFactoryBean.getObject();
                    
                    return client;
                }
            });
        return client;
    }
    
    private String buildRmiUrl(String hostName, int port, String interfaceName)
    {
        return "rmi://" + hostName + ":" + port + "/" + interfaceName;
    }
}
