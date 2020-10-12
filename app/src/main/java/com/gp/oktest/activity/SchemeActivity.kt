package com.gp.oktest.activity

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * 1.通过小程序，利用Scheme协议打开原生app
    2.H5页面点击锚点，根据锚点具体跳转路径APP端跳转具体的页面
    3.APP端收到服务器端下发的PUSH通知栏消息，根据消息的点击跳转路径跳转相关页面
    4.APP根据URL跳转到另外一个APP指定页面
    5.通过短信息中的url打开原生app
 *
 * startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("chicha://domain/path?params")));
 * <a href="yc://ycbjie:8888/from?type=yangchong">打开叮咚app</a>
 */
class SchemeActivity : AppCompatActivity() {

    val TAG = SchemeActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContentView(R.layout.activity_scheme)
//        scheme_tvnodata.text = Html.fromHtml("<a href='chicha://domain/path?params'>CLICK THIS NODATA</a>")
//        //激活链接
//        scheme_tvnodata.movementMethod = LinkMovementMethod.getInstance()
//
//        scheme_tvdata.text = Html.fromHtml("<a href='chicha://scheme_activity?type=0&buffer=这是个字符串'>CLICK THIS DATA</a>")
//        scheme_tvdata.movementMethod = LinkMovementMethod.getInstance()


        val uri: Uri? = intent.data
        if (uri != null) {
            //获取指定参数值
            val type: String? = uri.getQueryParameter("type")
            if (type == "yangchong") {
//                ActivityUtils.startActivity(GuideActivity::class.java)
            } else if (type == "main") {
//                ActivityUtils.startActivity(MainActivity::class.java)
            }
        }
    }


    /**
     * 为了避免多次实例化这里我们使用onNewIntent
     */
    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val uri = intent?.data
        if (uri != null) {
            // 完整的url信息
            val url = uri.toString()
            Log.e(TAG, "url: $uri")

            // scheme部分
            val scheme = uri.scheme
            Log.e(TAG, "scheme: $scheme")

            // host部分
            val host = uri.host
            Log.e(TAG, "host: $host")

            //port部分
            val port = uri.port
            Log.e(TAG, "port: $port")

            // 访问路劲
            val path = uri.path
            Log.e(TAG, "path: $path")

            // Query部分
            val query = uri.query
            Log.e(TAG, "query: $query")

            //获取指定参数值
            val type = uri.getQueryParameter("type")
            Log.e(TAG, "type: $type")
            val buffer = uri.getQueryParameter("buffer")
            Log.e(TAG, "buffer: $buffer")
        }
    }


    fun isValidate(){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("yc://ycbjie:8888/from?type=yangchong"))
        val activities: List<ResolveInfo> = packageManager.queryIntentActivities(intent, 0)
        if (!activities.isEmpty()) {
            startActivity(intent)
        }
    }
}