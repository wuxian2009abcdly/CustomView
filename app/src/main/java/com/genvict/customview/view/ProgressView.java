package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.genvict.customview.R;

/**
 * Created by longyu on 2018/11/29.
 * Description 自定义进度条
 */
public class ProgressView extends View {
    private int mOuterColor = Color.BLUE;
    private int mInnerColor = Color.RED;
    private int mTextColor = Color.RED;
    private int mTextSize = 20;
    private int mBoundWidth = 20;
    private Paint mOuterPaint;
    private Paint mInnerPaint;
    private Paint mTextPaint;

    private float mCurrentProgress;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint(context, attrs);
    }

    private void initPaint(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ProgressView);
        if (array != null) {
            mOuterColor = array.getColor(R.styleable.ProgressView_progressOuterColor, mOuterColor);
            mInnerColor = array.getColor(R.styleable.ProgressView_progressInnerColor, mInnerColor);
            mBoundWidth = array.getDimensionPixelSize(R.styleable.ProgressView_progressBoundWidth, mBoundWidth);
            mTextSize = array.getDimensionPixelSize(R.styleable.ProgressView_progressTextSize, mTextSize);
            mTextColor = array.getColor(R.styleable.ProgressView_progressTextColor, mTextColor);
            array.recycle();
        }

        mOuterPaint = initArcPaint(mOuterColor);
        mInnerPaint = initArcPaint(mInnerColor);
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(sp2px(mTextSize));
    }

    private Paint initArcPaint(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStrokeWidth(mBoundWidth);
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(Math.min(width, height), Math.min(width, height));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画外圆
        int radius = getWidth() / 2 - mBoundWidth / 2;
        canvas.drawCircle(getWidth() / 2, getWidth() / 2, radius, mOuterPaint);

        //画内圆（进度条）
        RectF rectF = new RectF(mBoundWidth / 2, mBoundWidth / 2, getWidth() - mBoundWidth / 2, getWidth() - mBoundWidth / 2);
        canvas.drawArc(rectF, 0, mCurrentProgress * 360, false, mInnerPaint);

        //画文字
        String text = (int) (mCurrentProgress * 100) + "%";
        Rect bounds = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), bounds);
        int dx = getWidth() / 2 - bounds.width() / 2;
        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        int baseLine = getWidth() / 2 + dy;
        canvas.drawText(text, dx, baseLine, mTextPaint);
    }

    private float sp2px(int mTextSize) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, mTextSize, getContext().getResources().getDisplayMetrics());
    }

    public void setCurrentProgress(float mCurrentProgress) {
        this.mCurrentProgress = mCurrentProgress;
        invalidate();
    }

}
