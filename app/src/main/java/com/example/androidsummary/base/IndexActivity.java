package com.example.androidsummary.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;

import java.util.ArrayList;
import java.util.List;

public abstract class IndexActivity extends BaseTitleActivity {
    private RecyclerView rvList;
    public IndexAdapter adapter;
    public List<String> data;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_index;
    }

    @Override
    protected void initView() {
        super.initView();
        rvList = findViewById(R.id.rv_list);
    }

    @Override
    protected void initData() {
        super.initData();
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new IndexAdapter();
        rvList.setAdapter(adapter);

        data = new ArrayList<>();

        setData();
        adapter.setData(data);
    }

    public abstract void setData();

    public abstract void setOnItemClickListener(View v, int position);

    public class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {
        private List<String> mData;

        @NonNull
        @Override
        public IndexAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.item_common_index, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull IndexAdapter.ViewHolder holder, int position) {
            holder.tvContent.setText(mData.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener(v, position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mData == null ? 0 : mData.size();
        }

        public void setData(List<String> data) {
            this.mData = data;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvContent;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvContent = itemView.findViewById(R.id.tv_content);
            }
        }
    }

}

