package com.example.androidsummary.libraries.Glide;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

public class GlideIndexActivity extends BaseTitleActivity implements View.OnClickListener {
    private TextView tv_glide_round;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, GlideIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.glide_activity_index;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_glide_round = findViewById(R.id.tv_glide_round);
    }

    @Override
    protected void initListener() {
        super.initListener();
        tv_glide_round.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_glide_round ://圆角图片
                RoundGlideActivity.actionStart(mContext);
                break;
        }
    }
}
