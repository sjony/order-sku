package com.sjony.support;

/**
 * @Description:  标识设置
 * @Create on: 2017/7/18 上午11:56 
 *
 * @author shujiangcheng
 */
public class ServiceNumberSupport {

    private static ThreadLocal<String> serviceNumLocal = new ThreadLocal<String>();

    public static void setServiceNum(String serviceNum) {
        serviceNumLocal.set(serviceNum);
    }

    public static String getServceNum() {
        return serviceNumLocal.get();
    }


}
