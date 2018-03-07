package com.navinfo.opentsp.dongfeng.rpws.webservice.dao;

import com.lc.rp.webservice.service.BasicDataQueryWebService;
import com.lc.rp.webservice.service.BasicDataQueryWebServiceService;
import com.lc.rp.webservice.service.DataAnalysisWebService;
import com.lc.rp.webservice.service.DataAnalysisWebServiceService;
import com.lc.rp.webservice.service.impl.center.SynchronousTerminalDataWebService;
import com.lc.rp.webservice.service.impl.center.TerminalWS;
import com.navinfo.opentsp.dongfeng.common.constant.CacheKeyConstants;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import com.navinfo.opentsp.dongfeng.rpws.cache.RPNodeDataCache;
import com.navinfo.opentsp.dongfeng.rpws.common.utils.douglas.WebServiceIp;
import com.navinfo.opentsp.dongfeng.rpws.webservice.dao.entity.WebServiceEntity;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Component
public class WebServiceFactory
{
    @Value("${CloudServerUserName}")
    private String userName;
    
    @Value("${passWord}")
    private String passWord;
    
    @Value("${ws.connect.timeout}")
    private int connectTimeout;// 默认5秒
    
    private final static Log log = LogFactory.getLog(WebServiceFactory.class);
    
    // private static WebServiceEntity<DataAnalysisWebService> dataAnalysisWebService;
    //
    // private static WebServiceEntity<BasicDataQueryWebService> basicDataQueryWebService;
    //
    // private static WebServiceEntity<SynchronousTerminalDataWebService> synchronousTerminalDataWebService;
    
    private static Long accessTocken = 0l;
    
    // private static ReentrantReadWriteLock dataAnalysisWebServiceLock = new ReentrantReadWriteLock();
    // private static ReentrantReadWriteLock basicDataQueryWebServiceLock = new ReentrantReadWriteLock();
    // private static ReentrantReadWriteLock synchronousTerminalDataWebServiceLock = new ReentrantReadWriteLock();
    
    @Autowired
    private RPNodeDataCache rPNodeDataCache;
    
    @SuppressWarnings("unchecked")
    public <T> WebServiceEntity<T> getWebServiceEntity(Class<?> webService)
    {
        
        if (DataAnalysisWebService.class.isAssignableFrom(webService))
        {
            
            // dataAnalysisWebServiceLock.readLock().lock();
            /*
             * if (null == dataAnalysisWebService || null == dataAnalysisWebService.getWebService()) { //
             * dataAnalysisWebServiceLock.readLock().unlock(); // dataAnalysisWebServiceLock.writeLock().lock(); if
             * (null == dataAnalysisWebService || null == dataAnalysisWebService.getWebService()) {
             * dataAnalysisWebService = getDataAnalysisWs(); } // dataAnalysisWebServiceLock.readLock().lock(); //
             * dataAnalysisWebServiceLock.writeLock().unlock(); }
             */
            // dataAnalysisWebServiceLock.readLock().unlock();
            return (WebServiceEntity<T>)getDataAnalysisWs();
        }
        
        if (BasicDataQueryWebService.class.isAssignableFrom(webService))
        {
            
            /*
             * basicDataQueryWebServiceLock.readLock().lock();
             * 
             * if (null == basicDataQueryWebService || null == basicDataQueryWebService.getWebService()) {
             * basicDataQueryWebServiceLock.readLock().unlock(); basicDataQueryWebServiceLock.writeLock().lock(); if
             * (null == basicDataQueryWebService || null == basicDataQueryWebService.getWebService()) {
             * basicDataQueryWebService = getWs(); } basicDataQueryWebServiceLock.readLock().lock();
             * basicDataQueryWebServiceLock.writeLock().unlock(); } basicDataQueryWebServiceLock.readLock().unlock();
             */
            return (WebServiceEntity<T>)getWs();
        }
        
        if (SynchronousTerminalDataWebService.class.isAssignableFrom(webService))
        {
            
            /*
             * synchronousTerminalDataWebServiceLock.readLock().lock();
             * 
             * if (null == synchronousTerminalDataWebService || null ==
             * synchronousTerminalDataWebService.getWebService()) {
             * synchronousTerminalDataWebServiceLock.readLock().unlock();
             * basicDataQueryWebServiceLock.writeLock().lock(); if (null == synchronousTerminalDataWebService || null ==
             * synchronousTerminalDataWebService.getWebService()) { synchronousTerminalDataWebService =
             * CloudSynchronizeAuth(); } synchronousTerminalDataWebServiceLock.readLock().lock();
             * synchronousTerminalDataWebServiceLock.writeLock().unlock(); }
             * synchronousTerminalDataWebServiceLock.readLock().unlock();
             */
            return (WebServiceEntity<T>)CloudSynchronizeAuth();
        }
        
        return null;
    }
    
    private WebServiceEntity<DataAnalysisWebService> getDataAnalysisWs()
    {
        
        DataAnalysisWebService service = null;
        
        WebServiceEntity<DataAnalysisWebService> entity = new WebServiceEntity<DataAnalysisWebService>();
        
        String accessTock = null;
        URL url = null;
        try
        {
            List<WebServiceIp> list =
                rPNodeDataCache.getWebServiceCache(CacheKeyConstants.WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY);
            if (!StringUtil.isEmpty(list))
            {
                accessTock = list.get(0).getIdentifies();
                String ip = list.get(0).getServiceIp();
                try
                {
                    url = getUrl("http://" + ip + "/DAnalysisWS?wsdl");
                    log.warn("调用位置云接口地址：" + url.toString());
                    
                    DataAnalysisWebServiceService dataAnalysisWebServiceService =
                        new DataAnalysisWebServiceService(url);
                    
                    if (dataAnalysisWebServiceService != null)
                    {
                        service = dataAnalysisWebServiceService.getDataAnalysisWebServicePort();
                    }
                    
                    entity.setWebService(service);
                    accessTocken = Long.parseLong((accessTock == null || accessTock.equals("null")) ? "0" : accessTock);
                    
                    entity.setAccessTocken(accessTocken);
                }
                catch (Exception e)
                {
                    log.warn("调用位置云接口出错，方法：getDataAnalysisWs", e);
                }
            }
        }
        catch (Exception e)
        {
            log.warn("调用位置云接口出错，方法：getDataAnalysisWs", e);
        }
        return entity;
    }
    
