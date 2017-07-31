package com.sjony.task.job;

import com.github.pagehelper.Constant;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sjony.cache.ICache;
import com.sjony.commons.Constants;
import com.sjony.dao.SkuSaleQtyDao;
import com.sjony.entity.SkuSaleQtyEntity;
import com.sjony.utils.CollectionUtils;
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
 * @Description: 获取商品销售了
 * @Create on: 2017/7/29 上午10:00 
 *
 * @author shujiangcheng
 */
public class SalesJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SalesJob.class);

    private int pageNum;

    private int pageSize = Constants.SALE_PAGE_SIZE;

    private ICache orderRedisService;

    private SkuSaleQtyDao skuSaleQtyDao;

    public SalesJob(int pageNum, ICache orderRedisService, SkuSaleQtyDao skuSaleQtyDao) {
        this.pageNum = pageNum;
        this.orderRedisService=orderRedisService;
        this.skuSaleQtyDao = skuSaleQtyDao;
    }

    public SalesJob(int pageNum, int pageSize, ICache orderRedisService, SkuSaleQtyDao skuSaleQtyDao) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.orderRedisService=orderRedisService;
        this.skuSaleQtyDao = skuSaleQtyDao;
    }

    /**
     * @Description: 异步获取商品销售额
     * @Create on: 2017/7/29 上午10:27 
     *
     * @author shujiangcheng
     */
    @Override
    public void run() {

        PageHelper.startPage(pageNum, pageSize, false);
        List<SkuSaleQtyEntity> skuSaleQtyEntityList = skuSaleQtyDao.selectSkuQtyForRank();
        if(CollectionUtils.isNotEmpty(skuSaleQtyEntityList)) {
            Set<ZSetOperations.TypedTuple<SkuSaleQtyVO>> tuples = Sets.newHashSet();
            List<SkuSaleQtyVO> resultList = Lists.newArrayList();
            for(SkuSaleQtyEntity entity : skuSaleQtyEntityList) {
                SkuSaleQtyVO result = new SkuSaleQtyVO();
                BeanUtils.copyProperties(entity,result);
                resultList.add(result);
                ZSetOperations.TypedTuple<SkuSaleQtyVO> tuple0 = new DefaultTypedTuple<SkuSaleQtyVO>(result, result.getSaleQty().doubleValue());
                tuples.add(tuple0);
            }

            long t1 = System.currentTimeMillis();
            orderRedisService.addZSet(Constants.SALE_TASK, tuples);
            /*for(SkuSaleQtyVO skuSaleQtyVO : resultList) {
                orderRedisService.addZSet(Constants.SALE_TASK, skuSaleQtyVO, skuSaleQtyVO.getSaleQty().doubleValue());
            }*/
            long t2 = System.currentTimeMillis();
            logger.warn("循环插入耗时：" + (t2-t1) + "ms");
        }

    }
}
