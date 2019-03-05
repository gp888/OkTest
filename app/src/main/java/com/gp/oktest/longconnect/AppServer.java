package com.gp.oktest.longconnect;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class AppServer {
    public static final String TAG = AppServer.class.getSimpleName();
    private static BufferedOutputStream bos;
    private static BufferedInputStream bis;
    private static Socket acceptSocket;

    public static void main (String args[]){
        try{
            ServerSocket serverSocket  = new ServerSocket(8292);
            while(true) {
                acceptSocket = serverSocket.accept();
                bos = new BufferedOutputStream(acceptSocket.getOutputStream());
                bis = new BufferedInputStream(acceptSocket.getInputStream());

                ReadThread readThread = new ReadThread();
                readThread.start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ReadThread extends Thread {
        @Override
        public void run() {
            while (true) {
                byte[] data = new byte[1024];
                int size = 0;
                try {
                    while ((size = bis.read(data)) != -1) {
                        String str = new String(data,0,size);
                        System.out.println(TAG+"----"+str);
                        //收到客户端发送的请求后，立马回一条心跳给客户端
                        bos.write("有时会突然忘了，我依然爱着你！".getBytes());
                        bos.flush();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
