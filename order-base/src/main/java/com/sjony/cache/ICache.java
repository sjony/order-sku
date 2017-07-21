package com.sjony.cache;


import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ICache {

    <T> T get(String key, Class<T> type);

    void put(String key, Object value);

    void clearKey();

    void clearSetKey();

    void clearMapKey();

    void delete(String key);

    <T> List<T> getBatch(List<String> key, Class<T> type);

    List<String> getNeedList();

    void putMap(String key, Map value);

    void putMapValue(String key, Object hk, Object value);

    void addZSet(String key, Object value, Double score);

    void addScore(String key, Object value, Double score);

    <T> T getMap(String key, Class<T> type);

    <T>Set<T> range(String key, Long start, Long end, Class<T> type);



}
