package com.sjony.study.dy.Taxi;

import java.util.Date;

/**
 * @Description: 出租车
 * @Create on: 2018/3/26 下午12:20 
 *
 * @author dongy
 */
public abstract class Taxi  implements TaxiRun {

    //起步路程
    protected Double startingRoad = new Double(3);

    //起步价
    protected Double initiateRate = new Double(14);

    //3公里-10公里中每公里价钱
    protected Double afterStartPerKmPrice = new Double(2.5);

    //10公里以后每公里价钱
    protected Double afterTenPerKmPrice;

    //起步价（夜间）
    protected Double initiateRateNight = new Double(18);

    //3公里-10公里中每公里价钱（夜间）
    protected Double afterStartPerKmPriceNight = new Double(3);

    //10公里以后每公里价钱（夜间）
    protected Double afterTenPerKmPriceNight;

    //夜间起始时间
    protected Double startNightTime = new Double(23);

    //夜间结束时间
    protected Double endNightTime = new Double(6);

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

    /**
     * @Description:获取行程费用
     * @Create on: 2018/3/26 下午1:43
     * @author dongying
     *
     * @param startTime  上车时间
     * @param distance   行程
     *
     */
    @Override
    public Double getRunTotal(Double startTime, Double distance) {

        //入参校验
        if(null == startTime || null == distance || new Double(0).compareTo(distance) >= 0) {
            System.out.println("系统异常，起步时间或者行程不能为空");
            System.exit(0);
        }

        Double currentInitiateRate = null;    //当前起步价
        Double currentAfterStartPerKmPrice = null;    //当前3-10的价钱
        Double currentAfterTenPerKmPrice = null;   //当前10公里以后的价钱

        //首先根据上车时间，判断当前算白天还是夜晚，然后对价钱取值
        // 夜间
        if(startTime.compareTo(startNightTime) >= 0 || startTime.compareTo(endNightTime) < 0) {
            currentInitiateRate = initiateRate;
            currentAfterStartPerKmPrice = afterStartPerKmPriceNight;
            currentAfterTenPerKmPrice = afterTenPerKmPriceNight;

        } else {    //白天
            currentInitiateRate = initiateRateNight;
            currentAfterStartPerKmPrice = afterStartPerKmPrice;
            currentAfterTenPerKmPrice = afterTenPerKmPrice;

        }

        //当前整数里程，如果有小数，往前进一位
        long currentKm = (long) Math.ceil(distance);

        //计算总价
        double total = 0;

        //如果小于3公里
        if(currentKm <= startingRoad) {

            total = currentInitiateRate;

        } else {
            //如果大于3公里并且小于10公里
            if(currentKm <= 10) {

                total = currentInitiateRate + (currentKm-3) * currentAfterStartPerKmPrice;

            } else {        //如果大于10公里

                total = currentInitiateRate + 7 * currentAfterStartPerKmPrice + (currentKm-10) * currentAfterTenPerKmPrice;

            }

        }

        return total;
    }

}
