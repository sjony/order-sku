package com.sjony.dao;

import com.sjony.entity.OrderUserEntity;
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

    int insertOrderUser(@Param("entity") OrderUserEntity skuQtyEntity);

    int updateSkuQty(@Param("orderUserList") List<OrderUserEntity> skuQtyList);

    List<OrderUserEntity> selectOrderUserByOrderCode(@Param("orderList") List<String> orderList);

}
