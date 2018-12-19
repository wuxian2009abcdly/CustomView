package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.genvict.customview.R;

/**
 * @author longy
 * @date 2018/12/18
 * Description 九宫格密码锁自定义View
 */

public class LockPatternView extends View {
    private int mLockHorizontalMargin = 20;
    private int mLockVerticalMargin = 20;
    private int mLockStrokeWidth = 10;
    private int mLockRadius = 80;
    private int mLockInnerRadius = 10;
    /**
     * mUnTouchPaint 未触摸画笔  mUnTouchColor 未触摸画笔颜色
     * mTouchPaint 触摸画笔  mTouchColor  触摸时画笔颜色
     * mUpPaint 抬起画笔  mUpColor  抬起画笔颜色
     */
    private Paint mUnTouchPaint;
    private Paint mTouchPaint;
    private Paint mUpPaint;
    private int mUnTouchColor;
    private int mTouchColor;
    private int mUpColor;

    public LockPatternView(Context context) {
        this(context, null);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LockPatternView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(R.styleable.LockPatternView);
        if (array != null) {
            mLockHorizontalMargin = array.getDimensionPixelSize(R.styleable.LockPatternView_lockHorizontalMargin, mLockHorizontalMargin);
            mLockVerticalMargin = array.getDimensionPixelSize(R.styleable.LockPatternView_lockVerticalMargin, mLockVerticalMargin);
            mLockStrokeWidth = array.getDimensionPixelSize(R.styleable.LockPatternView_lockStrokeWidh, mLockStrokeWidth);
            mLockRadius = array.getDimensionPixelSize(R.styleable.LockPatternView_lockRadius, mLockRadius);
            mUnTouchColor = array.getColor(R.styleable.LockPatternView_unTouchColor, Color.GRAY);
            mTouchColor = array.getColor(R.styleable.LockPatternView_touchColor, Color.BLUE);
            mUpColor = array.getColor(R.styleable.LockPatternView_upColor, Color.RED);
            array.recycle();
        }

        mUnTouchPaint = new Paint();
        mUnTouchPaint.setAntiAlias(true);
        mUnTouchPaint.setColor(mUnTouchColor);
        mUnTouchPaint.setStyle(Paint.Style.STROKE);
        mUnTouchPaint.setStrokeWidth(mLockStrokeWidth);

        mTouchPaint = new Paint();
        mTouchPaint.setAntiAlias(true);
        mTouchPaint.setColor(mTouchColor);
        mTouchPaint.setStrokeWidth(mLockStrokeWidth);

        mUpPaint = new Paint();
        mUpPaint.setAntiAlias(true);
        mUpPaint.setColor(mUpColor);
        mUpPaint.setStrokeWidth(mLockStrokeWidth);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            width = mLockRadius * 6 + mLockHorizontalMargin * 2;
        }

        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.AT_MOST) {
            height = mLockRadius * 6 + mLockVerticalMargin * 2;
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //第一排
        int dx = getPaddingStart() + mLockRadius + mLockStrokeWidth / 2;
        int dy = getPaddingTop() + mLockRadius + mLockStrokeWidth / 2;
        drawHorizontalView(dx, dy, canvas);

        //第二排
        dy = getPaddingTop() + mLockRadius * 3 + mLockVerticalMargin + mLockStrokeWidth / 2;
        drawHorizontalView(dx, dy, canvas);

        //第三排
        dy = getPaddingTop() + mLockRadius * 5 + mLockVerticalMargin * 2 + mLockStrokeWidth / 2;
        drawHorizontalView(dx, dy, canvas);
    }

    private void drawHorizontalView(int dx, int dy, Canvas canvas) {
        for (int i = 0; i < 3; i++) {
            canvas.drawCircle(dx, dy, mLockRadius, mUnTouchPaint);
            canvas.drawCircle(dx, dy, mLockInnerRadius, mUnTouchPaint);
            dx += mLockRadius * 2 + mLockHorizontalMargin;
        }
    }

}
