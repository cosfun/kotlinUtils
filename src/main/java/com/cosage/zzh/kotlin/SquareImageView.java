package com.cosage.zzh.kotlin;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;


/**
 * Created by Zhengzhihui on 2017/3/31.
 */

public class SquareImageView extends AppCompatImageView {
    float rate=1;
    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SquareImageView);
        rate= ta.getFloat(R.styleable.SquareImageView_rate,1.0f);
        ta.recycle();  //注意回收

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), (int) (getDefaultSize(0, widthMeasureSpec)*rate));
    }
}
