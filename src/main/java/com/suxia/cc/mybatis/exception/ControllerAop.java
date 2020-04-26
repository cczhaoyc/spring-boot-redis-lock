package com.suxia.cc.mybatis.exception;

import com.alibaba.fastjson.JSON;

import com.suxia.cc.mybatis.model.ActionResult;
import com.suxia.cc.mybatis.model.PageResult;
import com.suxia.cc.mybatis.query.AbstractQueryParam;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 项目web层拦截记录log,并对业务异常统一处理
 */
//@Aspect
//@Component
public class ControllerAop {
	private static final Logger logger = LoggerFactory.getLogger(ControllerAop.class);
	
	@Autowired
	MessageSource messageSource;

	@Pointcut("execution(* com.suxia.cc..*.*(..))")
    public void controllerMethod(){}
	
	@Around("controllerMethod()")
	public Object handlerControllerMethod(ProceedingJoinPoint pjp) {
		long startTime = System.currentTimeMillis();
		ActionResult result;
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			Object[] args = pjp.getArgs();
			logger.info("请求URL->{}", request.getRequestURI());
			logger.info("请求Method->{}", request.getMethod());
			logger.info("请求类和方法->{}", pjp.getSignature());
			logger.info("请求参数->{}", StringUtils.join(args, ":"));
			Object obj = pjp.proceed();
			logger.debug("请求结果:{}", JSON.toJSONString(obj));
			logger.info("请求耗时:{}", (System.currentTimeMillis() - startTime));
			return obj;
		} catch (Throwable e) {
			result = handlerException(pjp, e);
			MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
			Class<?> returnType = methodSignature.getReturnType();
			if(returnType == PageResult.class){
				return PageResult.create(new ExceptionQueryParam(result.getErrorNo(), result.getTitle()), null, 0);
			}
		}
		return result;
	}

	private ActionResult handlerException(ProceedingJoinPoint pjp, Throwable e) {
		ActionResult result = new ActionResult();
		if (e instanceof ServiceException) {
			ServiceException se = (ServiceException) e;
			String message = MessageSourceUtils.getMessageByArgs(messageSource, se.getMessage(), se.getArgs());
			logger.warn(pjp.getSignature() + " 业务异常: "+message, se);
			result.setTitle(message);
			result.setErrorNo(null == se.getCode()? 1 : se.getCode());
			result.setType(ActionResult.WARN);
		} else if (e instanceof NoLoginException) {
			logger.warn(pjp.getSignature() + " 没有登录 ", e);
			result.setTitle("没有登录");
			result.setErrorNo(201);
			result.setType(ActionResult.WARN);
		} else {
			logger.error(pjp.getSignature() + " 系统异常: "+e.getMessage(), e);
			result.setTitle(e.getMessage());
			result.setErrorNo(500);
			result.setType(ActionResult.ERROR);
		}
		return result;
	}
	
	class ExceptionQueryParam extends AbstractQueryParam {
		private static final long serialVersionUID = 1L;
		Integer errorNo;
		String Title;
		public ExceptionQueryParam(Integer errorNo, String title) {
			super();
			this.errorNo = errorNo;
			Title = title;
		}
		public Integer getErrorNo() {
			return errorNo;
		}
		public void setErrorNo(Integer errorNo) {
			this.errorNo = errorNo;
		}
		public String getTitle() {
			return Title;
		}
		public void setTitle(String title) {
			Title = title;
		}
	}
}
