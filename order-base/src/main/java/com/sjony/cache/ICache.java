package com.sjony.cache;


import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache<K, V> {

    V getValue(K key);

    void putValue(K key, V value);

    void putValueBatch(Map<K,V> map);

    void clearKey();

    void clearSetKey();

    void clearMapKey();

    void delete(String key);

    List<V> getValueBatch(List<K> key);

    void putMap(K key, Map value);

    void putMapValue(K key, Object hk, Object value);

    void addZSet(K key, V value, Double score);

    void addScore(K key, V value, Double score);

    <T> T getMap(K key, Class<T> type);

    <T> T getMapValue(K key, String hk, Class<T> type);

    Set<V> range(K key, Long start, Long end);

    void addZSet(K key, Set<ZSetOperations.TypedTuple<V>> set);

    Boolean setNX(String lockKey, String lockValue);

    Object getSet(String lockKey, String lockValue);

    boolean hasKey(K key);


}
