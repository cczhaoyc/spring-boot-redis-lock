package com.suxia.cc.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/24 14:03
 */
@RestController
@RequestMapping("/lock")
public class RedisLockTest {

    @Autowired
    private RenewalRedisLockImpl renewalRedisLock;
    @Autowired
    private SeckillServiceImpl seckillService;
    @Autowired
    private RedisLockClientImpl redisLockClient;
    @Autowired
    private MyRedisLockClientImpl myRedisLockClient;

    @RequestMapping("/orderProductMocckDiffUser")
    public String orderProductMocckDiffUser(String productId) {
        seckillService.orderProductMocckDiffUser(productId);
        return "success";
    }

    @RequestMapping("/renewalRedisLock")
    public String renewalRedisLock(String productId) {
        renewalRedisLock.orderProductMocckDiffUser(productId);
        return "success";
    }

    @RequestMapping("/redisLockClient")
    public String redisLockClient(String productId) {
        redisLockClient.orderProductMocckDiffUser(productId);
        return "success";
    }

    @RequestMapping("/myRedisLockClient")
    public String myRedisLockClient(String productId) {
        myRedisLockClient.orderProductMocckDiffUser(productId);
        return "success";
    }
}
