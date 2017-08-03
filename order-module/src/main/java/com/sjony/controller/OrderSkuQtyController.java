package com.sjony.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.sjony.base.BaseController;
import com.sjony.service.OrderSkuQtyService;
import com.sjony.service.SkuQtyService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import com.sjony.vo.SkuSaleQtyVO;
import jdk.nashorn.internal.ir.ReturnNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin.extension.ExtensionUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by sjony on 2017/7/15.
 */
@Controller
@RequestMapping("/rest/orderSkuQty")
public class OrderSkuQtyController extends BaseController {

    @Autowired
    private OrderSkuQtyService orderSkuQtyService;
    
    /**
     * @Description: 插入库存信息 
     * @Create on: 2017/7/15 下午2:38 
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/insertSkuQty", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String insertSkuQty() {
        orderSkuQtyService.testInsert();
        return null;
    }


    /**
     * @Description: 获取库存信息
     * @Create on: 2017/7/15 下午2:38
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/getSkuQtyBySkuCode", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getSkuQtyBySkuCode(@RequestParam(value = "data") String data) {

        /*-----------------------------------------------------------------*
                                    入参处理
        *----------------------------------------------------------------*/
        JSONObject json = JSON.parseObject(data);
        if(null == json) {
           return error("data is null");
        }
        String skuCode = json.getString("skuCode");
        if(StringUtils.isEmpty(skuCode)) {
            return error("skuCode is null");
        }
        List<String> skuCodeList = Arrays.asList(skuCode.split(","));
        if(CollectionUtils.isEmpty(skuCodeList)) {
            return error("skuCode is null");
        }
        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        List<SkuQtyVO> skuQtyList = orderSkuQtyService.getSkuQtyBySkuCode(skuCodeList);

        return success(skuQtyList);
    }

    /**
     * @Description: 获取销售排行 
     * @Create on: 2017/7/24 下午1:39 
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/getSkuQtyForRank", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getSkuQtyForRank() {

        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        Set<SkuSaleQtyVO> skuSaleQtyList = orderSkuQtyService.getSkuQtyForRank();

        return success(skuSaleQtyList);
    }



}
