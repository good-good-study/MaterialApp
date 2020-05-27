package com.sxt.chat.view;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import com.bumptech.glide.RequestBuilder;

/**
 * Created by xt.sun
 * 2020/5/27
 */
public class BindingAdapters {
    @BindingAdapter(value = {"srcUrl", "circleCrop", "placeHolder"}, requireAll = false)
    public static void binImageUrl(ImageView imageView, String url, boolean circleCrop, Drawable placeHolder) {
        RequestBuilder<Drawable> request = Glide.with(imageView.getContext()).load(url);
        if (circleCrop) request.circleCrop();
        if (placeHolder != null) request.placeholder(placeHolder);
        request.into(imageView);
    }

    @BindingAdapter("goneIf")
    public static void binGoneIf(View view, boolean gone) {
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }
}