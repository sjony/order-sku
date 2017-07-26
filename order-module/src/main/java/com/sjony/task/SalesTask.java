package com.sjony.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sjony.cache.ICache;
import com.sjony.commons.Constants;
import com.sjony.dao.SkuSaleQtyDao;
import com.sjony.entity.SkuQtyEntity;
import com.sjony.entity.SkuSaleQtyEntity;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * @Description: 定时任务
 * @Create on: 2017/7/24 上午11:51 
 *
 * @author shujiangcheng
 */
public class SalesTask {

    private static final Logger logger = LoggerFactory.getLogger(SalesTask.class);

    @Autowired
    private ICache orderRedisService;

    @Autowired
    private SkuSaleQtyDao skuSaleQtyDao;

    public void salesTask() {

    }

    public void saleRankTask() {
        orderRedisService.delete(Constants.SALE_TASK);
        logger.warn("定时同步商品销售量信息");
        List<SkuSaleQtyEntity> skuSaleQtyEntityList = skuSaleQtyDao.selectSkuQtyForRank();
        if(CollectionUtils.isNotEmpty(skuSaleQtyEntityList)) {
//            Set<ZSetOperations.TypedTuple<SkuSaleQtyVO>> tuples = Sets.newHashSet();
            List<SkuSaleQtyVO> resultList = Lists.newArrayList();
            for(SkuSaleQtyEntity entity : skuSaleQtyEntityList) {
                SkuSaleQtyVO result = new SkuSaleQtyVO();
                BeanUtils.copyProperties(entity,result);
                resultList.add(result);
                System.out.print("test");
//                ZSetOperations.TypedTuple<SkuSaleQtyVO> tuple0 = new DefaultTypedTuple<SkuSaleQtyVO>(result, result.getSaleQty().doubleValue());
//                tuples.add(tuple0);
            }

            long t1 = System.currentTimeMillis();
//            orderRedisService.addZSet(Constants.SALE_TASK, tuples, SkuSaleQtyVO.class);
            for(SkuSaleQtyVO skuSaleQtyVO : resultList) {
                orderRedisService.addZSet(Constants.SALE_TASK, skuSaleQtyVO, skuSaleQtyVO.getSaleQty().doubleValue());
            }
            long t2 = System.currentTimeMillis();
            logger.warn("循环插入耗时：" + (t2-t1) + "ms");
        }

    }

}
