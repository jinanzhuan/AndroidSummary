package com.example.androidsummary.android.animation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RotateAnimationActivity extends BaseTitleActivity implements View.OnClickListener {

    @BindView(R.id.iv_picture)
    ProgressBar ivPicture;
    @BindView(R.id.btn_start)
    Button btnStart;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RotateAnimationActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rotate_animation;
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start :
                startRotateAnimation();
                break;
        }
    }

    private void startRotateAnimation() {
        RotateAnimation animation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(500);
        animation.setRepeatCount(Animation.INFINITE);
        animation.setInterpolator(new LinearInterpolator());
        ivPicture.startAnimation(animation);
    }
}

