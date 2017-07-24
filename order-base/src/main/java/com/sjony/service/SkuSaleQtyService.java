package com.sjony.service;

import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;

import java.util.List;
import java.util.Set;

/**
 * @Description: 销售额相关接口
 * @Create on: 2017/7/24 下午1:45 
 *
 * @author shujiangcheng
 */
public interface SkuSaleQtyService {

    Set<SkuSaleQtyVO> getSkuQtyForRank();

}
