package com.gp.oktest.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.gp.oktest.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

public class AppUtils {

    /**
     * 判断app是否处于前台
     * @param context
     * @return
     */
    public static boolean isAppForeground(Context context){
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList==null){
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName()) && processInfo.importance==ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND){
                return true;
            }
        }
        return false;
    }

    /**
     * 判断app是否处于前台
     * @param context
     * @return
     */
    public static boolean isRunningForeground (Context context) {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if(!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true ;
        }
        return false ;
    }

    public static String getVersionName() {
        try {
            PackageInfo pi = App.Companion.getGlobalContext().getPackageManager().getPackageInfo(App.Companion.getGlobalContext().getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    //权限
    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    //权限
    private static boolean checkPermission(Context context, String permName, String pkgName){
        PackageManager pm = context.getPackageManager();
        if(PackageManager.PERMISSION_GRANTED == pm.checkPermission(permName, pkgName)){

            return true;
        }else{
            //PackageManager.PERMISSION_DENIED == pm.checkPermission(permName, pkgName)

            return false;
        }
    }

    //原签名信息
    private static final String SIGNATURE = "478yYkKAQF+KST8y4ATKvHkYibo=";
    private static final int VALID = 0;
    private static final int INVALID = 1;

    public static int checkAppSignature(Context context) {
       try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);

         for (Signature signature : packageInfo.signatures) {
            byte[] signatureBytes = signature.toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());

            final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

            Log.d("REMOVE_ME", "Include this string as a value for SIGNATURE:" + currentSignature);

            //compare signatures
            if (SIGNATURE.equals(currentSignature)){
                return VALID;
            };
         }
       } catch (Exception e) {
             //assumes an issue in checking signature., but we let the caller decide on what to do.
       }
       return INVALID;
    }


    //校验签名
    // 获取当前上下文
    Context context = App.Companion.getGlobalContext();
    // 发布apk时用来签名的keystore中查看到的sha1值，改成自己的
    String cert_sha1 = "937FF2936CDB81EEF4A776290EA9E076B3BC03A9";
    // 调用isOrgApp()获取比较结果
    boolean is_org_app = isOrgApp(context,cert_sha1);
    // 如果比较初始从证书里查看到的sha1，与代码获取到的当前证书中的sha1不一致，那么就自我销毁
//    if(!is_org_app){
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }

    // 此函数用于返回比较结果
    public static boolean isOrgApp(Context context,String cert_sha1){
        String current_sha1 = getAppSha1(context);
        // 返回的字符串带冒号形式，用replace去掉
        current_sha1 = current_sha1.replace(":","");
        return current_sha1.equals(cert_sha1);
    }
    // 此函数用于获取当前APP证书中的sha1值
    public static String getAppSha1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i]).toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
                hexString.append(":");
            }
            String result = hexString.toString();
            return result.substring(0, result.length()-1);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void requestDrawOverLays(Context context) {
        if (!Settings.canDrawOverlays(context)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + context.getPackageName()));
            ((Activity)context).startActivityForResult(intent, 0);
        }
    }

}
