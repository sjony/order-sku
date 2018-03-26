package com.sjony.study.dy;

import java.math.BigDecimal;

/**
 * @Description: 测试类
 * @Create on: 2018/3/26 下午1:43
 *
 * @author dongying
 */
public class TestTaxi {

    public static void main(String[] args) {

        //测试内环打车
        Taxi innerTaxi = new InnerTaxi();
        System.out.println("内环打车：" + innerTaxi.getRunTotal(23.0, 10.3));

        //测试外环打车
        Taxi outerTaxi = new OuterTaxi();
        System.out.println("外环打车：" + outerTaxi.getRunTotal(23.0, 10.3));


    }


}