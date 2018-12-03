package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.genvict.customview.R;

/**
 * @author longy
 * @date 2018/11/30
 * Description ${CLASS}
 */

public class ColorTrackView extends AppCompatTextView {
    private final String TAG = "ColorTrackView";

    private int mOriginColor = Color.BLACK;
    private int mChangeColor = Color.RED;
    private Paint mOriginPaint;
    private Paint mChangePaint;

    private Direction mDirection = Direction.LEFT_TO_RIGHT;

    public enum Direction {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT
    }

    /**
     * 百分比
     */
    private float mCurrentProgress;
    /**
     * 两种颜色的文字的中间值
     */
    private int mMiddle;
    private String mText = "";
    private int mBaseLine;

    public ColorTrackView(Context context) {
        this(context, null);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = getResources().obtainAttributes(attrs, R.styleable.ColorTrackView);
        if (array != null) {
            mOriginColor = array.getColor(R.styleable.ColorTrackView_originColor, mOriginColor);
            mChangeColor = array.getColor(R.styleable.ColorTrackView_changeColor, mChangeColor);
            array.recycle();
        }

        mOriginPaint = initPaintByColor(mOriginColor);
        mChangePaint = initPaintByColor(mChangeColor);
    }

    /**
     * 初始化画笔
     */
    private Paint initPaintByColor(int color) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setTextSize(getTextSize());
        paint.setColor(color);
        return paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //初始化要画的文字的相关坐标
        initPoint();

        if (mDirection == Direction.LEFT_TO_RIGHT) {
            drawText(canvas, mChangePaint, 0, mMiddle);
            drawText(canvas, mOriginPaint, mMiddle, getWidth());
        } else {
            drawText(canvas, mChangePaint, getWidth() - mMiddle, getWidth());
            drawText(canvas, mOriginPaint, 0, getWidth() - mMiddle);
        }
    }

    /**
     * 初始化文字、位置信息
     */
    private void initPoint() {
        mText = getText().toString();

        Paint.FontMetricsInt fontMetrics = new Paint.FontMetricsInt();
        int dy = (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom;
        mBaseLine = getHeight() / 2 + dy;

        mMiddle = (int) (mCurrentProgress * getWidth());
    }

    /**
     * 画文字
     *
     * @param canvas
     */
    private void drawText(Canvas canvas, Paint paint, int start, int end) {
        canvas.save();
        //裁剪区域
        Rect rect = new Rect(start, 0, end, getHeight());
        canvas.clipRect(rect);
        canvas.drawText(mText, 0, mBaseLine, paint);
        canvas.restore();
    }

    public void setDirection(Direction mDirection) {
        this.mDirection = mDirection;
    }

    public void setCurrentProgress(float currentProgress) {
        this.mCurrentProgress = currentProgress;
        invalidate();
    }


}
