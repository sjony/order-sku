package com.sjony.controller;

import com.sjony.base.BaseController;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by sjony on 2017/7/18.
 */
@Controller
@RequestMapping("rest/orderGoods")
public class OrderGoodsController extends BaseController {


    /**
     * @Description: 新建订单
     * @Create on: 2017/7/18 上午11:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/createOrder", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String createOrder(@RequestParam(value = "data") String data) {
        return null;
    }


}
