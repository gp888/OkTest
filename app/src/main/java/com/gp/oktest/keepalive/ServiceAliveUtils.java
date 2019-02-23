package com.gp.oktest.keepalive;

import android.app.ActivityManager;
import android.content.Context;

public class ServiceAliveUtils {

    /**
     * Service是否存活
     *
     * @param context
     * @param serviceName Service全名
     */
    public static boolean isServiceAlice(Context context, String serviceName) {
        boolean isServiceRunning = false;
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return true;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceName.equals(service.service.getClassName())) {
                isServiceRunning = true;
            }
        }
        return isServiceRunning;
    }
}
