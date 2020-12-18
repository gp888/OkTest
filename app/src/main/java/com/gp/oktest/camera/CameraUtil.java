package com.gp.oktest.camera;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.hardware.Camera;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 相机相关工具类
 */
public class CameraUtil {

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
     * @param th
     * @param scale
     * @return
     */
    public Camera.Size getPreviewSize(List<Camera.Size> list, int th, float scale){
        Collections.sort(list, sizeComparator);

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

    public Camera.Size getPictureSize(List<Camera.Size> list, int th, float scale){
        Collections.sort(list, sizeComparator);

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

    public class CameraSizeComparator implements Comparator<Camera.Size> {
        //按升序排列
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            if(lhs.width == rhs.width){
                return 0;
            } else if(lhs.width > rhs.width){
                return 1;
            } else{
                return -1;
            }
        }
    }

    public static float getScreenScale(Context context){
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        return (float) (display.getHeight()) / (float) (display.getWidth());
    }



    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth
     *            需要被进行对比的原宽
     * @param surfaceHeight
     *            需要被进行对比的原高
     * @param preSizeList
     *            需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    public static Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight, List<Camera.Size> preSizeList) {

        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
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

        return retSize;
    }
}
