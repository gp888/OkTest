package com.gp.oktest.opengl;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
//        glSurfaceView.setRenderer(new GLSurfaceView.Renderer() {
//            @Override
//            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//
//            }
//
//            @Override
//            public void onSurfaceChanged(GL10 gl, int width, int height) {
//
//            }
//
//            @Override
//            public void onDrawFrame(GL10 gl) {
//
//            }
//        });

        OneGlSurfaceView glSurfaceView = new OneGlSurfaceView(this);
        setContentView(glSurfaceView);


        setContentView(glSurfaceView);
    }
}
