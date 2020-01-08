package com.gp.oktest.javatest;

/**
 * 只能取到当前所在线程存的数据，如果所在线程没存数据，取出来的就是null
 *
 * 也可以通过HashMap<Thread, Object>来实现，考虑线程安全的话使用ConcurrentMap<Thread, Object>，
 * 不过使用Map会有一些麻烦的事要处理，比如当一个线程结束的时候我们如何删除这个线程的对象副本呢？
 * 如果使用ThreadLocal就不用有这个担心了，ThreadLocal保证每个线程都保持对其线程局部变量副本的隐式引用，
 * 只要线程是活动的并且 ThreadLocal 实例是可访问的；在线程消失之后，其线程局部实例的所有副本都会被垃圾回收（除非存在对这些副本的其他引用）
 *
 *
 * ThreadLocal类中维护一个Map，用于存储每一个线程的变量副本，Map中元素的键为线程对象，而值为对应线程的变量副本。
 */
public class ThreadLocalTest {

    private static final String TAG = "ThreadLocalTest";

    public static void main(String[] args) {

        ThreadLocal<Integer> mThreadLocal = new ThreadLocal<>();
        mThreadLocal.set(0);
        System.out.println("main  mThreadLocal=" + mThreadLocal.get());

        new Thread("Thread1") {
            @Override
            public void run() {
                mThreadLocal.set(1);
                System.out.println("Thread1  mThreadLocal=" + mThreadLocal.get());
            }
        }.start();

        new Thread("Thread2") {
            @Override
            public void run() {
//                mThreadLocal.set(2);
                System.out.println("Thread1  mThreadLocal=" + mThreadLocal.get());
            }
        }.start();

        System.out.println("main  mThreadLocal=" + mThreadLocal.get());



        //基本数据类型和String才会传递副本(事实上String也是传递的地址,只是string对象和其他对
        // 象是不同的，string对象是不能被改变的，内容改变就会产生新对象。)，其他的类型是按引用的传递的

        //如果真的需要改变对象的值。首先，对象应该是可变的，例如，StringBuilder对象
        // 其次，我们需要确保没有创建新的对象并将此对象分配给参数变量，因为Java是只是按值传递
        String x = new String("ab");
//        change(x);
//        System.out.println(x);


        StringBuilder x1 = new StringBuilder("ab");
        change(x1);
        System.out.println(x1);
    }

    public static void change(String x) {
        System.out.println(x);
        x = "cd";
        System.out.println(x);
    }

    public static void change(StringBuilder x) {
        x.delete(0, 2).append("cd");
        System.out.println(x);
    }

}
