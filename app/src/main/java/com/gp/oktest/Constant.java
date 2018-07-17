package com.gp.oktest;

import android.os.Environment;

import java.io.File;

/**
 * Created by guoping on 2017/12/19.
 */

public class Constant {
    public static File mExternalStorage = Environment.getExternalStorageDirectory();
    public static final String DIR_IMAGE_TEMP = "/okTest/temp/"; //临时目录
    public static final String IMAGE_TEMP_PATH = mExternalStorage + Constant.DIR_IMAGE_TEMP;  //图片保存路径

    public static final String FILE_PROVIDER_AUTHORITY = "com.gp.oktest.fileprovider";

    //相机路径
    public static String PHOTOFILEPATH = "";




    public static final int CHOICE_CMARE = 20101;
}
