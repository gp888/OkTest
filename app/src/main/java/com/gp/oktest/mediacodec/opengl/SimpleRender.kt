package com.gp.oktest.mediacodec

import android.opengl.GLES20
import android.opengl.GLSurfaceView
import com.gp.oktest.mediacodec.opengl.drawer.IDrawer
import com.gp.oktest.mediacodec.opengl.egl.OpenGLTools
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


/**
 * 简单的OpenGL渲染器
 *
 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 * @Datetime 2019-19-09 08:55
 *
 */
class SimpleRender: GLSurfaceView.Renderer {

    private val drawers = mutableListOf<IDrawer>()

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
        //清屏，清屏颜色为黑色。
        GLES20.glClearColor(0f, 0f, 0f, 0f)
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)

        //------开启混合，即半透明---------
        //开启混合
        GLES20.glEnable(GLES20.GL_BLEND)
        // 配置混合算法
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)

        val textureIds = OpenGLTools.createTextureIds(drawers.size)
        for ((idx, drawer) in drawers.withIndex()) {
            drawer.setTextureID(textureIds[idx])
        }
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        //设置OpenGL绘制的区域宽高和位置
        //这里所说的绘制区域，是指OpenGL在GLSurfaceView中的绘制区域，一般都是全部铺满
        GLES20.glViewport(0, 0, width, height)
        for (drawer in drawers) {
            drawer.setWorldSize(width, height)
        }
    }

    /**
     * 真正实现绘制的地方了。该接口会不停的回调，刷新绘制区域
     */
    override fun onDrawFrame(gl: GL10?) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
        drawers.forEach {
            it.draw()
        }
    }

    fun addDrawer(drawer: IDrawer) {
        drawers.add(drawer)
    }
}