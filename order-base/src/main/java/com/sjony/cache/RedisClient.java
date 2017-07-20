package com.sjony.cache;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jca.cci.core.support.CciDaoSupport;

import java.util.Map;

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

    /**
     *
     *  @author sjony
     *  @description :获取一般value
     *  @CreateTime : 0:12 2017/7/21
     *  @param
     */
    @Override
    public <T> T getValue(String key) {

        return (T) redisTemplate.boundValueOps(key).get();
    }

    /**
     *
     *  @author sjony
     *  @description :插入一般value
     *  @CreateTime : 0:11 2017/7/21
     *  @param
     */
    @Override
    public void putValue(String key, Object value) {
        putValue(key, 0, value);
    }

    /**
     *
     *  @author sjony
     *  @description :插入一般value（设置时间）
     *  @CreateTime : 0:11 2017/7/21
     *  @param
     */
    @Override
    public void putValue(String key, int exp, Object value) {
        redisTemplate.opsForValue().set(key, value, exp);
    }

    /**
     * @author jshu
     * @Description:删除缓存
     * @CreateTime : 0:24 2017/7/21
     * @param key
     *
     */
    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * @author jshu
     * @Description:获取key对应的整个map
     * @CreateTime : 0:25 2017/7/21
     * @param key
     *
     */
    @Override
    public <K,V>Map<K,V> getMap(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    @Override
    public void putMap(String key, Map value) {
        redisTemplate.opsForHash().putAll(key, value);
    }

    @Override
    public void putMapValue(String key, Object hk, Object value) {
        redisTemplate.opsForHash().put( key, hk, value);
    }


}
