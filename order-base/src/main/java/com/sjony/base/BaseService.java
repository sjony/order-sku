package com.sjony.base;

import com.google.common.collect.Lists;
import com.sjony.cache.ICache;
import com.sjony.cache.OrderRedisService;
import com.sjony.utils.CollectionUtils;
import com.sjony.utils.JedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by sjony on 2017/7/15.
 */
public class BaseService<K, V> {

    @Autowired
    private ICache<K,V> orderRedisService;

    protected ICache getRedisCache() {
        return orderRedisService;
    }


    protected List<String> getKeyList(List<String> keyList, Class type) {
        if(CollectionUtils.isEmpty(keyList)) {
            return null;
        }
        List<String> resultList = Lists.newArrayList();
        for(String key : keyList) {
            resultList.add(getKey(key, type));
        }
        return resultList;
    }

    protected String getKey(String key, Class type) {
        return type + "," + key;
    }

    protected String getLockKey(String key, Class type) {
        return "lock_" + type + "," + key;
    }

    private String reKey(String Key) {
        return Key.split(",")[1];
    }

    protected Jedis getJedis() {
        return JedisUtil.getJedis();
    }

}
