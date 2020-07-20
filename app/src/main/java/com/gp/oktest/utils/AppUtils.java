package com.gp.oktest.utils;

import android.Manifest;
import android.annotation.TargetApi;
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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gp.oktest.App;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

public class AppUtils {

    private static final String TAG = AppUtils.class.getSimpleName();

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

    /**
     * 校验签名
     * @param context
     * @return
     */
    public static int checkAppSignature(Context context) {
       try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(),PackageManager.GET_SIGNATURES);

         for (Signature signature : packageInfo.signatures) {
            byte[] signatureBytes = signature.toByteArray();//signatureBytes to HexString
            MessageDigest md = MessageDigest.getInstance("SHA");//SHA-1
             //使用sha-1算法获取摘要
            md.update(signature.toByteArray());

            final String currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

            Log.d("REMOVE_ME", "Include this string as a value for SIGNATURE:" + currentSignature);

            //compare signatures
            if (SIGNATURE.equals(currentSignature)){
                return VALID;
            }
         }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return INVALID;
    }


    //校验签名
    Context context = App.Companion.getGlobalContext();
    // 发布apk时用来签名的keystore中的sha1值
    String cert_sha1 = "937FF2936CDB81EEF4A776290EA9E076B3BC03A9";
    // 调用isOrgApp()获取比较结果
    boolean is_org_app = isOrgApp(context,cert_sha1);
    // 如果比较证书里的sha1，与代码获取到的当前证书中的sha1不一致，那么就自我销毁
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
            ((AppCompatActivity)context).startActivityForResult(intent, 0);
        }
    }

    public static void clearMemory(Context context){
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> infoList = am.getRunningAppProcesses();
        List<ActivityManager.RunningServiceInfo> serviceInfos = am.getRunningServices(100);

        ActivityManager.MemoryInfo memory = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memory);
        //Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
        //当前系统的可用内存
        long beforeMem = memory.availMem / (1024 * 1024);


        Log.e(TAG, "-----------before memory info : " + beforeMem);
        int count = 0;
        if (infoList != null) {
            for (int i = 0; i < infoList.size(); ++i) {
                ActivityManager.RunningAppProcessInfo appProcessInfo = infoList.get(i);
                Log.e(TAG, "process name : " + appProcessInfo.processName);
                //importance 该进程的重要程度  分为几个级别，数值越低就越重要。
                Log.e(TAG, "importance : " + appProcessInfo.importance);

                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_SERVICE的进程都长时间没用或者空进程了
                // 一般数值大于RunningAppProcessInfo.IMPORTANCE_VISIBLE的进程都是非可见进程，也就是在后台运行着
                if (appProcessInfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_VISIBLE) {
                    String[] pkgList = appProcessInfo.pkgList;
                    for (int j = 0; j < pkgList.length; ++j) {//pkgList 得到该进程下运行的包名
                        Log.e(TAG, "It will be killed, package name : " + pkgList[j]);
                        am.killBackgroundProcesses(pkgList[j]);
                        count++;
                    }
                }
            }
        }

        am.getMemoryInfo(memory);
        long afterMem = memory.availMem / (1024 * 1024);
        Log.e(TAG, "----------- after memory info : " + afterMem);
        Toast.makeText(context, "clear " + count + " process, "
                + (afterMem - beforeMem) + "M", Toast.LENGTH_LONG).show();
    }

}
