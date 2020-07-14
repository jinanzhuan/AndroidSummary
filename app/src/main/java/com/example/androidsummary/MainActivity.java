package com.example.androidsummary;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.androidsummary.android.AndroidIndexActivity;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.libraries.LibrariesIndexActivity;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
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
