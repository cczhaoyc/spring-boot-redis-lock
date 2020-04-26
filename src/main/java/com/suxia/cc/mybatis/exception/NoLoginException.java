package com.suxia.cc.mybatis.exception;

/**
 * 没有登录异常
 */
public class NoLoginException extends RuntimeException {
    private static final long serialVersionUID = 3583566093089790852L;

    public NoLoginException() {
        super("没有登录");
    }
}
