package com.example.androidsummary.android.widget.recyclerview.horizontalpage;

import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.common.CommonUtils;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Sunny on 2019/4/1.
 * 参考博文：https://blog.csdn.net/Y_sunny_U/article/details/89500464
 */
public class HorizontalPageLayoutManager extends RecyclerView.LayoutManager implements PageDecorationLastJudge {
    private int totalHeight = 0;
    private int totalWidth = 0;
    private int offsetY = 0;
    private int offsetX = 0;
    private int rows = 0;
    private int columns = 0;
    private int pageSize = 0;
    private int itemWidth = 0;
    private int itemHeight = 0;
    private int onePageSize = 0;
    private int itemWidthUsed;
    private int itemHeightUsed;
    private int itemDefaultHeight;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public HorizontalPageLayoutManager(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.onePageSize = rows * columns;
    }
    
    public void setItemDefaultHeight(int height) {
        itemDefaultHeight = height;
        Log.e("TAG", "itemDefaultHeight = "+height);
    }

    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        detachAndScrapAttachedViews(recycler);
        int newX = offsetX + dx;
        int result = dx;
        if (newX > totalWidth) {
            result = totalWidth - offsetX;
        } else if (newX < 0) {
            result = 0 - offsetX;
        }
        offsetX += result;
        offsetChildrenHorizontal(-result);
        recycleAndFillItems(recycler, state);
        return result;
    }

    private SparseArray<Rect> allItemFrames = new SparseArray<>();

    private int getUsableWidth() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

    private int getUsableHeight() {
        Log.e("TAG", "getHeight() = "+getHeight());
        return getHeight() - getPaddingTop() - getPaddingBottom();
    }

    @Override
    public void onMeasure(@NonNull RecyclerView.Recycler recycler, @NonNull RecyclerView.State state, int widthSpec, int heightSpec) {
        int heightMode = View.MeasureSpec.getMode(heightSpec);
        if(heightMode == View.MeasureSpec.AT_MOST && itemDefaultHeight > 0) {
           heightSpec = View.MeasureSpec.makeMeasureSpec(getMeasureHeight(), View.MeasureSpec.EXACTLY);
        }
        View view = recycler.getViewForPosition(0);
        super.onMeasure(recycler, state, widthSpec, heightSpec);
    }

    private int getMeasureHeight() {
        int itemCount = getItemCount();
        if(itemCount * 1.0f / columns > rows - 1) {
            return itemDefaultHeight * rows;
        }
        return (int) (itemDefaultHeight * Math.floor(itemCount * 1.0f / columns));
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            removeAndRecycleAllViews(recycler);
            return;
        }
        if (state.isPreLayout()) {
            return;
        }
        //获取每个Item的平均宽高
        itemWidth = getUsableWidth() / columns;
        itemHeight = getUsableHeight() / rows;

        //计算宽高已经使用的量，主要用于后期测量
        itemWidthUsed = (columns - 1) * itemWidth;
        itemHeightUsed = (rows - 1) * itemHeight;
        int maxUsed = Math.max(itemWidthUsed, itemHeightUsed);
        //计算总的页数

        computePageSize(state);
        //计算可以横向滚动的最大值
        totalWidth = (pageSize - 1) * getWidth();
        //分离view
        detachAndScrapAttachedViews(recycler);

        int count = getItemCount();
        for (int p = 0; p < pageSize; p++) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < columns; c++) {
                    int index = p * onePageSize + r * columns + c;
                    if (index == count) {
                        //跳出多重循环
                        c = columns;
                        r = rows;
                        p = pageSize;
                        break;
                    }
                    View view = recycler.getViewForPosition(index);
                    addView(view);
                    //测量item
                    measureChildWithMargins(view, maxUsed, maxUsed);

                    int width = getDecoratedMeasuredWidth(view);
                    int height = getDecoratedMeasuredHeight(view);
                    if(height < itemHeight) {
                        height = itemHeight;
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        layoutParams.height = itemHeight;
                        layoutParams.width = width;
                    }
                    Log.e("TAG", "width = "+width + " height = "+height);
                    //记录显示范围
                    Rect rect = allItemFrames.get(index);
                    if (rect == null) {
                        rect = new Rect();
                    }
                    int x = p * getUsableWidth() + c * itemWidth;
                    int y = r * itemHeight;
                    rect.set(x, y, width + x, height + y);
                    allItemFrames.put(index, rect);


                }
            }
            //每一页循环以后就回收一页的View用于下一页的使用
            removeAndRecycleAllViews(recycler);
        }

        recycleAndFillItems(recycler, state);
    }

    private void computePageSize(RecyclerView.State state) {
        pageSize = state.getItemCount() / onePageSize + (state.getItemCount() % onePageSize == 0 ? 0 : 1);
    }

    @Override
    public void onDetachedFromWindow(RecyclerView view, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(view, recycler);
        offsetX = 0;
        offsetY = 0;
    }

    private void recycleAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        Rect displayRect = new Rect(getPaddingLeft() + offsetX, getPaddingTop(), getWidth() - getPaddingLeft() - getPaddingRight() + offsetX, getHeight() - getPaddingTop() - getPaddingBottom());
        Rect childRect = new Rect();
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            childRect.left = getDecoratedLeft(child);
            childRect.top = getDecoratedTop(child);
            childRect.right = getDecoratedRight(child);
            childRect.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(displayRect, childRect)) {
                removeAndRecycleView(child, recycler);
            }
        }

        for (int i = 0; i < getItemCount(); i++) {
            if (Rect.intersects(displayRect, allItemFrames.get(i))) {
                View view = recycler.getViewForPosition(i);
                addView(view);
                measureChildWithMargins(view, itemWidthUsed, itemHeightUsed);
                Rect rect = allItemFrames.get(i);
                layoutDecorated(view, rect.left - offsetX, rect.top, rect.right - offsetX, rect.bottom);
            }
        }

    }

    @Override
    public boolean isLastRow(int index) {
        if (index >= 0 && index < getItemCount()) {
            int indexOfPage = index % onePageSize;
            indexOfPage++;
            if (indexOfPage > (rows - 1) * columns && indexOfPage <= onePageSize) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isLastColumn(int position) {
        if (position >= 0 && position < getItemCount()) {
            position++;
            if (position % columns == 0) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isPageLast(int position) {
        position++;
        return position % onePageSize == 0;
    }

    @Override
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        computePageSize(state);
        return pageSize * getWidth();
    }

    @Override
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return offsetX;
    }

    @Override
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return getWidth();
    }
}