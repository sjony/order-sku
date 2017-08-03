package com.sjony.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sjony.base.BaseController;
import com.sjony.service.SeckillSkuService;
import com.sjony.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: 秒杀相关
 * @Create on: 2017/8/2 上午10:28 
 *
 * @author shujiangcheng
 */
@Controller
@RequestMapping("/rest/seckillSku")
public class SeckillSkuController extends BaseController {

    @Autowired
    private SeckillSkuService seckillSkuService;


    /**
     * @Description: 新增秒杀商品
     * @Create on: 2017/7/24 下午1:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/insertCacheSeckillSkuQty", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String insertCacheSeckillSkuQty(@RequestParam(value = "data") String data) throws IllegalAccessException, IntrospectionException, InvocationTargetException {

        /*-----------------------------------------------------------------*
                                入参处理
        *----------------------------------------------------------------*/
        if(data == null) {
            error("data is null");
        }
        List<String> skuList = JSON.parseArray(JSON.parseObject(data).getString("skuCode"), String.class);
        if(CollectionUtils.isEmpty(skuList)) {
            error("skuCode is null");
        }
        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        int result = seckillSkuService.insertCacheSeckillSkuQty(skuList);

        /*-----------------------------------------------------------------*
                                数据返回
        *----------------------------------------------------------------*/
        return success(result);

    }

    /**
     * @Description: 秒杀商品扣库存
     * @Create on: 2017/7/24 下午1:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/updateSeckillSkuQty", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String updateSeckillSkuQty(@RequestParam(value = "data") String data) {

        /*-----------------------------------------------------------------*
                                入参处理
        *----------------------------------------------------------------*/
        if(data == null) {
            error("data is null");
        }
        List<String> skuList = JSON.parseArray(JSON.parseObject(data).getString("skuCode"), String.class);
        if(CollectionUtils.isEmpty(skuList)) {
            error("skuCode is null");
        }
        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        int result = seckillSkuService.updateSeckillSkuQty(skuList);

        /*-----------------------------------------------------------------*
                                数据返回
        *----------------------------------------------------------------*/
        return success(result);

    }

}
