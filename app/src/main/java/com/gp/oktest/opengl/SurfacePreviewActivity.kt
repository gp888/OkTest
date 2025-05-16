package com.gp.oktest.opengl

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.graphics.PixelFormat
import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import com.gp.oktest.R
import com.gp.oktest.base.BaseActivity
import com.gp.oktest.camera.CameraUtil
import com.gp.oktest.databinding.ActivitySurfacePreviewBinding
import java.io.IOException

class SurfacePreviewActivity : BaseActivity<ActivitySurfacePreviewBinding>(), SurfaceHolder.Callback {

    lateinit var mHolder: SurfaceHolder
    lateinit var  mCamera: Camera
    private lateinit var mParameters: Camera.Parameters

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        mHolder = binding.mSurface.getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        binding.btnChange.setOnClickListener({
//            val valuesHolder2: PropertyValuesHolder= PropertyValuesHolder.ofFloat("rotationX", 0.0f, 360.0f, 0.0F)
//            val valuesHolder: PropertyValuesHolder = PropertyValuesHolder.ofFloat("rotationY", 0.0f, 360.0f, 0.0f)
            val valuesHolder1: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f, 1.0f)
            val valuesHolder3: PropertyValuesHolder = PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f, 1.0f)
            val objectAnimator: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.mSurface, valuesHolder1, valuesHolder3)
            objectAnimator.setDuration(5000).start()
        })

        binding.btnSwitch.setOnClickListener({
            if (mCamera != null) {
                mCamera.stopPreview()
                mCamera.release()
            }
            mCamera = Camera.open(1)
            try {
                mCamera.setPreviewDisplay(mHolder)
                CameraUtil.setCameraDisplayOrientation(this, 1, mCamera)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            mCamera.startPreview()
        })
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        mCamera.autoFocus { success, camera ->
            if (success) {
                mParameters = mCamera.parameters
                mParameters.setPictureFormat(PixelFormat.JPEG) //图片输出格式
//              mParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);//预览持续发光
                mParameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE) //持续对焦模式
                mCamera.parameters = mParameters
                mCamera.startPreview()
                mCamera.cancelAutoFocus()
            }
        }
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        try {
            mCamera = Camera.open(0)
            mCamera.setDisplayOrientation(90)
            mCamera.setPreviewDisplay(holder)
            mCamera.startPreview()
        } catch (e: IOException) {
        }
    }


}