package com.gp.oktest.javatest.StringBufferTest;


public class StringBufferTest {
    private StringBuffer contents = new StringBuffer();

    public void log(String message){
        contents.append(System.currentTimeMillis());
        contents.append("; ");
        contents.append(Thread.currentThread().getName());
        for(int i = 0; i < 100; i++){
            contents.append(i);
            contents.append(message);    //append本身是线程安全的，修改contents时，其它线程无法访问。
            contents.append("\n");
        }
        contents.append("\n\n");
    }

    public void getContents(){
        System.out.println(contents);
    }
}
