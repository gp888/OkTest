package com.gp.oktest.serial;

import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class SerialPort {

    private FileInputStream mInput;
    private FileOutputStream mOutput;
    private FileDescriptor mFd;

    private native FileDescriptor open(String path, int baudRate, int flags);

    public native void close();

    static {
        System.loadLibrary("serial_port");
    }

    /**
     * 构造函数
     * @param portPath 串口路径.
     * @param baudRate 串口波特率.
     * @param flags    串口类型.一般为0.
     */
    public SerialPort(String portPath, int baudRate, int flags) throws SecurityException, IOException {
        File file = new File(portPath);
        if (!file.canRead() || !file.canWrite()) {
            //在Android 5.0 之后，这里无法获取root 权限，所以无法读取到我们串口的消息
            try {
                Process su;
                su = Runtime.getRuntime().exec("/system/xbin/su");
                String cmd = "chmod 666 " + portPath + "\n";
                su.getOutputStream().write(cmd.getBytes());
                if ((su.waitFor() != 0) || !file.canWrite() || !file.canRead()) {
                    throw new SecurityException();
                }
                su.destroy();
            } catch (Exception e) {
                e.printStackTrace();
                throw new SecurityException();
            }
        }
        try {
            mFd = open(portPath, baudRate, flags);
            if (mFd == null) {
                throw new IOException();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("serial port","is failed");
            return;
        }
        mInput = new FileInputStream(mFd);
        mOutput = new FileOutputStream(mFd);
        Log.e("serial port","is open");
    }

    /**
     * 获取输入流
     */
    public InputStream getInputStream() {
        return mInput;
    }

    /**
     * 获取输出流
     */
    public OutputStream getOutputStream() {
        return mOutput;
    }

    public void doClose(){
        close();
    }
}
