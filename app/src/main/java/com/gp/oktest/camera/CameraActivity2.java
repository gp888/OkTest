package com.gp.oktest.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.gp.oktest.R;
import com.gp.oktest.base.BaseActivity;

import java.io.IOException;

public class CameraActivity2 extends BaseActivity {

    private Camera camera;
    private boolean isPreview = false;
    private int screenWidth;
    private int screenHeight;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        ctx = this;

        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;

        SurfaceView mSurfaceView = findViewById(R.id.surface_view);
        SurfaceHolder mSurfaceHolder = mSurfaceView.getHolder();

        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setKeepScreenOn(true);
        mSurfaceHolder.setFixedSize(screenWidth, screenHeight);

        // 设置 Surface 类型
        // 参数：
        //        SURFACE_TYPE_NORMAL       : 用 RAM 缓存原生数据的普通 Surface
        //        SURFACE_TYPE_HARDWARE     : 适用于 DMA(Direct memory access )引擎和硬件加速的Surface
        //        SURFACE_TYPE_GPU          : 适用于 GPU 加速的 Surface
        //        SURFACE_TYPE_PUSH_BUFFERS ：表明该 Surface 不包含原生数据，Surface用到的数据由其他对象提供
        // 在 Camera 图像预览中就使用 SURFACE_TYPE_PUSH_BUFFERS 类型的 Surface，有 Camera 负责提供给预览 Surface 数据，这样图像预览会比较流
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mSurfaceHolder.addCallback(mSurfaceCallback);
    }

    private SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {

        /**
         *  在 Surface 首次创建时被立即调用：获得焦点时。一般在这里开启画图的线程
         * @param surfaceHolder 持有当前 Surface 的 SurfaceHolder 对象
         */
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                camera = Camera.open(0);
                //此处也可以设置摄像头参数
                /**
                 WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
                 Display display  = wm.getDefaultDisplay();//得到当前屏幕
                 Camera.Parameters parameters = camera.getParameters();//得到摄像头的参数
                 parameters.setPictureFormat(PixelFormat.RGB_888);//设置照片的格式
                 parameters.setJpegQuality(85);//设置照片的质量
                 parameters.setPictureSize(display.getHeight(), display.getWidth());//设置照片的大小，默认是和     屏幕一样大
                 camera.setParameters(parameters);
                 **/
                //设置角度，此处 CameraId 我默认 为 0 （后置）
                // CameraId 也可以 通过 参考 Camera.open() 源码 方法获取
                setCameraDisplayOrientation(CameraActivity2.this,0,camera);
                camera.setPreviewDisplay(surfaceHolder);//通过SurfaceView显示取景画面
                camera.startPreview();//开始预览
                isPreview = true;//设置是否预览参数为真
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        /**
         *  在 Surface 格式 和 大小发生变化时会立即调用，可以在这个方法中更新 Surface
         * @param surfaceHolder   持有当前 Surface 的 SurfaceHolder 对象
         * @param format          surface 的新格式
         * @param width           surface 的新宽度
         * @param height          surface 的新高度
         */
        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

        }

        /**
         *  在 Surface 被销毁时立即调用：失去焦点时。一般在这里将画图的线程停止销毁
         * @param surfaceHolder 持有当前 Surface 的 SurfaceHolder 对象
         */
        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if(camera != null){
                if(isPreview){//正在预览
                    camera.stopPreview();
                    camera.release();
                }
            }
        }
    };

    /**
     * 设置 摄像头的角度
     *
     * @param activity 上下文
     * @param cameraId 摄像头ID（假如手机有N个摄像头，cameraId 的值 就是 0 ~ N-1）
     * @param camera   摄像头对象
     */
    public static void setCameraDisplayOrientation(Activity activity,
                                                   int cameraId, Camera camera) {

        Camera.CameraInfo info = new Camera.CameraInfo();
        //获取摄像头信息
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
        //获取摄像头当前的角度
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            //前置摄像头
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360; // compensate the mirror
        } else {
            // back-facing  后置摄像头
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
    }

}
