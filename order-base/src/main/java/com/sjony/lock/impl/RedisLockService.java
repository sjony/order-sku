package com.sjony.lock.impl;

import com.sjony.base.BaseService;
import com.sjony.lock.ILockService;
import redis.clients.jedis.Jedis;

/**
 * @Description: redis分布式锁 
 * @Create on: 2017/12/3 下午6:20 
 *
 * @author shujiangcheng
 */
public class RedisLockService extends BaseService implements ILockService {


    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    /**
     * @Description:获取锁
     * @Create on: 2017/12/4 下午2:29
     * @author jshu
     *
     * @param lockKey
     * @param requestId
     * @param expireTime 超时时间
     *
     */
    @Override
    public boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {

        Jedis jedis = getJedis();

        /*-----------------------------------------------------------------*
        第一个为key，我们使用key来当锁，因为key是唯一的。
        第二个为value，我们传的是requestId，很多童鞋可能不明白，有key作为锁不就够了吗，为什么还要用到value？原因就是我们在上面讲到可靠性时，分布式锁要满足第四个条件解铃还须系铃人，通过给value赋值为requestId，我们就知道这把锁是哪个请求加的了，在解锁的时候就可以有依据。requestId可以使用UUID.randomUUID().toString()方法生成。
        第三个为nxxx，这个参数我们填的是NX，意思是SET IF NOT EXIST，即当key不存在时，我们进行set操作；若key已经存在，则不做任何操作；
        第四个为expx，这个参数我们传的是PX，意思是我们要给这个key加一个过期的设置，具体时间由第五个参数决定。
        第五个为time，与第四个参数相呼应，代表key的过期时间。
        *----------------------------------------------------------------*/
        String result = jedis.set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);

        if (LOCK_SUCCESS.equals(result)) {
            return true;
        }
        return false;

    }


}
