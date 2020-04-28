package com.suxia.cc.mybatis.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author cczhaoyc@163.com
 * @version v_1.0.0
 * @description 文件工具
 * @date 2020/4/22 10:35
 */
public class FileUtils extends org.apache.commons.io.FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 删除文件
     */
    public static boolean deleteFile(File f) {
        if (f.isFile()) {
            f.delete();
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        return deleteFile(new File(filePath));
    }

    /**
     * 删除文件夹
     *
     * @param dir
     */
    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] files = dir.listFiles();
            if (null != files && files.length > 0) {
                for (File file : files) {
                    if (null != file && file.isDirectory()) {
                        deleteDir(file);
                    }
                }
            }
        }
        dir.delete();
        return true;
    }

    /**
     * 删除文件夹
     *
     * @param dir
     */
    public static boolean deleteDir(String dir) {
        return deleteDir(new File(dir));
    }

    /**
     * 文件重命名
     *
     * @param oldFile
     * @param newFileName
     */
    public static boolean renameFile(File oldFile, String newFileName) {
        if (oldFile.isFile()) {
            oldFile.renameTo(new File(oldFile.getAbsolutePath().substring(0,
                    oldFile.getAbsolutePath().lastIndexOf(File.separator) + 1)
                    + newFileName));
        }
        return true;
    }

    /**
     * 文件重命名
     *
     * @param oldFile
     * @param newFileName
     */
    public static boolean renameFile(String oldFile, String newFileName) {
        return renameFile(new File(oldFile), newFileName);
    }

    /**
     * 将源文件复制一份到目标文件
     *
     * @param srcFile    源文件全路径
     * @param targetFile 目标文件全路径
     * @throws IOException
     */
    public static void copyFile(String srcFile, String targetFile) throws IOException {
        copyFile(new File(srcFile), new File(targetFile));
    }

    /**
     * 生成文件目录
     *
     * @param outFilePath
     */
    public static void mkdirs(String outFilePath) {
        File file = new File(outFilePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    /**
     * 文件名加后缀
     *
     * @param fileName
     * @param suffix
     * @return
     */
    public static String fileNameAddSuffix(String fileName, String suffix) {
        int p = fileName.lastIndexOf(".");
        String fileName1 = fileName.substring(0, p);
        String extName = fileName.substring(p);
        return fileName1 + suffix + extName;
    }

    /**
     * 将内容写入文件，如果文件不存在，则创建一个新文件
     *
     * @param file 写入的文件物理路径
     * @param data 写入的文件内容
     * @throws IOException
     */
    public static void writeStringToFile(String file, String data, String webencoding) throws IOException {
        writeStringToFile(new File(file), data, webencoding);
    }

    /**
     * 读文件
     *
     * @param file
     */
    public static String readFile(String file) {
        FileInputStream fis = null;

        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }

        StringBuffer str = null;
        if (fis != null) {
            FileChannel fc = fis.getChannel();
            ByteBuffer readBuffer = ByteBuffer.allocate(4096);
            try {
                str = new StringBuffer((int) fc.size());
                while (fc.read(readBuffer) != -1) {
                    readBuffer.flip();
                    str.append(StandardCharsets.UTF_8.decode(readBuffer));
                    readBuffer.clear();
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage(), ex);
            } finally {
                try {
                    fis.close();
                    fc.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }

            }
        }
        return str == null ? null : str.toString();
    }

    /**
     * 写文件
     *
     * @param inputStream
     * @param target
     * @throws IOException
     */
    public static void writeFile(InputStream inputStream, String target) throws IOException {
        File fileTarget = new File(target);

        File dir = fileTarget.getParentFile();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileTarget);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        if (outputStream == null) {
            logger.error("Write file " + target + "fail!");
            return;
        }
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        while ((bytesRead = inputStream.read(buffer, 0, 8192)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
    }

    /**
     * 判断文件夹是否为空
     *
     * @param path
     */
    public static boolean isEmptyDir(String path) {
        File d = new File(path);
        File[] list = d.listFiles();
        if (list != null && list.length > 0) {
            return false;
        } else {
            return true;
        }
    }
}

