package com.gp.oktest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.gp.testlibrary.FileProvider7Util;

import java.io.File;
import java.io.IOException;

/**
 * Created by guoping on 2017/12/19.
 */

public class DeviceUtils {

    public static void openCamera(Context mContext) {
        String filename = "okTestCamera_" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(Constant.IMAGE_TEMP_PATH, filename);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            Uri photoUri = FileProvider7Util.getUriForFile24(mContext, file);
            //photoUri：content://com.gp.oktest.fileprovider/okPhotos/okTest/temp/filename
            Intent intent = new Intent();
            //表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            //设置Action为拍照
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
            //将拍取的照片保存到指定URI
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            Constant.PHOTOFILEPATH = Constant.IMAGE_TEMP_PATH + filename;
            ((Activity) mContext).startActivityForResult(intent, Constant.CHOICE_CMARE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File dir = new File(Constant.IMAGE_TEMP_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constant.IMAGE_TEMP_PATH, filename)));
            Constant.PHOTOFILEPATH = Constant.IMAGE_TEMP_PATH + filename;
            ((Activity) mContext).startActivityForResult(intent, Constant.CHOICE_CMARE);
        }
    }


    /**
     * 读取图片属性：旋转的角度
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return degree;
        }
        return degree;
    }

    /**
     * 旋转图片，使图片保持正确的方向。
     * @param srcPath 原始图片路径
     * @param degrees 原始图片的角度
     * @return Bitmap 旋转后的图片
     */
    public static Bitmap rotateBitmap(String srcPath, int degrees) {
        Bitmap bitmap = BitmapFactory.decodeFile(srcPath);
        if (degrees == 0 || null == bitmap) {
            return bitmap;
        }
        Matrix matrix = new Matrix();
        matrix.setRotate(degrees, bitmap.getWidth() / 2, bitmap.getHeight() / 2);
        Bitmap bmp = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        if (null != bitmap) {
            bitmap.recycle();
        }
        return bmp;
    }

    public static void testAndfix() {
        String str = "strr";
        int len = str.length();
        ToastUtil.showToastShort(len + "");
    }
}
