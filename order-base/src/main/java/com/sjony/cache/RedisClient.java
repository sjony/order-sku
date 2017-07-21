package com.sjony.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Map;
import java.util.Set;

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

    /**
     * @Description:存入所有map
     * @Create on: 2017/7/21 上午10:24
     * @author jshu
     *
     * @param key
     * @param value
     *
     */
    @Override
    public void putMap(String key, Map value) {

        redisTemplate.opsForHash().putAll(key, value);
    }

    /**
     * @Description:针对性的存入map
     * @Create on: 2017/7/21 上午10:24
     * @author jshu
     *
     * @param key
     * @param hk
     * @param value
     *
     */
    @Override
    public void putMapValue(String key, Object hk, Object value) {
        redisTemplate.opsForHash().put( key, hk, value);
    }

    /**
     * @Description:获取list
     * @Create on: 2017/7/21 上午10:40
     * @author jshu
     *
     * @param key
     *
     */
    @Override
    public <T> List<T> getList(String key) {
        return redisTemplate.opsForList().range(key, 0, redisTemplate.opsForList().size(key));
    }

    /**
     * @Description:左插入（list)
     * @Create on: 2017/7/21 下午1:27
     * @author jshu
     *
     * @param key
     * @param value
     *
     */
    @Override
    public void pushLeft(String key, Object value) {
        redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * @Description:右插入（list)
     * @Create on: 2017/7/21 下午1:31
     * @author jshu
     *
     * @param key
     * @param value
     *
     */
    @Override
    public void pushRight(String key, Object value) {

        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * @Description:左抛出（List)
     * @Create on: 2017/7/21 下午1:31
     * @author jshu
     *
     * @param key
     *
     */
    @Override
    public <T> T popLeft(String key) {

        return (T) redisTemplate.opsForList().leftPop(key);
    }

    /**
     * @Description:右抛出
     * @Create on: 2017/7/21 下午1:32
     * @author jshu
     *
     * @param key
     *
     */
    @Override
    public <T> T popRight(String key) {

        return (T) redisTemplate.opsForList().rightPop(key);
    }

    /**
     * @Description:获取数据(set)
     * @Create on: 2017/7/21 下午2:00
     * @author jshu
     *
     * @param key
     *
     */
    @Override
    public <T> Set<T> getSet(String key) {

        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @Description:取交集(set)
     * @Create on: 2017/7/21 下午2:02
     * @author jshu
     *
     * @param key
     * @param key1
     *
     */
    @Override
    public <T> Set<T> intersect(String key, String key1) {
        return redisTemplate.opsForSet().intersect(key, key1);
    }

    /**
     * @Description:新增值（有序集合，根据score）
     * @Create on: 2017/7/21 下午2:10
     * @author jshu
     *
     * @param key
     * @param value
     * @param score
     *
     */
    @Override
    public void addZSet(String key, Object value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * @Description:新增比较数
     * @Create on: 2017/7/21 下午2:14
     * @author jshu
     *
     * @param key
     * @param value
     * @param score
     *
     */
    @Override
    public void addScore(String key, Object value, Double score) {
        redisTemplate.opsForZSet().incrementScore(key, value, score);
    }

    /**
     * @Description:获取一定的范围的值(正序)
     * @Create on: 2017/7/21 下午2:16
     * @author jshu
     *
     * @param key
     * @param start
     * @param end
     *
     */
    @Override
    public <T> Set<T> range(String key, Long start, Long end) {

        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * @Description:获取一定的范围的值(逆序)
     * @Create on: 2017/7/21 下午2:16
     * @author jshu
     *
     * @param key
     * @param start
     * @param end
     *
     */
    @Override
    public <T> Set<T> reverseRange(String key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }


}
