package com.example.androidsummary.libraries.video;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pManager;
import android.service.voice.AlwaysOnHotwordDetector;
import android.util.Log;

import com.afollestad.easyvideoplayer.EasyVideoCallback;
import com.afollestad.easyvideoplayer.EasyVideoPlayer;
import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.io.File;
import java.io.InputStream;

public class EasyVideoActivity extends BaseTitleActivity implements EasyVideoCallback {
    private EasyVideoPlayer evPlayer;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EasyVideoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_easy_video;
    }

    @Override
    protected void initView() {
        super.initView();
        evPlayer = findViewById(R.id.ev_player);
        evPlayer.setAutoPlay(true);
        evPlayer.setHideControlsOnPlay(true);
    }

    @Override
    protected void initData() {
        super.initData();
        File video = getExternalFilesDir("video");
        if(!video.exists()) {
            video.mkdirs();
        }
        File file = new File(video, "VID_20180802_141510.mp4");
        if(file.exists()) {
            evPlayer.setCallback(this);
            evPlayer.setSource(Uri.fromFile(file));
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(evPlayer != null) {
            evPlayer.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(evPlayer != null) {
            evPlayer.release();
            evPlayer = null;
        }
    }

    @Override
    public void onStarted(EasyVideoPlayer player) {
        Log.e("TAG", "onStarted");
    }

    @Override
    public void onPaused(EasyVideoPlayer player) {
        Log.e("TAG", "onPaused");
    }

    @Override
    public void onPreparing(EasyVideoPlayer player) {
        Log.e("TAG", "onPreparing");
    }

    @Override
    public void onPrepared(EasyVideoPlayer player) {
        Log.e("TAG", "onPrepared");
        player.start();
    }

    @Override
    public void onBuffering(int percent) {

    }

    @Override
    public void onError(EasyVideoPlayer player, Exception e) {
        Log.e("TAG", "onError message = "+e.getMessage());
    }

    @Override
    public void onCompletion(EasyVideoPlayer player) {
        Log.e("TAG", "onPrepared");
    }

    @Override
    public void onClickVideoFrame(EasyVideoPlayer player) {

    }
}

