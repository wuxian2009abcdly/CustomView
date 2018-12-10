package com.genvict.customview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.genvict.customview.adapter.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by longyu on 2018/12/8.
 * Description
 */
public class TagLayout extends ViewGroup {
    /**
     * 所有行的View集合
     */
    private List<List<View>> mChildViews = new ArrayList<>();

    private BaseAdapter mBaseAdapter;

    /**
     * 所有行的View的最大高度的集合
     */
    private List<Integer> mMaxHeights = new ArrayList<>();

    public TagLayout(Context context) {
        super(context);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.e("TAG", "onMeasure");
        //清空集合
        mChildViews.clear();
        mMaxHeights.clear();

        //获取子View的数量
        int childCount = getChildCount();

        //获取到自己的宽度
        int width = MeasureSpec.getSize(widthMeasureSpec);

        //高度需要计算
        int height = getPaddingTop() + getPaddingBottom();

        //一行的宽度
        int lineWidth = getPaddingLeft();


        ArrayList<View> childViews = new ArrayList<>();
        mChildViews.add(childViews);
        //每一行的最大高度
        int maxHeight = 0;

        for (int i = 0; i < childCount; i++) {
            //1.1 for循环测量子View
            View childView = getChildAt(i);
            if (childView.getVisibility() == GONE) {
                continue;
            }
            // 这段话执行之后就可以获取子View的宽高，因为会调用子View的onMeasure方法
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);

            //margin值 ViewGroup.LayoutParams 没有  就用系统的MarginLayoutParams
            //LinearLayout有自己的 LayoutParams 会复写一个非常重要的方法
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
            Log.e("TAG", "leftMargin->" + params.leftMargin + "  topMargin->" + params.topMargin + "    rightMargin->" + params.rightMargin + "  bottomMargin->" + params.bottomMargin);
            //1.2 根据子View计算和指定自己的布局
            //什么时候需要换行，一行不够的情况下 考虑margin
            if (lineWidth + (childView.getMeasuredWidth() + params.leftMargin + params.rightMargin) > width) {
                //换行,累加高度
                height += maxHeight;
                lineWidth = childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                mMaxHeights.add(maxHeight);
                maxHeight = childView.getMeasuredHeight() + params.leftMargin + params.rightMargin;
                childViews = new ArrayList<>();
                mChildViews.add(childViews);
            } else {
                lineWidth += childView.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                maxHeight = Math.max(childView.getMeasuredHeight() + params.topMargin + params.bottomMargin, maxHeight);
            }
            childViews.add(childView);
        }

        height += maxHeight;
        Log.e("TAG", "width->" + width + "  height->" + height);
        Log.e("TAG", "mMaxHeights-Size:" + childViews.size());
        //指定自己的宽高
        setMeasuredDimension(width, height);
    }

    /**
     * 摆放子View
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int left, top = getPaddingTop(), right, bottom;

        for (List<View> childViews : mChildViews) {
            left = getPaddingLeft();
            int maxHeight = 0;
            for (View childView : childViews) {
                if (childView.getVisibility() == View.GONE) {
                    continue;
                }

                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childView.getLayoutParams();
                Log.e("TAG", "left-margin-》" + params.leftMargin);
                left += params.leftMargin;
                Log.e("TAG", "left-》" + left);
                int childTop = top + params.topMargin;
                Log.e("TAG", "childTop-》" + childTop);
                right = left + childView.getMeasuredWidth();
                Log.e("TAG", "right-》" + right);
                bottom = childTop + childView.getMeasuredHeight();
                Log.e("TAG", "bottom-》" + bottom);

                Log.e("TAG", "left->" + left + "  top->" + childTop + "    right->" + right + "  bottom->" + bottom);
                // 摆放
                childView.layout(left, childTop, right, bottom);
                // left 叠加
                left += childView.getMeasuredWidth() + params.rightMargin;
                Log.e("TAG", "left2-》" + left);
                // 不断的叠加top值
                int childHeight = childView.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                maxHeight = Math.max(maxHeight, childHeight);
            }
            //不断的叠加top值
            top += maxHeight;
            Log.e("TAG", "top2-》" + left);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setAdapter(BaseAdapter adapter) {
        if (adapter == null) {
            //抛空指针异常
            throw new NullPointerException();
        }

        //清空所有子View
        removeAllViews();

        mBaseAdapter = null;
        mBaseAdapter = adapter;

        //获取数量
        int childCount = mBaseAdapter.getCount();
        for (int i = 0; i < childCount; i++) {
            View childView = mBaseAdapter.getView(i, this);
            addView(childView);
        }
    }


}
