package com.gp.oktest.opengl

import android.content.Intent
import android.os.Bundle
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import kotlinx.android.synthetic.main.activity_camera_record.*

class CameraRecord : BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_record)
        camera_surfaceview.setOnClickListener({
            startActivity(Intent(this, SurfacePreviewActivity::class.java))
        })
    }


}