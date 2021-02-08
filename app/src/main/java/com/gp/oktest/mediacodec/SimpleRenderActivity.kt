package com.gp.oktest.mediacodec

import androidx.appcompat.app.AppCompatActivity
import android.graphics.BitmapFactory
import android.os.Bundle
import com.gp.oktest.R
import com.gp.oktest.mediacodec.opengl.drawer.BitmapDrawer
import com.gp.oktest.mediacodec.opengl.drawer.IDrawer
import com.gp.oktest.mediacodec.opengl.drawer.TriangleDrawer
import kotlinx.android.synthetic.main.activity_simple_render.*


/**
 * 简单渲染页面
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-10-09 09:23
 *
 */
class SimpleRenderActivity : AppCompatActivity() {

    //自定义opengl渲染器
    private lateinit var drawer: IDrawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_render)

        drawer = if (intent.getIntExtra("type", 0) == 0) {
            TriangleDrawer()
        } else {
            BitmapDrawer(BitmapFactory.decodeResource(resources, R.drawable.czech))
        }
        initRender(drawer)
    }

    private fun initRender(drawer: IDrawer) {
        gl_surface.setEGLContextClientVersion(2)
        val render = SimpleRender()
        render.addDrawer(drawer)
        gl_surface.setRenderer(render)
    }

    override fun onDestroy() {
        drawer.release()
        super.onDestroy()
    }
}