package com.gp.oktest.mediacodec

import android.media.MediaCodec
import java.nio.ByteBuffer


/**
 * 一帧数据
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-12-15 19:28
 *
 */
class Frame {
    //未编码数据
    var buffer: ByteBuffer? = null

    //未编码数据信息
    var bufferInfo = MediaCodec.BufferInfo()
    private set

    fun setBufferInfo(info: MediaCodec.BufferInfo) {
        bufferInfo.set(info.offset, info.size, info.presentationTimeUs, info.flags)
    }
}