package com.gp.oktest.mediacodec

import android.os.Bundle
import android.os.Environment
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityOpenglPlayerBinding
import com.gp.oktest.mediacodec.decoder.AudioDecoder
import com.gp.oktest.mediacodec.decoder.VideoDecoder
import com.gp.oktest.mediacodec.opengl.drawer.IDrawer
import com.gp.oktest.mediacodec.opengl.drawer.VideoDrawer
import java.util.concurrent.Executors


/**
 * 使用OpenGL渲染的播放器
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-10-26 21:07
 *
 */
class OpenGLPlayerActivity: BaseActivity<ActivityOpenglPlayerBinding>() {
    var path = ""
    lateinit var drawer: IDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = Environment.getExternalStorageDirectory().absolutePath + "/mvtest.mp4";
        initRender()
    }

    private fun initRender() {
        drawer = VideoDrawer()
        drawer.setVideoSize(1080, 1920)
        drawer.getSurfaceTexture {
            initPlayer(Surface(it))
        }
        binding.glSurface.setEGLContextClientVersion(2)
        val render = SimpleRender()
        render.addDrawer(drawer)
        binding.glSurface.setRenderer(render)
    }

    private fun initPlayer(sf: Surface) {
        val threadPool = Executors.newFixedThreadPool(10)

        val videoDecoder = VideoDecoder(path, null, sf)
        threadPool.execute(videoDecoder)

        val audioDecoder = AudioDecoder(path)
        threadPool.execute(audioDecoder)

        videoDecoder.goOn()
        audioDecoder.goOn()
    }
}