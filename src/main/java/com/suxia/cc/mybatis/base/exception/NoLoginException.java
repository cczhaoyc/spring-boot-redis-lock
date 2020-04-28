package com.suxia.cc.mybatis.base.exception;


/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 没有登录异常
 * @date 2020/4/22 10:35
 */
public class NoLoginException extends RuntimeException {

    private static final long serialVersionUID = 7429507448814349322L;

    public NoLoginException() {
        super("没有登录");
    }
}
