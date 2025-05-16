package com.gp.oktest.mediacodec.opengl.egl

import android.opengl.GLES20
import android.view.Surface
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.cxp.learningvideo.opengl.egl.EGLSurfaceHolder
import com.gp.oktest.mediacodec.opengl.drawer.IDrawer
import java.lang.ref.WeakReference


/**
 * 自定义的OpenGL渲染器
 *
 * 包含EGL的初始化，线程与OpenGL上下文绑定，渲染循环，资源销毁等
 *
 * 一个自定义的渲染线程RenderThread
    一个SurfaceView的弱引用
    一个绘制器列表
    初始化时，启动渲染线程。然后就是将SurfaceView生命周期转发给渲染线程

【1】在进入while(true)之前，initEGL使用EGLSurfaceHolder来初始化EGL。

需要注意的是，initEGL只会调用一次，也就是说EGL只初始化一次，无论后面surface销毁和重建多少次。
【2】有了可用的surface后，进入FRESH_SURFACE，调用EGLSurfaceHolder的createEGLSurface和makeCurrent来绑定线程、上下文和窗口。

【3】根据surface窗口宽高，设置OpenGL窗口的宽高，然后自动进入RENDERING状态。这部分对应GLSurfaceView.Renderer中回调onSurfaceChanged方法。

【4】进入循环渲染render，这里每隔20ms渲染一次画面。对应GLSurfaceView.Renderer中回调onDrawFrame方法。

【5】如果surface被销毁（比如，进入后台），调用EGLSurfaceHolder的destroyEGLSurface销毁和解绑窗口。

注：当页面重新回到前台时，会重新创建surface，这时只要重新创建EGLSurface，并绑定上下文和EGLSurface，就可以继续渲染画面，无需开启新的渲染线程。
【6】SurfaceView被销毁（比如，页面finish），这时已经无需再渲染了，需要释放所有的EGL资源，并退出线程。

 * @author Chen Xiaoping (562818444@qq.com)
 * @since LearningVideo
 * @version LearningVideo
 *
 */
class CustomerGLRenderer : SurfaceHolder.Callback {

    //opengl 渲染线程
    private val mThread = RenderThread()

    private var mSurfaceView: WeakReference<SurfaceView>? = null

    private var mSurface: Surface? = null

    private val mDrawers = mutableListOf<IDrawer>()

    init {
        mThread.start()
    }

    fun setSurface(surface: SurfaceView) {
        mSurfaceView = WeakReference(surface)
        surface.holder.addCallback(this)

        surface.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener{
            override fun onViewDetachedFromWindow(v: View) {
                stop()
            }

            override fun onViewAttachedToWindow(v: View) {
            }
        })
    }

    //设置Surface接口
    fun setSurface(surface: Surface, width: Int, height: Int) {
        mSurface = surface
        mThread.onSurfaceCreate()
        mThread.onSurfaceChange(width, height)
    }

    //设置渲染模式
    fun setRenderMode(mode: RenderMode) {
        mThread.setRenderMode(mode)
    }

