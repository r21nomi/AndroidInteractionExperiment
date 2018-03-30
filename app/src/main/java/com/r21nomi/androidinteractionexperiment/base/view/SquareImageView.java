package com.r21nomi.androidinteractionexperiment.base.view;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Created by Ryota Niinomi on 2016/11/19.
 */
public class SquareImageView extends AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        if (widthMeasureSpec > heightMeasureSpec) {
//            super.onMeasure(heightMeasureSpec, heightMeasureSpec);
//        } else {
//            super.onMeasure(widthMeasureSpec, widthMeasureSpec);
//        }
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}