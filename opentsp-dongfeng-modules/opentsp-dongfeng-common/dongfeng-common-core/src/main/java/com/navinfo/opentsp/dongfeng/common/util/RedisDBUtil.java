package com.navinfo.opentsp.dongfeng.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 哨兵模式连接redis集群
 *
 * @author lichao
 */
public class RedisDBUtil implements Serializable {

    private static Logger logger = LoggerFactory.getLogger(RedisDBUtil.class);
    private Pool pool;
    /**
     * redis db索引
     */
    private int dbIndex = 0;
    /**
     * Redis实例
     */
    private static volatile RedisDBUtil instance = null;

    public static RedisDBUtil getInstance() {
        if (instance == null) {
            throw new RuntimeException("Do not initialize!!!");
        }
        return instance;
    }

    /**
     * 实例化redis对象
     *
     * @param conf 配置参数
     */
    public static void buildInstance(Map conf) throws Exception {
        if (instance == null) {
            synchronized (RedisDBUtil.class) {
                if (instance == null) {
                    RedisDBUtil local = new RedisDBUtil();
                    local.init(conf);
                    instance = local;
                }
            }
        }
    }

    /**
     * 初始化
     *
     * @param conf 配置参数
     */
    private void init(Map conf) {
        logger.info("开始初始化redis连接池");
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(Integer.parseInt((String) (conf.get("redis.max_idle"))));
        config.setTestOnBorrow(Boolean.valueOf((String) (conf.get("redis.isTest"))));
        config.setMaxTotal(Integer.parseInt((String) (conf.get("redis.maxTotal"))));
        String password = (String) conf.get("redis.password");
        int timeOut = Integer.parseInt((String) (conf.get("redis.timeout")));
        dbIndex = Integer.parseInt((String) (conf.get("redis.db.index")));
        if (conf.get("redis.server") != null && conf.get("redis.node") != null) {
            String[] addressArr = ((String) (conf.get("redis.server"))).split(",");
            String node = (String) (conf.get("redis.node"));
            Set<String> sentinels = new HashSet<String>();
            for (String str : addressArr) {
                sentinels.add(str);
            }
            if (StringUtils.isEmpty(password)) {
                pool = new JedisSentinelPool(node, sentinels, config, timeOut);
            } else {
                pool = new JedisSentinelPool(node, sentinels, config, timeOut, password);
            }
        } else if (conf.get("redis.host") != null && conf.get("redis.port") != null) {
            String host = (String) conf.get("redis.host");
            int port = Integer.parseInt((String) conf.get("redis.port"));
            if (StringUtils.isEmpty(password)) {
                pool = new JedisPool(config, host, port, timeOut);
            } else {
                pool = new JedisPool(config, host, port, timeOut, password);
            }
        } else {
            logger.error("参数配置错误，如果使用哨兵连接池需要配置redis.server与redis.node");
            logger.error("如果使用普通连接池需要配置redis.host与redis.port");
        }
        logger.info("成功初始化redis连接池");
    }

    public Jedis getRedisTemplate() {
        Jedis resource = (Jedis) pool.getResource();
        resource.select(dbIndex);
        return resource;
    }

