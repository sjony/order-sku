package com.sjony.cache;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sjony.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Description: redis缓存
 * @Create on: 2017/7/18 下午5:10 
 *
 * @author shujiangcheng
 */
public class OrderRedisService implements  ICache{


    private CacheClient redisClient;

    private static Set<String> keySet = Sets.newHashSet();
    private static Set<String> mapkeySet = Sets.newHashSet();
    private static Set<String> setkeySet = Sets.newHashSet();


    private static Logger logger = LoggerFactory
            .getLogger(OrderRedisService.class);

    public OrderRedisService(CacheClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public <T> T getValue(String key, Class<T> type) {
        T value = null;
        List<String> keyList = Lists.newArrayList(key);
        try {
            List<T> valueList = redisClient.getValue(keyList);
            value =  valueList.get(0);
            if (logger.isDebugEnabled()) {
                logger.debug("get cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return value;
    }
    
    /**
     * @Description: 批量存入值
     * @Create on: 2017/7/24 下午2:09 
     *
     * @author shujiangcheng
     */
    @Override
    public void putValueBatch(Map<String,Object> map) {
        try {
            redisClient.putBatch(map);
            for(String key : map.keySet()) {
                keySet.add(key);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + map.keySet() + ", value is " + map.values());
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }
    }

    @Override
    public <T> List<T> getValueBatch(List<String> keyList, Class<T> type) {
        List<T> result = Lists.newArrayList();
        try {
            T value = null;
            result = redisClient.getValue(keyList);
                if (logger.isDebugEnabled()) {
                    logger.debug("get cache: key is " + JSON.toJSONString(keyList) + ", value is " + value);
            }

        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return result;
    }


    /**
     * @Description: 存放修改部分值的对象
     * @Create on: 2017/7/21 下午2:37 
     *
     * @author shujiangcheng
     */
    @Override
    public void putMap(String key, Map value) {
        try {
            redisClient.putMap(key, value);
            mapkeySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }
    }

    /**
     * @Description:设置具体需要修改的值
     * @Create on: 2017/7/21 下午2:47
     * @author jshu
     *
     * @param key
     * @param hk
     * @param value
     *
     */
    @Override
    public void putMapValue(String key, Object hk, Object value) {
        try {
            redisClient.putMapValue(key, hk, value);
            mapkeySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }
    }

    /**
     * @Description:新增value(ZSet)
     * @Create on: 2017/7/21 下午2:54
     * @author jshu
     *
     * @param key
     * @param value
     * @param score
     *
     */
    @Override
    public void addZSet(String key, Object value, Double score) {
        try {
            redisClient.addZSet(key, value, score);
            setkeySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }
    }

    /**
     * @Description:  新增value(ZSet),批量
     * @Create on: 2017/7/24 下午2:44 
     *
     * @author shujiangcheng
     */
    public <T>void addZSet(String key, Set<ZSetOperations.TypedTuple<T>> set, Class<T> type) {
        try {
            redisClient.addZSet(key, set, type);
            setkeySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + set);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            logger.error("存放缓存出错");
        }
    }

    /**
     * @Description:新增score值
     * @Create on: 2017/7/21 下午2:54
     * @author jshu
     *
     * @param key
     * @param value
     * @param score
     *
     */
    @Override
    public void addScore(String key, Object value, Double score) {
        try {
            redisClient.addScore(key, value, score);
            setkeySet.add(key);
            if (logger.isDebugEnabled()) {
                logger.debug("put cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("存放缓存出错");
        }
    }

    /**
     * @Description: 获取map的值
     * @Create on: 2017/7/21 下午3:00 
     *
     * @author shujiangcheng
     */
    @Override
    public <T> T getMap(String key, Class<T> type) {
        T value = null;
        try {
            Map valueMap = redisClient.getMap(key);
            if(CollectionUtils.isNotEmpty(valueMap)) {
                value = CollectionUtils.mapToBean(valueMap, type);
            }
            if (logger.isDebugEnabled()) {
                logger.debug("get cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return value;
    }

    /**
     * @Description: 获取排序的set
     * @Create on: 2017/7/21 下午3:08 
     *
     * @author shujiangcheng
     */
    @Override
    public <T> Set<T> range(String key, Long start, Long end, Class<T> type) {
        Set<T> value = null;
        try {
            value = redisClient.range(key, start, end);
            if (logger.isDebugEnabled()) {
                logger.debug("get cache: key is " + key + ", value is " + value);
            }
        } catch (Exception e2) {
            logger.error("获取缓存出错", e2);
        }

        return value;
    }

    /**
     * @Description: 存放值
     * @Create on: 2017/7/21 下午3:06 
     *
     * @author shujiangcheng
     */
    @Override
    public void putValue(String key, Object value) {
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

    /**
     * @Description: 删除key
     * @Create on: 2017/7/21 下午2:58 
     *
     * @author shujiangcheng
     */
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
    public void clearKey() {
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

    @Override
    public void clearSetKey() {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("delete cache: key is " + setkeySet);
            }
            for (String key : setkeySet) {
                delete(key);
                setkeySet.remove(key);
            }
        } catch (Exception e2) {
            logger.error("清空缓存出错");
        }
    }

    @Override
    public void clearMapKey() {
        try {
            if (logger.isDebugEnabled()) {
                logger.debug("delete cache: key is " + mapkeySet);
            }
            for (String key : mapkeySet) {
                delete(key);
                mapkeySet.remove(key);
            }
        } catch (Exception e2) {
            logger.error("清空缓存出错");
        }
    }


}
