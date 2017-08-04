package com.sjony.cache;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by sjony on 2017/7/18.
 */
public interface CacheClient<K,V> {

    List<V> getValue(Collection<K> key);
    void putBatch(Map map);
    void putValue(K key, V value);
    void putValue(K key, int exp, V value);
    void delete(String key);
    Map getMap(K key);
    <T>T getMapValue(K key, String hk, Class<T> type);
    void putMap(K key, Map value);
    void putMapValue(K key, Object hk, Object value);
    List<V> getList(K key);
    void pushLeft(K key, V value);
    void pushRight(K key, V value);
    V popLeft(K key);
    V popRight(K key);
    Set<V> getSet(K key);
    void addSet(K key, V... value);
    Set<V> intersect(K key, K key1);
    void addZSet(K key, V value, Double score);
    void addZSet(K key, Set<ZSetOperations.TypedTuple<V>> set);
    void addScore(K key, V value, Double score);
    Set<V> range(K key, Long start, Long end);
    Set<V> reverseRange(K key, Long start, Long end);
    boolean setNX(String lockName, String value);
    Object getSet(String key, String value);
    boolean hasKey(K key);

}
