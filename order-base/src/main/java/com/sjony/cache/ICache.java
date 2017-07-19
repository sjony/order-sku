package com.sjony.cache;


import java.util.List;

public interface ICache {

    <T> T get(String key, Class<T> type);

    void put(String key, Object value);

    void clear();

    void delete(String key);

    <T> List<T> getBatch(List<String> key, Class<T> type);

    List<String> getNeedList();

}
