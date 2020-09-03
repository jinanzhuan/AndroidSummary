package com.example.androidsummary.android.event;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.logging.LogRecord;

public class EventIndexActivity extends BaseTitleActivity {
    private RelativeLayout rl_touch;
    private ConstraintLayout cl_touch;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100 :
                    rl_touch.requestLayout();
                    handler.sendEmptyMessageDelayed(100, 500);
                    break;
            }
        }
    };

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, EventIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_event_index;
    }

    @Override
    protected void initView() {
        super.initView();
        rl_touch = findViewById(R.id.rl_touch);
        cl_touch = findViewById(R.id.cl_touch);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ViewScrollHelper.getInstance(mContext).makeViewCanScroll(rl_touch);
        cl_touch.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e("TAG", "onLayoutChange");
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        handler.sendEmptyMessage(100);
    }
}

