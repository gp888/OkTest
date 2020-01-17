package com.gp.oktest.javatest;


/**
 * 静态内部类和非静态内部类一样，都不会因为外部类的加载而加载，
 * 同时静态内部类的加载不需要依附外部类，在使用时才加载，不过在加载静态内部类的过程中也会加载外部类
 */
public class Outer {

    public static long OUTER_VARIABLE_TIME = System.currentTimeMillis();

    static {
        System.out.println("外部类静态块加载时间：" + System.currentTimeMillis());
    }

    public Outer() {
        System.out.println("外部类构造函数时间：" + System.currentTimeMillis());
    }

    static class StaticInnerClass {

        public static long INNER_STATIC_DATE = System.currentTimeMillis();
        static{
            System.out.println("静态内部类静态块加载时间：" + System.currentTimeMillis());
        }
    }

    class InnerClass {
        public long INNER_DATE = 0;
        public InnerClass() {
            INNER_DATE = System.currentTimeMillis();
        }
    }


    public static void main(String[] args) {

//        Outer outer = new Outer();
//        System.out.println("外部类静态变量加载时间：" + Outer.OUTER_VARIABLE_TIME);

//        System.out.println("非静态内部类加载时间"+outer.new InnerClass().INNER_DATE);



        System.out.println("静态内部类加载时间："+StaticInnerClass.INNER_STATIC_DATE);
    }
}