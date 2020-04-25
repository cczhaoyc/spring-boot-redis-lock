package com.suxia.cc.redis.constant;

import java.util.UUID;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description TODO
 * @date 2020/4/23 18:02
 */
public class KeyUtil {

    public static String getUniqueKey() {
        return "REDIS_KEY_" + UUID.randomUUID().toString().replace("-", "");
    }

    public static String getUniqueValue() {
        return "REDIS_VALUE_" + UUID.randomUUID().toString().replace("-", "");
    }
}
