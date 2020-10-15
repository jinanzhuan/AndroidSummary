package com.example.androidsummary.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
    public BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        initIntent();
        initView();
        initListener();
        initData();
    }

    protected abstract int getLayoutId();

    protected void initIntent() {}

    protected void initView() {}

    protected void initListener() {}

    protected void initData() {}
}
