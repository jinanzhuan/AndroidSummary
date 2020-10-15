package com.example.androidsummary.android.widget.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

public class GridLayoutManagerActivity extends BaseTitleActivity implements View.OnClickListener {
    private RecyclerView rv_list;
    private Button btn_have_data;
    private Button btn_no_data;
    private List<String> data = new ArrayList<>();
    private GridLayoutAdapter adapter;
    private static final int SPAN_COUNT = 2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, GridLayoutManagerActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_grid_layout_manager;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_list = findViewById(R.id.rv_list);
        btn_have_data = findViewById(R.id.btn_have_data);
        btn_no_data = findViewById(R.id.btn_no_data);

        //设置layoutManager
        GridLayoutManager manager = new GridLayoutManager(mContext, SPAN_COUNT, RecyclerView.VERTICAL, false);
        rv_list.setLayoutManager(manager);

        //设置空白数据时，空白布局占据的spanCount
        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int viewType = rv_list.getAdapter().getItemViewType(position);
                if(viewType == -1) {
                    return SPAN_COUNT;
                }
                return 1;
            }
        });

        //设置适配器
        adapter = new GridLayoutAdapter();
        rv_list.setAdapter(adapter);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_have_data.setOnClickListener(this);
        btn_no_data.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_have_data :
                setData();
                break;
            case R.id.btn_no_data :
                clearData();
                break;
        }
    }

    private void clearData() {
        data.clear();
        adapter.notifyDataSetChanged();
    }

    private void setData() {
        for(int i = 0; i < 30; i++) {
            data.add("index"+(i+1));
        }
        adapter.setData(data);
    }
}

