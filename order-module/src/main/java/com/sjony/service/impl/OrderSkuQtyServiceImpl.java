package com.sjony.service.impl;

import com.google.common.collect.Lists;
import com.sjony.service.OrderSkuQtyService;
import com.sjony.service.SkuQtyService;
import com.sjony.service.SkuSaleQtyService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: 库存相关
 * @Create on: 2017/7/15 下午3:07 
 *
 * @author shujiangcheng
 */
@Service
public class OrderSkuQtyServiceImpl implements OrderSkuQtyService {

    @Autowired
    private SkuQtyService skuQtyService;

    @Autowired
    private SkuSaleQtyService skuSaleQtyService;
    

    /**
     * @Description: 插入商品数据
     * @Create on: 2017/7/18 下午1:46 
     *
     * @author shujiangcheng
     */
    @Transactional
    @Override
    public void testInsert() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
        Date date=new Date();
        String dateString = sdf.format(date);

        for(int i=0; i<10; i++) {
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for(int i=0; i<10000; i++) {
                        SkuQtyVO skuQtyVO = new SkuQtyVO();
                        skuQtyVO.setSkuQty(new BigDecimal(i * 5));
                        skuQtyVO.setCreateTime(new Date());
                        skuQtyVO.setUpdateTime(new Date());
                        skuQtyVO.setCreateUser("jshu");
                        skuQtyVO.setUpdateUser("jshu");
                        int id = skuQtyService.inserSkuQty(skuQtyVO);
                        String skuCode = "ks" + dateString + id;
                        skuQtyVO.setSkuCode(skuCode);
                        skuQtyVO.setId(Long.valueOf(id));
                        skuQtyService.updateSkuQty(skuQtyVO);
                    }

                }
            });

        }
        pool.shutdown();

    }

    /**
     * @Description: 查询商品库存
     * @Create on: 2017/7/18 下午1:46 
     *
     * @author shujiangcheng
     */
    @Override
    public List<SkuQtyVO> getSkuQtyBySkuCode(List<String> skuCodeList) {
        List<SkuQtyVO> result = null;
        //入参校验
        if(CollectionUtils.isEmpty(skuCodeList)) {
            return result;
        }

        result = skuQtyService.getSkuQtyListBySkuList(skuCodeList);

        return result;
    }

    @Override
    public Set<SkuSaleQtyVO> getSkuQtyForRank() {
        Set<SkuSaleQtyVO> resultList = skuSaleQtyService.getSkuQtyForRank();
        return resultList;
    }
}
