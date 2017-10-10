package com.sjony.test;

import com.sjony.base.BaseController;
import com.sjony.service.SkuQtyService;
import com.sjony.vo.SkuQtyVO;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("rest/test")
public class TestController extends BaseController {

    @Autowired
    private SkuQtyService skuQtyService;


    /**
     * @Description: 秒杀商品扣库存
     * @Create on: 2017/7/24 下午1:39
     *
     * @author shujiangcheng
     */
    @ResponseBody
    @RequestMapping(value = "/test", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public String test(@RequestParam(value = "data") String data) throws IOException {

        /*-----------------------------------------------------------------*
                                入参处理
        *----------------------------------------------------------------*/

        String pathResult = "/Users/sjony/Desktop/study/test/result.txt";

        File file = new File(pathResult);

        //获取所有的sysNo
        List<String> result = FileUtils.readLines(file);

        /*-----------------------------------------------------------------*
                                逻辑处理
        *----------------------------------------------------------------*/
        Long t1 = System.currentTimeMillis();
        List<SkuQtyVO> result1 = skuQtyService.getSkuQtyListBySkuList(result, false);
        Long t2 = System.currentTimeMillis();
        System.out.println("耗时：" + (t2-t1) + "ms");

        Long t3 = System.currentTimeMillis();
        for(String s : result) {
            skuQtyService.getSkuQtyBySkuCode(s, false);
        }
        Long t4 = System.currentTimeMillis();
        System.out.println("耗时：" + (t4-t3) + "ms");

        /*-----------------------------------------------------------------*
                                数据返回
        *----------------------------------------------------------------*/
        return success(result1);

    }


}
