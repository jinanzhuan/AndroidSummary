package com.example.androidsummary.libraries.Glide;

import android.content.Context;
import android.content.Intent;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.common.CommonUtils;

public class RoundGlideActivity extends BaseTitleActivity {
    private ImageView iv_demo1;
    private ImageView iv_demo2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RoundGlideActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.glide_activity_round;
    }

    @Override
    protected void initView() {
        super.initView();
        iv_demo1 = findViewById(R.id.iv_demo1);
        iv_demo2 = findViewById(R.id.iv_demo2);
    }

    @Override
    protected void initData() {
        super.initData();
        RequestOptions options = RequestOptions.circleCropTransform();
        Glide.with(mContext).load(R.drawable.test1).apply(options).into(iv_demo1);

        RoundedCorners corners = new RoundedCorners((int) CommonUtils.dip2px(mContext, 20));
        RequestOptions options2 = RequestOptions.bitmapTransform(corners);
        Glide.with(mContext).load(R.drawable.test1).apply(options2).into(iv_demo2);
    }
}
