package com.gp.oktest.javatest.StringBufferTest;

/**
 * 每一次append是线程安全的，但众多次append的组合并不是线程安全的，这个输出结果不是太可控的，
 * 但如果对于log和getContest方法加关键字synchronized，那么结果就会变得非常条理，
 * 如果换成StringBuider甚至是append到一半，它也会让位于其它在此基础上操作的线程
 */
class RunThread extends Thread{

    String message;
    StringBufferTest buffer;

    public RunThread(StringBufferTest buffer, String message){
        this.buffer = buffer;
        this.message = message;
    }
    @Override
    public void run(){
        while(true){
            buffer.log(message);
            buffer.getContents();
        }
    }
    public static void main(String[] args) {
        StringBufferTest ss = new StringBufferTest();
        new RunThread(ss, "you").start();
        new RunThread(ss, "me").start();
        new RunThread(ss, "she").start();
    }
}
