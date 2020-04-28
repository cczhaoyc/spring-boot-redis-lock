package com.suxia.cc.mybatis.base.exception;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 业务异常
 * @date 2020/4/22 10:35
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 514187374144179841L;

    private Integer code = 200;
    private Object[] args;

    public ServiceException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceException(int code, String message) {
        super(message);
        this.code = code;
    }

    public ServiceException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ServiceException(int code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}