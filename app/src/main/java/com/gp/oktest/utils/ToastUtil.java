package com.gp.oktest.utils;

import android.widget.Toast;

import com.gp.oktest.App;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Toast管理
 */
public class ToastUtil {

    public static Toast toast;

    public static void showToastShort(String toastText) {
        if (toast == null) {
            toast = Toast.makeText(App.globalContext, "", Toast.LENGTH_SHORT);
        }
        toast.setText(toastText);
        toast.show();
    }

    public static void showToastLong(String toastText) {
        if (toast == null) {
            toast = Toast.makeText(App.globalContext, "", Toast.LENGTH_LONG);
        }
        toast.setText(toastText);
        toast.show();
    }

    public static void showToastShort(int res) {
        showToastShort(App.globalContext.getString(res));
    }

    public static void showToastLong(int res) {
        showToastLong(App.globalContext.getString(res));
    }


    private static void execToast(final Toast toast, final int timerNumber) {
        if (timerNumber <= 0) {
            toast.cancel();
        } else {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                @Override
                public void run() {
                    execToast(toast, timerNumber - 1);
                }
            }, 1000);
        }
    }

}