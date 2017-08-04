package com.sjony.service.impl;

import com.google.common.collect.Maps;
import com.sjony.base.BaseService;
import com.sjony.cache.OrderRedisService;
import com.sjony.commons.Constants;
import com.sjony.service.SkuSaleSeckillService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description: 秒杀相关
 * @Create on: 2017/8/2 上午10:57 
 *
 * @author shujiangcheng
 */
@Service
public class SkuSaleSeckillServiceImpl extends BaseService implements SkuSaleSeckillService {

    private static final long EXPIRE_TIME = 3000;

    private static final Logger logger = LoggerFactory.getLogger(SkuSaleSeckillServiceImpl.class);


    /**
     * @Description: 更新库存 秒杀
     * @Create on: 2017/7/15 下午2:00
     *
     * @author shujiangcheng
     */
    @Override
    public int updateQtyForSale(String skuCode) {
        if(StringUtils.isEmpty(skuCode)) {
            return 0;
        }
        String key = getKey(skuCode, SkuQtyVO.class);
        String lockKey = getLockKey(skuCode, SkuQtyVO.class);
        try {

            while(true) {
                long wantlockTime = System.currentTimeMillis();
                boolean lock = getRedisCache().setNX(lockKey, String.valueOf(wantlockTime));
                if(!lock) {
                    long nowThreadTime = System.currentTimeMillis();
                    Object lockTiemLatestObject = getRedisCache().getValue(lockKey);
                    long lockTiemLatest = 0;
                    if(null == lockTiemLatestObject) {
                        continue;
                    } else {
                        lockTiemLatest = Long.valueOf((String)lockTiemLatestObject);
                    }
                    if((nowThreadTime-lockTiemLatest) > 10000) {
                        logger.warn("超时了，请重试");
                        return 2;
                    }
                    if((nowThreadTime-lockTiemLatest) > EXPIRE_TIME) {
                        lockTiemLatestObject = getRedisCache().getValue(lockKey);
                        if(null == lockTiemLatestObject) {
                            continue;
                        } else {
                            lockTiemLatest = Long.valueOf((String)lockTiemLatestObject);
                        }
                        Object lockTimeObject = getRedisCache().getSet(lockKey, String.valueOf(nowThreadTime));
                        long  lockTime = 0;
                        if(null == lockTiemLatestObject) {
                            continue;
                        } else {
                            lockTime = Long.valueOf((String)lockTimeObject);
                        }
                        if(lockTime == lockTiemLatest) {
                            lock = true;
                        }
                    }


                }
                if(lock){
                    SkuQtyVO skuQtyVO = (SkuQtyVO) getRedisCache().getValue(key);
                    if(BigDecimal.ZERO.compareTo(skuQtyVO.getSkuQty()) >= 0) {
                        getRedisCache().delete(lockKey);
                        logger.warn(skuCode + "此商品已卖完，请看看别的商品");
                        return 3;
                    }
                    BigDecimal skuQty = skuQtyVO.getSkuQty().subtract(BigDecimal.ONE);
                    skuQtyVO.setSkuQty(skuQty);
                    getRedisCache().putValue(key,  skuQtyVO);
                    getRedisCache().delete(lockKey);
                    break;
                }
            }
        } catch (Exception e) {
            getRedisCache().delete(lockKey);
            e.printStackTrace();
        }

        return 1;
    }

    /**
     * @Description:   更新库存 秒杀（批量）
     * @Create on: 2017/8/2 上午11:44 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateQtyForSale(List<String> skuCodeList) {
        if(CollectionUtils.isEmpty(skuCodeList)) {
            return 0;
        }
        int result = 0;
        for(String skuCode : skuCodeList) {
            result = updateQtyForSale(skuCode);
            if(0 == result) {
                return 0;
            } else if (2 == result) {
                return result;
            }
        }
        return 1;
    }

    /**
     * @Description: 插入缓存秒杀商品库存
     * @Create on: 2017/8/2 上午11:21 
     *
     * @author shujiangcheng
     */
    @Override
    public int insertCacheSeckillSkuQty(List<SkuQtyVO> skuQtyList) throws IllegalAccessException, IntrospectionException, InvocationTargetException {
        int result = 0;
        if(CollectionUtils.isEmpty(skuQtyList)) {
            return 1;
        }
        Map<String, Object> cacheMap = Maps.newHashMap();

        for(SkuQtyVO skuQtyVO : skuQtyList) {
            cacheMap.put(getKey(skuQtyVO.getSkuCode(), SkuQtyVO.class), skuQtyVO);
        }
        getRedisCache().putValueBatch(cacheMap);
        return 1;
    }


}
