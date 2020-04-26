package com.suxia.cc.mybatis.exception;

import com.suxia.cc.mybatis.model.ActionResult;
import org.apache.tomcat.util.http.fileupload.FileUploadBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
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

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Set;

/**
 * SpringMvc常见异常处理
 */
@ControllerAdvice(basePackages="com.yibi.quick.trial")
@ResponseBody
public class CommonExceptionAdvice  {
	private static Logger logger = LoggerFactory.getLogger(CommonExceptionAdvice.class);

	@Resource
	MessageSource messageSource;

	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ActionResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		logger.error("缺少请求参数", e);
		ActionResult result = new ActionResult();
		result.setTitle("缺少请求参数");
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ActionResult handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		logger.error("参数解析失败", e);
		ActionResult result = new ActionResult();
		result.setTitle("参数解析失败");
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ActionResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		logger.error("参数验证失败", e);
		BindingResult bingResult = e.getBindingResult();
		FieldError error = bingResult.getFieldError();
		String field = error.getField();
		String code = error.getDefaultMessage();
		String message = String.format("%s:%s", field, code);
		ActionResult result = new ActionResult();
		result.setTitle(message);
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ExceptionHandler(BindException.class)
	public ActionResult handleBindException(BindException e) {
		logger.error("参数绑定失败", e);
		BindingResult bingResult = e.getBindingResult();
		FieldError error = bingResult.getFieldError();
		String code = error.getDefaultMessage();
		String message = MessageSourceUtils.getMessage(messageSource, code);
		ActionResult result = new ActionResult();
		result.setTitle(message);
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	public ActionResult handleServiceException(ConstraintViolationException e) {
		logger.error("参数验证失败", e);
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
		ConstraintViolation<?> violation = violations.iterator().next();
		String message = violation.getMessage();
		ActionResult result = new ActionResult();
		result.setTitle(message);
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	/**
	 * 400 - Bad Request
	 */
	@ExceptionHandler(ValidationException.class)
	public ActionResult handleValidationException(ValidationException e) {
		logger.error("参数验证失败", e);
		ActionResult result = new ActionResult();
		result.setTitle("参数验证失败");
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}

	/**
	 * 405 - Method Not Allowed
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ActionResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		logger.error("不支持当前请求方法", e);
		ActionResult result = new ActionResult();
		result.setTitle("不支持当前请求方法");
		result.setType(ActionResult.WARN);
		result.setErrorNo(405);
		return result;
	}

	/**
	 * 415 - Unsupported Media Type
	 */
	@ExceptionHandler(HttpMediaTypeNotSupportedException.class)
	public ActionResult handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e) {
		logger.error("不支持当前媒体类型", e);
		ActionResult result = new ActionResult();
		result.setTitle("不支持当前媒体类型");
		result.setType(ActionResult.WARN);
		result.setErrorNo(415);
		return result;
	}
	

	/*@ExceptionHandler({MultipartException.class, FileUploadBase.SizeLimitExceededException.class})
	public ActionResult handle(MultipartException ex) {
		logger.error(ex.getMessage(), ex);
		ActionResult result = new ActionResult();
		result.setTitle(ex.getMessage());
		result.setType(ActionResult.WARN);
		result.setErrorNo(400);
		return result;
	}*/
}
