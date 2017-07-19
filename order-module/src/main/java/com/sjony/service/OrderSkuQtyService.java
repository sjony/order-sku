package com.sjony.service;

import com.sjony.vo.SkuQtyVO;

import java.util.List;

/**
 * @Description: 库存相关
 * @Create on: 2017/7/15 下午3:06 
 *
 * @author shujiangcheng
 */
public interface OrderSkuQtyService {

    void testInsert();


    List<SkuQtyVO> getSkuQtyBySkuCode(List<String> skuCodeList);
}
