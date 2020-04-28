package com.suxia.cc.mybatis.base.utils;

import java.util.UUID;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description UUID去掉中间横杠（-）
 * @date 2020/4/27 20:15
 */
public class UUIDUtils {

    /**
     * 生成uuid
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
