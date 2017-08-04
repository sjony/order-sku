package com.sjony.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sjony.base.BaseService;
import com.sjony.cache.CacheClient;
import com.sjony.cache.ICache;
import com.sjony.commons.Constants;
import com.sjony.dao.SkuQtyDao;
import com.sjony.entity.SkuQtyEntity;
import com.sjony.service.SkuQtyService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

/**
 * @Description: 库存相关接口
 * @Create on: 2017/7/15 下午1:41
 *
 * @author shujiangcheng
 */
@Service
public class SkuQtyServiceImpl extends BaseService<String, SkuQtyVO> implements SkuQtyService {

    @Autowired
    private SkuQtyDao skuQtyDao;

    private static final Logger logger = LoggerFactory.getLogger(SkuQtyServiceImpl.class);


    /**
     * @Description: 插入库存信息
     * @Create on: 2017/7/15 下午1:59 
     *
     * @author shujiangcheng
     */
    @Override
    public int inserSkuQty(SkuQtyVO skuQtyVO) {
        if(null == skuQtyVO) {
            return 0;
        }
        SkuQtyEntity skuQtyEntity = new SkuQtyEntity();
        BeanUtils.copyProperties(skuQtyVO, skuQtyEntity);
        int result = skuQtyDao.insertSkuQty(skuQtyEntity);
        return skuQtyEntity.getId().intValue();
    }

    /**
     * @Description: 单个更新库存信息
     * @Create on: 2017/7/15 下午2:00
     *
     * @author shujiangcheng
     */
    @Override
    public int updateSkuQty(SkuQtyVO skuQtyVO) {
        if(null == skuQtyVO) {
            return 0;
        }
        List<SkuQtyVO> skuQtyVOList = Lists.newArrayList();
        skuQtyVOList.add(skuQtyVO);
        return updateSkuQtyBatch(skuQtyVOList);
    }

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
        int result = skuQtyDao.updateQtyForSale(skuCode);
        return result;
    }





    /**
     * @Description: 更新库存 购物 
     * @Create on: 2017/7/15 下午2:00 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateQty(List<SkuQtyVO> skuQtyList) {
        if(CollectionUtils.isEmpty(skuQtyList)) {
            return 0;
        }
        List<SkuQtyEntity> entityList = Lists.newArrayList();
        for(SkuQtyVO vo : skuQtyList) {
            SkuQtyEntity skuQtyEntity = new SkuQtyEntity();
            BeanUtils.copyProperties(vo,skuQtyEntity);
            entityList.add(skuQtyEntity);
        }
        int result = skuQtyDao.updateQty(entityList);
        return result;
    }

    /**
     * @Description: 批量更新库存信息
     * @Create on: 2017/7/15 下午2:00 
     *
     * @author shujiangcheng
     */
    @Override
    public int updateSkuQtyBatch(List<SkuQtyVO> skuQtyList) {
        if(CollectionUtils.isEmpty(skuQtyList)) {
            return 0;
        }
        List<SkuQtyEntity> entityList = Lists.newArrayList();
        for(SkuQtyVO vo : skuQtyList) {
            SkuQtyEntity skuQtyEntity = new SkuQtyEntity();
            BeanUtils.copyProperties(vo,skuQtyEntity);
            entityList.add(skuQtyEntity);
        }
        int result = skuQtyDao.updateSkuQty(entityList);
        return result;
    }

    /**
     * @Description: 获取库存信息(批量)
     * @Create on: 2017/7/15 下午2:01 
     *
     * @author shujiangcheng
     */
    @Override
    public List<SkuQtyVO> getSkuQtyListBySkuList(List<String> skuList, boolean isCache) {
        if(CollectionUtils.isEmpty(skuList)) {
            return null;
        }
        List<SkuQtyVO> resultList = Lists.newArrayList();
        List<String> skuNeedList = Lists.newArrayList();  //需要读取数据库的sku
        skuNeedList.addAll(skuList);
        if(isCache) {
            List<String> cacheSkuList = Lists.newArrayList();
            for(String skuCode : skuList) {
                boolean hasKey = getRedisCache().hasKey(getKey(skuCode, SkuQtyVO.class));
                if(hasKey) {
                    cacheSkuList.add(skuCode);
                    skuNeedList.remove(skuCode);
                }
            }
            if(CollectionUtils.isNotEmpty(cacheSkuList)) {
                resultList = getRedisCache().getValueBatch(getKeyList(cacheSkuList, SkuQtyVO.class));
            }
        }
        /*-----------------------------------------------------------------*
                                缓存没有读取到的，读取数据库
        *----------------------------------------------------------------*/

        //缓存未获取的读取数据库
        if(CollectionUtils.isNotEmpty(skuNeedList)) {
            logger.warn(JSON.toJSONString(skuNeedList) + "未通过缓存获取到");
            List<SkuQtyEntity> entityList = skuQtyDao.selectSkuQtyBySkuCode(skuNeedList);
            if(CollectionUtils.isNotEmpty(entityList)) {
                Map<String, Object> cacheMap = Maps.newHashMap();
                for(SkuQtyEntity entity : entityList) {
                    SkuQtyVO result = new SkuQtyVO();
                    BeanUtils.copyProperties(entity,result);
                    resultList.add(result);
                    cacheMap.put(getKey(result.getSkuCode(), SkuQtyVO.class), result);
                }
                if(isCache) {
                    getRedisCache().putValueBatch(cacheMap);
                }
            }


        }
        return resultList;
    }

    /**
     * @Description:获取库存信息
     * @Create on: 2017/7/18 下午5:30 
     *
     * @author shujiangcheng
     */
    @Override
    public SkuQtyVO getSkuQtyBySkuCode(String skuCode, boolean isCache) {
        if(StringUtils.isEmpty(skuCode)) {
            return null;
        }
        List<String> skuList = Lists.newArrayList(skuCode);
        List<SkuQtyVO> resultList = getSkuQtyListBySkuList(skuList, isCache);
        if(CollectionUtils.isEmpty(resultList)) {
            return null;
        }
        return resultList.get(0);
    }
}
