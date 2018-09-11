package com.gp.oktest;

import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by guoping on 2017/12/19.
 */

public class FileUtils {

    /**
     *
     * @param path 文件路径
     * @return 文件大小
     */
    public static long getFileSize(String path) {
        File file = new File(path);
        return file.length() / 1024;
    }

    /**
     *
     * @param path 文件路径
     * @return 文件大小
     */
    public static long getSize (String path) {
        File file = new File(path);
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            return fis.available() / 1024;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void createProjectSdcardFile() {
        try {
            if (!FileUtils.isSDCardAvailable()) {
                return;
            }

            File file = new File(Constant.DIR_DOWNLOAD);
            if (!file.exists()) {
                file.mkdirs();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isSDCardAvailable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }
}
