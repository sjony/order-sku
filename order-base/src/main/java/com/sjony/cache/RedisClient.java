package com.sjony.cache;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.script.ScriptExecutor;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.Jedis;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: redis客户端
 * @Create on: 2017/7/18 下午4:52 
 *
 * @author shujiangcheng
 */
public class RedisClient<K, V> implements CacheClient<K,V> {

    private RedisTemplate<K, V> redisTemplate;


    public RedisClient(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    @Override
    public List<V> getValue(Collection<K> key) {
        return redisTemplate.opsForValue().multiGet(key);
    }

    /**
     * @Description: 插入一般的value，批量 
     * @Create on: 2017/7/24 下午2:07
     *
     * @author shujiangcheng
     */
    @Override
    public void putBatch(Map map) {
        redisTemplate.opsForValue().multiSet(map);
    }

    /**
     *
     *  @author sjony
     *  @description :插入一般value
     *  @CreateTime : 0:11 2017/7/21
     *  @param
     */
    @Override
    public void putValue(K key, V value) {
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
    public void putValue(K key, int exp, V value) {
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
        redisTemplate.delete((K) key);
    }

    /**
     * @author jshu
     * @Description:获取key对应的整个map
     * @CreateTime : 0:25 2017/7/21
     * @param key
     *
     */
    @Override
    public Map getMap(K key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * @Description:获取key对应的key value
     * @Create on: 2017/7/30 下午1:56
     * @author jshu
     *
     * @param key
     * @param hk
     *
     */
    @Override
    public <T> T getMapValue(K key, String hk, Class<T> type) {
        return (T) redisTemplate.opsForHash().get(key, hk);
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
    public void putMap(K key, Map value) {

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
    public void putMapValue(K key, Object hk, Object value) {
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
    public List<V> getList(K key) {
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
    public void pushLeft(K key, V value) {
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
    public void pushRight(K key, V value) {

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
    public V popLeft(K key) {

        return  redisTemplate.opsForList().leftPop(key);
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
    public V popRight(K key) {
        return  redisTemplate.opsForList().rightPop(key);
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
    public  Set<V> getSet(K key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * @Description: 新增set
     * @Create on: 2017/7/22 下午3:06
     *
     * @author shujiangcheng
     */
    @Override
    public void addSet(K key, V... value) {

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
    public  Set<V> intersect(K key, K key1) {
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
    public void addZSet(K key, V value, Double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    public void addZSet(K key, Set<ZSetOperations.TypedTuple<V>> set) {
        redisTemplate.opsForZSet().add(key, set);
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
    public void addScore(K key, V value, Double score) {
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
    public Set<V> range(K key, Long start, Long end) {

        return redisTemplate.opsForZSet().reverseRange(key, start, end);
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
    public  Set<V> reverseRange(K key, Long start, Long end) {
        return redisTemplate.opsForZSet().reverseRange(key, start, end);
    }

    /**
     * @Description:redis分布式锁
     * @Create on: 2017/7/30 下午2:11
     * @author jshu
     *
     * @param lockName
     *
     */
    @Override
    public boolean setNX(String lockName, String lockValue) {
        boolean res = redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                JdkSerializationRedisSerializer valueSerializer = new JdkSerializationRedisSerializer();
                byte[] key = serializer.serialize(lockName);
                byte[] value = valueSerializer.serialize(lockValue);
                //set not exits
                return connection.setNX(key, value);
            }
        });
        return res;
    }

    @Override
    public Object getSet(String key, String value) {
        Object result = false;

        result =  redisTemplate
                .execute(new RedisCallback<Object>() {
                    @Override
                    public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                StringRedisSerializer serializer =  new StringRedisSerializer();
                        RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                        byte[] data = connection.get(serializer.serialize(key));
                        byte[] valueByte = connection.get(serializer.serialize(value));
                        Object result = connection.getSet(data, valueByte);
                        connection.close();
                        return result;
                    }
                });


        return result;
    }

    /**
     * @Description: 是否有key这个缓存
     * @Create on: 2017/8/4 下午5:12 
     *
     * @author shujiangcheng
     */
    @Override
    public boolean hasKey(K key) {
        return redisTemplate.hasKey(key);
    }


}
