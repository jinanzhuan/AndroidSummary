package com.example.androidsummary.android.widget.progressbar;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

public class ProgressBarIndexActivity extends BaseTitleActivity implements View.OnClickListener {
    private RecyclerView rv_list;
    private Button btn_refresh;
    private ProgressBarAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ProgressBarIndexActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_progress_bar_index;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_list = findViewById(R.id.rv_list);
        btn_refresh = findViewById(R.id.btn_refresh);
    }

    @Override
    protected void initListener() {
        super.initListener();
        btn_refresh.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        rv_list.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new ProgressBarAdapter();
        rv_list.setAdapter(adapter);

        List<String> names = new ArrayList<>();
        for(int i = 0; i < 20; i++) {
            names.add("position"+(i+1));
        }

        adapter.setData(names);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_refresh :
                adapter.notifyDataSetChanged();
                break;
        }
    }

    private class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarAdapter.ViewHolder> {
        private List<String> mData;

        @NonNull
        @Override
        public ProgressBarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_progress_bar, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            //viewHolder.setIsRecyclable(false);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ProgressBarAdapter.ViewHolder holder, int position) {
            Log.e("TAG", "position = " + position + " view = "+holder.itemView);
            holder.tv_name.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public void setData(List<String> data) {
            this.mData = data;
            notifyDataSetChanged();
        }

        public void notifyData() {
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ProgressBar pb_loading;
            private TextView tv_name;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                pb_loading = itemView.findViewById(R.id.pb_loading);
                tv_name = itemView.findViewById(R.id.tv_name);
            }

        }
    }
}

