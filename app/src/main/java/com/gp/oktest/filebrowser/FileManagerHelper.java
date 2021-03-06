package com.gp.oktest.filebrowser;


import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 文件浏览器
 * 使用MediaStore方式，直接获取到所有的有效文件，再进行文件夹组合。
 * 使用Runtime.getRuntion().exec("cmdline")直接执行命令行命令，然后解析返回结果。
 * 使用File类的方式来访问目录之下所有的文件。
 */
public class FileManagerHelper {

    /**
     * 得到特定路径下有效文件
     */
    public static List<File> getFilesByFile(File file) {

        if (file != null && file.exists()) {
            File[] files = file.listFiles(new MyFileFilter());
            List<File> filterFile = new ArrayList<>();
            if (Collections.addAll(filterFile, files)) {
                return filterFile;
            }
        }
        return null;
    }

    /**
     * 得到特定路径下有效文件
     */
    public static List<File> getFilesByPath(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        File file = new File(path);
        return getFilesByFile(file);
    }

    /**
     * 检查是否存在上一级目录
     *
     * @return
     */
    public static boolean hasParent(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return hasParent(file);
    }

    /**
     * 得到上一级目录
     * @param filePath
     * @return
     */
    public static String getParent(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return "";
        }

        File file = new File(filePath);
        return file.getParent();
    }

    /**
     * 检查是否存在上一级目录
     *
     * @return
     */
    public static boolean hasParent(File file) {
        if (file != null && file.exists()) {
            return file.getParentFile() != null;
        }

        return false;
    }

    /**
     * 得到文件名
     */
    public static String getFileName(File file) {
        if (file != null) {
            return file.getName();
        }
        return "";
    }

    /**
     * 返回文件最后修改日期
     */
    public static String getFileLastDate(File file) {
        if (file == null) {
            return "";
        }
        long date = file.lastModified();
        if (date == 0) {
            return "";
        }
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return simpleDateFormat.format(new Date(date));
    }

    public static String getFileSize(File file) {
        if (file.isFile()) {
            float size = file.length() / 1024f;
            if (size < 1024) {
                if (size < 0.01) {
                    size = 0.01f;
                }
                return String.format("%.2fKB", size);
            }
            size = size / 1024f;
            return String.format("%.2fMB", size);
        }
        return "";
    }

    /**
     * 获得文件的mimeType
     *
     * @param file
     * @return
     */
    public static String getMimeType(File file) {
        String suffix = getSuffix(file);
        if (suffix == null) {
            return "file/*";
        }
        String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(suffix);
        if (type != null || !type.isEmpty()) {
            return type;
        }
        return "file/*";
    }

    /**
     * 获得文件的后缀
     * @param file
     * @return
     */
    private static String getSuffix(File file) {
        if (file == null || !file.exists() || file.isDirectory()) {
            return null;
        }
        String fileName = file.getName();
        if (fileName.equals("") || fileName.endsWith(".")) {
            return null;
        }
        int index = fileName.lastIndexOf(".");
        if (index != -1) {
            return fileName.substring(index + 1).toLowerCase(Locale.US);
        } else {
            return null;
        }
    }
}