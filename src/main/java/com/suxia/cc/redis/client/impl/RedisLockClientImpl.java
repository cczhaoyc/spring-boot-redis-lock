package com.suxia.cc.redis.client.impl;

import com.suxia.cc.redis.client.RedisClient;
import com.suxia.cc.redis.client.RedisLockClient;
import com.suxia.cc.redis.exception.RedisClientException;
import com.suxia.cc.redis.lock.RedisLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 直接使用Redis进行分布式锁
 * @date 2020/4/23 15:15
 */
@Component
public class RedisLockClientImpl implements RedisLockClient {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisClient redisClient;

    /**
     * Redis加锁的操作
     *
     * @param key            缓存key
     * @param value          缓存值
     * @param expireTime     缓存失效时间（秒）
     * @param tryLockTimeout 获取锁等待时间（毫秒），不能小于5秒
     * @return true获取锁成功，false获取锁失败
     */
    @Override
    public Boolean lock(String key, String value, Long expireTime, Long tryLockTimeout) {
        if (tryLockTimeout > 0L) {
            tryLockTimeout = System.currentTimeMillis() + tryLockTimeout;
            while (System.currentTimeMillis() <= tryLockTimeout) {
                // setIfAbsent(),如果键不存在则新增,存在则不改变已经有的值
                if (Boolean.TRUE.equals(redisClient.setIfAbsent(key, value, expireTime))) {
                    LOG.info("第一次获取锁");
                    return Boolean.TRUE;
                }
                String currentValue = redisClient.get(key, String.class);
                if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                    // 获取上一个锁的时间,如果高并发的情况可能会出现已经被修改的问题,所以多一次判断保证线程的安全
                    // expireTime重入时重新设置锁的过期时间
                    String oldValue = redisClient.getAndSet(key, value, expireTime);
                    if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue)) {
                        LOG.info("重入锁");
                        return Boolean.TRUE;
                    }
                }
            }
        } else {
            // setIfAbsent(),如果键不存在则新增,存在则不改变已经有的值
            if (Boolean.TRUE.equals(redisClient.setIfAbsent(key, value, expireTime))) {
                LOG.info("第一次获取锁");
                return Boolean.TRUE;
            }
            String currentValue = redisClient.get(key, String.class);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                // 获取上一个锁的时间,如果高并发的情况可能会出现已经被修改的问题,所以多一次判断保证线程的安全
                // expireTime重入时重新设置锁的过期时间
                String oldValue = redisClient.getAndSet(key, value, expireTime);
                if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue)) {
                    LOG.info("重入锁");
                    return Boolean.TRUE;
                }
            }
        }
        return Boolean.FALSE;
    }

    /**
     * Redis释放锁
     *
     * @param key   缓存key
     * @param value 缓存值
     */
    @Override
    public void unlock(String key, String value) {
        String currentValue = redisClient.get(key, String.class);
        try {
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisClient.remove(key);
                LOG.info("Redis释放锁成功");
            }
        } catch (Exception e) {
            throw new RedisClientException("Redis释放锁异常", e);
        }
    }
}

