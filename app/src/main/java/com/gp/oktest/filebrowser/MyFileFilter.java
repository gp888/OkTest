package com.gp.oktest.filebrowser;

import java.io.File;
import java.io.FileFilter;

/**
 * 如果仅对文件名有过滤要求，可以使用FIlenameFilter来过滤
 *
 * 扫描目录下所有的APK文件
 */
public class MyFileFilter implements FileFilter {
    @Override
    public boolean accept(File pathname) {
        if (pathname.exists()) {

            if (pathname.isDirectory() && pathname.canRead() && pathname.canWrite()) {
                // 文件夹只要可读可写 就返回
                return pathname.listFiles().length > 0;
            }

            if (pathname.isFile() && pathname.canRead() && pathname.canWrite()) {
                // 文件还需要满足固定后缀
                if (pathname.getName().toLowerCase().endsWith(".apk")) {
                    return true;
                }
            }
        }
        return false;
    }
}