package com.sjony.dao;

import com.sjony.entity.SkuQtyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 库存相关dao
 * @Create on: 2017/7/15 上午11:47 
 *
 * @author shujiangcheng
 */
@Repository
public interface OrderUserDao {

    int insertSkuQty(@Param("entity") SkuQtyEntity skuQtyEntity);

    int updateSkuQty(@Param("skuQtyList") List<SkuQtyEntity> skuQtyList);

    int updateQtyForSale(@Param("skuCode") String skuCode);

    int updateQty(@Param("list") List<SkuQtyEntity> skuQtyList);

    List<SkuQtyEntity> selectSkuQtyBySkuCode(@Param("skuList") List<String> skuList);

}
