package com.example.androidsummary.android.menu;

import android.content.Context;
import android.content.Intent;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.Calendar;

public class MenuIndexActivity extends BaseTitleActivity {
    private TextView tv_menu;
    private ActionMode actionMode;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MenuIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_menu_index;
    }

    @Override
    protected void initView() {
        super.initView();
        tv_menu = findViewById(R.id.tv_menu);
    }

    @Override
    protected void initListener() {
        super.initListener();
        ActionMode.Callback callback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.action_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_invite_agree :
                        Toast.makeText(MenuIndexActivity.this, "同意了", Toast.LENGTH_SHORT).show();
                        PopupMenuActivity.actionStart(mContext);
                        mode.finish();
                        return true;
                    case R.id.action_invite_refuse :
                        Toast.makeText(MenuIndexActivity.this, "拒绝了", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                    case R.id.action_invite_delete :
                        Toast.makeText(MenuIndexActivity.this, "删除了", Toast.LENGTH_SHORT).show();
                        mode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
            }
        };
        tv_menu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(actionMode != null) {
                    return false;
                }
                actionMode = mContext.startActionMode(callback);
                v.setSelected(true);
                return true;
            }
        });

    }
}

