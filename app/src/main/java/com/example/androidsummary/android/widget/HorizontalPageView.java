package com.example.androidsummary.android.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * 基本能够实现左右滑动，及适应子控件大小的问题
 * 需要解决的问题：1、惯性滑动。2、计算边界问题
 */
public class HorizontalPageView extends ViewGroup {
    private int numRows = 3;
    private int numColumns = 2;
    private int onePageCount;
    private int horizontalSpacing = 0;
    private int verticalSpacing = 0;
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int position;

    private int scrollX;
    private int startX;
    private int startY;
    private int itemWidth;
    private int itemHeight;

    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private boolean isFirst;
    private int mWidth;
    private int mHeight;
    private int mLastFlingX;

    public HorizontalPageView(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onePageCount = numRows * numColumns;

        ViewConfiguration vc = ViewConfiguration.get(context);
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();

        gestureDetector = new GestureDetector(context, new MySimpleOnGestureListener());
        scroller = new Scroller(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int itemUsableWidth = getItemUsableWidth(width);
        int itemWidthMeasureSpec = MeasureSpec.makeMeasureSpec(itemUsableWidth, MeasureSpec.EXACTLY);

        int totalHeight = 0;//总的高度

        List<View> childViews = getVisibleChildViews();

        //这里只需要计算出单页的高度即可，默认认为item的宽高是一致的
        int maxChildHeight = 0;
        int maxChildWidth = 0;
        for(int i = 0; i < childViews.size(); i++) {
            View child = childViews.get(i);
            measureChild(child, itemWidthMeasureSpec, heightMeasureSpec);
            int childHeight = child.getMeasuredHeight();
            maxChildHeight = Math.max(childHeight, maxChildHeight);
            maxChildWidth = Math.max(child.getMeasuredWidth(), maxChildWidth);
        }

        //如果条目的宽度超过可用的宽度，则需要对宽高进行同比例缩放
        if(maxChildWidth > itemUsableWidth) {
            maxChildHeight = itemUsableWidth * maxChildHeight / maxChildWidth;
            maxChildWidth = itemUsableWidth;
        }

        int rows;//所需行数
        if(childViews.size() >= onePageCount) {
            //计算子控件的单个高度*numRows即可
            rows = numRows;
        }else {
            rows = (int) Math.ceil(childViews.size() * 1.0f / numColumns);
        }
        totalHeight = maxChildHeight * rows + verticalSpacing * (rows - 1);

        //如果条目的高度超过可用的高度，则需对宽度进行缩放
        if(totalHeight > height - getPaddingTop() - getPaddingBottom()) {
            int itemScaleHeight = (height - getPaddingTop() - getPaddingBottom() - verticalSpacing * (rows - 1))/ rows;
            int itemScaleWidth = maxChildWidth * itemScaleHeight / maxChildHeight;
            maxChildHeight = itemScaleHeight;
            maxChildWidth = itemScaleWidth;
        }

        //最后得到条目的宽和高
        itemHeight = maxChildHeight;
        itemWidth = maxChildWidth;

        if(heightMode == MeasureSpec.EXACTLY) {
            totalHeight = height;
        }else if(heightMode == MeasureSpec.AT_MOST) {
            totalHeight = Math.min(height, totalHeight);
            totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        }else {
            totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(width, totalHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int pages = getPageSize();
        List<View> childViews = getVisibleChildViews();
        for(int p = 0; p < pages; p++) {
            for(int ro = 0; ro < numRows; ro++) {
                for(int co = 0; co < numColumns; co++) {
                    int index = p * onePageCount + ro * numColumns + co;
                    if(index == getVisibleChildViews().size()) {
                        //跳出多重循环
                        co = numColumns;
                        ro = numRows;
                        p = onePageCount;
                        break;
                    }
                    View child = childViews.get(index);
                    //根据分配条目的宽高，再重新测量一次
                    child.measure(MeasureSpec.makeMeasureSpec(itemWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(itemHeight, MeasureSpec.EXACTLY));
                    LayoutParams layoutParams = child.getLayoutParams();
                    int leftMargin =  0;
                    int rightMargin = 0;
                    int topMargin = 0;
                    int bottomMargin = 0;
                    if(layoutParams instanceof MarginLayoutParams) {
                        leftMargin = ((MarginLayoutParams) layoutParams).leftMargin;
                        rightMargin = ((MarginLayoutParams) layoutParams).rightMargin;
                        topMargin = ((MarginLayoutParams) layoutParams).topMargin;
                        bottomMargin = ((MarginLayoutParams) layoutParams).bottomMargin;
                    }

                    int childWidth = child.getMeasuredWidth() + leftMargin + rightMargin;
                    int childHeight = child.getMeasuredHeight() + topMargin + bottomMargin;

                    int itemUsableWidth = getItemUsableWidth(getWidth());

                    //记录显示范围
                    Rect rect = allItemFrames.get(index);
                    if (rect == null) {
                        rect = new Rect();
                    }
                    int hSpacing = 0;
                    int vSpacing = 0;
                    if(co != 0) {
                        hSpacing = co * horizontalSpacing;
                    }
                    if(ro != 0) {
                        vSpacing = ro * verticalSpacing;
                    }
                    //p * getWidth():x轴方向上第几页
                    int x = paddingLeft + p * getWidth() + co * itemUsableWidth + hSpacing + itemUsableWidth / 2 - childWidth / 2;
                    int y = paddingTop + ro * childHeight + vSpacing;
                    rect.set(x, y, childWidth + x, childHeight + y);
                    allItemFrames.put(index, rect);
                }
            }
        }
        layoutItemViews();
    }

    private void layoutItemViews() {
        List<View> childViews = getVisibleChildViews();
        for(int i = 0; i < childViews.size(); i++) {
            View view = childViews.get(i);
            Rect rect = allItemFrames.get(i);
            view.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    float preX, curX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :
                preX = event.getX();
                isFirst = true;
                break;
            case MotionEvent.ACTION_MOVE :
                curX = event.getX();
                scrollX = getScrollX();
                //从右往左滑动
                if(preX > curX) {
                    position = (scrollX + getWidth() * 4 / 5) / getWidth();
                }else {
                    position = (scrollX + getWidth() / 5) / getWidth();
                }
                if(position >= getPageSize()) {
                    position = getPageSize() - 1;
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
        //just use for scroller
        if(scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
    }

    private class MySimpleOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            //dragging
            scrollBy((int) distanceX, 0);
            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            fling(e2.getX(), e2.getY(), velocityX, velocityY);
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }

    private void fling(float startX, float startY, float velocityX, float velocityY) {
        Log.e("TAG", "startX = "+startX + " startY = "+startY + " velocityX = "+velocityX);
        scroller.fling((int)startX, (int)startY, -(int)velocityX, -(int)velocityY, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    /**
     * 获取可用的宽度
     * @param width
     * @return
     */
    private int getUsableWidth(int width) {
        return width - getPaddingLeft() - getPaddingRight();
    }

    /**
     * 条目的可用宽度
     * @param width
     * @return
     */
    private int getItemUsableWidth(int width) {
        return (getUsableWidth(width) - horizontalSpacing * (numColumns - 1)) / numColumns;
    }

    private List<View> getVisibleChildViews() {
        List<View> views = new ArrayList<>();
        int childCount = getChildCount();
        for(int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() != GONE) {
                views.add(child);
            }
        }
        return views;
    }

    /**
     * 获取页数
     * @return
     */
    private int getPageSize() {
        return (int) Math.ceil(getVisibleChildViews().size() * 1.0f / onePageCount);
    }


}

