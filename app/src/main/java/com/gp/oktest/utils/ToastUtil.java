package com.gp.oktest.utils;

import android.widget.Toast;

import com.gp.oktest.App;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Toast管理
 */
public class ToastUtil {

    public static Toast toast;

    public static void showToastShort(String toastText) {
        if (toast == null) {
            toast = Toast.makeText(App.Companion.getGlobalContext(), "", Toast.LENGTH_SHORT);
        }
        toast.setText(toastText);
        toast.show();
    }

    public static void showToastLong(String toastText) {
        if (toast == null) {
            toast = Toast.makeText(App.Companion.getGlobalContext(), "", Toast.LENGTH_LONG);
        }
        toast.setText(toastText);
        toast.show();
    }

    public static void showToastShort(int res) {
        showToastShort(App.Companion.getGlobalContext().getString(res));
    }

    public static void showToastLong(int res) {
        showToastLong(App.Companion.getGlobalContext().getString(res));
    }


    private static void execToast(final Toast toast, final int timerNumber) {
        if (timerNumber <= 0) {
            toast.cancel();
        } else {
            ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
            executorService.schedule(new Runnable() {
                @Override
                public void run() {
                    execToast(toast, timerNumber - 1);
                }
            }, 1, TimeUnit.SECONDS);
        }
    }

}