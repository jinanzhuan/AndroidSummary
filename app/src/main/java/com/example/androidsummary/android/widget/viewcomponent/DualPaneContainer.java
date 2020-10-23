package com.example.androidsummary.android.widget.viewcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class DualPaneContainer extends LinearLayout implements Container {
    private MyDetailView detailView;
    public DualPaneContainer(Context context) {
        super(context);
    }

    public DualPaneContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DualPaneContainer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        detailView = (MyDetailView) getChildAt(1);
    }

    @Override
    public void showItem(String item) {
        detailView.setItem(item);
    }

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