//    通知更新画面
    fun notifySwap(timeUs: Long) {
        mThread.notifySwap(timeUs)
    }

    fun addDrawer(drawer: IDrawer) {
        mDrawers.add(drawer)
    }

    fun stop() {
        mThread.onSurfaceStop()
        mSurface = null
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        mSurface = holder.surface
        mThread.onSurfaceCreate()
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mThread.onSurfaceChange(width, height)
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        mThread.onSurfaceDestroy()
    }

    inner class RenderThread: Thread() {

        // 渲染状态
        private var mState = RenderState.NO_SURFACE

        private var mEGLSurface: EGLSurfaceHolder? = null

        // 是否绑定了EGLSurface
        private var mHaveBindEGLContext = false

        //是否已经新建过EGL上下文，用于判断是否需要生产新的纹理ID
        private var mNeverCreateEglContext = true

        private var mWidth = 0
        private var mHeight = 0

        private val mWaitLock = Object()

        private var mCurTimestamp = 0L

        private var mLastTimestamp = 0L

        private var mRenderMode = RenderMode.RENDER_WHEN_DIRTY

        private fun holdOn() {
            synchronized(mWaitLock) {
                mWaitLock.wait()
            }
        }

        private fun notifyGo() {
            synchronized(mWaitLock) {
                mWaitLock.notify()
            }
        }

        fun setRenderMode(mode: RenderMode) {
            mRenderMode = mode
        }

        fun onSurfaceCreate() {
            mState = RenderState.FRESH_SURFACE
            notifyGo()
        }

        fun onSurfaceChange(width: Int, height: Int) {
            mWidth = width
            mHeight = height
            mState = RenderState.SURFACE_CHANGE
            notifyGo()
        }

        fun onSurfaceDestroy() {
            mState = RenderState.SURFACE_DESTROY
            notifyGo()
        }

        fun onSurfaceStop() {
            mState = RenderState.STOP
            notifyGo()
        }

        fun notifySwap(timeUs: Long) {
            synchronized(mCurTimestamp) {
                mCurTimestamp = timeUs
            }
            notifyGo()
        }

        override fun run() {
            // 【1】初始化EGL
            initEGL()
            while (true) {
                when (mState) {
                    RenderState.FRESH_SURFACE -> {
                        //【2】使用surface初始化EGLSurface，并绑定上下文
                        createEGLSurfaceFirst()
                        holdOn()
                    }
                    RenderState.SURFACE_CHANGE -> {
                        createEGLSurfaceFirst()
                        //【3】初始化OpenGL世界坐标系宽高
                        GLES20.glViewport(0, 0, mWidth, mHeight)
                        configWordSize()
                        mState = RenderState.RENDERING
                    }
                    RenderState.RENDERING -> {
                        //【4】进入循环渲染
                        render()
                        if (mRenderMode == RenderMode.RENDER_WHEN_DIRTY) {
                            holdOn()
                        }
                    }
                    RenderState.SURFACE_DESTROY -> {
                        //【5】销毁EGLSurface，并解绑上下文
                        destroyEGLSurface()
                        mState = RenderState.NO_SURFACE
                    }
                    RenderState.STOP -> {
                        //【6】释放所有资源
                        releaseEGL()
                        return
                    }
                    else -> {
                        holdOn()
                    }
                }
                if (mRenderMode == RenderMode.RENDER_CONTINUOUSLY) {
                    sleep(16)
                }
            }
        }

        private fun initEGL() {
            mEGLSurface = EGLSurfaceHolder()
            mEGLSurface?.init(null, EGL_RECORDABLE_ANDROID)
        }

        private fun createEGLSurfaceFirst() {
            if (!mHaveBindEGLContext) {
                mHaveBindEGLContext = true
                createEGLSurface()
                if (mNeverCreateEglContext) {
                    mNeverCreateEglContext = false
                    GLES20.glClearColor(0f, 0f, 0f, 0f)
                    //开启混合，即半透明
                    GLES20.glEnable(GLES20.GL_BLEND)
                    GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA)
                    generateTextureID()
                }
            }
        }

        private fun createEGLSurface() {
            mEGLSurface?.createEGLSurface(mSurface)
            mEGLSurface?.makeCurrent()
        }

        private fun generateTextureID() {
            val textureIds = OpenGLTools.createTextureIds(mDrawers.size)
            for ((idx, drawer) in mDrawers.withIndex()) {
                drawer.setTextureID(textureIds[idx])
            }
        }

        private fun configWordSize() {
            mDrawers.forEach { it.setWorldSize(mWidth, mHeight) }
        }

        private fun render() {
            val render = if (mRenderMode == RenderMode.RENDER_CONTINUOUSLY) {
                true
            } else {
                synchronized(mCurTimestamp) {
                    if (mCurTimestamp > mLastTimestamp) {
                        mLastTimestamp = mCurTimestamp
                        true
                    } else {
                        false
                    }
                }
            }

            if (render) {
                GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT or GLES20.GL_DEPTH_BUFFER_BIT)
                mDrawers.forEach { it.draw() }
                mEGLSurface?.setTimestamp(mCurTimestamp)
                mEGLSurface?.swapBuffers()
            }
        }

        private fun destroyEGLSurface() {
            mEGLSurface?.destroyEGLSurface()
            mHaveBindEGLContext = false
        }

        private fun releaseEGL() {
            mEGLSurface?.release()
        }
    }

    /**
     * 渲染状态
     */
    enum class RenderState {
        NO_SURFACE, //没有有效的surface
        FRESH_SURFACE, //持有一个未初始化的新的surface
        SURFACE_CHANGE, //surface尺寸变化
        RENDERING, //初始化完毕，可以开始渲染
        SURFACE_DESTROY, //surface销毁
        STOP //停止绘制
    }

    enum class RenderMode {
        // 自动循环渲染
        RENDER_CONTINUOUSLY,
        // 由外部通过notifySwap通知渲染
        RENDER_WHEN_DIRTY
    }
}