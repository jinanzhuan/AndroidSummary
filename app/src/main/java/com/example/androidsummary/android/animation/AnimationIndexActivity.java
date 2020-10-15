package com.example.androidsummary.android.animation;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.progressbar.ProgressBarIndexActivity;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.base.IndexActivity;

public class AnimationIndexActivity extends IndexActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, AnimationIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void setData() {
        data.add("补间动画");
        data.add("帧动画");
        data.add("属性动画");
    }

    @Override
    public void setOnItemClickListener(View v, int position) {
        switch (position) {
            case 0 :
                TweenAnimationIndexActivity.actionStart(mContext);
                break;
            case 1 :
                FrameAnimationActivity.actionStart(mContext);
                break;
            case 2 :
                PropertyAnimationIndexActivity.actionStart(mContext);
                break;
        }
    }
}

