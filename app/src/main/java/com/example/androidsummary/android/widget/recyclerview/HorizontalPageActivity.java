package com.example.androidsummary.android.widget.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.horizontalpage.HorizontalPageLayoutManager;
import com.example.androidsummary.android.widget.recyclerview.horizontalpage.PagingScrollHelper;
import com.example.androidsummary.base.BaseTitleActivity;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPageActivity extends BaseTitleActivity {
    private RecyclerView rv_horizontal_page;
    private List<String> names = new ArrayList<>();
    private HorizontalAdapter adapter;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, HorizontalPageActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_horizontal_page;
    }

    @Override
    protected void initView() {
        super.initView();
        rv_horizontal_page = findViewById(R.id.rv_horizontal_page);

        PagingScrollHelper helper = new PagingScrollHelper();
        HorizontalPageLayoutManager manager = new HorizontalPageLayoutManager(2, 4);
        rv_horizontal_page.setHasFixedSize(true);
        rv_horizontal_page.setLayoutManager(manager);
        adapter = new HorizontalAdapter();
        rv_horizontal_page.setAdapter(adapter);

        helper.setUpRecycleView(rv_horizontal_page);
        helper.updateLayoutManger();
        helper.scrollToPosition(0);
        rv_horizontal_page.setHorizontalFadingEdgeEnabled(true);

    }

    @Override
    protected void initData() {
        super.initData();
        for(int i = 0; i < 30; i++) {
            names.add("test"+(i+1));
        }
        adapter.setData(names);
    }

    private class HorizontalAdapter extends RecyclerView.Adapter<HorizontalAdapter.ViewHolder> {
        private List<String> data;

        public void setData(List<String> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public HorizontalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            TextView tv = new TextView(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            tv.setLayoutParams(params);
            tv.setGravity(Gravity.CENTER);
            tv.setBackgroundColor(Color.GRAY);
            tv.setTextSize(16);
            return new ViewHolder(tv);
        }

        @Override
        public void onBindViewHolder(@NonNull HorizontalAdapter.ViewHolder holder, final int position) {
            String name = data.get(position);
            holder.tvName.setText(name);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "点击了position:"+position, Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView tvName;
            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = (TextView) itemView;
            }
        }
    }

}

