package com.genvict.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import com.genvict.customview.R;
import com.genvict.customview.utils.ViewUtils;

/**
 * Created by longyu on 2018/12/16.
 * Description
 */
public class SlidingMenu extends HorizontalScrollView {
    Context mContext;
    /**
     * mScreenWidth 屏幕宽度
     * mScreenHeight 屏幕高度
     * mMenuRightMargin 打开侧滑菜单时，内容的宽度
     * mMenuWidth 打开侧滑菜单时，菜单的高度
     */
    private int mScreenWidth;
    private int mMenuRightMargin = 80;
    private int mMenuWidth;

    private View mContentView;
    private View mMenuView;

    /**
     * 菜单是否打开
     */
    private boolean mMenuIsOpen = false;
    /**
     * 是否拦截自己的onTouch事件处理
     */
    private boolean mIsIntercept = false;

    //处理手势，快速滑动
    GestureDetector mGestureDetector;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;

        TypedArray array = context.obtainStyledAttributes(R.styleable.SlidingMenu);
        if (array != null) {
            mMenuRightMargin = array.getDimensionPixelSize(R.styleable.SlidingMenu_menuRightMargin, ViewUtils.dp2px(mContext, mMenuRightMargin));
            array.recycle();
        }

        //获取屏幕宽高
        mScreenWidth = mContext.getResources().getDisplayMetrics().widthPixels;
        //菜单的宽度
        mMenuWidth = mScreenWidth - mMenuRightMargin;

        mGestureDetector = new GestureDetector(mContext, mGestureListener);
    }

    private GestureDetector.OnGestureListener mGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            //只要快速滑动就会回调这个方法
//            Log.e("TAG", "velocityX->" + velocityX);
            if (mMenuIsOpen) {
                //打开的时候往右边快速滑动切换
                if (velocityX < 0 && (Math.abs(velocityX) > Math.abs(velocityY))) {
                    closeMenu();
                    return true;
                }
            } else {
                //关闭的时候往左边快速滑动切换
                if (velocityX > 0 && (Math.abs(velocityX) > Math.abs(velocityY))) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };

    //1、宽度不对，指定宽高
    @Override
    protected void onFinishInflate() {
        //这个方法是布局（xml布局文件）解析完毕
        super.onFinishInflate();
        //指定宽高
        //1、内容的宽度   屏幕的宽度
        //获取LinearLayout
        ViewGroup container = (ViewGroup) getChildAt(0);

        int childCount = container.getChildCount();
        if (childCount != 2) {
            throw new RuntimeException("Only two Views can be placed ");
        }

        mMenuView = container.getChildAt(0);
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        //7.0以下的手机必须采用下面的方式
        mMenuView.setLayoutParams(menuParams);

        //2、菜单页的宽度  屏幕的宽度 - 右边的小部分距离（自定义属性）
        mContentView = container.getChildAt(1);
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = mScreenWidth;
        mContentView.setLayoutParams(contentParams);
    }

    //5、处理右边的缩放，左边的缩放和透明度  需要不断的获取滚动的位置
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //算一个梯度值
        float scale = 1f * l / mMenuWidth; //从1变化到0
        //右边的缩放：最小0.7f，最大1f
        float rightScale = 0.7f + 0.3f * scale;

        //设置右边的缩放  默认以中心点缩放
        //设置缩放的中心点位置
        ViewCompat.setPivotX(mContentView, 0);
        ViewCompat.setPivotY(mContentView, mContentView.getMeasuredHeight() / 2);
        ViewCompat.setScaleX(mContentView, rightScale);
        ViewCompat.setScaleY(mContentView, rightScale);

        //设置菜单的缩放和透明度
        //透明度  由半透明到完全透明0.7-1.0f
        float leftAlpha = 0.5f + (1 - scale) * 0.5f;
        ViewCompat.setAlpha(mMenuView, leftAlpha);
        //缩放 0.7f - 1f
        float leftScale = (float) (0.7 + (1 - scale) * 0.3f);
        ViewCompat.setScaleX(mMenuView, leftScale);
        ViewCompat.setScaleY(mMenuView, leftScale);

        //最后一个效果  退出这个按钮刚开始是在右边，按照目前的方式永远是在左边
        //设置平移 l*0.7f
        ViewCompat.setTranslationX(mMenuView, 0.22f * l);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        //3、初始化进来是关闭状态  scrollTo 没动画
        scrollTo(mMenuWidth, 0);

    }

    //4、手指抬起  要么关闭要么打开
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //如果有拦截，则不需要处理自己的onTouchEvent
        if (mIsIntercept) {
            return true;
        }
        if (mGestureDetector.onTouchEvent(ev)) {
            //快速滑动触发了，下面的就不要执行了
            return true;
        }

        if (ev.getAction() == MotionEvent.ACTION_UP) {
            //只需要处理UP事件  根据当前滚动的距离来判断
            int currentScrollX = getScrollX();
            if (currentScrollX > mMenuWidth / 2) {
                //关闭
                closeMenu();
            } else {
                //打开
                openMenu();
            }
            //确保  super.onTouchEvent() 不会执行
            return true;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        mIsIntercept = false;
        //处理事件拦截 + ViewGroup 事件分发的源码实践
        //当菜单打开时，手指触摸右边内容部分需要关闭菜单，还要拦截事件（不会响应内容中的点击事件）
        if (mMenuIsOpen) {
            float currentX = ev.getX();
            if (currentX > mMenuWidth) {
                //关闭菜单
                closeMenu();
                //拦截子View事件 子View不需要响应任何事件（点击和触摸），
                //如果返回true 代表会拦截子View的事件，但是会响应自己的onTouch()事件
                mIsIntercept = true;
                return true;
            }
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 打开菜单 滚动到0的位置
     */
    private void openMenu() {
        //smoothScrollTo  有动画
        smoothScrollTo(0, 0);
        mMenuIsOpen = true;
    }

    /**
     * 关闭菜单  滚动到 mMenuWidth 的位置
     */
    private void closeMenu() {
        smoothScrollTo(mMenuWidth, 0);
        mMenuIsOpen = false;
    }

}
