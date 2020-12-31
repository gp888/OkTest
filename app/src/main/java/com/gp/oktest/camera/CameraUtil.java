package com.gp.oktest.camera;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import androidx.appcompat.app.AppCompatActivity;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Size;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.gp.oktest.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 相机相关工具类
 */
public class CameraUtil {

    private static final long PREVIEW_SIZE_MIN = 720 * 480;

    private CameraSizeComparator sizeComparator = new CameraSizeComparator();

    private static CameraUtil myCamPara = null;

    private CameraUtil() {

    }

    public static CameraUtil getInstance() {
        if (myCamPara == null) {
            myCamPara = new CameraUtil();
            return myCamPara;
        } else {
            return myCamPara;
        }
    }

    public Bitmap setTakePicktrueOrientation(int id, Bitmap bitmap) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(id, info);
        bitmap = rotaingImageView(id, info.orientation, bitmap);
        return bitmap;
    }

    /**
     * 把相机拍照返回照片转正
     *
     * @param angle 旋转角度
     * @return bitmap 图片
     */
    public Bitmap rotaingImageView(int id, int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //加入翻转 把相机拍照返回照片转正
        if (id == 1) {
            matrix.postScale(-1, 1);
        }
        // 创建新的图片
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    /**
     * 保证预览方向正确
     *
     * @param activity
     * @param cameraId
     * @param camera
     */
    public static void setCameraDisplayOrientation(Activity activity, int cameraId, Camera camera) {
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId, info);
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();
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
            default:
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % 360;
            result = (360 - result) % 360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + 360) % 360;
        }
        camera.setDisplayOrientation(result);
        Log.d("CameraUtil", "displayOrientation===:" + result);
    }

    /**
     * 获取所有支持的返回视频尺寸
     *
     * @param list
     * @param minHeight
     * @return
     */
    public Camera.Size getPropSizeForHeight(List<Camera.Size> list, int minHeight, int minWidth) {
        Collections.sort(list, new CameraAscendSizeComparatorForHeight());

        int i = 0;
        for (Camera.Size s : list) {
            if ((s.height >= minHeight) && (s.width >= minWidth)) {
                break;
            }
            i++;
        }
        if (i == list.size()) {
            //如果没找到，就选最小的size
            i = 0;
        }
        return list.get(i);
    }

    //升序 按照高度
    public class CameraAscendSizeComparatorForHeight implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if (lhs.height == rhs.height) {
                return 0;
            } else if (lhs.height > rhs.height) {
                return 1;
            } else {
                return -1;
            }
        }
    }


    /**
     * 支持的分辨率列表list，最小宽度，屏幕比例。返回合适的camera分辨率
     * @param list
     * @return
     */
    public Camera.Size getPreviewSize(List<Camera.Size> list){
        Collections.sort(list, sizeComparator);

        float scale = getScreenScale();
        DisplayMetrics dm = App.Companion.getGlobalContext().getResources().getDisplayMetrics();
        int th = dm.widthPixels;
        int i = 0;
        for(Camera.Size s : list){
            if((s.width > th) && equalRate(s, scale)){
                Log.i("CameraUtil", "最终设置预览尺寸:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }
        return list.get(i);
    }

    public Camera.Size getPictureSize(List<Camera.Size> list){
        Collections.sort(list, sizeComparator);

        float scale = getScreenScale();
        DisplayMetrics dm = App.Companion.getGlobalContext().getResources().getDisplayMetrics();
        int th = dm.widthPixels;

        int i = 0;
        for(Camera.Size s : list){
            if((s.width > th) && equalRate(s, scale)){
                Log.i("CameraUtil", "最终设置图片尺寸:w = " + s.width + "h = " + s.height);
                break;
            }
            i++;
        }

        return list.get(i);
    }

    public boolean equalRate(Camera.Size s, float rate){
        float r = (float)(s.width)/(float)(s.height);
        if(Math.abs(r - rate) <= 0.2) {
            return true;
        } else{
            return false;
        }
    }

    public static class CameraSizeComparator implements Comparator<Camera.Size> {
        //按升序排列
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if(lhs.width == rhs.width){
                return 0;
            } else if(lhs.width > rhs.width){
                return 1;
            } else{
                return -1;
            }

//            return Long.signum((long) lhs.width * lhs.height -
//                    (long) rhs.width * rhs.height);
        }
    }

    public static float getScreenScale(){
        DisplayMetrics dm = App.Companion.getGlobalContext().getResources().getDisplayMetrics();
        Log.i("CameraUtil", "屏幕宽高:w = " + dm.widthPixels + ",h = " + dm.heightPixels);
        return (float) (dm.heightPixels) / (float) dm.widthPixels;
    }


    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     * @param preSizeList
     *            需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(List<Camera.Size> preSizeList) {

        DisplayMetrics dm = App.Companion.getGlobalContext().getResources().getDisplayMetrics();
        //dm.widthPixels = 1080h,   dm.heightPixels = 2030
        int surfaceHeight = dm.heightPixels;
        int surfaceWidth = dm.widthPixels;

        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        if(surfaceWidth < surfaceHeight) {
            surfaceHeight = dm.widthPixels;
            surfaceWidth = dm.heightPixels;
        }

        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        //size.width = 1280h, size.height = 720
        for(Camera.Size size : preSizeList){
            if((size.width == surfaceWidth) && (size.height == surfaceHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) surfaceWidth) / surfaceHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }
        Log.d("CameraUtil", "屏幕分辨率：w:" + dm.heightPixels + ",h:" + dm.widthPixels);
        Log.d("CameraUtil", "预览分辨率：w:" + retSize.width + ",h:" + retSize.height);
        return retSize;
    }

    /**
     * 相机输出尺寸默认是横向的（宽>高），手机窗口一般是竖向的（不考虑旋转横置的情况），所以比较时将输出尺寸的 宽高比 与 预览窗口的 高宽比 进行比较。
     * 录制视频的时候，为了预览和播放效果好（充满窗口），可以选择宽高比与预览窗口高宽比一致的输出尺寸。
     * 如果没有宽高比一致的输出尺寸，则可以选择高宽比接近的。
     * 但是如果上面选择的尺寸过小，则预览图像画质比较模糊，所以可以设定一个阈值，如果不满足阈值，则退而求其次选择和预览窗口面积最接近的输出尺寸。
     * @param preSizeList
     * @return
     */
    public static Camera.Size updateCameraPreview(List<Camera.Size> preSizeList){

        DisplayMetrics dm = App.Companion.getGlobalContext().getResources().getDisplayMetrics();
        //dm.widthPixels = 1080h,   dm.heightPixels = 2030
        int surfaceHeight = dm.heightPixels;
        int surfaceWidth = dm.widthPixels;
        Log.d("CameraUtil", "1屏幕分辨率：w:" + dm.heightPixels + ",h:" + dm.widthPixels);

        List<Camera.Size> sizes = new ArrayList<>();
        //计算预览窗口高宽比，高宽比，高宽比
        float ratio = ((float) surfaceHeight / surfaceWidth);

        //首先选取宽高比与预览窗口高宽比一致且最大的输出尺寸
        for (int i = 0; i < preSizeList.size(); i++){
            if (((float)preSizeList.get(i).width) / preSizeList.get(i).height == ratio){
                sizes.add(preSizeList.get(i));
            }
        }

        Camera.Size previewSize = null;//预览尺寸
        if (sizes.size() > 0){
            previewSize = Collections.max(sizes, new CameraSizeComparator());
            Log.d("CameraUtil", "1预览分辨率：w:" + previewSize.width + ",h:" + previewSize.height);
            return previewSize;
        }


        //如果不存在宽高比与预览窗口高宽比一致的输出尺寸，则选择与其宽高比最接近的输出尺寸
        sizes.clear();
        float detRatioMin = Float.MAX_VALUE;
        for (int i = 0; i < preSizeList.size(); i++){
            Camera.Size size = preSizeList.get(i);
            float curRatio = ((float)size.width) / size.height;
            if (Math.abs(curRatio - ratio) < detRatioMin){
                detRatioMin = curRatio;
                previewSize = size;
            }
        }

        if (previewSize.width * previewSize.height > PREVIEW_SIZE_MIN) {
            Log.d("CameraUtil", "2预览分辨率：w:" + previewSize.width + ",h:" + previewSize.height);
           return previewSize;
        }

        //如果宽高比最接近的输出尺寸太小，则选择与预览窗口面积最接近的输出尺寸
        long area = surfaceWidth * surfaceHeight;
        long detAreaMin = Long.MAX_VALUE;
        for (int i = 0; i < preSizeList.size(); i++){
            Camera.Size size = preSizeList.get(i);
            long curArea = size.width * size.height;
            if (Math.abs(curArea - area) < detAreaMin){
                detAreaMin = curArea;
                previewSize = size;
            }
        }
        Log.d("CameraUtil", "3预览分辨率：w:" + previewSize.width + ",h:" + previewSize.height);
        return previewSize;
    }
}
