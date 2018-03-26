package com.sjony.study.dy;

import java.util.Date;

/**
 * @Description: 内环出租车
 * @Create on: 2018/3/26 下午1:43
 *
 * @author dongying
 */

public class InnerTaxi extends Taxi{

    public InnerTaxi() {
        //起步路程
        this.startingRoad = super.startingRoad;

        //起步价
        this.initiateRate = super.initiateRate;

        //3公里-10公里中每公里价钱
        this.afterStartPerKmPrice = super.afterStartPerKmPrice;

        //10公里以后每公里价钱
        this.afterTenPerKmPrice = new Double(3.5);

        //起步价（夜间）
        this.initiateRateNight = super.initiateRateNight;

        //3公里-10公里中每公里价钱（夜间）
        this.afterStartPerKmPriceNight = super.afterStartPerKmPriceNight;

        //10公里以后每公里价钱（夜间）
        this.afterTenPerKmPriceNight = new Double(4.7);

        //夜间开始时间
        this.startNightTime = super.startNightTime;

        //夜间结束时间
        this.endNightTime = super.endNightTime;
    }

    public InnerTaxi(Double startingRoad, Double initiateRate, Double afterStartPerKmPrice, Double afterTenPerKmPrice, Double initiateRateNight, Double afterStartPerKmPriceNight,
                     Double afterTenPerKmPriceNight, Double startNightTime, Double endNightTime) {
        //起步路程
        this.startingRoad = startingRoad;

        //起步价
        this.initiateRate = initiateRate;

        //3公里-10公里中每公里价钱
        this.afterStartPerKmPrice = afterStartPerKmPrice;

        //10公里以后每公里价钱
        this.afterTenPerKmPrice = afterTenPerKmPrice;

        //起步价
        this.initiateRateNight = initiateRateNight;

        //3公里-10公里中每公里价钱
        this.afterStartPerKmPriceNight = afterStartPerKmPriceNight;

        //10公里以后每公里价钱
        this.afterTenPerKmPriceNight = afterTenPerKmPriceNight;

        //夜间开始时间
        this.startNightTime = startNightTime;

        //夜间结束时间
        this.endNightTime = endNightTime;
    }


    public Double getStartingRoad() {
        return startingRoad;
    }

    public void setStartingRoad(Double startingRoad) {
        this.startingRoad = startingRoad;
    }

    public Double getInitiateRate() {
        return initiateRate;
    }

    public void setInitiateRate(Double initiateRate) {
        this.initiateRate = initiateRate;
    }

    public Double getAfterStartPerKmPrice() {
        return afterStartPerKmPrice;
    }

    public void setAfterStartPerKmPrice(Double afterStartPerKmPrice) {
        this.afterStartPerKmPrice = afterStartPerKmPrice;
    }

    public Double getAfterTenPerKmPrice() {
        return afterTenPerKmPrice;
    }

    public void setAfterTenPerKmPrice(Double afterTenPerKmPrice) {
        this.afterTenPerKmPrice = afterTenPerKmPrice;
    }

    public Double getInitiateRateNight() {
        return initiateRateNight;
    }

    public void setInitiateRateNight(Double initiateRateNight) {
        this.initiateRateNight = initiateRateNight;
    }

    public Double getAfterStartPerKmPriceNight() {
        return afterStartPerKmPriceNight;
    }

    public void setAfterStartPerKmPriceNight(Double afterStartPerKmPriceNight) {
        this.afterStartPerKmPriceNight = afterStartPerKmPriceNight;
    }

    public Double getAfterTenPerKmPriceNight() {
        return afterTenPerKmPriceNight;
    }

    public void setAfterTenPerKmPriceNight(Double afterTenPerKmPriceNight) {
        this.afterTenPerKmPriceNight = afterTenPerKmPriceNight;
    }

    public Double getStartNightTime() {
        return startNightTime;
    }

    public void setStartNightTime(Double startNightTime) {
        this.startNightTime = startNightTime;
    }

    public Double getEndNightTime() {
        return endNightTime;
    }

    public void setEndNightTime(Double endNightTime) {
        this.endNightTime = endNightTime;
    }


}
