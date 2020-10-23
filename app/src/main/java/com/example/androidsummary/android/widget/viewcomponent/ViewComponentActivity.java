package com.example.androidsummary.android.widget.viewcomponent;

import android.content.Context;
import android.content.Intent;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

public class ViewComponentActivity extends BaseTitleActivity {
    private Container container;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ViewComponentActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_component;
    }

    @Override
    protected void initView() {
        super.initView();
        container = findViewById(R.id.container);
    }

    public Container getContainer() {
        return container;
    }

    @Override
    public void onBackPressed() {
        boolean handled = container.onBackPressed();
        if(!handled) {
            finish();
        }
    }
}

