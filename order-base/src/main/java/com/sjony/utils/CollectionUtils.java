package com.sjony.utils;

import java.util.Collection;
import java.util.Map;

/**
 * @Description:CollectionUtils工具类
 * @Create on: 2017/7/15 下午2:04 
 *
 * @author shujiangcheng
 */
public class CollectionUtils extends org.springframework.util.CollectionUtils {

    public static boolean isNotEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }

    public static boolean isNotEmpty(Map<?,?> map) {
        return !isEmpty(map);
    }

}
