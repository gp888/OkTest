package com.example.testdaynight

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import kotlinx.android.synthetic.main.activity_main.*

/**
 * API23后，Android就有自带的api能够实现夜间模式与白天模式的切换，
 * 用到的就是AppCompatDelegate.setDefaultNightMode
 *
 * MODE_NIGHT_FOLLOW_SYSTEM 模式：设置为跟随系统，通常为 MODE_NIGHT_NO 即日间模式
    MODE_NIGHT_AUTO模式：自动模式，当我们的APP有网络及定位权限时。系统会根据当地的时间判断当前时处于白天还是黑夜，从而自动加载不同的模式

    //启动就需要显示夜间模式
    class BaseApplication : Application() {
        static {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        override fun onCreate() {
            super.onCreate()
        }

    1.切换只作用于新生成的组件，对原先处于任务栈中的Activity不起作用。
    （解决方法：发送广播，让它重启）如果直接在Activity的onCreate()中调用切换代码，可以不需要调用recreate()。
    对于一些数据的保存与切换后的显示我们可以用savedInstanceState来保存与复原
    2.在切换后可以不调用recreate()，而是自己添加一个重启该Activity的方法，然后加个过度动画
 */
class MainActivity : AppCompatActivity() {

    val DAY_NIGHT_STATE = "day_night_state"

    var isNight = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 检查是否正在重新创建一个以前销毁的实例
//        if (savedInstanceState != null) {
//            // 从已保存状态恢复成员的值
//            isNight = savedInstanceState.getBoolean(DAY_NIGHT_STATE);
//        } else {
//            // 可能初始化一个新实例的默认值的成员
//        }
    }

    fun onClick(view: View) {
        val intent = Intent(this, SingleTaskActivity::class.java)
        startActivity(intent)
        return;

        //MODE_NIGHT_FOLLOW_SYSTEM 与 MODE_NIGHT_AUTO
        if(isNight) {
            //夜间 切换 日间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else{
            //日间 切换 夜间
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
//        getWindow().setWindowAnimations(R.style.OutInAnimation);
        recreate()
        isNight = !isNight


    }

    override fun onSaveInstanceState(outState: Bundle) {
        Log.d("gptag", "onSaveInstanceState")
        outState.putBoolean(DAY_NIGHT_STATE, isNight);
        super.onSaveInstanceState(outState)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        Log.d("gptag", "onRestoreInstanceState")
        super.onRestoreInstanceState(savedInstanceState)

        // 从已保存的实例中恢复状态成员
        isNight = savedInstanceState.getBoolean(DAY_NIGHT_STATE);
    }
}
