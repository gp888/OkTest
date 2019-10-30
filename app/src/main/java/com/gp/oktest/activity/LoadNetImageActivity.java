package com.gp.oktest.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.gp.oktest.R;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by guoping on 2017/11/15.
 */

public class LoadNetImageActivity extends AppCompatActivity {

    ImageView iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loadnetimage);
        ImageView image = findViewById(R.id.imageview);
        iv2 = findViewById(R.id.imageview2);
        ImageView iv3 = findViewById(R.id.imageview3);
        ImageView iv4 = findViewById(R.id.imageview4);
        TextView tv1 = findViewById(R.id.tv1);

        new DownloadImageThread("http://img.hb.aicdn.com/54903d0bfae5c7ce7210144e52b9588e4bfaf793120977-R2jqbe_fw658", image).start();
        iv2.setImageBitmap(getDropShadow());
        iv3.setImageBitmap(getRoundedBitmap());
        iv4.setImageBitmap(getGrayBitmap());
        setTextColor(tv1);
    }


    class DownloadImageThread extends Thread {
        private String url;
        private ImageView imageView;

        public DownloadImageThread(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {
                Response response = client.newCall(request).execute();
                if (response.isSuccessful()) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 对图的非透明区域着色,并模糊
     *
     * @return
     */
    private Bitmap getDropShadow() {
        float radius = 15;
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_chat_face1);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(getResources().getColor(R.color.colorAccent));

        Bitmap dest = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //包含原始位图的Alpha通道的新位图
        Bitmap alphaBitmap = bitmap.extractAlpha();
        Canvas canvas = new Canvas(dest);
        canvas.drawBitmap(alphaBitmap, 0, 0, paint);

        //模糊
        BlurMaskFilter filter = new BlurMaskFilter(radius, BlurMaskFilter.Blur.OUTER);
        paint.setMaskFilter(filter);
        canvas.drawBitmap(alphaBitmap, 0, 0, paint);
        return dest;
    }

    /**
     * 图片圆角。
     *
     * @return
     */
    public Bitmap getRoundedBitmap() {
        //设置圆角半径为20
        float roundPx = 30;
        Paint mPaint = new Paint();
        mPaint.setAntiAlias(true);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.www555);

        //创建新的位图
        Bitmap bgBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        //把创建的位图作为画板
        Canvas mCanvas = new Canvas(bgBitmap);

        Rect mRect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
        RectF mRectF = new RectF(mRect);

        //先绘制圆角矩形
        mCanvas.drawRoundRect(mRectF, roundPx, roundPx, mPaint);

        //设置图像的叠加模式
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //绘制图像
        mCanvas.drawBitmap(mBitmap, mRect, mRect, mPaint);

        return bgBitmap;
    }

    //图片灰化处理
    public Bitmap getGrayBitmap() {
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.tt3366);
        Bitmap mGrayBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(mGrayBitmap);
        Paint mPaint = new Paint();

        //创建颜色变换矩阵
        ColorMatrix mColorMatrix = new ColorMatrix();
        //设置灰度影响范围
        mColorMatrix.setSaturation(0);
        //创建颜色过滤矩阵
        ColorMatrixColorFilter mColorFilter = new ColorMatrixColorFilter(mColorMatrix);
        //设置画笔的颜色过滤矩阵
        mPaint.setColorFilter(mColorFilter);
        //使用处理后的画笔绘制图像
        mCanvas.drawBitmap(mBitmap, 0, 0, mPaint);

        return mGrayBitmap;
    }

    //提取图像Alpha位图
    public Bitmap getAlphaBitmap() {
        BitmapDrawable mBitmapDrawable = (BitmapDrawable) getResources().getDrawable(R.drawable.ic_chat_face1);
        Bitmap mBitmap = mBitmapDrawable.getBitmap();

        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(Color.BLUE);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }

    private void setTextColor (final TextView tv) {
        int colorStart = ContextCompat.getColor(this, R.color.colorPrimary);
        int colorEnd = ContextCompat.getColor(this, R.color.colorAccent);
        ValueAnimator valueAnimator = ValueAnimator
                .ofObject(new ArgbEvaluator(), colorStart, colorEnd)
                .setDuration(3000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                tv.setTextColor((Integer) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

}

