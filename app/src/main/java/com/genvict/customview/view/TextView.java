package com.genvict.customview.view;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.genvict.customview.R;

/**
 * @author longy
 * @date 2018/11/19
 * Description ${CLASS}
 */

public class TextView extends View {

    private Paint mTextPaint;
    private int mTextSize = 100;
    private int mTextColor = Color.BLACK;

    private String mText;

    /**
     * 构造函数会在代码里面new的时候调用该构造函数
     * TextView tv = new TextView(this)
     *
     * @param context
     */
    public TextView(Context context) {
        this(context, null);
    }

    /**
     * 在布局layout中使用时会调用该构造函数
     *
     * @param context
     * @param attrs
     */
    public TextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 在布局中使用并用了style时会调用该构造函数
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    public TextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义属性
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TextView, defStyleAttr, 0);
        mText = typedArray.getString(R.styleable.TextView_text);
        mTextColor = typedArray.getColor(R.styleable.TextView_textColor, mTextColor);
        mTextSize = typedArray.getInteger(R.styleable.TextView_textSize, mTextSize);
        typedArray.recycle();

        //初始化Paint
        mTextPaint = new Paint();
        //防止边缘锯齿FS
        mTextPaint.setAntiAlias(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // 获取宽高的模式
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            //计算的宽度 与 字体的长度和字体的大小有关   用画笔来测量
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
            width = bounds.width();
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            //计算的宽度 与 字体的长度和字体的大小有关   用画笔来测量
            Rect bounds = new Rect();
            mTextPaint.getTextBounds(mText, 0, mText.length(), bounds);
            height = bounds.height();
        }
        Log.e("onMeasure", "width:" + width);
        Log.e("onMeasure", "height:" + height);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //计算基线
        Paint.FontMetricsInt fontMetrics = mTextPaint.getFontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getHeight() / 2 + dy;

        Log.e("onDraw", "mText:" + mText);
        Log.e("onDraw", "baseLine:" + baseLine);
        canvas.drawText(mText, 0, baseLine, mTextPaint);
    }


}