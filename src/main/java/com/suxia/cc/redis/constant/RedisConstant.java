package com.suxia.cc.redis.constant;

import java.util.concurrent.TimeUnit;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description Redis常量
 * @date 2020/4/22 15:09
 */
public interface RedisConstant {

    /**
     * key分隔符
     */
    String DELIMITER = ":";

    /**
     * 默认缓存失效时间2小时
     */
    Long DEFAULT_EXPIRE_TIME = TimeUnit.HOURS.toSeconds(2);

    /**
     * 缓存失效时间10小时
     */
    Long EXPIRE_TIME_10_HOURS = TimeUnit.HOURS.toSeconds(10);

    /**
     * 缓存失效时间2分钟
     */
    Long DEFAULT_LOCK_EXPIRE_2_MINUTES = TimeUnit.MINUTES.toSeconds(2);

    /**
     * 默认获取锁超时时间5秒
     */
    Long DEFAULT_TRY_LOCK_TIMEOUT = 5 * 1000L;

}
