package com.sjony.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by sjony on 2017/12/4.
 */
public class JedisUtil {

    private static Logger logger= LoggerFactory.getLogger(JedisUtil.class);

    @Autowired
    private static JedisPool jedisPool;

    public static final int EXPIRE = 5 * 60;

    public static final String LOCKED = "TRUE";

    /**
     * 从redis连接池取得实例
     * @return
     */
    public static Jedis getJedis() {
        Jedis jedis;
        jedis = jedisPool.getResource();
        return jedis;
    }

}
