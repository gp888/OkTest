package com.gp.oktest.mediacodec

import android.os.Bundle
import android.view.Surface
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityOpenglPlayerBinding
import com.gp.oktest.mediacodec.decoder.AudioDecoder
import com.gp.oktest.mediacodec.decoder.VideoDecoder
import com.gp.oktest.mediacodec.opengl.drawer.VideoDrawer
import java.util.concurrent.Executors

class MultiOpenGLPlayerActivity: BaseActivity<ActivityOpenglPlayerBinding>() {
    private var path = ""
    private var path2 = ""

    private val render = SimpleRender()

    private val threadPool = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        path = getExternalFilesDir(null)?.getPath() + "/decode/mvtest.mp4"
        path2 = getExternalFilesDir(null)?.getPath() + "/decode/mvtest2.mp4"

        initFirstVideo()
        initSecondVideo()
        initRender()
    }

    private fun initFirstVideo() {
        val drawer = VideoDrawer()
        drawer.setVideoSize(1080, 1920)
        drawer.getSurfaceTexture {
            initPlayer(path, Surface(it), false)
        }
        render.addDrawer(drawer)
    }

    private fun initSecondVideo() {
        val drawer = VideoDrawer()
        drawer.setAlpha(0.5f)
        drawer.setVideoSize(1920, 1080)

        drawer.getSurfaceTexture {
            initPlayer(path2, Surface(it), true)
        }
        render.addDrawer(drawer)

        //设置绘制器，用于触摸移动
        binding.glSurface.addDrawer(drawer)
    }

    private fun initPlayer(path: String, sf: Surface, withSound: Boolean) {
        val videoDecoder = VideoDecoder(path, null, sf)
        threadPool.execute(videoDecoder)
        videoDecoder.goOn()

        if (withSound) {
            val audioDecoder = AudioDecoder(path)
            threadPool.execute(audioDecoder)
            audioDecoder.goOn()
        }
    }

    private fun initRender() {
        binding.glSurface.setEGLContextClientVersion(2)
        binding.glSurface.setRenderer(render)
    }

    override fun onDestroy() {
        threadPool.shutdownNow()
        super.onDestroy()
    }
}