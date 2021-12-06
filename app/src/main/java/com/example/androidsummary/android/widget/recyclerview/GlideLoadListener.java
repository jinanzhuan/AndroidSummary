package com.example.androidsummary.android.widget.recyclerview;

import androidx.annotation.Nullable;

import com.bumptech.glide.load.engine.GlideException;

public interface GlideLoadListener {
    void onResourceReady(int position);
    void onLoadFailed(int position, @Nullable GlideException e);
}
