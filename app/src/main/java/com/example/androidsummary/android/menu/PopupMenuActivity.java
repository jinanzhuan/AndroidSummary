package com.example.androidsummary.android.menu;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.view.menu.MenuPopupHelper;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.lang.reflect.Field;

public class PopupMenuActivity extends BaseTitleActivity {
    private Button btn_click;
    private Button btn_click2;
    private Button btn_click3;
    private int rawX;
    private int rawY;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PopupMenuActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_popup_menu;
    }

    @Override
    protected void initView() {
        super.initView();
        btn_click = findViewById(R.id.btn_click);
        btn_click2 = findViewById(R.id.btn_click2);
        btn_click3 = findViewById(R.id.btn_click3);
    }

    private void updatePopUpMenuItemLayout() {
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_click.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent ev) {
                rawX = (int) ev.getX();
                rawY = (int) ev.getY();
                Log.e("TAG", "执行了这里");
                return false;
            }
        });
        btn_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "rawX = "+rawX + " rawY = "+rawY);
                new PopupMenuHelper().showMenu(mContext, v, rawX, rawY);
            }
        });
        btn_click2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupMenuHelper().showMenu(mContext, v, rawX, rawY);
            }
        });

        btn_click3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PopupMenuHelper().showMenu(mContext, v, rawX, rawY);
            }
        });
    }
}

