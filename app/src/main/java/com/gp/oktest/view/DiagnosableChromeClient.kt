package com.gp.oktest.view

import android.util.Log
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
open class DiagnosableChromeClient: WebChromeClient() {
    override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
//不需要调用super方法
        Log.d("onConsoleMessage", "message:" + message + ",lineNumber:" + lineNumber + ",sourceID:" + sourceID)
    }
    override fun onConsoleMessage(consoleMessage: ConsoleMessage?): Boolean {
        Log.d("onConsoleMessage", "message:" + consoleMessage?.message())
//返回true，不再需要webview内部处理
        return true
    }
}
