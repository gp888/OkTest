package com.gp.oktest.mediacodec.extractor

import android.media.MediaExtractor
import android.media.MediaFormat
import java.nio.ByteBuffer

//音视频解封提取
class MMExtractor(path: String) {

    /**
     * 音视频分离器
     */
    var mExtractor : MediaExtractor

    /**音视频通道索引*/
    var mVideoTrack: Int = -1
    var mAudioTrack: Int = -1

    /**当前帧时间戳*/
    var mCurSampleTime: Long = 0

    /**当前帧标志*/
    private var mCurSampleFlag: Int = 0

    /**开始解码时间点*/
    private var mStartPos:Long = 0

    init {
        mExtractor = MediaExtractor();
        mExtractor.setDataSource(path)
    }


    /**
     * 获取视频格式参数
     */
    fun getVideoFormat(): MediaFormat? {
        for (i in 0 until mExtractor.trackCount) {
            val mediaFormat = mExtractor.getTrackFormat(i)
            val mime = mediaFormat.getString(MediaFormat.KEY_MIME)
            if (mime!!.startsWith("video/")) {
                mVideoTrack = i
                break
            }
        }
        return if (mVideoTrack >= 0)
            mExtractor.getTrackFormat(mVideoTrack)
        else null
    }

    /**
     * 获取音频格式参数
     */
    fun getAudioFormat(): MediaFormat? {
        for (i in 0 until mExtractor.trackCount) {
            val mediaFormat = mExtractor.getTrackFormat(i)
            val mime = mediaFormat.getString(MediaFormat.KEY_MIME)
            if (mime != null) {
                if (mime.startsWith("audio/")) {
                    mAudioTrack = i
                    break
                }
            }
        }
        return if (mAudioTrack >= 0) {
            mExtractor.getTrackFormat(mAudioTrack)
        } else null
    }

    /**
     * 读取视频数据
     * byteBuffer 解码器传进来的，用于存放待解码数据的缓冲区
     * 返回读取到的音视频数据流的大小，<0表示数据已读完
     */
    fun readBuffer(byteBuffer: ByteBuffer): Int {
        byteBuffer.clear()
        selectSourceTrack()
        var readSampleCount = mExtractor.readSampleData(byteBuffer, 0)
        if (readSampleCount < 0) {
            return -1
        }
        //记录当前帧的时间戳
        mCurSampleTime = mExtractor.sampleTime
        mCurSampleFlag = mExtractor.sampleFlags
        //进入下一帧
        mExtractor.advance()
        return readSampleCount
    }

    /**
     * 选择通道
     */
    private fun selectSourceTrack() {
        if (mVideoTrack >= 0) {
            mExtractor.selectTrack(mVideoTrack)
        } else if (mAudioTrack >= 0) {
            mExtractor.selectTrack(mAudioTrack)
        }
    }

    /**
     * seek到指定位置，并返回实际帧的时间戳
     *
     * seek(pos: Long)方法，主要用于跳播，快速将数据定位到指定的播放位置，但是，由于视频中，除了I帧以外，
     * PB帧都需要依赖其他的帧进行解码，所以，通常只能seek到I帧，但是I帧通常和指定的播放位置有一定误差，
     * 因此需要指定seek靠近哪个关键帧，有以下三种类型：
    SEEK_TO_PREVIOUS_SYNC：跳播位置的上一个关键帧
    SEEK_TO_NEXT_SYNC：跳播位置的下一个关键帧
    SEEK_TO_CLOSEST_SYNC：距离跳播位置的最近的关键帧
     */
    fun seek(pos:Long):Long{
        mExtractor.seekTo(pos, MediaExtractor.SEEK_TO_PREVIOUS_SYNC)
        return mExtractor.sampleTime
    }

    /**
     * 停止读取数据
     */
    fun stop(){
        mExtractor.release()
    }

    fun getVideoTrack() : Int{
        return mVideoTrack
    }

    fun getAudoTrack():Int{
        return mAudioTrack
    }

    fun setStartPos(pos:Long) {
        mStartPos = pos
    }

    /**
     * 获取当前帧时间
     */
    fun getCurrentTimestamp():Long{
        return mCurSampleTime
    }

    fun getSampleFlag(): Int {
        return mCurSampleFlag
    }
}