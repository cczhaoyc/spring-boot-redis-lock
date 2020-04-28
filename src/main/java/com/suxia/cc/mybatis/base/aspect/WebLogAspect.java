package com.suxia.cc.mybatis.base.aspect;

import com.alibaba.fastjson.JSON;
import com.suxia.cc.mybatis.base.exception.NoLoginException;
import com.suxia.cc.mybatis.base.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 项目WEB层拦截记录LOG
 * @date 2020/4/22 10:35
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(* com.suxia.cc.mybatis.user.controller..*.*(..))")
    public void controllerMethod() {
    }

    @Around("controllerMethod()")
    public Object handlerControllerMethod(ProceedingJoinPoint pjp) throws Throwable {
        long startTime = System.currentTimeMillis();
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            Object[] args = pjp.getArgs();
            logger.info("请求URL-->{}", request.getRequestURL().toString());
            logger.info("请求Method-->{}", request.getMethod());
            logger.info("请求类和方法-->{}", pjp.getSignature());
            logger.info("请求参数-->{}", StringUtils.join(args, ":"));
            Object obj = pjp.proceed();
            logger.debug("请求结果:{}", JSON.toJSONString(obj));
            logger.info("请求耗时:{}", (System.currentTimeMillis() - startTime));
            return obj;
        } catch (ServiceException e) {
            throw new ServiceException(e.getCode(), e.getMessage(), e);
        } catch (NoLoginException e) {
            throw new NoLoginException();
        } catch (Throwable e) {
            throw e;
        }
    }
}
