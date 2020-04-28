package com.suxia.cc.mybatis.base.constant;

/**
 * 常量类
 */
public class DefaultConstants {

    /**
     * 默认密码
     */
    public static final String DEFAULT_PWD = "000000";

    /**
     * 默认每页显示条数
     */
    public static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * 人民币币种对应的枚举标识
     */
    public static final String RMB_CODE = "cny";

    /**
     * 解绑类型（1:手机；2:邮箱）
     */
    public static final Integer PHONE_TYPE = 1;
    public static final Integer EMAIL_TYPE = 2;

    /**
     * 文件是否加密, 1:加密 0:不加密
     */
    public static final int FILE_ENCRYPT_TRUE = 1;
    public static final int FILE_ENCRYPT_FALSE = 0;

    /**
     * 文件加密前缀
     */
    public static final String ENCRYPTED_NAME = "encrypted_";

    public static final String DATE_PATTERN = "yyyy/MM/dd";

    public static final String TIME_PATTERN = "yyyy/MM/dd HH:mm:ss";
}
