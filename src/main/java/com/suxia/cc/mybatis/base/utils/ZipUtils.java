package com.suxia.cc.mybatis.base.utils;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 文件压缩工具类
 * @date 2020/4/22 10:35
 */
public class ZipUtils {

    private static final Logger log = LoggerFactory.getLogger(ZipUtils.class);

    /**
     * 压缩不加密
     *
     * @param sourceFileNameList
     * @param destZipFileName
     * @return
     */
    public static File zip(List<String> sourceFileNameList, String destZipFileName) {
        ArrayList<File> sourceFileList = new ArrayList<File>(sourceFileNameList.size());
        for (String sourceFileName : sourceFileNameList) {
            File sourceFile = new File(sourceFileName);
            sourceFileList.add(sourceFile);
        }
        return zip(sourceFileList, destZipFileName);
    }

    /**
     * 压缩不加密
     *
     * @param sourceFileList
     * @param destZipFileName
     * @return
     */
    public static File zip(ArrayList<File> sourceFileList, String destZipFileName) {
        try {
            ZipFile zipFile = new ZipFile(destZipFileName);
            ZipParameters parameters = new ZipParameters();
            // 设置压缩模式
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            for (File sourceFile : sourceFileList) {
                if (sourceFile.isDirectory()) {
                    zipFile.addFolder(sourceFile, parameters);
                } else {
                    zipFile.addFile(sourceFile, parameters);
                }
            }
            return new File(destZipFileName);
        } catch (ZipException e) {
            log.error("zip error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 压缩加密
     *
     * @param sourceFileNameList
     * @param destZipFileName
     * @param password
     * @return
     */
    public static boolean zip(List<String> sourceFileNameList, String destZipFileName, String password) {
        ArrayList<File> sourceFileList = new ArrayList<File>(sourceFileNameList.size());
        for (String sourceFileName : sourceFileNameList) {
            File sourceFile = new File(sourceFileName);
            sourceFileList.add(sourceFile);
        }
        return zip(sourceFileList, destZipFileName, password);
    }

    /**
     * 压缩加密
     *
     * @param sourceFileList
     * @param destZipFileName
     * @param password
     * @return
     */
    public static boolean zip(ArrayList<File> sourceFileList, String destZipFileName, String password) {
        try {
            ZipFile zipFile = new ZipFile(destZipFileName);
            ZipParameters parameters = new ZipParameters();
            // 设置压缩模式
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            // 设置加密标志
            parameters.setEncryptFiles(true);
            // 设置aes加密
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            // 设置密码
            parameters.setPassword(password);
            for (File sourceFile : sourceFileList) {
                if (sourceFile.isDirectory()) {
                    zipFile.addFolder(sourceFile, parameters);
                } else {
                    zipFile.addFile(sourceFile, parameters);
                }
            }
            return true;
        } catch (ZipException e) {
            log.error("zip error:{}", e.getMessage());
        }
        return false;
    }

    /**
     * 解压缩到当前文件夹
     *
     * @param zipFileName
     * @param password
     * @return
     */
    public static List<File> unzip(String zipFileName, String password) {
        ZipFile zipFile = null;
        try {
            zipFile = new ZipFile(zipFileName);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            File file = new File(zipFileName);
            String destDir = file.getParent();
            return unzip(zipFileName, destDir, password);
        } catch (ZipException e) {
            log.error("unzip error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 解压缩
     *
     * @param zipFileName
     * @param destDir
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    public static List<File> unzip(String zipFileName, String destDir, String password) {
        List<File> fileList = new ArrayList<File>();
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            File destFile = new File(destDir);
            if (!destFile.exists()) {
                destFile.mkdirs();
            }
            zipFile.extractAll(destDir);
            List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
            for (FileHeader fileHeader : fileHeaderList) {
                fileList.add(new File(destDir, fileHeader.getFileName()));
            }
        } catch (ZipException e) {
            log.error("unzip error:{}", e.getMessage());
        }
        return fileList;
    }

    /**
     * 单文件解压解密
     *
     * @param zipFileName
     * @param password
     * @return
     */
    @SuppressWarnings("unchecked")
    public static InputStream singleUnzipToStream(String zipFileName, String password) {
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
            if (fileHeaderList.size() == 1) {
                return zipFile.getInputStream(fileHeaderList.get(0));
            }
        } catch (ZipException e) {
            log.error("unzip to inputstream error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 单文件解压解密
     *
     * @param zipFileName      压缩包文件
     * @param originalFileName 原始文件
     * @param password
     * @return
     */
    public static InputStream singleUnzipToStream(String zipFileName, String originalFileName, String password) {
        try {
            ZipFile zipFile = new ZipFile(zipFileName);
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
            }
            FileHeader fileHeader = zipFile.getFileHeader(originalFileName);
            return zipFile.getInputStream(fileHeader);
        } catch (ZipException e) {
            log.error("unzip to inputstream error:{}", e.getMessage());
        }
        return null;
    }

    /**
     * 单文件解压解密
     *
     * @param zipFileName      压缩包文件
     * @param originalFileName 原始文件
     * @param destFileName     输出的目标文件
     * @param password         密码
     * @return
     */
    public static File singleUnzipOutput(String zipFileName, String originalFileName, String destFileName,
                                         String password) {
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        File destFile = new File(destFileName);
        try {
            inputStream = singleUnzipToStream(zipFileName, originalFileName, password);
            fileOutputStream = new FileOutputStream(new File(destFileName));
            IOUtils.copy(inputStream, fileOutputStream);
            return destFile;
        } catch (FileNotFoundException e) {
            log.error("unzip output error:{}", e.getMessage());
        } catch (IOException e) {
            log.error("unzip output inputstream error:{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(inputStream);
        }
        return null;
    }
}
