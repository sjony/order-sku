package com.sjony.cache;

import java.util.List;
import java.util.Map;

/**
 * Created by sjony on 2017/7/18.
 */
public interface CacheClient {

    <T> T getValue(String key);
    void putValue(String key, Object value);
    void putValue(String key, int exp, Object value);
    void delete(String key);
    <K,V>Map<K,V> getMap(String key);
    void putMap(String key, Map value);
    void putMapValue(String key, Object hk, Object value);
    <T>List<T> getList(String key);
    void pushLeft(String key, Object value);
    void pushRight(String key, Object value);
    <T>T popLeft(String key);
    <T>T popRight(String key);


}
