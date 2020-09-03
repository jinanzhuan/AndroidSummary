package com.example.androidsummary.android.event;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

public class ViewScrollHelper {
    private static final String TAG = ViewScrollHelper.class.getSimpleName();
    private static ViewScrollHelper instance;

    private int screenWidth;
    private int viewWidth;
    private int viewLeft;
    private int viewTop;
    private int viewRight;
    private int viewBottom;
    private int lastLeft, lastTop, lastRight, lastBottom;

    private ViewScrollHelper(Context context) {
        screenWidth = (int) getScreenInfo(context)[0];
    }

    public static ViewScrollHelper getInstance(Context context) {
        if(instance == null) {
            synchronized (ViewScrollHelper.class) {
                if(instance == null) {
                    instance = new ViewScrollHelper(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public void makeViewCanScroll(View view) {
        view.post(()-> {
            viewWidth = view.getWidth();
        });
        view.setOnTouchListener(new View.OnTouchListener() {
            boolean result = false;

            float left;
            float top;
            float startX = 0;
            float startY = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        result = false;
                        startX = event.getRawX();
                        startY = event.getRawY();

                        viewLeft = view.getLeft();
                        viewTop = view.getTop();
                        viewRight = view.getRight();
                        viewBottom = view.getBottom();

                        Log.e("TAG", "startX: " + startX + ", startY: " + startY + ", left: " + viewLeft + ", top: " + viewTop);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        float currentX = event.getRawX();
                        float currentY = event.getRawY();
                        if (Math.abs(currentX - startX) > 20 || Math.abs(currentY - startY) > 20) {
                            result = true;
                        }

                        int deltaX = (int) (currentX - startX);
                        int deltaY = (int) (currentY - startY);

                        lastLeft = viewLeft + deltaX;
                        lastTop = viewTop + deltaY;
                        lastRight = viewRight + deltaX;
                        lastBottom = viewBottom + deltaY;
                        view.layout(lastLeft, lastTop, lastRight, lastBottom);

                        Log.e("TAG", "left = "+viewLeft + " top = "+viewTop + " right = "+viewRight + " bottom = "+viewBottom);
                        break;
                    case MotionEvent.ACTION_UP:
                        smoothScrollToBorder(view);
                        break;
                }
                return true;
            }
        });
        ((ViewGroup)view.getParent()).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(viewRight != 0) {
                    view.layout(lastLeft, lastTop, lastRight, lastBottom);
                }
            }
        });
    }

    private void checkTopAndBottom(int deltaY, View view) {
        int top = view.getTop();
        int bottom = view.getBottom();
        Log.e("TAG", "top = "+top + " bottom = "+bottom);
        ViewGroup parent = (ViewGroup) view.getParent();
        if(top  + deltaY < 0 || bottom  + deltaY > parent.getHeight()) {
            if(top + deltaY < 0) {
                view.offsetTopAndBottom(-top);
            }
            if(bottom + deltaY> parent.getHeight()) {
                view.offsetTopAndBottom(parent.getHeight() - bottom);
            }
        }else {
            view.offsetTopAndBottom(deltaY);
        }
    }

    private void smoothScrollToBorder(View view) {
        int splitLine = screenWidth / 2 - viewWidth / 2;
        final int left = view.getLeft();
        int top = view.getTop();
        int bottom = view.getBottom();
        int targetX;

        if (left < splitLine) {
            // 滑动到最左边
            targetX = 0;
        } else {
            // 滑动到最右边
            targetX = screenWidth - viewWidth;
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if(top < 0) {
            lastBottom = lastBottom - lastTop;
            lastTop = 0;
        }
        if(bottom > parent.getHeight()) {
            lastTop = parent.getHeight() - (lastBottom - lastTop);
            lastBottom = parent.getHeight();
        }

        ValueAnimator animator = ValueAnimator.ofInt(left, targetX);
        animator.setDuration(100)
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        if (view == null) return;

                        int value = (int) animation.getAnimatedValue();
                        view.offsetLeftAndRight(value - view.getLeft());
                    }
                });
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                lastRight = lastRight - lastLeft + targetX;
                lastLeft = targetX;
                view.requestLayout();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.start();
    }

    /**
     * 获取屏幕的基本信息
     * @param context
     * @return
     */
    public static float[] getScreenInfo(Context context) {
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        float[] info = new float[5];
        if(manager != null) {
            DisplayMetrics dm = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(dm);
            info[0] = dm.widthPixels;
            info[1] = dm.heightPixels;
            info[2] = dm.densityDpi;
            info[3] = dm.density;
            info[4] = dm.scaledDensity;
        }
        return info;
    }
}

