package com.example.androidsummary.android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPageView extends ViewGroup {
    private int numRows = 2;
    private int numColumns = 2;
    private int onePageCount;
    private int horizontalSpacing;
    private int verticalSpacing;
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    private GestureDetector gestureDetector;
    private Scroller scroller;
    private int position;

    private int scrollX;
    private int startX;
    private int startY;

    public HorizontalPageView(@NonNull Context context) {
        this(context, null);
    }

    public HorizontalPageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HorizontalPageView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onePageCount = numRows * numColumns;

        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
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

        scroller = new Scroller(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int itemUsedWidth = getItemUsableWidth(width);
        int itemWidthMeasureSpec = MeasureSpec.makeMeasureSpec(itemUsedWidth, MeasureSpec.EXACTLY);

        int totalHeight = 0;//总的高度
        int totalWidth = 0;//总的高度

        List<View> childViews = getVisibleChildViews();
        int pages = getPageSize();

        //这里只需要计算出单页的高度即可，默认认为item的宽高是一致的
        int childHeight = 0;
        if(childViews.size() > 0) {
            View child = childViews.get(0);
            measureChild(child, itemWidthMeasureSpec, heightMeasureSpec);
            Log.e("TAG", "measureChild getHeight = "+child.getMeasuredHeight());
            childHeight = itemUsedWidth * child.getMeasuredHeight() / child.getMeasuredWidth();
            Log.e("TAG", "measureChild childHeight = "+childHeight);
            int itemHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY);
            measureChild(child, itemWidthMeasureSpec, itemHeightMeasureSpec);
            Log.e("TAG", "measureChild getHeight22 = "+child.getMeasuredHeight());
        }
        if(childViews.size() >= onePageCount) {
            //计算子控件的单个高度*numRows即可
            totalHeight = childHeight * numRows;
        }else {
            int rows = (int) Math.ceil(childViews.size() * 1.0f / numColumns);
            totalHeight = childHeight * rows;
        }
        totalWidth = itemUsedWidth * pages;

        if(heightMode == MeasureSpec.EXACTLY) {
            totalHeight = height;
        }else if(heightMode == MeasureSpec.AT_MOST) {
            totalHeight = Math.min(height, totalHeight);
            totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        }else {
            totalHeight = totalHeight + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(totalWidth, totalHeight);
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
                    Log.e("TAG", "index = "+index);
                    if(index == getVisibleChildViews().size()) {
                        break;
                    }
                    View child = childViews.get(p);
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

                    //记录显示范围
                    Rect rect = allItemFrames.get(index);
                    if (rect == null) {
                        rect = new Rect();
                    }
                    int hSpacing = 0;
                    int vSpacing = 0;
                    if(co != 0 && co != numColumns - 1) {
                        hSpacing = co * horizontalSpacing;
                    }
                    if(ro != 0 && ro != numRows - 1) {
                        vSpacing = ro * verticalSpacing;
                    }
                    int x = paddingLeft + p * getWidth() + co * childWidth + hSpacing;
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN :

                break;
            case MotionEvent.ACTION_MOVE :
                scrollX = getScrollX();
                position = (getScrollX() + getWidth() / 2) / getWidth();
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
        if(scroller.computeScrollOffset()) {
            scrollTo(scroller.getCurrX(), 0);
            postInvalidate();
        }
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

