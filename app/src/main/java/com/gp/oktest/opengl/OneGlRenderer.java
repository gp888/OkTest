package com.gp.oktest.opengl;

import android.opengl.EGLConfig;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.SystemClock;

import javax.microedition.khronos.opengles.GL10;

public class OneGlRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square   mSquare;

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    //透视投影
    private final float[] mProjectionMatrix = new float[16];
    //相机视图
    private final float[] mViewMatrix = new float[16];
    //旋转矩阵
    private float[] mRotationMatrix = new float[16];

    public void onSurfaceCreated(GL10 unused, javax.microedition.khronos.egl.EGLConfig config) {
        // 设置背景颜色
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        // 初始化triangle
        mTriangle = new Triangle();
        // 初始化 square
        mSquare = new Square();
    }


    public void onDrawFrame(GL10 unused) {
        // Redraw background color
//        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);


//        mTriangle.draw();


        //定义相机视图
        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, 0, 0, -3, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        // 计算投影和视图变换
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        // Draw shape
//        mTriangle.draw(mMVPMatrix);


        float[] scratch = new float[16];
        // 创建一个旋转矩阵
        long time = SystemClock.uptimeMillis() % 4000L;
        float angle = 0.090f * ((int) time);
        Matrix.setRotateM(mRotationMatrix, 0, angle, 0, 0, -1.0f);

        // 将旋转矩阵与投影和相机视图组合在一起
        // Note that the mMVPMatrix factor *must be first* in order
        // for the matrix multiplication product to be correct.
        Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);

        // Draw triangle
        mTriangle.draw(scratch);
    }

    public void onSurfaceChanged(GL10 unused, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        //定义透视投影
        float ratio = (float) width / height;
        // 这个投影矩阵被应用于对象坐标在onDrawFrame（）方法中
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
    }

    /**
     * 执行着色器代码
     * @param type
     * @param shaderCode
     * @return
     */
    public static int loadShader(int type, String shaderCode){

        // 创造顶点着色器类型(GLES20.GL_VERTEX_SHADER)
        // 或者是片段着色器类型 (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);
        // 添加上面编写的着色器代码并编译它
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;
    }

}
