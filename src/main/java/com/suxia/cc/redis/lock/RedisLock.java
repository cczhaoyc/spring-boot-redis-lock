package com.suxia.cc.redis.lock;

import com.suxia.cc.redis.client.RedisClient;
import com.suxia.cc.redis.constant.RedisConstant;
import com.suxia.cc.redis.exception.RedisClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description Redis分布式锁
 * @date 2020/4/23 17:52
 */
@Component
public class RedisLock {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisClient redisClient;

    /**
     * 为资源添加分布式锁
     *
     * @param lockName   锁名称
     * @param expireTime 指定锁过期时间
     * @param lockTime   等待获取锁的时间
     */
    public Boolean lock(String lockName, Long expireTime, Long lockTime) {
        // 获取锁的时间，超过这个时间还没获取锁则放弃
        StringBuffer lock = new StringBuffer("lock:");
        lock.append(lockName);
        Long end = System.currentTimeMillis() + lockTime;
        while (System.currentTimeMillis() < end) {
            //获取锁，如果成功则返回设置锁的value
            //setIfAbsent相当于setNX
            if (redisClient.setIfAbsent(lock.toString(), String.valueOf(System.currentTimeMillis()), expireTime)) {
                return Boolean.TRUE;
            }
            //设置锁的过期时间失败
            /*if (redisClient.getExpire(lock.toString()) == -1) {
                redisClient.expire(lock.toString(), expireTime);
            }*/
            //currentLock:锁的过期时间
            String currentLock = redisClient.get(lock.toString(), String.class);
            //获取锁失败，判断是否锁超时
            //currentLock<当前时间，锁已过期
            if (StringUtils.isNotEmpty(currentLock) && Long.parseLong(currentLock) < System.currentTimeMillis()) {
                //getAndSet==getSet,如果设置成功，则返回旧值，如果设置不成功则返回nil
                String oldValue = redisClient.getAndSet(lock.toString(), String.valueOf(System.currentTimeMillis()), RedisConstant.DEFAULT_EXPIRE_TIME);
                //新的值设置成功
                if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentLock)) {
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * 释放分布式锁
     *
     * @param key
     * @param value
     */
    public void unlock(String key, String value) {
        try {
            StringBuffer lock = new StringBuffer("lock:");
            lock.append(key);
            String currentValue = redisClient.get(lock.toString(), String.class);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisClient.remove(lock.toString());
                LOG.info("释放redis分布式锁成功");
            }
        } catch (Exception e) {
            throw new RedisClientException("释放redis分布式锁失败", e);
        }
    }
}