    private WebServiceEntity<BasicDataQueryWebService> getWs()
    {
        
        BasicDataQueryWebService bdqws = null;
        
        WebServiceEntity<BasicDataQueryWebService> entity = new WebServiceEntity<BasicDataQueryWebService>();
        String accessTock = null;
        
        URL url = null;
        try
        {
            List<WebServiceIp> list =
                rPNodeDataCache.getWebServiceCache(CacheKeyConstants.WEB_SERVICE_URL_WEBSERVICE_SERVERIDENTIFIES_KEY);
            if (!StringUtil.isEmpty(list))
            {
                
                String key = list.get(0).getIdentifies();
                String ip = list.get(0).getServiceIp();
                
                if (accessTocken == Long.parseLong(key) && bdqws != null)
                {
                    
                    entity.setWebService(bdqws);
                    entity.setAccessTocken(accessTocken);
                    return entity;
                }
                accessTock = key;
                try
                {
                    url = getUrl("http://" + ip + "/BasicDQWS?wsdl");
                    log.warn("位置云webservice：" + "http://" + ip + "/BasicDQWS?wsdl");
                }
                catch (MalformedURLException e)
                {
                    log.warn("调用位置云接口出错，方法：getWs");
                }
                
                BasicDataQueryWebServiceService basicDataQueryWebServiceService =
                    new BasicDataQueryWebServiceService(url);
                if (basicDataQueryWebServiceService != null)
                {
                    bdqws = basicDataQueryWebServiceService.getBasicDataQueryWebServicePort();
                }
                
                entity.setWebService(bdqws);
            }
            accessTocken = Long.parseLong((accessTock == null || accessTock.equals("null")) ? "0" : accessTock);
            entity.setAccessTocken(accessTocken);
        }
        catch (Exception e)
        {
            log.error("调用位置云接口出错，方法：getWs" , e);
        }
        return entity;
    }
    
    private WebServiceEntity<SynchronousTerminalDataWebService> CloudSynchronizeAuth()
    {
        WebServiceEntity<SynchronousTerminalDataWebService> synchronousTerminalDataWebService =
            new WebServiceEntity<SynchronousTerminalDataWebService>();
        SynchronousTerminalDataWebService services = null;
        
        URL url = null;
        List<WebServiceIp> list =
            rPNodeDataCache.getWebServiceCache(CacheKeyConstants.WEB_SERVICE_URL_SYNCHRONIZEADDRESS_KEY);
        if (!StringUtil.isEmpty(list))
        {
            String ip = list.get(0).getServiceIp();
            try
            {
                url = getUrl("http://" + ip + "/TerminalWS?wsdl");
                
                TerminalWS terminalWS = new TerminalWS(url);
                if (terminalWS != null)
                {
                    services = terminalWS.getTerminalPort();
                }
                synchronousTerminalDataWebService.setWebService(services);
            }
            catch (MalformedURLException e)
            {
                log.warn("调用位置云出错，调用方法:CloudSynchronizeAuth");
            }
            
            // 从配置文件获取用户名和密码进行鉴权，获取动态鉴权码
            String cloudUserName = userName;
            String cloudPassword = encrypt(passWord.toString(), 32);
            String acckey = null;
            try
            {
                if (services != null)
                {
                    acckey = services.dataSynchronizeAuth(cloudUserName, cloudPassword);
                    synchronousTerminalDataWebService.setAcckey(acckey);
                }
            }
            catch (Exception e)
            {
                log.warn("调用位置云出错，调用方法:CloudSynchronizeAuth", e);
            }
        }
        return synchronousTerminalDataWebService;
    }
    
    /**
     * MD5 16或32位加密
     *
     * @param message
     * @param x int 采用多少位的加密方式(16或者32)
     *
     * @return String 加密后的密文
     */
    private String encrypt(String message, int x)
    {
        try
        {
            // 提供了消息摘要算法,应用MD5加密
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(message.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++)
            {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            // 机密成32位
            if (x == 32)
            {
                return buf.toString();
                // 加密成16为
            }
            else if (x == 16)
            {
                return buf.toString().substring(8, 24);
            }
        }
        catch (NoSuchAlgorithmException e)
        {
            log.error("异常", e);
        }
        return null;
    }
    
    /**
     * @param urlStr
     * @return
     * @throws MalformedURLException
     */
    private URL getUrl(String urlStr)
        throws MalformedURLException
    {
        URL url = new URL(null, urlStr, new URLStreamHandler()
        {
            @Override
            protected URLConnection openConnection(URL url)
                throws IOException
            {
                URL clone_url = new URL(url.toString());
                HttpURLConnection clone_urlconnection = (HttpURLConnection)clone_url.openConnection();
                clone_urlconnection.setRequestMethod("POST");
                // TimeOut settings
                clone_urlconnection.setConnectTimeout(connectTimeout);
                clone_urlconnection.setReadTimeout(connectTimeout);
                return (clone_urlconnection);
            }
        });
        return url;
    }
}
