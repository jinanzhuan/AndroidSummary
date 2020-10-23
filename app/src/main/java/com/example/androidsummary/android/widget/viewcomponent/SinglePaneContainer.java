package com.example.androidsummary.android.widget.viewcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidsummary.R;

public class SinglePaneContainer extends FrameLayout implements Container {
    private ItemListView listView;

    public SinglePaneContainer(@NonNull Context context) {
        super(context);
    }

    public SinglePaneContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SinglePaneContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        listView = (ItemListView) getChildAt(0);
    }

    @Override
    public void showItem(String item) {
        if(listViewAttached()) {
            removeViewAt(0);
            View.inflate(getContext(), R.layout.detail, this);
        }
        MyDetailView detailView = (MyDetailView) getChildAt(0);
        detailView.setItem(item);
    }

    @Override
    public boolean onBackPressed() {
        if(!listViewAttached()) {
            removeViewAt(0);
            addView(listView);
            return true;
        }
        return false;
    }

    private boolean listViewAttached() {
        return listView.getParent() != null;
    }
}

