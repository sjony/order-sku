package com.sjony.service.impl;

import com.google.common.collect.Maps;
import com.sjony.base.BaseService;
import com.sjony.cache.OrderRedisService;
import com.sjony.commons.Constants;
import com.sjony.service.SkuSaleSeckillService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
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
        try {
            String key = getKey(skuCode, SkuQtyVO.class);
            long wantlockTime = System.currentTimeMillis();
            while(true) {
                boolean lock = getRedisCache().setNX(Constants.LOCK_SECKILL, String.valueOf(wantlockTime));
                if(!lock) {
                    long nowThreadTime = System.currentTimeMillis();
                    long lockTiemLatest = (long) getRedisCache().getValue(Constants.LOCK_SECKILL);
                    if((nowThreadTime-lockTiemLatest) > 10000) {
                        return 2;
                    }
                    if((nowThreadTime-lockTiemLatest) > 3000) {
                        lockTiemLatest = (long) getRedisCache().getValue(Constants.LOCK_SECKILL);
                        long  lockTime = (long) getRedisCache().getSet(Constants.LOCK_SECKILL, String.valueOf(nowThreadTime));
                        if(lockTime == lockTiemLatest) {
                            lock = true;
                        }
                    }


                }
                if(lock){
                    SkuQtyVO skuQtyVO = (SkuQtyVO) getRedisCache().getValue(key);
                    BigDecimal skuQty = skuQtyVO.getSkuQty().subtract(BigDecimal.ONE);
                    skuQtyVO.setSkuQty(skuQty);
                    getRedisCache().putValue(key,  skuQtyVO);
                    getRedisCache().delete(Constants.LOCK_SECKILL);
                    break;
                }
            }
        } catch (Exception e) {
            getRedisCache().delete(Constants.LOCK_SECKILL);
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
