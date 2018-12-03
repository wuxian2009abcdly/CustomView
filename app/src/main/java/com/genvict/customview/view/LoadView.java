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
        mTrianglePaint.setColor(Color.BLUE);
        mRectanglePaint.setStyle(Paint.Style.FILL);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mTrianglePaint.setColor(Color.GREEN);
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

        Path trianglePath = new Path();
        trianglePath.moveTo(0, 0);
        trianglePath.lineTo(getWidth() / 2, getHeight());
        trianglePath.lineTo(getWidth(), 0);
        canvas.drawPath(trianglePath, mTrianglePaint);

        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, mRectanglePaint);

        canvas.drawCircle(0, 0, getWidth() / 2, mCirclePaint);
    }
}
