package com.example.androidsummary.android.widget.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.contactAdapter.ContactAdapterActivity;
import com.example.androidsummary.base.BaseTitleActivity;

public class RecyclerViewIndexActivity extends BaseTitleActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RecyclerViewIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_recyclerview_index;
    }

    public void horizontalPage(View view) {
        HorizontalPageActivity.actionStart(mContext);
    }

    public void concatAdapter(View view) {
        ContactAdapterActivity.actionStart(mContext);
    }
}

