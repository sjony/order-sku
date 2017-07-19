package com.sjony.cache;

import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: redis客户端
 * @Create on: 2017/7/18 下午4:52 
 *
 * @author shujiangcheng
 */
public class RedisClient implements CacheClient {

    private RedisTemplate redisTemplate;

    public RedisClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <T> T get(String key) {
        return (T) redisTemplate.boundValueOps(key).get();
    }

    @Override
    public void put(String key, Object value) {
        put(key, 0, value);
    }

    @Override
    public void put(String key, int exp, Object value) {
        redisTemplate.opsForValue().set(key, value, exp);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
