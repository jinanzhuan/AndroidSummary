package com.example.androidsummary.android.animation;

import android.content.Context;
import android.content.Intent;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

public class FrameAnimationActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, FrameAnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_frame_animation;
    }
}

