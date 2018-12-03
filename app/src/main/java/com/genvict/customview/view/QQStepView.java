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
    private int mCurrentStep;
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
            mStepMax = array.getInt(R.styleable.QQStepView_stepMax, mStepMax);
            mCurrentStep = array.getInt(R.styleable.QQStepView_currentStep, mCurrentStep);
            mBoundWidth = dp2px(array.getDimensionPixelSize(R.styleable.QQStepView_bounds, mBoundWidth));
            mOuterColor = array.getColor(R.styleable.QQStepView_outerColor, mOuterColor);
            mInnerColor = array.getColor(R.styleable.QQStepView_innerColor, mInnerColor);
            mTextSize = sp2px(array.getDimensionPixelSize(R.styleable.QQStepView_stepSize, mTextSize));
            mTextColor = array.getColor(R.styleable.QQStepView_stepColor, mTextColor);
            //获取完属性 销毁TypedArray实例
            array.recycle();
        }

        //初始化背景圆弧画笔
        mOuterPaint = new Paint();
        //防边缘锯齿
        mOuterPaint.setAntiAlias(true);
        mOuterPaint.setStrokeCap(Paint.Cap.ROUND);
        mOuterPaint.setStrokeWidth(mBoundWidth);
        mOuterPaint.setColor(mOuterColor);
        mOuterPaint.setStyle(Paint.Style.STROKE);

        //初始化内圆弧画笔
        mInnerPaint = new Paint();
        mInnerPaint.setAntiAlias(true);
        mInnerPaint.setStrokeCap(Paint.Cap.ROUND);
        mInnerPaint.setStrokeWidth(mBoundWidth);
        mInnerPaint.setColor(mInnerColor);
        mInnerPaint.setStyle(Paint.Style.STROKE);

        //初始化文字画笔
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(width < height ? width : height, width < height ? width : height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画背景圆弧
        RectF rectF = new RectF(mBoundWidth / 2, mBoundWidth / 2, getWidth() - mBoundWidth / 2, getWidth() - mBoundWidth / 2);
        canvas.drawArc(rectF, 135, 270, false, mOuterPaint);

        //画内圆弧
        float sweep = (float) mCurrentStep / (float) mStepMax;
        canvas.drawArc(rectF, 135, sweep * 270, false, mInnerPaint);

        //画文字
        String stepText = mCurrentStep + "";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(stepText, getPaddingStart(), stepText.length(), bounds);
        float dx = getWidth() / 2 - bounds.width() / 2;
        int dy = (bounds.bottom - bounds.top) / 2 - bounds.bottom;
        float baseLine = getWidth() / 2 + dy;
        canvas.drawText(stepText, dx, baseLine, mTextPaint);
    }

    public synchronized void setStepMax(int mStepMax) {
        if (mStepMax < 0) {
            throw new IllegalArgumentException("最大步数不能小于0");
        }
        this.mStepMax = mStepMax;
    }

    public void setCurrentStep(int mCurrentStep) {
        if (mCurrentStep < 0 || mCurrentStep > mStepMax) {
            throw new IllegalArgumentException("当前步数不能小于0或大于最大值");
        }
        this.mCurrentStep = mCurrentStep;
        //刷新界面
        invalidate();
    }

    public void setBoundWidth(int mBoundWidth) {
        this.mBoundWidth = mBoundWidth;
    }

    public void setOuterColor(int mOuterColor) {
        this.mOuterColor = mOuterColor;
    }

    public void setInnerColor(int mInnerColor) {
        this.mInnerColor = mInnerColor;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    private int sp2px(int sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, getResources().getDisplayMetrics());
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

}
