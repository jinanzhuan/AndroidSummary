package com.example.androidsummary;

import android.app.Application;

import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseUI;

public class SummaryApp extends Application {
    private static SummaryApp instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        initHXSDK();
    }

    private void initHXSDK() {
        EaseUI.getInstance().init(this, null);
        EMClient.getInstance().setDebugMode(true);
    }

    public static SummaryApp getInstance() {
        return instance;
    }
}
