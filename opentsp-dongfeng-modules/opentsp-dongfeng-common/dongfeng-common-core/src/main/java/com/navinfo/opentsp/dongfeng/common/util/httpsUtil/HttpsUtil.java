package com.navinfo.opentsp.dongfeng.common.util.httpsUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

/**
 * Created by zhangy on 2016/4/26.
 */
public class HttpsUtil
{
    /**
     * @param args
     */
    
    protected static final Logger logger = LoggerFactory.getLogger(HttpsUtil.class);
    
    public static void main(String[] args)
        throws Exception
    {
        String address =
            "https://10.30.10.103/xdata/invoke?value={%22biz%22:%22BIZ_02%22,%22param%22:{%22tripId%22:%22d915bbc9537e42ac9fdf2d133e3e407b%22}}";
        System.out.println(callService(address));;
    }

    static HostnameVerifier hv = new HostnameVerifier() {
        public boolean verify(String urlHostName, SSLSession session) {
            System.out.println("Warning: URL Host: " + urlHostName + " vs. "
                    + session.getPeerHost());
            return true;
        }
    };

    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext
                .getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc
                .getSocketFactory());
    }

    static class miTM implements javax.net.ssl.TrustManager,
            javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(
                java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(
                java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }
    
    /**
     * 调用客户端
     *
     * @param address：客户端url（带参数）
     * @return
     * @throws IOException
     */
    public static String callService(String address)
            throws Exception
    {
        trustAllHttpsCertificates();
        HttpsURLConnection.setDefaultHostnameVerifier(hv);

        Resource resource = new ClassPathResource("web/tomcat.https.properties");
        InputStream is = null;
        Properties props = new Properties();
        String json = "";
        BufferedReader bufferedReader = null;
        try
        {
            is = resource.getInputStream();
            props.clear();
            props.load(is);
            // 设置客户端信任证书
            System.setProperty("javax.net.ssl.trustStore",
                (System.getProperty("java.home") + props.getProperty("https.keystore")));
            System.setProperty("javax.net.ssl.trustStorePassword", props.getProperty("https.keystore.password"));
            
            // 主机名称验证策略
            HttpsURLConnection.setDefaultHostnameVerifier(new TrustAnyVerifier());
            
            URL url = new URL(address);
            URLConnection conn = url.openConnection();
            InputStream input = conn.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(input));
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null)
            {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            json = new String(temp.toString().getBytes(), "utf-8");
            
            // System.out.println(json);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        }
        finally
        {
            if (is != null)
            {
                is.close();
            }
            
            if (bufferedReader != null)
            {
                bufferedReader.close();
            }
        }
        return json;
    }
}
