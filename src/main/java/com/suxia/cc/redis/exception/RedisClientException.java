package com.suxia.cc.redis.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description RedisClientException
 * @date 2020/4/22 14:55
 */
public class RedisClientException extends RuntimeException {

    private static final Logger LOG = LoggerFactory.getLogger(RedisClientException.class);

    public RedisClientException() {
        super();
    }

    public RedisClientException(String message) {
        super(message);
        LOG.error(message);
    }

    public RedisClientException(String message, Throwable cause) {
        super(message, cause);
        LOG.error(message, cause);
    }

    public RedisClientException(Throwable cause) {
        super(cause);
        LOG.error(cause.getMessage());
    }

    protected RedisClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        LOG.error(message, cause);
    }
}
