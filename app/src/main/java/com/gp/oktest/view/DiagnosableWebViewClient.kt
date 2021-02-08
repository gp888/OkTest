package com.gp.oktest.view


import android.net.http.SslError
import android.os.Build
import android.util.Log
import android.webkit.*
import androidx.annotation.RequiresApi
import com.gp.oktest.BuildConfig
import java.lang.System.getProperties

/**
 * 诊断（错误信息）的WebViewClient,会以日志输出形式输出错误信息，便于发现网页的问题
 */
open class DiagnosableWebViewClient : WebViewClient() {

    override fun onReceivedError(view: WebView?, errorCode: Int, description: String?, failingUrl: String?) {
        super.onReceivedError(view, errorCode, description, failingUrl)
        Log.e("onReceivedError", "errorCode:" + errorCode + ",description:" + description +
                ",failingUrl:" + failingUrl + ",webview.info:" + view?.url)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
        super.onReceivedError(view, request, error)
        Log.e("onReceivedError", "request:" + request?.url + ",error:" + error?.errorCode + error?.description)
    }
    override fun onSafeBrowsingHit(view: WebView?, request: WebResourceRequest?, threatType: Int, callback: SafeBrowsingResponse?) {
        super.onSafeBrowsingHit(view, request, threatType, callback)
        Log.e("onSafeBrowsingHit", "request:" + request?.url + ",threatType:" + threatType +
                ",webview.info:" + view?.url)
    }
    override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
        super.onReceivedHttpError(view, request, errorResponse)
        Log.e("onReceivedHttpError", "request:" + request + ",errorResponse:" + errorResponse?.statusCode +
                ",webview.info:" + view?.url)
    }
    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
        super.onReceivedSslError(view, handler, error)
        Log.e("onReceivedSslError", "error:" + error.toString() + ",webview.info:" + view?.url)
    }


    //这个版本是相对最好的实现，规避了非Debug环境下的字符串拼接和具体求值的操作
    //smartMessage 建议只在 Kotlin 中调用，否则会生成实例，因为无法inline处理
    inline fun smartMessage(lazyMessage: () -> Any?) {
        if (BuildConfig.DEBUG) {
            Log.d("smartMessage", lazyMessage().toString())
        }
    }
    private fun testSmartMessage() {
        smartMessage {
            "getProperties " + getProperties()
        }
    }
}