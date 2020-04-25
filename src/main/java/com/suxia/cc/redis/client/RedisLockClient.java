package com.suxia.cc.redis.client;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 直接使用Redis进行分布式锁
 * @date 2020/4/23 15:15
 */
public interface RedisLockClient {

    /**
     * Redis加锁的操作
     *
     * @param key            缓存key
     * @param value          缓存值
     * @param expireTime     缓存失效时间（秒）
     * @param tryLockTimeout 获取锁等待时间（毫秒）
     * @return true获取锁成功，false获取锁失败
     */
    Boolean lock(String key, String value, Long expireTime, Long tryLockTimeout);

    /**
     * Redis释放锁
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    void unlock(String key, String value);
}