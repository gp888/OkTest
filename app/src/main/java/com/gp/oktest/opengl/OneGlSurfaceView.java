package com.gp.oktest.opengl;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class OneGlSurfaceView extends GLSurfaceView {
    private final OneGlRenderer mRenderer;

    public OneGlSurfaceView(Context context) {
        super(context);
        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        mRenderer = new OneGlRenderer();

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(mRenderer);
    }
}
