package com.example.androidsummary.libraries.hx;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.constants.EaseConstant;

public class EaseLoginActivity extends BaseTitleActivity implements View.OnClickListener {
    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EaseLoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login :
                String username = etUsername.getText().toString().trim();
                String password = etPassword.getText().toString().trim();
                if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                    EMClient.getInstance().login(username, password, new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            EaseChatActivity.actionStart(mContext, "ljn", EaseConstant.CHATTYPE_SINGLE);
                        }

                        @Override
                        public void onError(int code, String error) {

                        }

                        @Override
                        public void onProgress(int progress, String status) {

                        }
                    });
                }
                break;
        }
    }
}

