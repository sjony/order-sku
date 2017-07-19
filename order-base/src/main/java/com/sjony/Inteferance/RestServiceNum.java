package com.sjony.Inteferance;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

/**
 * @Description: 标识接口
 * @Create on: 2017/7/18 上午11:55 
 *
 * @author shujiangcheng
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = { METHOD })
@Inherited
public @interface RestServiceNum {

    String value();

}
