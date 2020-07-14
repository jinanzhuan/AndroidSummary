package com.example.androidsummary.libraries;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.libraries.Glide.GlideIndexActivity;

public class LibrariesIndexActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LibrariesIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_libraries_index;
    }

    public void glide_relation(View view) {
        GlideIndexActivity.actionStart(mContext);
    }
}
