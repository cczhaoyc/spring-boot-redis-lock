package com.suxia.cc.mybatis.exception;

/**
 * 业务异常
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 514187374144179841L;
    
    private Integer code;
    private Object[] args;

	public ServiceException(String message, Object ... args) {
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

	public ServiceException(int code, Throwable cause) {
		super(cause);
		this.code = code;
	}

	public Integer getCode() {
		return code;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}
}