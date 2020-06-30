package com.sxt.chat.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import androidx.core.view.ViewCompat;
import androidx.core.view.ViewPropertyAnimatorListener;

import com.sxt.chat.R;

import cn.bmob.v3.util.V;

import static android.view.View.GONE;

/**
 * @author Miguel Catalan Bañuls
 */
public class AnimationUtil {
    public static final String TAG = "AnimationUtil";
    public static int ANIMATION_DURATION_SHORT = 200;
    public static int ANIMATION_DURATION_MEDIUM = 500;
    public static int ANIMATION_DURATION_LONG = 800;

    public static final String SCALE_X = "scaleX";
    public static final String SCALE_Y = "scaleY";
    public static final String TRANSLATION_X = "translationX";
    public static final String TRANSLATION_Y = "translationY";
    public static final String ROTATION_X = "rotationX";
    public static final String ROTATION_Y = "rotationY";
    public static final String ALPHA = "alpha";

    public interface AnimationListener {
        /**
         * @return true to override parent. Else execute Parent method
         */
        boolean onAnimationStart(View view);

        boolean onAnimationEnd(View view);

        boolean onAnimationCancel(View view);
    }

    /**
     * 旋转动画
     */
    public static void rotationInput(View target, float startAngle, float endAngle, boolean isHidden) {
        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, "rotation", startAngle, endAngle);
        rotation.setDuration(ANIMATION_DURATION_SHORT);
        if (!isHidden) target.setVisibility(View.VISIBLE);
        if (isHidden) rotation.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                target.setVisibility(GONE);
            }
        });
        rotation.start();
    }

    /**
     * 旋转动画
     */
    public static void rotation(View target, float startAngle, float endAngle, long... durations) {
        long duration = durations == null || durations.length == 0 ? ANIMATION_DURATION_SHORT : durations[0];
        ObjectAnimator rotation = ObjectAnimator.ofFloat(target, "rotation", startAngle, endAngle);
        rotation.setDuration(duration);
        rotation.start();
    }

    /**
     * Y轴执行位移动画
     */
    public static void translationY(View target, int startTranslationY, int endTranslationY, long duration) {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(target, TRANSLATION_Y, startTranslationY, endTranslationY).setDuration(duration);
        translationAnimator.setInterpolator(new LinearInterpolator());
        translationAnimator.start();
    }

    /**
     * Y轴执行位移,透明渐变动画
     */
    private void translationYAlpha(View targetView, int startTranslationY, int endTranslationY, int startAlpha, int endAlpha, long duration) {
        ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(targetView, TRANSLATION_Y, startTranslationY, endTranslationY).setDuration(200);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(targetView, ALPHA, startAlpha, endAlpha);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.playTogether(translationAnimator, alphaAnimator);
        animatorSet.start();
    }

    /**
     * 缩放动画
     */
    public static void scale(View target, float startScale, float endScale, long duration, ValueAnimator.AnimatorUpdateListener... listeners) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, SCALE_X, startScale, endScale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, SCALE_Y, startScale, endScale);
        if (listeners != null) {
            for (ValueAnimator.AnimatorUpdateListener listener : listeners) {
                scaleX.addUpdateListener(listener);
                scaleY.addUpdateListener(listener);
            }
        }
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(duration);
        set.start();
    }
}