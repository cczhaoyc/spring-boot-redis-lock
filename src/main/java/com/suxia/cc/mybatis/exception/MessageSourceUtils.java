package com.suxia.cc.mybatis.exception;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

public class MessageSourceUtils {
	private static final Logger log = LoggerFactory.getLogger(MessageSourceUtils.class);
	
	/**
	 * 从MessageSource中获取错误信息
	 * @param messageSource
	 * @param code
	 * @param args
	 * @return
	 */
	public static String getMessageByArgs(MessageSource messageSource, String code, Object[] args){
		try{
			return messageSource.getMessage(code, args, Locale.CHINESE);
		}catch(Exception e){
			log.warn("在message-zh.properties中未配置[{}]", code, e.getMessage());
			return code;
		}
	}
	
	public static String getMessage(MessageSource messageSource, String code, String ... args){
		return getMessageByArgs(messageSource, code, args);
	}

}
