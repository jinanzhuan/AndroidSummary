package com.example.androidsummary.android;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.WidgetIndexActivity;
import com.example.androidsummary.base.BaseTitleActivity;

public class AndroidIndexActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AndroidIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_android_index;
    }

    public void widgetRelation(View view) {
        WidgetIndexActivity.actionStart(mContext);
    }

    public void animationRelation(View view) {

    }
}

