package com.sjony.service;

import com.sjony.entity.SkuQtyEntity;
import com.sjony.vo.SkuQtyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Description: 库存相关接口
 * @Create on: 2017/7/15 下午1:35 
 *
 * @author shujiangcheng
 */
public interface SkuQtyService {

    int inserSkuQty(SkuQtyVO skuQtyVO);

    int updateSkuQty(SkuQtyVO skuQtyVO);

    int updateQtyForSale(String skuCode);

    int updateQty(List<SkuQtyVO> skuQtyList);

    int updateSkuQtyBatch(List<SkuQtyVO> skuQtyList);

    List<SkuQtyVO> getSkuQtyListBySkuList(List<String> skuList, boolean isCache);

    SkuQtyVO getSkuQtyBySkuCode(String skuCode, boolean isCache);

}
