package com.sxt.chat.utils;

import com.sxt.chat.App;

import java.io.File;

/**
 * Created by xt.sun
 * 2020/5/9
 */
public class KeyPath {
    public static final String KEY_PATH_CROP_IMG = App.getCtx().getExternalCacheDir() + File.separator + "crop_img";
    public static final String KEY_PATH_TAKE_PHOTO_IMG = App.getCtx().getExternalCacheDir() + File.separator + "take_photo_img";
    public static final String KEY_PATH_CAPTURE_IMG = App.getCtx().getExternalCacheDir() + File.separator + "capture_img";
}
