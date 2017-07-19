package com.sjony.handler;

import com.sjony.Inteferance.RestServiceNum;
import com.sjony.support.ServiceNumberSupport;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @Description: 返回结果处理 
 * @Create on: 2017/7/18 上午11:46
 *
 * @author shujiangcheng
 */
public class ServiceNumberHandler {


    /**
     * @Description: 切面获取 RestServiceNum的值
     * @Create on: 2017/7/18 下午12:00 
     *
     * @author shujiangcheng
     */
    public void doBefore(JoinPoint jp) throws Throwable {

        if(jp.getSignature() instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) jp
                    .getSignature();
            Method method = methodSignature.getMethod();
            RestServiceNum anno = method.getAnnotation(RestServiceNum.class);
            if (anno != null) {
                String serviceNum = anno.value();
                ServiceNumberSupport.setServiceNum(serviceNum);
            }
        }


    }


}
