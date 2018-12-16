package com.genvict.customview.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by longyu on 2018/12/12.
 * Description
 */
public class TouchViewGroup extends LinearLayout {

    public TouchViewGroup(Context context) {
        super(context);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TouchViewGroup(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("TAG", "ViewGroup dispatchTouchEvent->" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件拦截
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("TAG", "ViewGroup onInterceptTouchEvent->" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 事件触摸
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("TAG", "ViewGroup onTouchEvent->" + event.getAction());
        return super.onTouchEvent(event);
    }
}
