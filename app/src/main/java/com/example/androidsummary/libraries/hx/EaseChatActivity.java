package com.example.androidsummary.libraries.hx;

import android.content.Context;
import android.content.Intent;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.hyphenate.easeui.constants.EaseConstant;
import com.hyphenate.easeui.ui.chat.EaseChatFragment;

public class EaseChatActivity extends BaseTitleActivity {

    public static void actionStart(Context context, String username, int chatType) {
        Intent intent = new Intent(context, EaseChatActivity.class);
        intent.putExtra(EaseConstant.EXTRA_CHAT_TYPE, chatType);
        intent.putExtra(EaseConstant.EXTRA_USER_ID, username);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ease_chat;
    }

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
    }

    @Override
    protected void initData() {
        super.initData();
        EaseChatFragment fragment = new EaseChatFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_fragment, fragment).commit();
    }
}

