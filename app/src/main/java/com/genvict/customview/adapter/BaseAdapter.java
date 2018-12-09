package com.genvict.customview.adapter;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by longyu on 2018/12/10.
 * Description  流式布局adapter
 */
public abstract class BaseAdapter {
    //1、有多少个条目
    public abstract int getCount();

    //1、getView通过position
    public abstract View getView(int position,ViewGroup parent);

    //3、观察者模式，及时通知更新

}
