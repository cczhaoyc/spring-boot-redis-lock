
package com.suxia.cc.mybatis.base.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 手机号码、身份证、邮箱验证及脱敏
 * @date 2020/4/22 10:35
 */
public class CommonUtils {

    /**
     * @param account 校验手机号
     */
    public static boolean checkMobile(String account) {
        boolean flag = false;
        try {
            String check = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0-9])|(19[0-9]))\\d{8}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(account);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * @param account 校验邮箱账号
     */
    public static boolean checkEmail(String account) {
        boolean flag = false;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(account);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 我国公民的身份证号码特点如下
     * 1.长度18位
     * 2.第1-17号只能为数字
     * 3.第18位只能是数字或者x
     * 4.第7-14位表示特有人的年月日信息
     * 请实现身份证号码合法性判断的函数，函数返回值：
     * 1.如果身份证合法返回true
     * 2.如果身份证长度不合法返回false
     */
    public static boolean validatorIdentNo(String s) {
        String str = "[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}[0-9x]{1}";
        Pattern pattern = Pattern.compile(str);
        return pattern.matcher(s).matches() ? true : false;
    }


    /**
     * 手机号码中间四位脱敏
     */
    public static String mobileEncrypt(String mobile) {
        if (StringUtils.isEmpty(mobile) || (mobile.length() != 11)) {
            return mobile;
        }
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 身份证前三后四脱敏
     */
    public static String idEncrypt(String id) {
        if (StringUtils.isEmpty(id) || (id.length() < 8)) {
            return id;
        }
        //return id.replaceAll("(?<=\\w{3})\\w(?=\\w{4})", "*");       
        return id.substring(0, id.length() - 4) + "****";
    }


    /**
     * 个人邮箱第一位和@之前的字符脱敏
     */
    public static String emailEncrpt(String email) {
        if (StringUtils.isEmpty(email) || (email.length() < 8)) {
            return email;
        }
        int location = email.indexOf("@");
        String arr1 = email.substring(1, location);
        arr1 = arr1.replaceAll("\\w", "*");
        String arr2 = email.substring(location, email.length());
        return email.substring(0, 1) + arr1 + arr2;
    }


    public static void main(String[] args) {
        String mobile = "15868482262";
        String str = mobileEncrypt(mobile);
        String idcard = "3408230859";
        String str1 = idEncrypt(idcard);
        String email = "shidw_hz@163.com";
        System.out.println(str);
        System.out.println(str1);
        String str3 = emailEncrpt(email);
        System.out.println(str3);
    }


}
