package com.sjony.dao;

import com.sjony.entity.SkuQtyEntity;
import com.sjony.entity.SkuSaleQtyEntity;
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
public interface SkuSaleQtyDao {

    int insertSkuSaleQty(@Param("entity") SkuSaleQtyEntity skuQtyEntity);

    int updateSkuSaleQty(@Param("skuSaleQtyList") List<SkuSaleQtyEntity> skuSaleQtyList);

    List<SkuSaleQtyEntity> selectSkuQtyBySkuCode(@Param("skuList") List<String> skuList);

    List<SkuSaleQtyEntity> selectSkuQtyForRank();

}