    public Set keys(String pattern) {
        Jedis jedis = null;
        Set<String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.keys(pattern);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public void setValue(byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }


    public void setValue(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 保存list到redis
     *
     * @param key   键
     * @param value 字符串数组
     */
    public void addToList(String key, String... value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.sadd(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 返回redis中list
     *
     * @param key 键
     * @return list value
     */
    public Set<String> getList(String key) {
        Jedis jedis = null;
        Set<String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.smembers(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }


    public void delKey(String key) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.del(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void setExpireValue(byte[] key, byte[] value, int second) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            jedis.expire(key, second);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void setExpireValue(String key, int second) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.expire(key, second);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void setExpireValue(String key, String value, int second) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.set(key, value);
            jedis.expire(key, second);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 设置redis中map的指定key的value
     *
     * @param mapName
     * @param key
     * @param value
     */
    public void setHashValue(String mapName, String key, String value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hset(mapName, key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void setHashValue(byte[] mapName, byte[] key, byte[] value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hset(mapName, key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public long incrValue(String key) {
        Jedis jedis = null;
        long r = 0;
        try {
            jedis = getRedisTemplate();
            r = jedis.incrBy(key, 1);
            closeJedis(jedis);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return r;
    }

    /**
     * 将redis中map的value加1，由redis保证原子性
     *
     * @param mapName
     * @param key
     */
    public long incrHashValue(String mapName, String key) {
        Jedis jedis = null;
        long r = 0;
        try {
            jedis = getRedisTemplate();
            r = jedis.hincrBy(mapName, key, 1);
            closeJedis(jedis);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return r;
    }

    /**
     * 将redis中map的value加上指定值，由redis保证原子性
     *
     * @param mapName
     * @param key
     * @param value
     */
    public void incrHashValue(String mapName, String key, long value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hincrBy(mapName, key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 将redis中map的value加上指定值，由redis保证原子性
     *
     * @param mapName
     * @param key
     * @param value
     */
    public void incrHashValue(String mapName, String key, double value) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hincrByFloat(mapName, key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void delHashValue(String mapName, String key) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
//			jedis.hset(mapName, key, value);
            jedis.hdel(mapName, key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public byte[] getValue(byte[] key) {
        Jedis jedis = null;
        byte[] re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.get(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public String getValue(String key) {
        Jedis jedis = null;
        String re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.get(key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public String getHashValue(String mapName, String key) {
        Jedis jedis = null;
        String re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hget(mapName, key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public Map<String, String> getHashValues(String mapName) {
        Jedis jedis = null;
        Map<String, String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hgetAll(mapName);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public byte[] getHashValue(byte[] mapName, byte[] key) {
        Jedis jedis = null;
        byte[] re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hget(mapName, key);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }


    /**
     * 获取key对应的数据list
     *
     * @param key   key
     * @param start 开始下标
     * @param end   结束下标
     * @return List<byte[]>
     * @author hanzhu
     */
    public List<byte[]> getValueList(byte[] key, int start, int end) {
        Jedis jedis = null;
        List<byte[]> list = null;
        try {
            jedis = getRedisTemplate();
            list = jedis.lrange(key, start, end);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return list;
    }

    /**
     * value类型为list
     *
     * @param key   key
     * @param value value
     * @author hanzhu
     */
    public void setValueListObject(byte[] key, byte[] value) {

        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.lpush(key, value);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    /**
     * 删除value(Map)中指定的key
     *
     * @param mapName mapName
     * @param fields  key
     */
    public void delMKey(String mapName, String... fields) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hdel(mapName, fields);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public Map<String, String> getAllKeys(String mapName) {
        Jedis jedis = null;
        Map<String, String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hgetAll(mapName);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public Map<byte[], byte[]> getAllKeys(byte[] mapName) {
        Jedis jedis = null;
        Map<byte[], byte[]> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hgetAll(mapName);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    // ZhaiCY Add-Start
    public List<String> getMapValue(String key, String... fields) {
        Jedis jedis = null;
        List<String> re = null;
        try {
            jedis = getRedisTemplate();
            re = jedis.hmget(key, fields);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
        return re;
    }

    public void setMapValue(String key, Map<String, String> map) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hmset(key, map);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    public void setMapValue(byte[] key, Map<byte[], byte[]> map) {
        Jedis jedis = null;
        try {
            jedis = getRedisTemplate();
            jedis.hmset(key, map);
            closeJedis(jedis);
        } catch (Exception e) {
            e.printStackTrace();
            closeBreakJedis(jedis);
        }
    }

    // ZhaiCY Add-End.

    /**
     * 正常连接池回收
     *
     * @param jedis
     */
    public void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    /**
     * 异常连接池回收
     *
     * @param jedis
     */
    public void closeBreakJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
