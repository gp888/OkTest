package com.gp.oktest.androidmedia;

import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * H264 编码类
 */
public class H264Encoder {

    private final static int TIMEOUT_USEC = 12000;

    private MediaCodec mediaCodec;

    public boolean isRuning = false;
    private int width, height, framerate;
    public byte[] configbyte;

    private BufferedOutputStream outputStream;

    public ArrayBlockingQueue<byte[]> yuv420Queue = new ArrayBlockingQueue<>(10);

    /***
     * 构造函数
     * @param width
     * @param height
     * @param framerate 帧率 一般在15至30之内，太小容易造成视频卡顿
     */
    public H264Encoder(int width, int height, int framerate) {
        this.width = width;
        this.height = height;
        this.framerate = framerate;

        MediaFormat mediaFormat = MediaFormat.createVideoFormat("video/avc", width, height);
        //色彩格式
        mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420SemiPlanar);
        //描述平均位速率（以位/秒为单位）的键。 关联的值是一个整数
        mediaFormat.setInteger(MediaFormat.KEY_BIT_RATE, width * height * 5);//码率
        //描述视频格式的帧速率（以帧/秒为单位）的键。
        mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, 30);
        //关键帧间隔时间，单位是秒
        mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 1);
        try {
            mediaCodec = MediaCodec.createEncoderByType("video/avc");
            mediaCodec.configure(mediaFormat, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
            mediaCodec.start();
            createfile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createfile() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/okTest/video/gpdata.mp4";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putData(byte[] buffer) {
        if (yuv420Queue.size() >= 10) {
            yuv420Queue.poll();
        }
        yuv420Queue.add(buffer);
    }

    /***
     * 开始编码
     */
    public void startEncoder() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                isRuning = true;
                byte[] input = null;
                long pts = 0;
                long generateIndex = 0;

                while (isRuning) {
                    if (yuv420Queue.size() > 0) {
                        input = yuv420Queue.poll();
                        byte[] yuv420sp = new byte[width * height * 3 / 2];
                        // 必须要转格式，否则录制的内容播放出来为绿屏
                        NV21ToNV12(input, yuv420sp, width, height);
                        input = yuv420sp;
                    }
                    if (input != null) {
                        try {
                            ByteBuffer[] inputBuffers = mediaCodec.getInputBuffers();//拿到输入缓冲区,用于传送数据进行编码
                            ByteBuffer[] outputBuffers = mediaCodec.getOutputBuffers();;//拿到输出缓冲区,用于取到编码后的数据
                            int inputBufferIndex = mediaCodec.dequeueInputBuffer(-1);
                            if (inputBufferIndex >= 0) {////当输入缓冲区有效时,就是>=0
                                pts = computePresentationTime(generateIndex);
                                ByteBuffer inputBuffer = inputBuffers[inputBufferIndex];
                                inputBuffer.clear();
                                inputBuffer.put(input);//往输入缓冲区写入数据,
                                //第一个是输入缓冲区的索引，第二个数据是输入缓冲区起始索引，第三个是放入的数据大小，第四个是时间戳，保证递增就是
                                mediaCodec.queueInputBuffer(inputBufferIndex, 0, input.length, System.currentTimeMillis(), 0);
                                generateIndex += 1;
                            }

                            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
                            int outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);//拿到输出缓冲区的索引
                            while (outputBufferIndex >= 0) {
                                ByteBuffer outputBuffer = outputBuffers[outputBufferIndex];
                                byte[] outData = new byte[bufferInfo.size];
                                outputBuffer.get(outData); //outData就是输出的h264数据
                                if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_CODEC_CONFIG) {
                                    configbyte = new byte[bufferInfo.size];
                                    configbyte = outData;
                                } else if (bufferInfo.flags == MediaCodec.BUFFER_FLAG_SYNC_FRAME) {
                                    byte[] keyframe = new byte[bufferInfo.size + configbyte.length];
                                    System.arraycopy(configbyte, 0, keyframe, 0, configbyte.length);
                                    System.arraycopy(outData, 0, keyframe, configbyte.length, outData.length);
                                    outputStream.write(keyframe, 0, keyframe.length);
                                } else {
                                    outputStream.write(outData, 0, outData.length);//将输出的h264数据保存为文件，用vlc就可以播放
                                }

                                mediaCodec.releaseOutputBuffer(outputBufferIndex, false);
                                outputBufferIndex = mediaCodec.dequeueOutputBuffer(bufferInfo, TIMEOUT_USEC);
                            }

                        } catch (Throwable t) {
                            t.printStackTrace();
                        }
                    } else {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                // 停止编解码器并释放资源
                try {
                    mediaCodec.stop();
                    mediaCodec.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // 关闭数据流
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 停止编码数据
     */
    public void stopEncoder() {
        isRuning = false;
    }

    private void NV21ToNV12(byte[] nv21, byte[] nv12, int width, int height) {
        if (nv21 == null || nv12 == null) return;
        int framesize = width * height;
        int i = 0, j = 0;
        System.arraycopy(nv21, 0, nv12, 0, framesize);
        for (i = 0; i < framesize; i++) {
            nv12[i] = nv21[i];
        }
        for (j = 0; j < framesize / 2; j += 2) {
            nv12[framesize + j - 1] = nv21[j + framesize];
        }
        for (j = 0; j < framesize / 2; j += 2) {
            nv12[framesize + j] = nv21[j + framesize - 1];
        }
    }

    /**
     * 根据帧数生成时间戳
     */
    private long computePresentationTime(long frameIndex) {
        return 132 + frameIndex * 1000000 / framerate;
    }
}
