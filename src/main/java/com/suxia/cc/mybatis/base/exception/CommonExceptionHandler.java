package com.suxia.cc.mybatis.base.exception;

import com.suxia.cc.mybatis.base.result.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 统一异常处理
 * @date 2020/4/22 10:35
 */
@ControllerAdvice
@ResponseBody
public class CommonExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CommonExceptionHandler.class);

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ActionResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e, HttpServletRequest request) {
        log.error("缺少请求参数", e);
        ActionResult result = new ActionResult();
        result.setTitle("缺少请求参数");
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ActionResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("参数解析失败", e);
        ActionResult result = new ActionResult();
        result.setTitle("参数解析失败");
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ActionResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("参数验证失败", e);
        BindingResult bingResult = e.getBindingResult();
        FieldError error = bingResult.getFieldError();
        String field = error.getField();
        String code = error.getDefaultMessage();
        String message = String.format("%s:%s", field, code);
        ActionResult result = new ActionResult();
        result.setTitle(message);
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(BindException.class)
    public ActionResult handleBindException(BindException e, HttpServletRequest request) {
        BindingResult bingResult = e.getBindingResult();
        FieldError error = bingResult.getFieldError();
        ActionResult result = new ActionResult();
        result.setTitle(error.getDefaultMessage());
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        log.error("参数绑定失败: " + error.getDefaultMessage(), e);
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ActionResult handleServiceException(ConstraintViolationException e, HttpServletRequest request) {
        log.error("参数验证失败", e);
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        ConstraintViolation<?> violation = violations.iterator().next();
        String message = violation.getMessage();
        ActionResult result = new ActionResult();
        result.setTitle(message);
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 400 - Bad Request
     */
    @ExceptionHandler(ValidationException.class)
    public ActionResult handleValidationException(ValidationException e, HttpServletRequest request) {
        log.error("参数验证失败", e);
        ActionResult result = new ActionResult();
        result.setTitle("参数验证失败");
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 405 - Method Not Allowed
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ActionResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("不支持当前请求方法", e);
        ActionResult result = new ActionResult();
        result.setTitle("不支持当前请求方法");
        result.setType(ActionResult.ERROR);
        result.setErrorNo(405);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 415 - Unsupported Media Type
     */
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ActionResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        log.error("不支持当前媒体类型", e);
        ActionResult result = new ActionResult();
        result.setTitle("不支持当前媒体类型");
        result.setType(ActionResult.ERROR);
        result.setErrorNo(415);
        result.setUrl(request.getRequestURI());
        return result;
    }


    /**
     * 上传超出最大限制捕获异常
     */
    @ExceptionHandler({MultipartException.class})
    public ActionResult handle(MultipartException ex, HttpServletRequest request) {
        log.error(ex.getMessage(), ex);
        ActionResult result = new ActionResult();
        result.setTitle(ex.getMessage());
        result.setType(ActionResult.ERROR);
        result.setErrorNo(400);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    private ActionResult handlerServiceException(ServiceException e, HttpServletRequest request) {

        ActionResult result = new ActionResult();
        result.setTitle(e.getMessage());
        int errorNo = null == e.getCode() ? ActionResult.SERVICE_ERROR_CODE : e.getCode();
        result.setErrorNo(errorNo);
        log.error("ServiceException--> 业务异常: " + e.getMessage() + ", 错误码: " + errorNo, e);
        result.setType(ActionResult.ERROR);
        result.setUrl(request.getRequestURI());
        return result;
    }

    /**
     * 没有登录异常
     */
    @ExceptionHandler(NoLoginException.class)
    public ActionResult handlerNoLoginException(NoLoginException e, HttpServletRequest request) {
        log.error("NoLoginException--> 没有登录 ", e);
        ActionResult result = new ActionResult();
        result.setTitle("没有登录");
        result.setErrorNo(ActionResult.NO_LOGIN_ERROR_CODE);
        result.setType(ActionResult.ERROR);
        result.setUrl(request.getRequestURI());
        return result;
    }
}
