package com.gp.oktest.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Environment;

import com.gp.oktest.Constant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;


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

    public static String getFilesPath( Context context ){
        String filePath ;
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            filePath = context.getExternalFilesDir(null).getPath();
        }else {
            filePath = context.getFilesDir().getPath() ;
        }
        return filePath ;
    }

    /**
     * 获取应用签名
     * @param context
     * @return
     */
    private byte[] getCertificateSHA1Fingerprint(Context context) {
        PackageManager pm = context.getPackageManager();
        String packageName = context.getPackageName();

        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            Signature[] signatures = packageInfo.signatures;
            byte[] cert = signatures[0].toByteArray();
            X509Certificate x509 = X509Certificate.getInstance(cert);
            MessageDigest md = MessageDigest.getInstance("SHA1");
            return md.digest(x509.getEncoded());
        } catch (PackageManager.NameNotFoundException | CertificateException |
                NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
