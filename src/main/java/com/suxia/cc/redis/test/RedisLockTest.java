package com.suxia.cc.redis.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
    private RedisLockClientService redisLockClient;

    @PostMapping("/myRedisLockClient")
    public String myRedisLockClient(String productId) {
        redisLockClient.orderProductMocckDiffUser(productId);
        return "success";
    }
}
