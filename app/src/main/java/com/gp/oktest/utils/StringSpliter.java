package com.gp.oktest.utils;

import java.io.UnsupportedEncodingException;

public class StringSpliter {

    static void fun1(String str, int length)
            throws UnsupportedEncodingException {

        // System.out.println(str.getBytes().length);
        byte[] bt = str.getBytes("gbk");
        for (int i = 0; i < bt.length; i++) {
            // 打印出字节数组中的值，可以看出只要是汉字[采用GBK编码时占两个字节/UTF-8占3个字节]，为两个负整数
            // 由于字节的第一位都是1,所以它就是由负数来表示的
            System.out.println(bt[i]);
        }

        // 打印出按实际出入的字节数的所截字符串
        // System.out.println(new String(bt,0,length));

        // 判断最后一个是否为负，如果是负的则丢掉该字节
        if (bt[length] < 0) {
            System.out.println(new String(bt, 0, --length, "gbk"));
        } else {
            System.out.println(new String(bt, 0, length, "gbk"));
        }
    }

    public static void main(String[] args) {
        // StringSpliter.fun1("我ABC", 4);
        try {
            StringSpliter.fun1("我ABC汉DEF", 6);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
