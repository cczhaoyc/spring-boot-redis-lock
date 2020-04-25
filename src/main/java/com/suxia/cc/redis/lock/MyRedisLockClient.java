package com.suxia.cc.redis.lock;

import com.suxia.cc.redis.client.RedisClient;
import com.suxia.cc.redis.constant.RedisConstant;
import com.suxia.cc.redis.exception.RedisClientException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 直接使用Redis进行分布式锁
 * @date 2020/4/23 15:15
 */

@Component
public class MyRedisLockClient {

    private static final Logger LOG = LoggerFactory.getLogger(RedisLock.class);

    @Autowired
    private RedisClient redisClient;

    /**
     *  Redis加锁的操作
     */
    public Boolean lock(String key, String value, Long tryLockTimeout) {
        tryLockTimeout = System.currentTimeMillis() + tryLockTimeout;
        while (System.currentTimeMillis() < tryLockTimeout) {
            // setIfAbsent(),如果键不存在则新增,存在则不改变已经有的值
            if (Boolean.TRUE.equals(redisClient.setIfAbsent(key, value, RedisConstant.DEFAULT_LOCK_EXPIRE_2_MINUTES))) {
                LOG.info("第一次获取锁");
                return Boolean.TRUE;
            }
            String currentValue = redisClient.get(key, String.class);
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                // 获取上一个锁的时间,如果高并发的情况可能会出现已经被修改的问题,所以多一次判断保证线程的安全
//            String oldValue = redisClient.get(key, String.class);
                String oldValue = redisClient.getAndSet(key, value, RedisConstant.DEFAULT_LOCK_EXPIRE_2_MINUTES);
                if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue)) {
                    LOG.info("重入锁");
                    return Boolean.TRUE;
                }
            }
        /*String currentValue = redisClient.get(key, String.class);
        if (StringUtils.isNotEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()) {
            // 获取上一个锁的时间,如果高并发的情况可能会出现已经被修改的问题,所以多一次判断保证线程的安全
            String oldValue = redisClient.getAndSet(key, value, RedisConstant.DEFAULT_EXPIRE_TIME);
            if (StringUtils.isNotEmpty(oldValue) && oldValue.equals(currentValue)) {
                return Boolean.TRUE;
            }
        }*/
        }
        return Boolean.FALSE;
    }

    /**
     * Redis解锁的操作
     */
    public void unlock(String key, String value) {
        String currentValue = redisClient.get(key, String.class);
        try {
            if (StringUtils.isNotEmpty(currentValue) && currentValue.equals(value)) {
                redisClient.remove(key);
                LOG.info("释放redis分布式锁成功------------------------------");

            }
        } catch (Exception e) {
            throw new RedisClientException("Redis解锁异常", e);
        }
    }

    /*public boolean decrementProductStore(Long productId, Integer productQuantity) {
        String key = "dec_store_lock_" + productId;
        long time = System.currentTimeMillis();
        try {
            // 如果加锁失败
            if (!this.lock(key, String.valueOf(time))) {
                return false;
            }
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(productId);
            //如果库存为空
            if (productInfo.getProductStock() == 0) {
                return false;
            }
            //减库存操作
            productInfo.setProductStock(productInfo.getProductStock() - 1);
            productInfoMapper.updateByPrimaryKey(productInfo);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            //解锁
            redisLock.unlock(key, String.valueOf(time));
        }
        return true;
    }*/

}

