package com.gp.oktest;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import static com.gp.oktest.GlobalApplication.globalContext;

public class Utils {

    public static String getVersionName() {
        try {
            PackageInfo pi = globalContext.getPackageManager().getPackageInfo(globalContext.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }
}
