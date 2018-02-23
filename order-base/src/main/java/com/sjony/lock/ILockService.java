package com.sjony.lock;

/**
 * @Description: 分布式锁 
 * @Create on: 2017/12/3 下午6:20
 *
 * @author shujiangcheng
 */
public interface ILockService {


    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime);

}