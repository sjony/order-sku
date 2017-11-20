package com.sjony.service;

import com.sjony.vo.SkuQtyVO;

import java.util.List;

/**
 * Created by sjony on 2017/11/17.
 */
public interface SkuService {

    List<SkuQtyVO> getSkuQtyBySkuCode(List<String> skuCodeList);


}
