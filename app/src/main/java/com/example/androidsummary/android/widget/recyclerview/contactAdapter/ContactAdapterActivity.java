package com.example.androidsummary.android.widget.recyclerview.contactAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ConcatAdapter;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.common.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapterActivity extends BaseTitleActivity implements View.OnClickListener {
    private RecyclerView rvList;
    private Button btn_add;
    private Button btn_remove;
    private List<HeaderBean> headerData;
    private List<ContentBean> contentData;
    private HeaderAdapter headerAdapter;
    private ContentAdapter contentAdapter;
    private FooterAdapter footerAdapter;
    private ConcatAdapter adapter;
    private DividerItemDecoration decoration;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ContactAdapterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_contact_adapter;
    }

    @Override
    protected void initView() {
        super.initView();
        title_bar = findViewById(R.id.title_bar);
        rvList = findViewById(R.id.rv_list);
        btn_add = findViewById(R.id.btn_add);
        btn_remove = findViewById(R.id.btn_remove);

        headerData = new ArrayList<>();
        contentData = new ArrayList<>();

        //设置为垂直线性布局
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ConcatAdapter();
        headerAdapter = new HeaderAdapter(headerData);
        contentAdapter = new ContentAdapter(contentData);
        footerAdapter = new FooterAdapter();
        //添加不同的适配器
        adapter.addAdapter(headerAdapter);
        adapter.addAdapter(contentAdapter);
        adapter.addAdapter(footerAdapter);
        //设置适配器
        rvList.setAdapter(adapter);
        //添加DividerItemDecoration
        decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        decoration.setDrawable(ContextCompat.getDrawable(mContext, R.drawable.divider_bg));
        //设置itemDecoration
        rvList.addItemDecoration(decoration);

    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_add.setOnClickListener(this);
        btn_remove.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        HeaderBean header;
        for(int i = 0; i < 2; i++) {
            header = new HeaderBean();
            header.setName("header"+(i+1));
            headerData.add(header);
        }
        headerAdapter.notifyDataSetChanged();

        ContentBean content;
        for(int i = 0; i < 6; i++) {
            content = new ContentBean();
            content.setName("content"+(i+1));
            contentData.add(content);
        }
        contentAdapter.notifyDataSetChanged();

        headerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ContactAdapterActivity.this, "header position = "+position, Toast.LENGTH_SHORT).show();
            }
        });

        contentAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ContactAdapterActivity.this, "content position = "+position, Toast.LENGTH_SHORT).show();
            }
        });

        footerAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(ContactAdapterActivity.this, "footer position = "+position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add :
                rvList.addItemDecoration(decoration);
                break;
            case R.id.btn_remove :
                rvList.removeItemDecoration(decoration);
                break;
        }
    }
}

