package com.sjony.cache;

/**
 * Created by sjony on 2017/7/18.
 */
public interface CacheClient {

    <T> T get(String key);
    void put(String key, Object value);
    void put(String key, int exp, Object value);
    void delete(String key);
}
