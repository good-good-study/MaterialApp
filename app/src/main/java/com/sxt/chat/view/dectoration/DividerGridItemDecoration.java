package com.sxt.chat.view.dectoration;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class DividerGridItemDecoration extends DividerItemDecoration {

    public DividerGridItemDecoration(Context context) {
        super(context);
    }

    public void drawHorizontal(Canvas c, RecyclerView parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left;
            final int right;

            int n = (i + 1) % 3 == 0 ? (i + 1) / 3 : (i + 1) / 3 + 1;//当前在第几行
            if (i == (n - 1) * 3) {//最左边的
                left = child.getLeft() - params.leftMargin + 130;
                right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();

            } else if (i == (n - 1) * 3 + 2) {//最右边的
                left = child.getLeft() - params.leftMargin;
                right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth() - 130;

            } else {
                left = child.getLeft() - params.leftMargin;
                right = child.getRight() + params.rightMargin + mDivider.getIntrinsicWidth();
            }

            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    public void drawVertical(Canvas c, RecyclerView parent) {
        final int childCount = parent.getChildCount();
        int span = childCount % 3 == 0 ? childCount / 3 : childCount / 3 + 1;//总行数
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top;
            final int bottom;

            int n = (i + 1) % 3 == 0 ? (i + 1) / 3 : (i + 1) / 3 + 1;//当前在第几行
            if (n == 1) {//第一行
                top = child.getTop() - params.topMargin + 130;
                bottom = child.getBottom() + params.bottomMargin;

            } else if (n == span) {//最后一行
                top = child.getTop() - params.topMargin;
                bottom = child.getBottom() + params.bottomMargin - 130;

            } else {
                top = child.getTop() - params.topMargin;
                bottom = child.getBottom() + params.bottomMargin;
            }


            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}