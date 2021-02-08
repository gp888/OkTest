package com.gp.oktest.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.media.ExifInterface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import com.gp.oktest.App;
import com.gp.oktest.Constant;
import com.gp.oktest.R;
import com.gp.testlibrary.FileProvider7Util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

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
            ((AppCompatActivity) mContext).startActivityForResult(intent, Constant.CHOICE_CMARE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File dir = new File(Constant.IMAGE_TEMP_PATH);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constant.IMAGE_TEMP_PATH, filename)));
            Constant.PHOTOFILEPATH = Constant.IMAGE_TEMP_PATH + filename;
            ((AppCompatActivity) mContext).startActivityForResult(intent, Constant.CHOICE_CMARE);
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

    //是否有摄像头
    public static boolean hasCameraDevice(Context ctx) {
        return ctx.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    //是否支持自动对焦
    public static boolean isAutoFocusSupported(Camera.Parameters params) {
        List<String> modes = params.getSupportedFocusModes();
        return modes.contains(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     *            （DisplayMetrics类中属性density）
     * @return
     */
    public static int dip2px(float dipValue) {
        final float scale = App.Companion.getGlobalContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    public static float dip2px1(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }


    /**
     * 屏幕适配
     * 设计图宽360dp
     * oncreate 调用
     */
    private static float sNoncompatDensity;
    private static float sNoncompatScaledDensity;
    private static void setCustomDensity(@NonNull AppCompatActivity activity, @NonNull final Application application) {
        DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sNoncompatDensity == 0) {
            sNoncompatDensity = appDisplayMetrics.density;
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNoncompatScaledDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        float targetDensity = appDisplayMetrics.widthPixels / 360;
        float targetScaledDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    public static Bitmap getAvatar(Resources res, int width){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, R.drawable.brighton, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(res, R.drawable.brighton, options);
    }


    /**
     * 设备识别
     * android 29(10)以下 deviceId mac
     * @param context
     * @return
     */
    public static String getDeviceInfo(Context context) {
        try {
            org.json.JSONObject json = new org.json.JSONObject();
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            String device_id = null;
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                device_id = tm.getDeviceId();
            }
            String mac = getMac(context);

            json.put("mac", mac);
            if (TextUtils.isEmpty(device_id)) {
                device_id = mac;
            }
            if (TextUtils.isEmpty(device_id)) {
                device_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            json.put("device_id", device_id);
            return json.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getMac(Context context) {
        String mac = "";
        if (context == null) {
            return mac;
        }
        if (Build.VERSION.SDK_INT < 23) {
            mac = getMacBySystemInterface(context);
        } else {
            mac = getMacByJavaAPI();
            if (TextUtils.isEmpty(mac)) {
                mac = getMacBySystemInterface(context);
            }
        }
        return mac;
    }

    @TargetApi(9)
    private static String getMacByJavaAPI() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface netInterface = interfaces.nextElement();
                if ("wlan0".equals(netInterface.getName()) || "eth0".equals(netInterface.getName())) {
                    byte[] addr = netInterface.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }
                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    return buf.toString().toLowerCase(Locale.getDefault());
                }
            }
        } catch (Throwable e) {
        }
        return null;
    }

    private static String getMacBySystemInterface(Context context) {
        if (context == null) {
            return "";
        }
        try {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (checkPermission(context, Manifest.permission.ACCESS_WIFI_STATE)) {
                WifiInfo info = wifi.getConnectionInfo();
                return info.getMacAddress();
            } else {
                return "";
            }
        } catch (Throwable e) {
            return "";
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                Class clazz = Class.forName("android.content.Context");
                Method method = clazz.getMethod("checkSelfPermission", String.class);
                int rest = (Integer) method.invoke(context, permission);
                if (rest == PackageManager.PERMISSION_GRANTED) {
                    result = true;
                } else {
                    result = false;
                }
            } catch (Throwable e) {
                result = false;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }


//===========================================
    public static String getDeviceIdForGeneral(Context var0) {
        String var1 = "";
        if (var0 == null) {
            return var1;
        } else {
            try {
                if (Build.VERSION.SDK_INT < 23) {
                    var1 = getIMEI(var0);
                    if (TextUtils.isEmpty(var1)) {

                        var1 = getMacBySystemInterface(var0);
                        if (TextUtils.isEmpty(var1)) {
                            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");


                            if (TextUtils.isEmpty(var1)) {
                                var1 = getSerialNo();
                            }
                        }
                    }
                } else if (Build.VERSION.SDK_INT == 23) {
                    var1 = getIMEI(var0);
                    if (TextUtils.isEmpty(var1)) {
                        var1 = getMacByJavaAPI();
                        if (TextUtils.isEmpty(var1)) {
//                            if (AnalyticsConstants.CHECK_DEVICE) {
//                                var1 = getMacShell();
//                            } else {
                                var1 = getMacBySystemInterface(var0);
//                            }
                        }

                        if (TextUtils.isEmpty(var1)) {
                            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");

                            if (TextUtils.isEmpty(var1)) {
                                var1 = getSerialNo();
                            }
                        }
                    }
                } else if (Build.VERSION.SDK_INT >= 29) {
                    var1 = getOaid(var0);
                    if (TextUtils.isEmpty(var1)) {
//                        var1 = getIdfa(var0);
                        if (TextUtils.isEmpty(var1)) {
                            var1 = getAndroidId(var0);
                            if (TextUtils.isEmpty(var1)) {
                                var1 = getSerialNo();
                                if (TextUtils.isEmpty(var1)) {
                                    var1 = getMacByJavaAPI();
                                    if (TextUtils.isEmpty(var1)) {
                                        var1 = getMacBySystemInterface(var0);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    var1 = getIMEI(var0);
                    if (TextUtils.isEmpty(var1)) {
                        var1 = getSerialNo();
                        if (TextUtils.isEmpty(var1)) {
                            var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");

                            if (TextUtils.isEmpty(var1)) {
                                var1 = getMacByJavaAPI();
                                if (TextUtils.isEmpty(var1)) {
                                    var1 = getMacBySystemInterface(var0);
                                }
                            }
                        }
                    }
                }
            } catch (Throwable var3) {
            }

            return var1;
        }
    }
    private static String getIMEI(Context var0) {
        String var1 = "";
        if (var0 == null) {
            return var1;
        } else {
            TelephonyManager var2 = (TelephonyManager)var0.getSystemService(Context.TELEPHONY_SERVICE);
            if (var2 != null) {
                try {
                    if (checkPermission(var0, "android.permission.READ_PHONE_STATE")) {
                        var1 = var2.getDeviceId();
                    }
                } catch (Throwable var4) {

                }
            }

            return var1;
        }
    }

    private static String getSerialNo() {
        String var0 = "";
        if (Build.VERSION.SDK_INT >= 9) {
            if (Build.VERSION.SDK_INT >= 26) {
                try {
                    Class var1 = Class.forName("android.os.Build");
                    Method var2 = var1.getMethod("getSerial");
                    var0 = (String)var2.invoke(var1);
                } catch (Throwable var3) {
                }
            } else {
                var0 = Build.SERIAL;
            }
        }

        return var0;
    }

    private static String getOaid(Context var0) {
        String var1 = "";

        try {
            SharedPreferences var2 = var0.getSharedPreferences("umeng_sp_oaid", 0);
            if (var2 != null) {
                var1 = var2.getString("key_umeng_sp_oaid", "");
            }
        } catch (Throwable var3) {
        }

        return var1;
    }

    public static String getAndroidId(Context var0) {
        String var1 = null;
        if (var0 != null) {
            try {
                var1 = Settings.Secure.getString(var0.getContentResolver(), "android_id");
            } catch (Exception var3) {

            }
        }

        return var1;
    }
}
