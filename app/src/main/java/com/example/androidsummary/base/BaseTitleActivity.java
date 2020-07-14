package com.example.androidsummary.base;

import android.view.View;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.androidsummary.R;
import com.example.androidsummary.common.EaseTitleBar;

public abstract class BaseTitleActivity extends BaseActivity {
    protected EaseTitleBar title_bar;

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
        setTitle();
    }

    private void setTitle() {
        if(title_bar != null) {
            title_bar.setBackgroundColor(ContextCompat.getColor(mContext, R.color.colorPrimaryDark));
            title_bar.setToolbarCustomColor(R.color.white);
            title_bar.getTitle().setTextColor(ContextCompat.getColor(mContext, R.color.white));
        }
    }

    @Override
    protected void initListener() {
        super.initListener();
        if(title_bar != null) {
            title_bar.setOnBackPressListener(new EaseTitleBar.OnBackPressListener() {
                @Override
                public void onBackPress(View view) {
                    onBackPressed();
                }
            });
        }
    }

    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }
}
