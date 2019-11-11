package com.gp.oktest.utils

import android.text.InputFilter
import android.text.LoginFilter
import android.text.Spanned
import android.widget.EditText
import java.util.regex.Pattern

/**
 * 单字节字符最长16位，双字节字符最长8位
 */
class NameFilter(var maxLen: Int = 16) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence {
        var dindex = 0
        var count = 0 // 判断是否到达最大长度

        while (count <= maxLen && dindex < dest.length) {
            val c = dest[dindex++]
            if (c.toInt() < 128) {// 按ASCII码表0-127算
                count = count + 1
            } else {
                count = count + 2
            }
        }

        if (count > maxLen) {
            return dest.subSequence(0, dindex - 1)
        }

        var sindex = 0
        while (count <= maxLen && sindex < source.length) {
            val c = source[sindex++]
            if (c.toInt() < 128) {
                count = count + 1
            } else {
                count = count + 2
            }
        }

        if (count > maxLen) {
            sindex--
        }
        return source.subSequence(0, sindex)
    }
}

//UsernameFilterGeneric、UsernameFilterGMail
class MyLoginFilter : LoginFilter.UsernameFilterGMail() {

    override fun isAllowed(c: Char): Boolean {
        return c in '0'..'9' || c in 'a'..'z' || c in 'A'..'Z'
    }
}

fun setP(et: EditText) {
    et.filters = arrayOf(InputFilter.LengthFilter(16))
}

class PwdFilter : InputFilter{

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence {
        val regexStr = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
        val pattern = Pattern.compile(regexStr)
        val matcher = pattern.matcher(source.toString())
        if (matcher.matches()) {
            return ""
        } else {
            return source.toString()
        }
    }
}