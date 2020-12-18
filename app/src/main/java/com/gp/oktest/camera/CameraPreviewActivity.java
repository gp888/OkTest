package com.gp.oktest.camera;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;
import com.gp.oktest.utils.ToastUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CameraPreviewActivity extends BaseActivity implements SurfaceHolder.Callback{

    @BindView(R.id.surface_view)
    SurfaceView mSurface;
    @BindView(R.id.switch_camera)
    Button switchCamera;

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int screenWidth;
    private int screenHeight;
    private Camera.Size size;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO,
                    Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.CAMERA}, 5);
        }

        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);


        mHolder = mSurface.getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.setKeepScreenOn(true);
        mHolder.setFixedSize(screenWidth, screenHeight);

        // 设置 Surface 类型
        // 参数：
        //        SURFACE_TYPE_NORMAL       : 用 RAM 缓存原生数据的普通 Surface
        //        SURFACE_TYPE_HARDWARE     : 适用于 DMA(Direct memory access )引擎和硬件加速的Surface
        //        SURFACE_TYPE_GPU          : 适用于 GPU 加速的 Surface
        //        SURFACE_TYPE_PUSH_BUFFERS ：表明该 Surface 不包含原生数据，Surface用到的数据由其他对象提供
        // 在 Camera 图像预览中就使用 SURFACE_TYPE_PUSH_BUFFERS 类型的 Surface，由 Camera 负责提供给预览 Surface 数据，这样图像预览会比较流
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
        }
    }
    @OnClick(R.id.switch_camera)
    public void onSwitchCamera() {
        releaseCamera();
        if(mCameraId == Camera.CameraInfo.CAMERA_FACING_BACK) {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_FRONT;
        } else {
            mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        }
        mCamera = getCamera(mCameraId);
        if (mHolder != null) {
            startPreview(mCamera, mHolder);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        ToastUtil.showToastShort("pause");
        releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ToastUtil.showToastShort("resume");
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mHolder != null) {
                startPreview(mCamera, mHolder);
            }
        }
    }

    /**
     *  在 Surface 首次创建时被立即调用
     * @param holder 当前 Surface 的 SurfaceHolder 对象
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
        }
        startPreview(mCamera, holder);
    }

    /**
     *  在 Surface 格式 和 大小发生变化时会立即调用，可以在这个方法中更新 Surface
     * @param holder   当前 Surface 的 SurfaceHolder 对象
     * @param format          surface 的新格式
     * @param width           surface 的新宽度
     * @param height          surface 的新高度
     */
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            CameraUtil.setCameraDisplayOrientation(this, mCameraId, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        Camera.Parameters parameters = camera.getParameters();
        List<String> focusMode = parameters.getSupportedFocusModes();
        if (focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//            mCamera.cancelAutoFocus();
        }

        if (focusMode.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){
            //连续对焦
            parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
//            mCamera.cancelAutoFocus();
        }

        /**
         * surface大小根据 屏幕宽 和 比例尺 动态设置高度
         */
        Camera.Size previewSize = CameraUtil.getInstance().getPreviewSize(parameters.getSupportedPreviewSizes(), screenWidth, CameraUtil.getScreenScale(CameraPreviewActivity.this));
        parameters.setPreviewSize(previewSize.width, previewSize.height);
        size = parameters.getPreviewSize();

        Camera.Size pictrueSize = CameraUtil.getInstance().getPictureSize(parameters.getSupportedPictureSizes(), screenWidth,  CameraUtil.getScreenScale(CameraPreviewActivity.this));
        parameters.setPictureSize(pictrueSize.width, pictrueSize.height);

//        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);


//        parameters.setPictureFormat(PixelFormat.RGBA_8888);//设置照片的格式
//        parameters.setJpegQuality(85);//设置照片的质量
//        parameters.setPictureSize(screenHeight, screenWidth);//设置照片的大小，默认是和屏幕一样大
        camera.setParameters(parameters);



    }

    /**
     * 获取Camera实例
     *
     * @return
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {
            Log.d(TAG, e.getStackTrace().toString());
        }
        return camera;
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * 拍照
     */
    private void captrue() {
        mCamera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Bitmap saveBitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                //把bitmap缩放成想要的大小
                saveBitmap = Bitmap.createScaledBitmap(saveBitmap, screenWidth * 4 / 5, screenHeight, true);
                //保存图片,并把图片裁切成固定大小
//                try {
//                    ImageUtil.saveBitmapFile(saveBitmap, imgOrginalPath);
//                    ImageCutUtil.cut(imgOrginalPath, imgThumbnailPath);
//                } catch (Exception e) {
//                    MyLogUtil.e("Save camera picture error!");
//                }
//
//                imgDisplay.setImageBitmap(saveBitmap);
//                isCaptrueFinish(true);
            }
        });
    }

}
