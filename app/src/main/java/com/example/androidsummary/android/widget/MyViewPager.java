package com.example.androidsummary.android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.example.androidsummary.R;

public class MyViewPager extends ViewGroup {
    private Context mContext;
    private int[] images = {R.drawable.image01, R.drawable.image02, R.drawable.image03, R.drawable.image04, R.drawable.image05};
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int position;

    private int scrollX;
    private int startX;
    private int startY;

    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        for(int i = 0; i < images.length; i++) {
            ImageView iv = new ImageView(mContext);
            iv.setBackgroundResource(images[i]);
            addView(iv);
        }

        gestureDetector = new GestureDetector(mContext, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                scrollBy((int) distanceX, 0);
                return super.onScroll(e1, e2, distanceX, distanceY);
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        scroller = new Scroller(mContext);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(i * getWidth(), t, (i+1)*getWidth(), b);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :

                break;
            case MotionEvent.ACTION_MOVE :
                scrollX = getScrollX();
                position = (getScrollX() + getWidth() / 2) / getWidth();
                if(position >= images.length) {
                    position = images.length - 1;
                }
                if(position < 0) {
                    position = 0;
                }
                break;
            case MotionEvent.ACTION_UP:
                scroller.startScroll(scrollX, 0, -(scrollX - position * getWidth()), 0);
                invalidate();
                break;
        }
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // 如果左右滑动, 就需要拦截, 上下滑动,不需要拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = (int) ev.getX();
                startY = (int) ev.getY();
                gestureDetector.onTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int endX = (int) ev.getX();
                int endY = (int) ev.getY();
                int dx = endX - startX;
                int dy = endY - startY;
                if (Math.abs(dx) > Math.abs(dy)) {
                    // 左右滑动
                    return true;// 中断事件传递, 不允许孩子响应事件了, 由父控件处理
                }
                break;
            default:
                break;
        }
        return false;// 不拦截事件,优先传递给孩子(也就是ScrollView，让它正常处理上下滑动事件)处理
    }

    @Override
    public void computeScroll() {
        if(scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}

