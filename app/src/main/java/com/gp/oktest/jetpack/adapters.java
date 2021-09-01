package com.gp.oktest.jetpack;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class adapters {

    @BindingAdapter({"app:imageUrl", "app:placeHolder"})
    public static void loadImageFromUri(ImageView imageView, String imageUri, Drawable placeHolder){
        Glide.with(imageView.getContext())
                .load(imageUri)
                .placeholder(placeHolder)
                .into(imageView);
    }
}
