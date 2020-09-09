package com.gp.oktest.javatest;

import java.text.DecimalFormat;

public class randomTest {
    public static void main(String[] args) {
        DecimalFormat df = new DecimalFormat("0.0%");
        double rightRate = Math.random() * 0.4 + 0.3;
        System.out.println(df.format(rightRate));

        System.out.println();
    }
}
