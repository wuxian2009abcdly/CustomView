package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.genvict.customview.R;

/**
 * @author longy
 * @date 2018/12/3
 * Description ${CLASS}
 */

public class RatingBar extends View {
    private final String TAG = "RatingBar";

    private Canvas mCanvas;

    private Bitmap mStar;
    private Bitmap mStarDownVote;
    private Bitmap mStarLike;
    private Bitmap mStarUpVote;

    private Paint mStarPaint;
    private Paint mStarSelectedPaint;

    /**
     * 星星的数量
     */
    private int mStarNum = 5;
    /**
     * 星星之间的间隔
     */
    private int mStarInterval;
    /**
     * 当前评分等级
     */
    private int mCurrentStarNum;

    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        if (array != null) {
            mStar = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.RatingBar_star, 0));
            mStarDownVote = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.RatingBar_starDownVote, 0));
            mStarLike = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.RatingBar_starLike, 0));
            mStarUpVote = BitmapFactory.decodeResource(getResources(), array.getResourceId(R.styleable.RatingBar_starUpVote, 0));
            mStarNum = array.getInt(R.styleable.RatingBar_starNum, mStarNum);
            mStarInterval = array.getDimensionPixelSize(R.styleable.RatingBar_starInterval, mStarInterval);
            array.recycle();
        }

        mStarPaint = new Paint();
        mStarPaint.setAntiAlias(true);
        mStarPaint.setDither(true);
        mStarSelectedPaint = new Paint();
        mStarSelectedPaint.setAntiAlias(true);
        mStarSelectedPaint.setDither(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mStar.getWidth() * mStarNum + mStarInterval * (mStarNum - 1);
        int height = mStar.getHeight();
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mCanvas = canvas;
        //画星星
        for (int i = 0; i < mStarNum; i++) {
            if (i < mCurrentStarNum) {
                if (mCurrentStarNum <= mStarNum / 2) {
                    drawSelectedStar(mStarDownVote, i);
                } else if ((mCurrentStarNum > mStarNum / 2) && mCurrentStarNum < mStarNum) {
                    drawSelectedStar(mStarLike, i);
                } else if (mCurrentStarNum == mStarNum) {
                    drawSelectedStar(mStarUpVote, i);
                }
            } else {
                mCanvas.drawBitmap(mStar, (mStar.getWidth() + mStarInterval) * i, 0, mStarPaint);
            }
        }
    }

    private void drawSelectedStar(Bitmap starBitmap, int i) {
        mCanvas.drawBitmap(starBitmap, (starBitmap.getWidth() + mStarInterval) * i, 0, mStarSelectedPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                Log.e(TAG, "ACTION_UP");
                //如果点击位置不在控件上
                if (event.getX() <= 0 || event.getX() > getWidth()) {
                    return true;
                }
                mCurrentStarNum = (int) (event.getX() / (mStar.getWidth() + mStarInterval));
                if (event.getX() % (mStar.getWidth() + mStarInterval) != 0) {
                    mCurrentStarNum++;
                }

                invalidate();
                break;
            default:
                break;
        }

        return true;
    }
}
