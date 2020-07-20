package com.example.androidsummary.android.widget;

import android.content.Context;
import android.content.Intent;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

public class MyViewPagerActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyViewPagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_pager;
    }
}

