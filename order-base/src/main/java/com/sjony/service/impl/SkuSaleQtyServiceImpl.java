package com.sjony.service.impl;

import com.sjony.base.BaseService;
import com.sjony.commons.Constants;
import com.sjony.dao.SkuSaleQtyDao;
import com.sjony.entity.SkuSaleQtyEntity;
import com.sjony.service.SkuSaleQtyService;
import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @Description: 销售额相关接口
 * @Create on: 2017/7/24 下午1:45
 *
 * @author shujiangcheng
 */
@Service
public class SkuSaleQtyServiceImpl extends BaseService implements SkuSaleQtyService {

    @Autowired
    private SkuSaleQtyDao skuSaleQtyDao;

    @Override
    public Set<SkuSaleQtyVO> getSkuQtyForRank() {
        return getRedisCache().range(Constants.SALE_TASK,0L,19L, SkuSaleQtyVO.class);
    }
}
