package com.gp.oktest.serial;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPortUtil {
    private SerialPort mSerialPort;
    private OutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String path = "/dev/ttyMT0";
    private int baudrate = 115200;
    private static SerialPortUtil portUtil;
    private OnDataReceiveListener onDataReceiveListener = null;
    private boolean isStop = false;

    public interface OnDataReceiveListener {
        void onSerialDataReceive(byte[] buffer, int size);
    }

    public void setOnDataReceiveListener(
            OnDataReceiveListener dataReceiveListener) {
        onDataReceiveListener = dataReceiveListener;
    }

    public static SerialPortUtil getInstance() {
        if (null == portUtil) {
            portUtil = new SerialPortUtil();
            portUtil.onCreate();
        }
        return portUtil;
    }

    /**
     * 初始化串口信息
     */
    public void onCreate() {
        try {
            mSerialPort = new SerialPort(path, baudrate,0);
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();

            mReadThread = new ReadThread();
            isStop = false;
            mReadThread.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean sendData(byte[] data){
        boolean result = true;
        try {
            if (mOutputStream != null) {
                mOutputStream.write(data);
                Log.d("发出", data.toString());
            } else {
                result = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        }
        return result;
    }

    public void sendBuffer(byte[] mBuffer) {
        if (!sendData(mBuffer)){
            closeSerialPort();
            onCreate();
            sendBuffer(mBuffer);
        }
    }

    private class ReadThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (!isStop && !isInterrupted()) {
                int size;
                try {
                    if (mInputStream == null)
                        return;
                    byte[] buffer = new byte[512];
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        if (null != onDataReceiveListener) {
                            onDataReceiveListener.onSerialDataReceive(buffer, size);
                        }
                    }
                    Thread.sleep(10);
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        isStop = true;
        if (mReadThread != null) {
            mReadThread.interrupt();
        }
        if (mSerialPort != null) {
            try {
                mSerialPort.doClose();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
