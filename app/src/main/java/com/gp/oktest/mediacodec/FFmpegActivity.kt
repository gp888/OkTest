package com.gp.oktest.mediacodec

import android.os.Bundle
import android.os.Environment
import android.view.Surface
import android.view.SurfaceHolder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.databinding.ActivityFfmpegInfoBinding
import java.io.File


/**
 * FFmpeg测试页面
 *
 */
class FFmpegActivity: BaseActivity<ActivityFfmpegInfoBinding>() {

    val path = Environment.getExternalStorageDirectory().absolutePath + "/mvtest.mp4"

    private var player: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.tv.text = ffmpegInfo()
        initSfv()
    }

    private fun initSfv() {
        if (File(path).exists()) {
            binding.sfv.holder.addCallback(object : SurfaceHolder.Callback {
                override fun surfaceChanged(holder: SurfaceHolder, format: Int,
                                            width: Int, height: Int) {}
                override fun surfaceDestroyed(holder: SurfaceHolder) {}

                override fun surfaceCreated(holder: SurfaceHolder) {
                    if (player == null) {
                        player = createPlayer(path, holder.surface)
                        play(player!!)
                    }
                }
            })
        } else {
            Toast.makeText(this, "视频文件不存在，请在手机根目录下放置 mvtest.mp4", Toast.LENGTH_SHORT).show()
        }
    }

    private external fun ffmpegInfo(): String

    private external fun createPlayer(path: String, surface: Surface): Int

    private external fun play(player: Int)

    private external fun pause(player: Int)

    companion object {
        init {
            System.loadLibrary("native-lib")
        }
    }
}