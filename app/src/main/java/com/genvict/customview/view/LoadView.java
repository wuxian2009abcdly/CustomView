package com.genvict.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author longy
 * @date 2018/12/3
 * Description 58同城加载框
 */

public class LoadView extends View {

    /**
     * 三角形画笔
     */
    private Paint mTrianglePaint;
    /**
     * 矩形画笔
     */
    private Paint mRectanglePaint;
    /**
     * 圆形画笔
     */
    private Paint mCirclePaint;

    private Path mPath;

    private Shape mCurrentShape = Shape.Circle;

    public enum Shape {
        Circle, Rectangle, Triangle
    }

    public LoadView(Context context) {
        this(context, null);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTrianglePaint = new Paint();
        mTrianglePaint.setAntiAlias(true);
        mTrianglePaint.setColor(Color.RED);
        mTrianglePaint.setStyle(Paint.Style.FILL);

        mRectanglePaint = new Paint();
        mRectanglePaint.setAntiAlias(true);
        mRectanglePaint.setColor(Color.BLUE);
        mRectanglePaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GREEN);
        mCirclePaint.setStyle(Paint.Style.FILL);
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

        switch (mCurrentShape) {
            case Circle:
                //画圆
                int center = getWidth() / 2;
                canvas.drawCircle(center, center, center, mCirclePaint);
                break;
            case Rectangle:
                Rect rect = new Rect(0, 0, getWidth(), getHeight());
                canvas.drawRect(rect, mRectanglePaint);
                break;
            case Triangle:
                if (mPath == null) {
                    mPath = new Path();
                    mPath.moveTo(getWidth() / 2, 0);
                    mPath.lineTo(0, (float) (getWidth() / 2 * Math.sqrt(3)));
                    mPath.rLineTo(getWidth(), 0);
                }
                canvas.drawPath(mPath, mTrianglePaint);
                break;
        }
    }

    public void exchange() {
        switch (mCurrentShape) {
            case Circle:
                mCurrentShape = Shape.Rectangle;
                break;
            case Rectangle:
                mCurrentShape = Shape.Triangle;
                break;
            case Triangle:
                mCurrentShape = Shape.Circle;
                break;
        }
        invalidate();
    }


}
