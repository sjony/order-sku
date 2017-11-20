package com.sjony.controller;

import com.alibaba.fastjson.JSON;
import com.sjony.base.BaseController;
import com.sjony.service.SkuService;
import com.sjony.utils.CollectionUtils;
import com.sjony.vo.SkuQtyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @Description: sku相关信息 
 * @Create on: 2017/11/20 上午10:50 
 *
 * @author shujiangcheng
 */
@Controller
@RequestMapping("/rest/sku")
public class SkuController extends BaseController {

    @Autowired
    private SkuService skuService;

    /**
     * @Description: 新增秒杀商品
     * @Create on: 2017/7/24 下午1:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/getSkuInfo", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String getSkuInfo(@RequestParam(value = "data") String data) {

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
        List<SkuQtyVO> result = skuService.getSkuQtyBySkuCode(skuList);

        /*-----------------------------------------------------------------*
                                数据返回
        *----------------------------------------------------------------*/
        return success(result);

    }

}
