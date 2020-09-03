package com.example.androidsummary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import android.Manifest;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidsummary.android.AndroidIndexActivity;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.libraries.LibrariesIndexActivity;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.util.List;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        super.initData();
        requestPermission();

    }

    private void requestPermission() {
        AndPermission.with(this)
                .runtime()
                .permission(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {

                    }
                })
                .start();
    }

    public void androidRelation(View view) {
        AndroidIndexActivity.actionStart(mContext);
    }

    public void javaRelation(View view) {

    }

    public void librariesRelation(View view) {
        LibrariesIndexActivity.actionStart(mContext);
    }
}
