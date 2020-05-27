package com.sxt.chat.view;

import android.content.Context;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.google.android.material.expandable.ExpandableWidget;
import com.google.android.material.expandable.ExpandableWidgetHelper;

/**
 * Created by xt.sun
 * 2020/5/16
 */
public class ExpandTextView extends AppCompatTextView implements ExpandableWidget {

    private ExpandableWidgetHelper helper;

    public ExpandTextView(Context context) {
        this(context, null);
    }

    public ExpandTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExpandTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        helper = new ExpandableWidgetHelper(this);
    }

    @Override
    public boolean isExpanded() {
        return helper.isExpanded();
    }

    @Override
    public boolean setExpanded(boolean expanded) {
        return helper.setExpanded(expanded);
    }
}