package com.sxt.chat.utils;

import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

/**
 * Created by xt.sun
 * 2020/5/11
 */
public class SnackBarHelper {

    public static void showSnackBarShort(View view, int message, View.OnClickListener... listeners) {
        if (view == null) {
            throw new IllegalArgumentException("Please provide a view.");
        }
        showSnackBarShort(view, view.getContext().getString(message), listeners);
    }

    public static void showSnackBarShort(View view, CharSequence message, View.OnClickListener... listeners) {
        showSnackBarAnchorShort(view, null, message, listeners);
    }

    public static void showSnackBarAnchorShort(View view, View anchorView, CharSequence message, View.OnClickListener... listeners) {
        if (view == null) {
            throw new IllegalArgumentException("Please provide a view.");
        }
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.BLACK)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        if (anchorView != null) {
            snackbar.setAnchorView(anchorView);
        }
        if (listeners != null && listeners.length > 0) {
            snackbar.setActionTextColor(Color.WHITE)
                    .setAction("撤销", v -> {
                        for (View.OnClickListener listener : listeners) {
                            listener.onClick(v);
                        }
                    });
        }
        snackbar.show();
    }

    public static void showSnackBarShort(View view, int message, int actionText, View.OnClickListener... listeners) {
        if (view == null) {
            throw new IllegalArgumentException("Please provide a view.");
        }
        String pMessage = view.getContext().getString(message);
        String pActionText = view.getContext().getString(actionText);
        showSnackBarShort(view, pMessage, pActionText, listeners);
    }

    public static void showSnackBarShort(View view, CharSequence message, CharSequence actionText, View.OnClickListener... listeners) {
        showSnackBarAnchorShort(view, null, message, actionText, listeners);
    }

    public static void showSnackBarAnchorShort(View view, View anchorView, CharSequence message, CharSequence actionText, View.OnClickListener... listeners) {
        if (view == null) {
            throw new IllegalArgumentException("Please provide a  view.");
        }
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.BLACK)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        if (anchorView != null) {
            snackbar.setAnchorView(anchorView);
        }
        if (listeners != null && listeners.length > 0) {
            snackbar.setActionTextColor(Color.WHITE)
                    .setAction(actionText, v -> {
                        for (View.OnClickListener listener : listeners) {
                            listener.onClick(v);
                        }
                    });
        }
        snackbar.show();
    }

    public static void showSnackBar(View anchor, int message, @BaseTransientBottomBar.Duration int duration, View.OnClickListener... listeners) {
        if (anchor == null) {
            throw new IllegalArgumentException("Please provide a anchor view.");
        }
        showSnackBar(anchor, anchor.getContext().getString(message), duration, listeners);
    }

    public static void showSnackBar(View view, String message, @BaseTransientBottomBar.Duration int duration, View.OnClickListener... listeners) {
        showSnackBarAnchor(view, null, message, duration, listeners);
    }

    public static void showSnackBarAnchor(View view, View anchorView, String message, @BaseTransientBottomBar.Duration int duration, View.OnClickListener... listeners) {
        if (view == null) {
            throw new IllegalArgumentException("Please provide a view.");
        }
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
                .setTextColor(Color.WHITE)
                .setBackgroundTint(Color.BLACK)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE);
        if (anchorView != null) {
            snackbar.setAnchorView(anchorView);
        }
        if (listeners != null && listeners.length > 0) {
            snackbar.setActionTextColor(Color.WHITE)
                    .setAction("撤销", v -> {
                        for (View.OnClickListener listener : listeners) {
                            listener.onClick(v);
                        }
                    });
        }
        snackbar.show();
    }
}