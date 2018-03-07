package com.navinfo.dongfeng.terminal.comm.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.navinfo.dongfeng.terminal.comm.common.StringUtils;
import com.navinfo.dongfeng.terminal.comm.common.util.json.JsonUtil;
import com.navinfo.dongfeng.terminal.comm.common.util.tcp.Convert;
import com.navinfo.dongfeng.terminal.comm.services.IRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangyu on 2016/12/13.
 */
@Service(value = "redisService")
public class RedisServiceImpl implements IRedisService
{
    @Autowired
    public StringRedisTemplate redisTemplate;
    
    private static final Logger logger = LoggerFactory.getLogger(RedisServiceImpl.class);
    
    /**
     * 存储json格式数据(普通对象类型)
     *
     * @param key
     * @param value
     * @throws JsonProcessingException
     */
    @Override
    public void saveObjectToJson(String key, Object value)
        throws JsonProcessingException
    {
        redisTemplate.opsForValue().set(key, JsonUtil.toJson(value));
    }
    
    /**
     * 存储为hash存储结构(普通对象类型)
     *
     * @param key1
     * @param key2
     * @param value
     */
    @Override
    public void saveObjectToJson(final String key1, final String key2, Object value)
        throws JsonProcessingException
    {
        redisTemplate.opsForHash().put(key1, key2, JsonUtil.toJson(value));
    }
    
    /**
     * 插入key并设置有效期(普通对象类型)
     *
     * @param key 键
     * @param value 值
     * @param liveTime 有效期 毫秒
     */
    
    @Override
    public void saveObjectToJson(String key, Object value, long liveTime)
        throws JsonProcessingException
    {
        redisTemplate.opsForValue().set(key, JsonUtil.toJson(value), liveTime, TimeUnit.MILLISECONDS);
    }
    
    /**
     * byte[] 存储为String类型数据
     *
     * @param key
     * @param value
     */
    @Override
    public void saveByteToString(String key, byte[] value)
    {
        redisTemplate.opsForValue().set(key, Convert.bytesToHexString(value));
    }
    
    /**
     * byte[] 存储为hash存储结构
     *
     * @param key1
     * @param key2
     * @param value
     */
    @Override
    public void saveByteToString(final String key1, final String key2, final byte[] value)
        throws JsonProcessingException
    {
        redisTemplate.opsForHash().put(key1, key2, JsonUtil.toJson(Convert.bytesToHexString(value)));
    }
    
    /**
     * 获取json类型数据(普通对象类型)
     *
     * @param key
     * @param elementType
     * @throws JsonProcessingException
     */
    @Override
    public <T> T getJson(String key, Class<T> elementType)
    {
        // update fwm 去掉trim()，如果出现NULL会报错
        return JsonUtil.fromJson(redisTemplate.opsForValue().get(key), elementType);
    }
    
    /**
     * 获取hash存储结构的一个元素(普通对象类型)
     *
     * @param key1
     * @param key2
     * @return
     */
    @Override
    public <T> T getJson(final String key1, final String key2, Class<T> elementType)
    {
        return JsonUtil.fromJson((String)redisTemplate.opsForHash().get(key1, key2), elementType);
    }
    
    /**
     * 获取hash存储结构的一个元素集合(普通对象类型)
     *
     * @param key
     * @param elementType
     * @return
     */
    @Override
    public <T> List<T> getAllListJson(final String key, Class<T> elementType)
    {
        List resultList = new ArrayList();
        
        List<Object> list = redisTemplate.opsForHash().values(key);
        for (Object object : list)
        {
            resultList.add(JsonUtil.fromJson((String)object, elementType));
        }
        return resultList;
    }
    
    /**
     * 获取String类型数据( byte[]类型)
     *
     * @param key
     * @return
     */
    @Override
    public byte[] getByte(String key)
    {
        
        return Convert.hexStringToBytes(redisTemplate.opsForValue().get(key));
    }
    
    /**
     * 获取hash存储结构的一个元素( byte[]类型)
     *
     * @param key1
     * @param key2
     * @return
     */
    @Override
    public byte[] getByte(final String key1, final String key2)
    {
        String s = (String)redisTemplate.opsForHash().get(key1, key2);
        if (!StringUtils.isEmpty(s))
        {
            return Convert.hexStringToBytes(s.replace("\"", ""));
        }
        return null;
    }
    
    /**
     * 获取hash存储结构的所有集合( byte[]类型)
     *
     * @param key
     * @return
     */
    @Override
    public List<byte[]> getAllListByte(final String key)
    {
        List<byte[]> resultList = new ArrayList<byte[]>();
        List<Object> list = redisTemplate.opsForHash().values(key);
        for (Object object : list)
        {
            resultList.add(Convert.hexStringToBytes(((String)object).replace("\"", "")));
        }
        return resultList;
    }
    
    /**
     * 按照key删除
     *
     * @param key
     */
    @Override
    public void del(String key)
    {
        redisTemplate.delete(key);
    }
    
    /**
     * 删除hash存储结构
     *
     * @param key1
     * @param key2
     */
    @Override
    public void del(final String key1, final String key2)
    {
        
        redisTemplate.opsForHash().delete(key1, key2);
    }
    
    /**
     * 模糊获取所有key
     *
     * @param key
     * @return
     */
    @Override
    public Set<String> getKeys(String key)
    {
        Set<String> keys = redisTemplate.keys(key);
        return keys;
    }
    
    /**
     * 模糊删除所有key
     *
     * @param keys
     */
    @Override
    public void delKeys(String keys)
    {
        // 删除redis记录
        Set<String> tb_set = this.getKeys(keys);
        
        // 判空
        if (!StringUtils.isEmpty(tb_set))
        {
            for (String key : tb_set)
            {
                del(key);
            }
        }
    }
    
}