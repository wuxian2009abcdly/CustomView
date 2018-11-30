package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.genvict.customview.R;


/**
 * @author longy
 * @date 2018/11/28
 * Description ${CLASS}
 */

public class QQStepView extends View {

    private int mStepMax = 5000;
    private int mCurrentStep = 3000;
    /**
     * 圆弧画笔宽度
     */
    private int mBoundWidth = 20;
    /**
     * 背景圆弧颜色
     */
    private int mOuterColor = Color.GREEN;
    /**
     * 内圆弧颜色
     */
    private int mInnerColor = Color.BLUE;
    private int mTextSize = 24;
    private int mTextColor = Color.BLACK;

    /**
     * 画外圆弧的paint
     */
    private Paint mOuterPaint;
    /**
     * 画内圆弧的paint
     */
    private Paint mInnerPaint;
    /**
     * 画文字的paint
     */
    private Paint mTextPaint;

    public QQStepView(Context context) {
        this(context, null);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQStepView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取自定义属性
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.QQStepView, defStyleAttr, 0);
        if (array != null) {
            mStepMax = array.getInteger(R.styleable.QQStepView_stepMax, mStepMax);
            mCurrentStep = array.getInteger(R.styleable.QQStepView_currentStep, mCurrentStep);
            mTextSize = array.getInteger(R.styleable.QQStepView_stepSize, mTextSize);
            mTextColor = array.getColor(R.styleable.QQStepView_stepColor, mTextColor);
            //获取完属性 销毁TypedArray实例
            array.recycle();
        }

        //初始化背景圆弧画笔
        mOuterPaint = new Paint();
        //防边缘锯齿
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeWidth(mBoundWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        //初始化内圆弧画笔
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeWidth(mBoundWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(sp2px(context, mTextSize));
        mTextPaint.setColor(mTextColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width < height ? width : height, width < height ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景圆弧
        RectF rectF = new RectF(mBoundWidth / 2, mBoundWidth / 2, getWidth() - mBoundWidth / 2, getWidth() - mBoundWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOuterPaint);

        //画内圆弧
        canvas.drawArc(rectF, 135, 50, false, mInnerPaint);

        //画文字
        String stepText = mCurrentStep + "";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(stepText, getPaddingStart(), stepText.length(), bounds);
        Log.e("onDraw", "getWidth():" + getWidth());
        Log.e("onDraw", "bounds.width():" + bounds.width());
        Log.e("onDraw", "mBoundWidth:" + mBoundWidth);
        float dx = getWidth() / 2 - bounds.width() / 2 - mBoundWidth / 2;
        Log.e("onDraw", "dx:" + dx);
        int dy = (bounds.bottom - bounds.top) / 2 - bounds.bottom;
        float baseLine = getWidth() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }

    private int sp2px(Context context, int mTextSize) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, context.getResources().getDisplayMetrics());
    }

}
