package com.example.androidsummary.android.widget;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.progressbar.ProgressBarIndexActivity;
import com.example.androidsummary.android.widget.recyclerview.RecyclerViewIndexActivity;
import com.example.androidsummary.base.BaseTitleActivity;

public class WidgetIndexActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, WidgetIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_widget_index;
    }

    public void recyclerviewRelation(View view) {
        RecyclerViewIndexActivity.actionStart(mContext);
    }

    public void viewpagerRelation(View view) {
        MyViewPagerActivity.actionStart(mContext);
    }

    public void horizontalPage2(View view) {
        HorizontalPageActivity.actionStart(mContext);
    }

    public void progressBar(View view) {
        ProgressBarIndexActivity.actionStart(mContext);
    }
}

