package com.sjony.service;

import com.sjony.vo.SkuQtyVO;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: 秒杀相关 
 * @Create on: 2017/8/2 上午10:57
 *
 * @author shujiangcheng
 */
public interface SkuSaleSeckillService {

    int updateQtyForSale(String skuCode);

    int updateQtyForSale(List<String> skuCodeList);

    int insertCacheSeckillSkuQty(List<SkuQtyVO> skuQtyList) throws IllegalAccessException, IntrospectionException, InvocationTargetException;
}
