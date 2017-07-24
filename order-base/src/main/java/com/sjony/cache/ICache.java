package com.sjony.cache;


import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache {

    <T> T getValue(String key, Class<T> type);

    void putValue(String key, Object value);

    void putValueBatch(Map<String,Object> map);

    void clearKey();

    void clearSetKey();

    void clearMapKey();

    void delete(String key);

    <T> List<T> getValueBatch(List<String> key, Class<T> type);

    void putMap(String key, Map value);

    void putMapValue(String key, Object hk, Object value);

    void addZSet(String key, Object value, Double score);

    void addScore(String key, Object value, Double score);

    <T> T getMap(String key, Class<T> type);

    <T>Set<T> range(String key, Long start, Long end, Class<T> type);

    <T>void addZSet(String key, Set<ZSetOperations.TypedTuple<T>> set, Class<T> type);

}
