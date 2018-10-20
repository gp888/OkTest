package com.gp.oktest.camera;

import android.app.Application;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;

import static com.gp.oktest.App.globalContext;

public class CameraApi {

    //有多少个摄像头
    int numberOfCameras;
    int faceBackCameraId;
    int faceFrontCameraId;
    int faceBackCameraOrientation;
    int faceFrontCameraOrientation;
    Camera camera;
    Context ctx;

    public CameraApi(Context ctx) {
        this.ctx = ctx;
    }

    private void init() {
        numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; ++i) {
            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();

            Camera.getCameraInfo(i, cameraInfo);
            //后置摄像头
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                faceBackCameraId = i;
                faceBackCameraOrientation = cameraInfo.orientation;
            }
            //前置摄像头
            else if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                faceFrontCameraId = i;
                faceFrontCameraOrientation = cameraInfo.orientation;
            }
        }
    }

    private void setAutoFocus(Camera camera, Camera.Parameters parameters){
        String mode = parameters.getFocusMode();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
    }

    private void setFlashMode(String mode){

    }

    private void open(int cameraId) {
        camera = Camera.open(cameraId);
        //获取相机参数
        camera.getParameters();

//        闪光灯配置参数，可以通过Parameters.getFlashMode()接口获取。
//
//        Camera.Parameters.FLASH_MODE_AUTO 自动模式，当光线较暗时自动打开闪光灯；
//        Camera.Parameters.FLASH_MODE_OFF 关闭闪光灯；
//        Camera.Parameters.FLASH_MODE_ON 拍照时闪光灯；
//        Camera.Parameters.FLASH_MODE_RED_EYE 闪光灯参数，防红眼模式。
//
//        对焦模式配置参数，可以通过Parameters.getFocusMode()接口获取。
//
//        Camera.Parameters.FOCUS_MODE_AUTO 自动对焦模式，摄影小白专用模式；
//        Camera.Parameters.FOCUS_MODE_FIXED 固定焦距模式，拍摄老司机模式；
//        Camera.Parameters.FOCUS_MODE_EDOF 景深模式，文艺女青年最喜欢的模式；
//        Camera.Parameters.FOCUS_MODE_INFINITY 远景模式，拍风景大场面的模式；
//        Camera.Parameters.FOCUS_MODE_MACRO 微焦模式，拍摄小花小草小蚂蚁专用模式；
//
//        场景模式配置参数，可以通过Parameters.getSceneMode()接口获取。
//
//        Camera.Parameters.SCENE_MODE_BARCODE 扫描条码场景，NextQRCode项目会判断并设置为这个场景；
//        Camera.Parameters.SCENE_MODE_ACTION 动作场景，就是抓拍跑得飞快的运动员、汽车等场景用的；
//        Camera.Parameters.SCENE_MODE_AUTO 自动选择场景；
//        Camera.Parameters.SCENE_MODE_HDR 高动态对比度场景，通常用于拍摄晚霞等明暗分明的照片；
//        Camera.Parameters.SCENE_MODE_NIGHT 夜间场景；



        //设置相机参数
        camera.getParameters();
    }

    private void close() {
        camera.release();
    }

    private void startPreview(SurfaceHolder surfaceHolder) {
//        try {
//            final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
//            Camera.getCameraInfo(faceBackCameraId, cameraInfo);
//            int cameraRotationOffset = cameraInfo.orientation;
//
//            //获取相机参数
//            final Camera.Parameters parameters = camera.getParameters();
//            //设置对焦模式
//            setAutoFocus(camera, parameters);
//            //设置闪光模式
//            setFlashMode(parameters.getFlashMode());
//
//            if (mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_PHOTO
//                    || mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_UNSPECIFIED)
//                turnPhotoCameraFeaturesOn(camera, parameters);
//            else if (mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_PHOTO)
//                turnVideoCameraFeaturesOn(camera, parameters);
//
//            final int rotation = ((WindowManager) globalContext.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
//            int degrees = 0;
//            switch (rotation) {
//                case Surface.ROTATION_0:
//                    degrees = 0;
//                    break; // Natural orientation
//                case Surface.ROTATION_90:
//                    degrees = 90;
//                    break; // Landscape left
//                case Surface.ROTATION_180:
//                    degrees = 180;
//                    break;// Upside down
//                case Surface.ROTATION_270:
//                    degrees = 270;
//                    break;// Landscape right
//            }
//
//            //根据前置与后置摄像头的不同，设置预览方向，否则会发生预览图像倒过来的情况。
//            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//                displayRotation = (cameraRotationOffset + degrees) % 360;
//                displayRotation = (360 - displayRotation) % 360; // compensate
//            } else {
//                displayRotation = (cameraRotationOffset - degrees + 360) % 360;
//            }
//            this.camera.setDisplayOrientation(displayRotation);
//
//            if (Build.VERSION.SDK_INT > 13
//                    && (mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_VIDEO
//                    || mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_UNSPECIFIED)) {
////                parameters.setRecordingHint(true);
//            }
//
//            if (Build.VERSION.SDK_INT > 14
//                    && parameters.isVideoStabilizationSupported()
//                    && (mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_VIDEO
//                    || mCameraConfigProvider.getMediaAction() == CameraConfig.MEDIA_ACTION_UNSPECIFIED)) {
//                parameters.setVideoStabilization(true);
//            }
//
//            //设置预览大小
//            parameters.setPreviewSize(previewSize.getWidth(), previewSize.getHeight());
//            parameters.setPictureSize(photoSize.getWidth(), photoSize.getHeight());
//
//            //设置相机参数
//            camera.setParameters(parameters);
//            //设置surfaceHolder
//            camera.setPreviewDisplay(surfaceHolder);
//            //开启预览
//            camera.startPreview();
//
//        } catch (IOException error) {
//            Log.d(TAG, "Error setting camera preview: " + error.getMessage());
//        } catch (Exception ignore) {
//            Log.d(TAG, "Error starting camera preview: " + ignore.getMessage());
//        }
    }

    private void stopPreview() {
        camera.stopPreview();
    }

    private void capture() {
        camera.takePicture(null, null, new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] bytes, Camera camera) {
                //存储返回的图像数据
//                final File pictureFile = outputPath;
//                if (pictureFile == null) {
//                    Log.d(TAG, "Error creating media file, check storage permissions.");
//                    return;
//                }
//                try {
//                    FileOutputStream fileOutputStream = new FileOutputStream(pictureFile);
//                    fileOutputStream.write(bytes);
//                    fileOutputStream.close();
//                } catch (FileNotFoundException error) {
//                    Log.e(TAG, "File not found: " + error.getMessage());
//                } catch (IOException error) {
//                    Log.e(TAG, "Error accessing file: " + error.getMessage());
//                } catch (Throwable error) {
//                    Log.e(TAG, "Error saving file: " + error.getMessage());
//                }
            }
        });

    }

}
