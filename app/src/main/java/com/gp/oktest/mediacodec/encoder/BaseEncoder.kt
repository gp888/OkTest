package com.gp.oktest.mediacodec.encoder

import android.media.MediaCodec
import android.media.MediaFormat
import android.util.Log
import com.gp.oktest.mediacodec.Frame
import com.gp.oktest.mediacodec.muxer.MMuxer
import java.nio.ByteBuffer


/**
 * 基础编码器
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-12-15 17:10
 *
 *
    初始化编码器
    将数据压入编码器
    从编码器取出数据，并压入mp4
 *
 */
abstract class BaseEncoder(muxer: MMuxer, width: Int = -1, height: Int = -1) : Runnable {

    private val TAG = "BaseEncoder"

    // 目标视频宽，只有视频编码的时候才有效
    protected val mWidth: Int = width

    // 目标视频高，只有视频编码的时候才有效
    protected val mHeight: Int = height

    // Mp4合成器
    private var mMuxer: MMuxer = muxer

    // 线程运行
    private var mRunning = true

    // 编码帧序列,用来缓存需要编码的帧数据
    private var mFrames = mutableListOf<Frame>()

    // 编码器
    private lateinit var mCodec: MediaCodec

    // 当前编码帧信息
    private val mBufferInfo = MediaCodec.BufferInfo()

    // 编码输出缓冲区
    private var mOutputBuffers: Array<ByteBuffer>? = null

    // 编码输入缓冲区
    private var mInputBuffers: Array<ByteBuffer>? = null

    private var mLock = Object()

    // 是否编码结束
    private var mIsEOS = false

    // 编码状态监听器
    private var mStateListener: IEncodeStateListener? = null

    init {
        initCodec()
    }

    /**
     * 初始化编码器
     */
    private fun initCodec() {
        mCodec = MediaCodec.createEncoderByType(encodeType())
        configEncoder(mCodec)
        mCodec.start()
        mOutputBuffers = mCodec.outputBuffers
        mInputBuffers = mCodec.inputBuffers
        Log.i(TAG, "编码器初始化完成")
    }

    override fun run() {
        loopEncode()
        done()
    }

    /**
     * 循环编码
     */
    private fun loopEncode() {
        Log.i(TAG, "开始编码")
        while (mRunning && !mIsEOS) {
            val empty = synchronized(mFrames) {
                mFrames.isEmpty()
            }
            if (empty) {
                justWait()
            }
            if (mFrames.isNotEmpty()) {
                val frame = synchronized(mFrames) {
                    mFrames.removeAt(0)
                }

                if (encodeManually()) {
                    //【1. 数据压入编码】
                    encode(frame)
                } else if (frame.buffer == null) { // 如果是自动编码（比如视频），遇到结束帧的时候，直接结束掉
                    Log.e(TAG, "发送编码结束标志")
                    // This may only be used with encoders receiving input from a Surface
                    mCodec.signalEndOfInputStream()
                    mIsEOS = true
                }
            }
            //【2. 拉取编码好的数据】
            drain()
        }
    }

    /**
     * 编码
     */
    private fun encode(frame: Frame) {

        //可用的输入缓冲索引，接着把数据压入输入缓冲
        val index = mCodec.dequeueInputBuffer(-1)

        /*向编码器输入数据*/
        if (index >= 0) {
            val inputBuffer = mInputBuffers!![index]
            inputBuffer.clear()
            if (frame.buffer != null) {
                inputBuffer.put(frame.buffer)
            }
            if (frame.buffer == null || frame.bufferInfo.size <= 0) { // 小于等于0时，为音频结束符标记
                mCodec.queueInputBuffer(index, 0, 0,
                    frame.bufferInfo.presentationTimeUs, MediaCodec.BUFFER_FLAG_END_OF_STREAM)
            } else {
                frame.buffer?.flip()
                frame.buffer?.mark()
                mCodec.queueInputBuffer(index, 0, frame.bufferInfo.size,
                    frame.bufferInfo.presentationTimeUs, 0)
            }
            frame.buffer?.clear()
        }
    }

