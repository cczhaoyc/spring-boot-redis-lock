package com.suxia.cc.mybatis.base.utils;

import com.suxia.cc.mybatis.base.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 金额格式化工具类
 * @date 2020/4/22 10:35
 */
public class MoneyUtils {

    private static final Logger log = LoggerFactory.getLogger(MoneyUtils.class);

    public static String format(Double money) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(money);
    }

    public static String format(BigDecimal money) {
        DecimalFormat df = new DecimalFormat("0.00");
        df.setRoundingMode(RoundingMode.HALF_UP);
        return df.format(money);
    }

    public static String format(String money) {
        try {
            DecimalFormat df = new DecimalFormat("0.00");
            df.setRoundingMode(RoundingMode.HALF_UP);
            return df.format(money);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ServiceException("金额输入不合法");
        }
    }
}
