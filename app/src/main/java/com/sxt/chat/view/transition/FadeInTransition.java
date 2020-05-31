package com.sxt.chat.view.transition;

import android.content.Context;
import android.util.AttributeSet;

import androidx.transition.ChangeBounds;
import androidx.transition.Fade;
import androidx.transition.TransitionSet;

/**
 * Created by xt.sun
 * 2020/5/31
 */
public class FadeInTransition extends TransitionSet {
    public FadeInTransition() {
        init();
    }

    public FadeInTransition(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setOrdering(ORDERING_TOGETHER);
        addTransition(new ChangeBounds()).
                addTransition(new Fade(Fade.IN));
    }
}