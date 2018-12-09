package com.genvict.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.genvict.customview.utils.ViewUtils;

/**
 * @author longy
 * @date 2018/12/4
 * Description ${CLASS}
 */

public class LetterSideBar extends View {

    private String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "#"};
    private Paint mPaint;
    private int mTextSize = 20;
    private int mLetterHeight;
    // 当前触摸的位置字母
    private String mCurrentLetter;

    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLUE);
        mPaint.setTextSize(ViewUtils.sp2px(context, mTextSize));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = (int) (mPaint.measureText("M") + getPaddingLeft() + getPaddingRight());
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("onDraw", "onDraw");
        //计算每个字母的高度
        mLetterHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        for (int i = 0; i < mLetters.length; i++) {
            int baseLine = getPaddingTop() + mLetterHeight * i + mLetterHeight / 2 + dy;

            //X 绘制在最中间 = 宽度/2 -文字/2
            int textWidth = (int) mPaint.measureText(mLetters[i]);
            int x = getWidth() / 2 - textWidth / 2;
            Log.e("onDraw", "x:" + x);
            Log.e("baseLine", "baseLine:" + baseLine);
            //当前字母高亮
            canvas.drawText(mLetters[i], x, baseLine, mPaint);
        }
//        int itemHeight = (getHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;
//
//        for (int i = 0; i < mLetters.length; i++) {
//            // 知道每个字母的中心位置  1  字母的高度一半   2 字母高度一般+前面字符的高度
//            int letterCenterY = i * itemHeight + itemHeight / 2 + getPaddingTop();
//            // 基线，基于中心位置, 知道中心位置还不会基线，看一下之前的视频
//            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
//            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom);
//            int baseLine = letterCenterY + dy;
//            // x 绘制在最中间 = 宽度/2 - 文字/2
//            int textWidth = (int) mPaint.measureText(mLetters[i]);
//            int x = getWidth() / 2 - textWidth / 2;
//
//            Log.e("onDraw", "x:" + x);
//            Log.e("baseLine", "baseLine:" + baseLine);
//
//            // 当前字母 高亮  用两个画笔(最好) 改变颜色
//            if (mLetters[i].equals(mCurrentLetter)) {
//                mPaint.setColor(Color.RED);
//                canvas.drawText(mLetters[i], x, baseLine, mPaint);
//            } else {
//                mPaint.setColor(Color.BLUE);
//                canvas.drawText(mLetters[i], x, baseLine, mPaint);
//            }
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                //计算当前触摸字母
                float currentMoveY = event.getRawY();
                int currentPosition = (int) (currentMoveY / mLetterHeight);
                if (currentPosition < 0) {
                    currentPosition = 0;
                }
                if (currentPosition > mLetters.length - 1) {
                    currentPosition = mLetters.length - 1;
                }
                mCurrentLetter = mLetters[currentPosition];
                //重新绘制
                invalidate();
                break;
        }

        return true;
    }
}
