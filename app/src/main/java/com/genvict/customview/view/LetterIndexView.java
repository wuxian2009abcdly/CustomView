package com.genvict.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.genvict.customview.utils.ViewUtils;

/**
 * @author longy
 * @date 2018/12/4
 * Description ${CLASS}
 */

public class LetterIndexView extends View {

    private String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    /**
     * view高度
     */
    private int mHeight;

    private Paint mPaint;
    private int mTextSize = 20;

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(ViewUtils.sp2px(context, mTextSize));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        Rect rect = new Rect();
        mPaint.getTextBounds("M", 0, "M".length(), rect);
        int width = rect.width() + getPaddingStart() + getPaddingEnd();
        mHeight = MeasureSpec.getSize(heightMeasureSpec) - getPaddingTop() - getPaddingBottom();
        setMeasuredDimension(width, mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", "onDraw");
        //计算每个字母的高度
        int letterHeight = mHeight / mLetters.length;
        int dx = getPaddingStart();
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        for (int i = 0; i < mLetters.length; i++) {
            Log.e("onDraw", "dx:" + dx);
            int baseLine = getPaddingTop() + letterHeight * i + letterHeight / 2 + dy;
            canvas.drawText(mLetters[i], dx, baseLine, mPaint);
        }

    }
}
