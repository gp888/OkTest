package com.gp.oktest.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

import jp.wasabeef.glide.transformations.internal.FastBlur;

/**
 * 高斯模糊类，在glide升级到4.7后如果没将glide-transformations升级就自己写一个
 */

public class BlurTransformation extends BitmapTransformation {


    private static final int VERSION = 1;

    private static final String ID = "BlurTransformation." + VERSION;


    private static int MAX_RADIUS = 25;

    private static int DEFAULT_DOWN_SAMPLING = 1;


    private int radius;

    private int sampling;


    public BlurTransformation() {

        this(MAX_RADIUS, DEFAULT_DOWN_SAMPLING);

    }


    public BlurTransformation(int radius) {

        this(radius, DEFAULT_DOWN_SAMPLING);

    }


    public BlurTransformation(int radius, int sampling) {

        this.radius = radius;

        this.sampling = sampling;

    }


    @Override

    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {

        int width = toTransform.getWidth();

        int height = toTransform.getHeight();

        int scaledWidth = width / sampling;

        int scaledHeight = height / sampling;


        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);


        Canvas canvas = new Canvas(bitmap);

        canvas.scale(1 / (float) sampling, 1 / (float) sampling);

        Paint paint = new Paint();

        paint.setFlags(Paint.FILTER_BITMAP_FLAG);

        canvas.drawBitmap(toTransform, 0, 0, paint);

        bitmap = FastBlur.blur(bitmap, radius, true);


        return bitmap;

    }


    @Override

    public String toString() {

        return "BlurTransformation(radius=" + radius + ", sampling=" + sampling + ")";

    }


    @Override

    public boolean equals(Object o) {

        return o instanceof BlurTransformation &&

                ((BlurTransformation) o).radius == radius &&

                ((BlurTransformation) o).sampling == sampling;

    }


    @Override

    public int hashCode() {

        return ID.hashCode() + radius * 1000 + sampling * 10;

    }


    @Override

    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

        try {

            messageDigest.update((ID + radius + sampling).getBytes("utf-8"));

        } catch (UnsupportedEncodingException e) {

            e.printStackTrace();

        }

    }

}