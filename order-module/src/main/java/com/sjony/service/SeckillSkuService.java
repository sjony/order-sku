package com.sjony.service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: 秒杀相关service
 * @Create on: 2017/8/2 上午10:32 
 *
 * @author shujiangcheng
 */
public interface SeckillSkuService {
    
    /**
     * @Description: 秒杀信息放入缓存 
     * @Create on: 2017/8/2 上午10:41 
     *
     * @author shujiangcheng
     */
    int insertCacheSeckillSkuQty(List<String> skuList) throws IllegalAccessException, IntrospectionException, InvocationTargetException;

    /**
     * @Description: 秒杀扣库存
     * @Create on: 2017/8/2 上午11:41 
     *
     * @author shujiangcheng
     */
    int updateSeckillSkuQty(List<String> skuList);

    int updateSeckillSkuQtyTest(List<String> skuList);
}
