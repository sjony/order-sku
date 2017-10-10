package com.sjony.service.impl;

import com.mysql.jdbc.TimeUtil;
import com.sjony.base.BaseService;
import com.sjony.cache.OrderRedisService;
import com.sjony.service.SeckillSkuService;
import com.sjony.service.SkuQtyService;
import com.sjony.service.SkuSaleQtyService;
import com.sjony.service.SkuSaleSeckillService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Description: 秒杀相关service
 * @Create on: 2017/8/2 上午10:32
 *
 * @author shujiangcheng
 */
@Service
public class SeckillSkuServiceImpl extends BaseService implements SeckillSkuService {

    private static final Logger logger = LoggerFactory.getLogger(SeckillSkuServiceImpl.class);

    @Autowired
    private SkuSaleSeckillService skuSaleSeckillService;

    @Autowired
    private SkuQtyService skuQtyService;


    /**
     * @Description: 秒杀信息放入缓存 
     * @Create on: 2017/8/2 上午10:41 
     *
     * @author shujiangcheng
     */
    @Override
    public int insertCacheSeckillSkuQty(List<String> skuList) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        int result = 0;

        try {
            //入参校验
            if(CollectionUtils.isEmpty(skuList)) {
                return result;
            }
            //获取对应的sku的库存信息
            List<SkuQtyVO> skuQtyList = skuQtyService.getSkuQtyListBySkuList(skuList, false);
            result = skuSaleSeckillService.insertCacheSeckillSkuQty(skuQtyList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return result;
    }

    /**
     * @Description: 秒杀扣库存
     * @Create on: 2017/8/2 上午11:48 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateSeckillSkuQty(List<String> skuList) {
        int result = 0;
        //入参校验
        if(CollectionUtils.isEmpty(skuList)) {
            return result;
        }
        result = skuSaleSeckillService.updateQtyForSale(skuList);

        return result;
    }

    /**
     * @Description: 模拟100个人同时购买
     * @Create on: 2017/8/4 下午2:19 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateSeckillSkuQtyTest(List<String> skuList) {
        int result = 0;
        //入参校验
        if(CollectionUtils.isEmpty(skuList)) {
            return result;
        }
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10 , 0l, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(),new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i=0; i<100; i++) {
            logger.warn("第" + i + "个人开始买东西啦");
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    int response = updateSeckillSkuQty(skuList);
                    if(response == 3) {
                        logger.warn("东西卖完啦");
                    } else if(response == 2) {
                        logger.warn("超时啦，请重试");
                    }
                }
            });
            logger.warn("第" + i + "个人买完东西了");
        }
        poolExecutor.shutdown();
        return result;
    }

    /**
     * @Description: 没有分布式锁的100人购物
     * @Create on: 2017/8/4 下午2:20 
     *
     * @author shujiangcheng
     */
    @Override
    public  int updateSeckillSkuQtyTestWithoutsyn(List<String> skuList) {
        String skuCode = skuList.get(0);
        int result = 0;
        //入参校验
        if(CollectionUtils.isEmpty(skuList)) {
            return result;
        }
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(10, 10 , 0l, TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>(),new ThreadPoolExecutor.CallerRunsPolicy());
        for(int i=0; i<100; i++) {
            logger.warn("第" + i + "个人开始买东西啦");
            poolExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    SkuQtyVO skuQtyVO = skuQtyService.getSkuQtyBySkuCode(skuCode, true);
                    skuQtyVO.setSkuQty(skuQtyVO.getSkuQty().subtract(BigDecimal.ONE));
                    getRedisCache().putValue(getKey(skuCode, SkuQtyVO.class), skuQtyVO);
                }
            });
            logger.warn("第" + i + "个人买完东西了");
        }
        poolExecutor.shutdown();
        return result;
    }

    /**
     * @Description: 不带缓存的购物
     * @Create on: 2017/8/4 下午2:20 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateSeckillSkuQtyTestWithoutRedis(List<String> skuList) {
        String skuCode = skuList.get(0);
        int result = 0;
        //入参校验
        if(CollectionUtils.isEmpty(skuList)) {
            return result;
        }
        int response = skuQtyService.updateQtyForSale(skuCode);
        if(response == 3) {
            logger.warn("东西卖完啦");
        } else if(response == 2) {
            logger.warn("超时啦，请重试");
        }
        return result;
    }


}
