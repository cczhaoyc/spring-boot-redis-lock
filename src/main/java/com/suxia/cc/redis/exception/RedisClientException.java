package com.suxia.cc.redis.exception;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description RedisClientException
 * @date 2020/4/22 14:55
 */
public class RedisClientException extends RuntimeException {

    public RedisClientException() {
        super();
    }

    public RedisClientException(String message) {
        super(message);
    }

    public RedisClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisClientException(Throwable cause) {
        super(cause);
    }

    protected RedisClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
