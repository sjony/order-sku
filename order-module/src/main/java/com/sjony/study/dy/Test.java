package com.sjony.study.dy;

import com.sjony.study.dy.Taxi.OuterTaxi;
import com.sjony.study.dy.Taxi.Taxi;

/**
 * Created by sjony on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
        Taxi taxi = new OuterTaxi();
        System.out.println(taxi.getAfterStartPerKmPriceNight());
    }

}
