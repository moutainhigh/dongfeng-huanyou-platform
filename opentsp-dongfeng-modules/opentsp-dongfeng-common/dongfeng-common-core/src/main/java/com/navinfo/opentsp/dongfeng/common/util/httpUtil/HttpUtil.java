package com.navinfo.opentsp.dongfeng.common.util.httpUtil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.opentsp.dongfeng.common.exception.BaseServiceException;
import com.navinfo.opentsp.dongfeng.common.util.JsonUtil;
import com.navinfo.opentsp.dongfeng.common.util.ObjUtils;
import com.navinfo.opentsp.dongfeng.common.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class HttpUtil
{
    
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    
    static final String BOUNDARY = "----MyFormBoundarySMFEtUYQG6r5B920";
    
    private static final String APPLICATION_JSON = "application/json";
    
    private static final String CONTENT_TYPE_TEXT_JSON = "text/json";
    
    private static final String CHARSET_UTF_8 = "UTF-8";
    
    private static final int RESULT_SUCCESS_CODE = 200;
    
    public static String postRequest(String url, JSONObject param) throws BaseServiceException
    {
        logger.info("post json request url : {}, param : {}", url, param);
        String resp = "";
        try
        {
            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);
            // 绑定到请求 Entry
            StringEntity se = new StringEntity(param.toString(), CHARSET_UTF_8);
            se.setContentType(CONTENT_TYPE_TEXT_JSON);
            se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
            httpPost.setEntity(se);
            // 发送请求
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == RESULT_SUCCESS_CODE)
            {
                // 得到应答的字符串，这也是一个 JSON 格式保存的数据
                resp = EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF_8);
            }
            else
            {
                logger.warn("post request url : {}, response's errorCode is : {}", url, httpResponse.getStatusLine()
                    .getStatusCode());
            }
        }
        catch (UnsupportedEncodingException e)
        {
            logger.error("HttpUtil#postRequest#UnsupportedEncodingException is error", e);
            throw new BaseServiceException(e);
        }
        catch (ClientProtocolException e)
        {
            logger.error("HttpUtil#postRequest#ClientProtocolException is error", e);
            throw new BaseServiceException(e);
        }
        catch (IOException e)
        {
            logger.error("HttpUtil#postRequest#IOException is error", e);
            throw new BaseServiceException(e);
        }
        logger.info("post request url : {}, response : {}", url, resp.toString());
        return resp;
    }
    
    public static String get(String url, String param, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return get(url, param, headers);
    }
    
    public static String get(String url, Object object)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        Map<String, Object> paramMap = ObjUtils.toPropertyMap(object);
        StringBuffer sb = new StringBuffer();
        for (String filedName : paramMap.keySet())
        {
            if (paramMap.get(filedName) instanceof Collection)
            {
                if (paramMap.get(filedName) != null)
                {
                    String param = paramMap.get(filedName).toString();
                    if (StringUtil.isNotEmpty(param) && StringUtil.isNotEq(param.toLowerCase().trim(), "null"))
                    {
                        sb.append(filedName).append("=").append(param.substring(1, param.length() - 1)).append("&");
                    }
                }
                // else
                // {
                // sb.append(filedName).append("=").append("null").append("&");
                // }
            }
            else
            {
                if (paramMap.get(filedName) != null)
                {
                    String param = paramMap.get(filedName).toString();
                    if (StringUtil.isNotEmpty(param) && StringUtil.isNotEq(param.toLowerCase().trim(), "null"))
                    {
                        sb.append(filedName).append("=").append(paramMap.get(filedName)).append("&");
                    }
                }
            }
        }
        String param = sb.toString().substring(0, sb.length() - 1);
        logger.info("param:" + param);
        return get(url, param, headers);
    }
    
    public static String getHttps(String url, String param, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return getHttps(url, param, headers);
    }
    
    public static String get(String url, String param, Map<String, String> headers)
        throws IOException
    {
        BufferedReader br = null;
        OutputStream os = null;
        
        logger.info("get request url : {}, param : {}", url, param);
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(10000);// 超时时间10s
        conn.setConnectTimeout(10000);// 连接超时时间10s
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        if (!StringUtils.isEmpty(param))
        {
            os = conn.getOutputStream();
            os.write(param.getBytes("UTF-8"));
        }
        
        conn.connect();
        
        StringBuilder sb = new StringBuilder();
        int code = conn.getResponseCode();
        try
        {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
            if (os != null)
            {
                os.close();
            }
        }
        
        if (code != 200)
        {
            logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
            throw new IOException("request url [ " + url + " ] returns code [ " + code + " ], message [ "
                + sb.toString() + " ]");
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    /**
     * @param url
     * @return
     * @throws IOException
     */
    public static InputStream getHttpInputStream(String url)
        throws IOException
    {
        logger.info("get request url : {}, param : {}", url);
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.connect();
        int code = conn.getResponseCode();
        if (code != 200)
        {
            logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
            throw new IOException("request url [ " + url + " ] returns code [ " + code + " ]");
        }
        return conn.getInputStream();
    }
    
    public static String getWithAll(String url, String param, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return getWithAll(url, param, headers);
    }
    
    public static String getWithAll(String url, String param, Map<String, String> headers)
        throws IOException
    {
        logger.info("get request url : {}, param : {}", url, param);
        URL uri = new URL(url + param);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        // if (!StringUtils.isEmpty(param)) {
        // OutputStream os = conn.getOutputStream();
        // os.write(param.getBytes("UTF-8"));
        // os.close();
        // }
        
        conn.connect();
        
        StringBuilder sb = new StringBuilder();
        int code = conn.getResponseCode();
        BufferedReader br = null;
        try
        {
            if (code == 200)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            else
            {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    public static String getHttps(String url, String param, Map<String, String> headers)
        throws IOException
    {
        logger.info("get request url : {}, param : {}", url, param);
        URL uri = new URL(url);
        HttpsURLConnection conn = (HttpsURLConnection)uri.openConnection();
        
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        if (!StringUtils.isEmpty(param))
        {
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes("UTF-8"));
            os.close();
        }
        
        conn.connect();
        
        StringBuilder sb = new StringBuilder();
        int code = conn.getResponseCode();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
        
        if (code != 200)
        {
            logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
            throw new IOException("request url [ " + url + " ] returns code [ " + code + " ], message [ "
                + sb.toString() + " ]");
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    public static InputStream getInputStream(String url, String param, Map<String, String> headers)
    {
        logger.info("get request url : {}, param : {}", url, param);
        InputStream result = null;
        URL uri = null;
        HttpURLConnection conn = null;
        
        try
        {
            uri = new URL(url);
            conn = (HttpURLConnection)uri.openConnection();
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
            conn.setReadTimeout(30000);
            conn.setRequestProperty("contentType", "utf-8");
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            
            if (headers != null && headers.size() > 0)
            {
                for (Map.Entry<String, String> entry : headers.entrySet())
                {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            
            if (!StringUtils.isEmpty(param))
            {
                OutputStream os = conn.getOutputStream();
                os.write(param.getBytes("UTF-8"));
                os.close();
            }
            
            conn.connect();
            
            int code = conn.getResponseCode();
            
            if (code == 200)
            {
                result = conn.getInputStream();
            }
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (ProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        
        return result;
    }
    
    public static String post(String url, String param, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return post(url, param, headers);
    }
    
    public static String mapToParams(Map<String, Object> params)
    {
        if (params == null || params.size() == 0)
            return "";
        
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : params.entrySet())
        {
            sb.append("&").append(entry.getKey()).append("=").append(String.valueOf(entry.getValue()));
        }
        
        if (sb.length() > 0)
        {
            sb.deleteCharAt(0);
            return sb.toString();
        }
        
        return "";
    }
    
    public static String post(String url, String param, Map<String, String> headers)
        throws IOException
    {
        logger.info("post request url : {}, param : {}", url, param);
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("contentType", "utf-8");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        if (!StringUtils.isEmpty(param))
        {
            OutputStream os = conn.getOutputStream();
            os.write(param.getBytes("UTF-8"));
            os.close();
        }
        
        conn.connect();
        
        int code = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
                br.close();
        }
        
        if (code != 200)
        {
            logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
            throw new IOException("request url [ " + url + " ] returns code [ " + code + " ], message [ "
                + sb.toString() + " ]");
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    public static String postJson(String url, String json, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return postJson(url, json, headers);
    }
    
    public static String postJson(String url, String json, Map<String, String> headers)
        throws IOException
    {
        logger.info("post json request url : {}, param : {}", url, json);
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        if (!StringUtils.isEmpty(json))
        {
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
        }
        
        conn.connect();
        
        int code = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try
        {
            br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
                br.close();
        }
        
        if (code != 200)
        {
            logger.error("request url [ " + url + " ] returns code [ " + code + " ]");
            throw new IOException("request url [ " + url + " ] returns code [ " + code + " ], message [ "
                + sb.toString() + " ]");
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    /**
     * 文件上传
     *
     * @param contentByte 文件内容
     * @param fileName 文件名称
     * @param mimetype 文件类型
     * @param uuid
     * @param type 类型
     * @return
     */
    public static String uploadFile(String requestUrl, byte[] contentByte, String fileName, String mimetype,
        String uuid, String type)
    {
        String httpResult = null;
        String url = null;
        Map<String, String> mapParam = new HashMap<>();
        try
        {
            // {uuid}/create
            String uploadUrl = requestUrl + uuid + "/" + type;
            mapParam.put("data", Base64Utils.encodeToString(contentByte));
            Map<String, String> attributesParam = new HashMap<>();
            attributesParam.put("filename", fileName);
            Map<String, String> headerpParam = new HashMap<>();
            headerpParam.put("Accept", "application/json");
            headerpParam.put("mimetype", mimetype);
            httpResult = postJson(uploadUrl, JsonUtil.toJson(mapParam), headerpParam);
            Map<String, Object> mapResult = JsonUtil.toMap(httpResult);
            if (mapResult.containsKey("resultCode") && (int)mapResult.get("resultCode") == 200)
            {
                url = (String)mapResult.get("url");
                if (StringUtil.isEmpty(url))
                {
                    httpResult = url;
                    logger.debug(fileName + " upload fail!" + httpResult);
                }
                else
                {
                    url = url + "?mimetype=" + mimetype;
                    logger.debug(fileName + " upload success!" + httpResult);
                }
            }
            else
            {
                logger.debug(fileName + " upload fail!" + httpResult);
            }
            logger.debug(JsonUtil.toJson(mapParam));
        }
        catch (Exception e)
        {
            try
            {
                logger.error(JsonUtil.toJson(mapParam));
            }
            catch (JsonProcessingException e1)
            {
                logger.error(fileName + " json formatter error!", e);
            }
            logger.error(fileName + " upload fail!" + httpResult, e);
        }
        return url;
    }
    
    public static String postJsonWithAll(String url, String json, String token)
        throws IOException
    {
        Map<String, String> headers = new HashMap<>();
        if (!StringUtil.isEmpty(token))
        {
            headers.put("token", token);
        }
        return postJsonWithAll(url, json, headers);
    }
    
    public static String postJsonWithAll(String url, String json, Map<String, String> headers)
        throws IOException
    {
        logger.info("post json request url : {}, param : {}", url, json);
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        
        if (!StringUtils.isEmpty(json))
        {
            OutputStream os = conn.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.close();
        }
        
        conn.connect();
        
        int code = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try
        {
            if (code == 200)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            else
            {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
                br.close();
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
    public static String postWithRequest(String url, Map<String, String> param, Map<String, String> headers)
        throws Exception
    {
        logger.info("post json request url : {}, param : {}", url, JsonUtil.toJson(param));
        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection)uri.openConnection();
        
        conn.setRequestMethod("POST");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setReadTimeout(30000);
        // conn.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        // conn.setRequestProperty("accept", "*/*");
        // conn.setRequestProperty("connection", "Keep-Alive");
        // conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        
        if (headers != null && headers.size() > 0)
        {
            for (Map.Entry<String, String> entry : headers.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        if (param != null)
        {
            OutputStream os = conn.getOutputStream();
            StringBuffer params = new StringBuffer();
            for (String key : param.keySet())
            {
                params.append("&").append(key).append("=").append(URLEncoder.encode(param.get(key), "UTF-8"));
            }
            params.deleteCharAt(0);
            String sendInfo = params.toString();
            os.write(sendInfo.getBytes("UTF-8"));
            os.flush();
            os.close();
        }
        
        conn.connect();
        
        int code = conn.getResponseCode();
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try
        {
            if (code == 200)
            {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            }
            else
            {
                br = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
            }
            String msg = null;
            while ((msg = br.readLine()) != null)
            {
                sb.append(msg).append("\n");
            }
            
            if (sb.length() > 0)
            {
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        finally
        {
            if (br != null)
                br.close();
        }
        
        logger.info("get request url : {}, response : {}", url, sb.toString());
        return sb.toString();
    }
    
}
