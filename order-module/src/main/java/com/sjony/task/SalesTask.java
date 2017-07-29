package com.sjony.task;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sjony.cache.ICache;
import com.sjony.commons.Constants;
import com.sjony.dao.SkuSaleQtyDao;
import com.sjony.entity.SkuQtyEntity;
import com.sjony.entity.SkuSaleQtyEntity;
import com.sjony.task.job.SalesJob;
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
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    /**
     * @Description: 优化后同步商品销售量信息 
     * @Create on: 2017/7/29 上午11:19 
     *
     * @author shujiangcheng
     */
    public void saleRankTask() {
        orderRedisService.delete(Constants.SALE_TASK);
        logger.warn("定时同步商品销售量信息");
        long t1 = System.currentTimeMillis();

        Integer count = skuSaleQtyDao.selectCountForRank();
        int page = count/Constants.SALE_PAGE_SIZE;

        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(5, 5,  0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(),new ThreadPoolExecutor.CallerRunsPolicy());

        for(int i=0; i<page; i++) {
            SalesJob salesJob = new SalesJob(i, orderRedisService, skuSaleQtyDao);
            poolExecutor.execute(salesJob);
        }

        logger.warn("线程全部执行后关闭");
        poolExecutor.shutdown();
        while(true) {
            //当所有线程执行完毕以后，记录日志
            if(poolExecutor.isTerminated()) {
                logger.warn("线程全部执行完毕");
                long t2 = System.currentTimeMillis();
                logger.warn("--------------总插入耗时：" + (t2-t1) + "ms");
                break;
            }
        }

    }

    /**
     * @Description: 优化前
     * @Create on: 2017/7/29 上午11:18 
     *
     * @author shujiangcheng
     */
    /*public void saleRankTask() {
        orderRedisService.delete(Constants.SALE_TASK);
        logger.warn("定时同步商品销售量信息");
        long t1 = System.currentTimeMillis();
        List<SkuSaleQtyEntity> skuSaleQtyEntityList = skuSaleQtyDao.selectSkuQtyForRank();
        if(CollectionUtils.isNotEmpty(skuSaleQtyEntityList)) {
//            Set<ZSetOperations.TypedTuple<SkuSaleQtyVO>> tuples = Sets.newHashSet();
            List<SkuSaleQtyVO> resultList = Lists.newArrayList();
            for(SkuSaleQtyEntity entity : skuSaleQtyEntityList) {
                SkuSaleQtyVO result = new SkuSaleQtyVO();
                BeanUtils.copyProperties(entity,result);
                resultList.add(result);
//                ZSetOperations.TypedTuple<SkuSaleQtyVO> tuple0 = new DefaultTypedTuple<SkuSaleQtyVO>(result, result.getSaleQty().doubleValue());
//                tuples.add(tuple0);
            }

//            orderRedisService.addZSet(Constants.SALE_TASK, tuples, SkuSaleQtyVO.class);
            for(SkuSaleQtyVO skuSaleQtyVO : resultList) {
                orderRedisService.addZSet(Constants.SALE_TASK, skuSaleQtyVO, skuSaleQtyVO.getSaleQty().doubleValue());
            }
            long t2 = System.currentTimeMillis();
            logger.warn("循环插入耗时：" + (t2-t1) + "ms");
        }

    }*/

}
