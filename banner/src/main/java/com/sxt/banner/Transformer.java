package com.sxt.banner;

import androidx.viewpager.widget.ViewPager.PageTransformer;

import com.sxt.banner.transformer.AccordionTransformer;
import com.sxt.banner.transformer.BackgroundToForegroundTransformer;
import com.sxt.banner.transformer.CubeInTransformer;
import com.sxt.banner.transformer.CubeOutTransformer;
import com.sxt.banner.transformer.DefaultTransformer;
import com.sxt.banner.transformer.DepthPageTransformer;
import com.sxt.banner.transformer.FlipHorizontalTransformer;
import com.sxt.banner.transformer.FlipVerticalTransformer;
import com.sxt.banner.transformer.ForegroundToBackgroundTransformer;
import com.sxt.banner.transformer.RotateDownTransformer;
import com.sxt.banner.transformer.RotateUpTransformer;
import com.sxt.banner.transformer.ScaleInOutTransformer;
import com.sxt.banner.transformer.StackTransformer;
import com.sxt.banner.transformer.TabletTransformer;
import com.sxt.banner.transformer.ZoomInTransformer;
import com.sxt.banner.transformer.ZoomOutSlideTransformer;
import com.sxt.banner.transformer.ZoomOutTranformer;

public class Transformer {
    public static Class<? extends PageTransformer> Default = DefaultTransformer.class;
    public static Class<? extends PageTransformer> Accordion = AccordionTransformer.class;
    public static Class<? extends PageTransformer> BackgroundToForeground = BackgroundToForegroundTransformer.class;
    public static Class<? extends PageTransformer> ForegroundToBackground = ForegroundToBackgroundTransformer.class;
    public static Class<? extends PageTransformer> CubeIn = CubeInTransformer.class;
    public static Class<? extends PageTransformer> CubeOut = CubeOutTransformer.class;
    public static Class<? extends PageTransformer> DepthPage = DepthPageTransformer.class;
    public static Class<? extends PageTransformer> FlipHorizontal = FlipHorizontalTransformer.class;
    public static Class<? extends PageTransformer> FlipVertical = FlipVerticalTransformer.class;
    public static Class<? extends PageTransformer> RotateDown = RotateDownTransformer.class;
    public static Class<? extends PageTransformer> RotateUp = RotateUpTransformer.class;
    public static Class<? extends PageTransformer> ScaleInOut = ScaleInOutTransformer.class;
    public static Class<? extends PageTransformer> Stack = StackTransformer.class;
    public static Class<? extends PageTransformer> Tablet = TabletTransformer.class;
    public static Class<? extends PageTransformer> ZoomIn = ZoomInTransformer.class;
    public static Class<? extends PageTransformer> ZoomOut = ZoomOutTranformer.class;
    public static Class<? extends PageTransformer> ZoomOutSlide = ZoomOutSlideTransformer.class;
}
