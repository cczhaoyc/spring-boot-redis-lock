package com.suxia.cc.redis.lock;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;


/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description redis分布式锁事务实现
 * @date 2020/4/24 13:51
 */

@Component
public class RenewalRedisLock {

    private final String lock_prefix = "lock:";
    private static ThreadLocal<Thread> THREAD_LOCAL = new ThreadLocal<>();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Boolean lock(String lock, long timeout, long tryLockTimeout) {
        String identification = UUID.randomUUID().toString();
        String lockName = String.format("%s%s", lock_prefix, lock);
        tryLockTimeout = System.currentTimeMillis() + tryLockTimeout;
        while (System.currentTimeMillis() < tryLockTimeout) {
            Boolean flag = redisTemplate.opsForValue().setIfAbsent(lockName, identification, timeout, TimeUnit.MILLISECONDS);
            if (flag) {
                System.out.println(Thread.currentThread().getName() + "获得锁");

                // 续签
                Thread renewal = new Thread(new RenewalLock(lock, identification, timeout));
                renewal.setDaemon(true);
                THREAD_LOCAL.set(renewal);
                renewal.start();
                return Boolean.TRUE;
            }
        }
        System.err.println(Thread.currentThread().getName() + "超时未获得锁");
        return Boolean.FALSE;
    }

    public void unlock(String lock, String identification) {
        try {
            redisTemplate.setEnableTransactionSupport(true);
            String lockName = String.format("%s%s", lock_prefix, lock);
            redisTemplate.watch(lockName);
            String identificationDB = redisTemplate.opsForValue().get(lockName);
            if (identificationDB != null && identification != null && identification.equals(identificationDB)) {
                System.out.println(Thread.currentThread().getName() + "释放锁");
                redisTemplate.multi();
                redisTemplate.delete(lockName);
                redisTemplate.exec();
                THREAD_LOCAL.get().interrupt();
            }
            redisTemplate.unwatch();
        } finally {
            THREAD_LOCAL.remove();
        }
    }

    /**
     * 续签lock
     */
    private class RenewalLock implements Runnable {

        private String lockName;
        private String identification;
        private Long timeout;

        public RenewalLock(String lock, String identification, Long timeout) {
            this.lockName = lock_prefix + lock;
            this.identification = identification;
            this.timeout = timeout;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        return;
                    }
                    Thread.sleep(timeout - 500);
                    redisTemplate.setEnableTransactionSupport(true);
                    redisTemplate.watch(lockName);
                    String identificationDB = redisTemplate.opsForValue().get(lockName);
                    if (identificationDB != null && identification != null && identificationDB.equals(identification)) {
                        redisTemplate.multi();
                        redisTemplate.expire(lockName, timeout, TimeUnit.MILLISECONDS);
                        redisTemplate.exec();
                    }
                    redisTemplate.unwatch();
                } catch (InterruptedException e) {
                    System.err.println("守护线程续签中断");
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
