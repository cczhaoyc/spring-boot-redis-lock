package com.suxia.cc.mybatis.base.interceptor;

import com.suxia.cc.mybatis.base.constant.DefaultConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;

public class DateConverter implements Converter<String, Date> {

    private static final Logger log = LoggerFactory.getLogger(DateConverter.class);

    @Override
    public Date convert(String source) {
        if (StringUtils.isBlank(source)) {
            return null;
        }
        try {
            String pattern = DefaultConstants.DATE_PATTERN;
            if (source.length() > 10) {
                pattern = DefaultConstants.TIME_PATTERN;
            }
            FastDateFormat sdf = FastDateFormat.getInstance(pattern);
            return sdf.parse(source);
        } catch (Exception e) {
            log.warn("日期类型转换错误:{}, 正确的格式为:{}", source, "yyyy/MM/dd或者yyyy/MM/dd hh:mm:ss");
            return null;
        }
    }
}