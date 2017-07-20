package com.sjony.cache;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;
import java.util.Set;

/**
 * @Description: redis缓存
 * @Create on: 2017/7/18 下午5:10 
 *
 * @author shujiangcheng
 */
public class OrderRedisService implements  ICache{

    private ThreadLocal<List<String>> threadLocal = new ThreadLocal<List<String>>();

    private RedisClient redisClient;

    private static Set<String> keySet = Sets.newHashSet();

    private static Logger logger = LoggerFactory
            .getLogger(OrderRedisService.class);

    public OrderRedisService(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        T value = null;
        try {
            value = redisClient.getValue(key);
            if (logger.isDebugEnabled()) {
                logger.debug("get cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return value;
    }

    @Override
    public <T> List<T> getBatch(List<String> keyList, Class<T> type) {
        List<T> result = Lists.newArrayList();
        List<String> needKeyList = Lists.newArrayList(keyList);
        try {
            T value = null;
            for(String key : keyList) {
                value = redisClient.getValue(key);
                if (logger.isDebugEnabled()) {
                    logger.debug("get cache: key is " + key + ", value is " + value);
                }
                if(null != value) {
                    needKeyList.remove(key);
                    result.add(value);
                }
            }
            threadLocal.set(needKeyList);
        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return result;
    }

    @Override
    public List<String> getNeedList() {
        return threadLocal.get();
    }

    @Override
    public void put(String key, Object value) {
        try {
            redisClient.putValue(key, value);
            keySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }

    }

    @Override
    public void delete(String key) {
        try {
            redisClient.delete(key);
            keySet.remove(key);
            if (logger.isDebugEnabled()) {
                logger.debug("delete cache: key is " + key);
            }
        } catch (Exception e2) {
            logger.error("删除缓存出错");
        }
    }

    @Override
    public void clear() {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("delete cache: key is " + keySet);
            }
            for (String key : keySet) {
                delete(key);
                keySet.remove(key);
            }
        } catch (Exception e2) {
            logger.error("清空缓存出错");
        }
    }



}
