package com.gp.oktest.javatest;

import java.text.DecimalFormat;

public class randomTest {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.0%");
        double rightRate = Math.random() * 0.4 + 0.3;
        System.out.println(df.format(rightRate));
        System.out.println("======");


        //异或加密
        String a = "Password";        //要加密的密码
        String b = "encryption";     //密钥
        char[] c = new char[8];
        //加密代码
        for(int i=0; i < a.length(); i++) {
            c[i] = (char)(a.charAt(i) ^ b.charAt(i));
            System.out.println(c[i]);
        }
        System.out.println("========");


        /*解密代码*/
        for(int i=0; i < a.length() ;i++)
            c[i] = (char)(c[i] ^ b.charAt(i));
        System.out.println(c);
    }
}