    /**
     * 榨干编码输出数据
     */
    private fun drain() {
        loop@ while (!mIsEOS) {
            val index = mCodec.dequeueOutputBuffer(mBufferInfo, 1000)
            when (index) {
                MediaCodec.INFO_TRY_AGAIN_LATER -> break@loop
                //说明编码参数格式已经生成（比如视频的码率，帧率，SPS/PPS帧信息等），需要把这些信息写入到mp4对应媒体轨道中
                // （这里通过 addTrack 在子类中配置音视频对应的编码格式），之后才能开始将编码完成的数据，通过MediaMuxer写入到相应媒体通道中。
                MediaCodec.INFO_OUTPUT_FORMAT_CHANGED -> {
                    addTrack(mMuxer, mCodec.outputFormat)
                }
                MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED -> {
                    mOutputBuffers = mCodec.outputBuffers
                }
                else -> {
                    if (mBufferInfo.flags == MediaCodec.BUFFER_FLAG_END_OF_STREAM) {
                        mIsEOS = true
                        mBufferInfo.set(0, 0, 0, mBufferInfo.flags)
                        Log.e(TAG, "编码结束")
                    }

                    if (mBufferInfo.flags == MediaCodec.BUFFER_FLAG_CODEC_CONFIG) {
                        // SPS or PPS, which should be passed by MediaFormat.
                        mCodec.releaseOutputBuffer(index, false)
                        continue@loop
                    }

                    if (!mIsEOS) {
                        writeData(mMuxer, mOutputBuffers!![index], mBufferInfo)
                    }
                    mCodec.releaseOutputBuffer(index, false)
                }
            }
        }
    }

    /**
     * 编码结束，是否资源
     */
    private fun done() {
        try {
            Log.i(TAG, "release")
            release(mMuxer)
            mCodec.stop()
            mCodec.release()
            mRunning = false
            mStateListener?.encoderFinish(this)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 编码进入等待
     */
    private fun justWait() {
        try {
            synchronized(mLock) {
                mLock.wait(1000)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 通知继续编码
     */
    private fun notifyGo() {
        try {
            synchronized(mLock) {
                mLock.notify()
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 将一帧数据压入队列，等待编码
     */
    fun encodeOneFrame(frame: Frame) {
        synchronized(mFrames) {
            mFrames.add(frame)
        }
        notifyGo()
        // 延时一点时间，避免掉帧
        Thread.sleep(frameWaitTimeMs())
    }

    /**
     * 通知结束编码
     */
    fun endOfStream() {
        synchronized(mFrames) {
            val frame = Frame()
            frame.buffer = null
            mFrames.add(frame)
            notifyGo()
        }
    }

    /**
     * 设置状态监听器
     */
    fun setStateListener(l: IEncodeStateListener) {
        this.mStateListener = l
    }

    /**
     * 编码类型，配置音频和视频对应的编码类型
     * 视频编码为h264对应的编码类型为："video/avc" ；音频编码为AAC对应的编码类型为："audio/mp4a-latm"
     */
    abstract fun encodeType(): String

    /**
     * 子类配置编码器
     */
    abstract fun configEncoder(codec: MediaCodec)

    /**
     * 配置mp4音视频轨道
     */
    abstract fun addTrack(muxer: MMuxer, mediaFormat: MediaFormat)

    /**
     * 往mp4写入音视频数据
     */
    abstract fun writeData(muxer: MMuxer, byteBuffer: ByteBuffer, bufferInfo: MediaCodec.BufferInfo)

    /**
     * 释放子类资源
     */
    abstract fun release(muxer: MMuxer)

    /**
     * 每一帧排队等待时间
     */
    open fun frameWaitTimeMs() = 20L

    /**
     * 是否手动编码
     * 视频：false 音频：true
     *
     * 注：视频编码通过Surface，MediaCodec自动完成编码；音频数据需要用户自己压入编码缓冲区，完成编码
     */
    open fun encodeManually() = true
}