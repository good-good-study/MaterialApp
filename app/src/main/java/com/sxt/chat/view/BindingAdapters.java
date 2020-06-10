package com.sxt.chat.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.view.View;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.sxt.chat.view.transformer.GlideRoundTransformer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xt.sun
 * 2020/5/27
 */
public class BindingAdapters {
    @SuppressLint("CheckResult")
    @BindingAdapter(value = {"srcUrl", "circleCrop", "conner", "placeHolder"}, requireAll = false)
    public static void binImageUrl(ImageView imageView, String url, boolean circleCrop, float conner, Drawable placeHolder) {
        RequestBuilder<Drawable> request = Glide.with(imageView.getContext()).load(url);
        if (circleCrop) request.circleCrop();
        if (placeHolder != null) request.placeholder(placeHolder);
        if (conner > 0) {
            GlideRoundTransformer roundTransformer = new GlideRoundTransformer(imageView.getContext(), conner);
            List<BitmapTransformation> list = new ArrayList<>();
            list.add(new CenterCrop());
            list.add(roundTransformer);
            request.apply(RequestOptions.bitmapTransform(new MultiTransformation<>(list)));
        }
        request.into(imageView);
    }

    @BindingAdapter("goneIf")
    public static void binGoneIf(View view, boolean gone) {
        view.setVisibility(gone ? View.GONE : View.VISIBLE);
    }
}