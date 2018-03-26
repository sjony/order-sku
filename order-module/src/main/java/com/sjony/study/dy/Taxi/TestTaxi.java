package com.sjony.study.dy.Taxi;

import java.math.BigDecimal;

/**
 * Created by sjony on 2018/3/26.
 */
public class TestTaxi {


    public static void main(String[] args) {

        Taxi taxi = new InnerTaxi();
        System.out.print(taxi.getRunTotal(7.0, 4.0));

    }


}