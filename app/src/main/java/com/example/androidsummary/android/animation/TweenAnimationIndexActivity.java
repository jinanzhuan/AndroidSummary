package com.example.androidsummary.android.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.base.IndexActivity;

public class TweenAnimationIndexActivity extends IndexActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, TweenAnimationIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setData() {
        data.add("TranslateAnimation");
        data.add("ScaleAnimation");
        data.add("RotateAnimation");
        data.add("AlphaAnimation");
        data.add("set");
    }

    @Override
    public void setOnItemClickListener(View v, int position) {
        switch (position) {
            case 0 :

                break;
            case 1 :

                break;
            case 2 :
                RotateAnimationActivity.actionStart(mContext);
                break;
            case 3 :

                break;
        }
    }
}

