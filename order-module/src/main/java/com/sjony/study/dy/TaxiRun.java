package com.sjony.study.dy;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description: 出租车相关接口
 * @Create on: 2018/3/26 下午1:43
 *
 * @author dongying
 */
public interface TaxiRun {

    Double getRunTotal(Double startTime, Double distance);

}
