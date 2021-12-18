package com.example.androidsummary.android.widget.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.chat.ChatActivity;
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

    public void gridEmpty(View view) {
        GridLayoutManagerActivity.actionStart(mContext);
    }

    public void stackFromEnd(View view) {
        StackFromEndActivity.actionStart(mContext);
    }

    public void simpleChat(View view) {
        ChatActivity.actionStart(mContext);
    }
}

