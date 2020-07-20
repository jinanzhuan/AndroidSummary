package com.example.androidsummary.android.widget;

import android.content.Context;
import android.content.Intent;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

public class HorizontalPageActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HorizontalPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_page2;
    }
}

