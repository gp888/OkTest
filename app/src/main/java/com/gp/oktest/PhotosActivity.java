package com.gp.oktest;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Method;

/**
 * Created by guoping on 2017/12/19.
 */

public class PhotosActivity extends AppCompatActivity {

    ImageView photos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_photos);

        findViewById(R.id.openCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeviceUtils.openCamera(PhotosActivity.this);
            }
        });

        photos = findViewById(R.id.photos);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
        }
        compress();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case Constant.CHOICE_CMARE:
                    Bitmap bit = DeviceUtils.rotateBitmap(Constant.PHOTOFILEPATH, DeviceUtils.readPictureDegree(Constant.PHOTOFILEPATH));
                    photos.setImageBitmap(bit);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 图片质量压缩
     * 可以看到，图片的大小是没有变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的，
     * 这也是为什么该方法叫质量压缩方法。那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的。
     * 但是我们看到bytes.length是随着quality变小而变小的。这样适合去传递二进制的图片数据，比如微信分享图片，要传入二进制数据过去，限制32kb之内。
     * 这里要说，如果是bit.compress(CompressFormat.PNG, quality, baos);这样的png格式，quality就没有作用了，bytes.length不会变化，因为png图片是无损的，不能进行压缩。
     *
     * 当inJustDecodeBounds设置为true的时候，BitmapFactory通过decodeResource或者decodeFile解码图片时，将会返回空(null)的Bitmap对象，
     * 这样可以避免Bitmap的内存分配，但是它可以返回Bitmap的宽度、高度以及MimeType。
     */
    private void compress () {
        Bitmap bit = BitmapFactory.decodeFile("/storage/emulated/0/okTest/temp/jp.jpg");
        if (bit == null) {
            Toast.makeText(this, "jp.jpg", Toast.LENGTH_LONG).show();
            return;
        }
        Log.i("wechat", "压缩前图片的大小" + (bit.getByteCount() / 1024 / 1024) + "M宽度为" + bit.getWidth() + "高度为" + bit.getHeight());

        //qualitycompress
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int quality = Integer.valueOf("90");
        bit.compress(Bitmap.CompressFormat.JPEG, quality, baos);
        byte[] bytes = baos.toByteArray();
        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024) + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight()
                + "bytes.length=  " + (bytes.length / 1024) + "KB" + "quality=" + quality);

        //samplesizecompress
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        Bitmap bitmap = BitmapFactory.decodeFile("/storage/emulated/0/okTest/temp/jp.jpg", options);
        Log.i("wechat", "压缩后图片的大小" + (bitmap.getByteCount() / 1024 / 1024) + "M宽度为" + bitmap.getWidth() + "高度为" + bitmap.getHeight());

        //缩放compress
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        bm = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(), bit.getHeight(), matrix, true);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024)
                + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());

        //565
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inPreferredConfig = Bitmap.Config.RGB_565;
        bm = BitmapFactory.decodeFile("/storage/emulated/0/okTest/temp/jp.jpg", options2);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024 / 1024) + "M宽度为" + bm.getWidth() + "高度为" + bm.getHeight());

        //固定长宽
        bm = Bitmap.createScaledBitmap(bit, 150, 150, true);
        Log.i("wechat", "压缩后图片的大小" + (bm.getByteCount() / 1024) + "KB宽度为"
                + bm.getWidth() + "高度为" + bm.getHeight());
    }
}
