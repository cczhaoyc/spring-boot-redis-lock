package com.suxia.cc.mybatis.base.utils;

import com.suxia.cc.mybatis.base.constant.SymbolConstants;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 路径工具类
 * @date 2020/4/22 10:35
 */
public class PathUtil {

    private static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 组装存储路径
     *
     * @param filePrefix 路径前缀
     * @param fileType   文件后缀名
     */
    public static String makeUploadPath(String filePrefix, String fileType) {
        String formatDate = PathUtil.getPathUseDate();
        if (!fileType.startsWith(SymbolConstants.DOT)) {
            fileType = SymbolConstants.DOT + fileType;
        }
        String fileName = UUID.randomUUID().toString() + fileType;
        return concat(filePrefix, formatDate, fileName);
    }

    /**
     * 组装存储路径
     *
     * @param filePrefix 路径前缀
     * @param fileType   文件后缀名
     * @return
     */
    public static String makeUploadPath(String filePrefix, String fileName, String fileType) {
        if (!fileType.startsWith(SymbolConstants.DOT)) {
            fileType = SymbolConstants.DOT + fileType;
        }
        fileName = fileName + fileType;
        return concat(filePrefix, fileName);
    }

    /**
     * 获取文件名
     *
     * @param evidencePath
     * @return
     */
    public static String getFileName(String evidencePath) {
        if (StringUtils.isEmpty(evidencePath)) {
            return null;
        }
        int pos = evidencePath.lastIndexOf(SymbolConstants.SPT);
        if (-1 == pos) {
            return evidencePath;
        }
        return evidencePath.substring(pos + 1);
    }

    /**
     * 返回 yyyy-MM-dd 格式的路径
     */
    private static String getPathUseDate() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_PATTERN);
        String formatDate = sdf.format(new Date());
        return formatDate.replace(SymbolConstants.LINET_HROUGH, SymbolConstants.SPT);
    }

    public static String concat(String... paths) {
        StringBuilder sb = new StringBuilder(128);
        for (String path : paths) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(path)) {
                if (!path.startsWith("/") && !path.contains(":")) {
                    sb.append("/");
                }
                sb.append(path);
                char last = sb.charAt(sb.length() - 1);
                if ("/".equals(String.valueOf(last))) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 路径约定，所有文件夹路径末尾都要加"/",那么在路径相加的时候，就不会出现没有"/"而字符串直接相加的情况
     * 路径相加都调用此方法，消除"//"即可
     */
    public static String concatDeleteRepeat(String... paths) {
        StringBuilder sb = new StringBuilder(128);
        for (String path : paths) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(path)) {
                if (path.lastIndexOf("/") + 1 != path.length())
                    sb.append(path).append("/");
                else
                    sb.append(path);
            }
        }
        String result = sb.toString();
        result = result.replace("//", "/");
        result = result.substring(0, result.length() - 1);
        return result;
    }


    public static String createUploadPath(String roleSessionName, String bizKey, String fileName) {
        String prefix = "attach";
        if (StringUtils.isBlank(bizKey)) {
            bizKey = "null_bizKey";
        }

        Calendar now = Calendar.getInstance();
        String year = String.valueOf(now.get(Calendar.YEAR));
        String month = String.valueOf(now.get(Calendar.MONTH) + 1);
        String day = String.valueOf(now.get(Calendar.DAY_OF_MONTH));
        String hour = String.valueOf(now.get(Calendar.HOUR_OF_DAY));
        String fileKey = concat(prefix, roleSessionName, bizKey, year, month, day, hour, fileName);

        if (fileKey.startsWith("/")) {
            fileKey = fileKey.substring(1);
        }
        return fileKey;
    }
}
