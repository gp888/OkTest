package com.gp.oktest.mediacodec

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.R
import com.gp.oktest.mediacodec.decoder.AudioDecoder
import com.gp.oktest.mediacodec.decoder.VideoDecoder
import com.gp.oktest.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_simple_player.*
import java.io.File
import java.util.concurrent.Executors


/**
 * 简单播放器页面
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-10-12 09:33
 *
 */
class SimplePlayerActivity: AppCompatActivity() {
    lateinit var videoDecoder: VideoDecoder
    lateinit var audioDecoder: AudioDecoder

    lateinit var path:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_player)
         path = getExternalFilesDir(null)?.getPath() + "/decode/mvtest.mp4"

        if(!File(path).exists()) {
            ToastUtil.showToastLong(path)
        }

        initPlayer(path)
    }

    private fun initPlayer(path : String) {
        val threadPool = Executors.newFixedThreadPool(10)

        videoDecoder = VideoDecoder(path, sfv, null)
        threadPool.execute(videoDecoder)

        audioDecoder = AudioDecoder(path)
        threadPool.execute(audioDecoder)

        videoDecoder.goOn()
        audioDecoder.goOn()
    }

    fun clickRepack(view: View) {
        repack()
    }

    private fun repack() {
//        val repack = MP4Repack(path)
//        repack.start()
    }

    override fun onDestroy() {
        videoDecoder.stop()
        audioDecoder.stop()
        super.onDestroy()
    }
}
