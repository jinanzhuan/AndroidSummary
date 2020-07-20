package com.example.androidsummary.android.widget.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.horizontalpage.EaseChatExtendMenu;
import com.example.androidsummary.base.BaseTitleActivity;
import com.example.androidsummary.common.CommonUtils;
import com.example.androidsummary.common.EaseTitleBar;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPageActivity extends BaseTitleActivity implements EaseChatExtendMenu.EaseChatExtendMenuItemClickListener {
    private EaseChatExtendMenu extendMenu;
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
        extendMenu = findViewById(R.id.rv_horizontal_page);
        Log.e("TAG", "screenWidth = "+ CommonUtils.getScreenInfo(mContext)[0]);
//        PagingScrollHelper helper = new PagingScrollHelper();
//        //LinearLayoutManager manager = new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false);
//        HorizontalPageLayoutManager manager = new HorizontalPageLayoutManager(2, 4);
//        rv_horizontal_page.setHasFixedSize(true);
//        rv_horizontal_page.setLayoutManager(manager);
//        adapter = new HorizontalAdapter();
//        rv_horizontal_page.setAdapter(adapter);
//
//        helper.setUpRecycleView(rv_horizontal_page);
//        helper.updateLayoutManger();
//        helper.scrollToPosition(0);
//        rv_horizontal_page.setHorizontalFadingEdgeEnabled(true);

        extendMenu.init();
        extendMenu.registerMenuItem(R.string.attach_picture, R.drawable.ease_chat_image_selector, 0, this);
        extendMenu.registerMenuItem(R.string.attach_take_pic, R.drawable.ease_chat_takepic_selector, 1, this);
        extendMenu.registerMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, 2, this);
        extendMenu.registerMenuItem(R.string.attach_picture, R.drawable.ease_chat_image_selector, 3, this);
        extendMenu.registerMenuItem(R.string.attach_take_pic, R.drawable.ease_chat_takepic_selector, 4, this);
        extendMenu.registerMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, 5, this);
        extendMenu.registerMenuItem(R.string.attach_picture, R.drawable.ease_chat_image_selector, 6, this);
        extendMenu.registerMenuItem(R.string.attach_take_pic, R.drawable.ease_chat_takepic_selector, 7, this);
        extendMenu.registerMenuItem(R.string.attach_video, R.drawable.em_chat_video_selector, 8, this);

    }

    @Override
    protected void initListener() {
        super.initListener();
        title_bar.setOnRightClickListener(new EaseTitleBar.OnRightClickListener() {
            @Override
            public void onRightClick(View view) {
                if(extendMenu.getVisibility() == View.GONE) {
                    extendMenu.setVisibility(View.VISIBLE);
                }else {
                    extendMenu.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
//        for(int i = 0; i < 30; i++) {
//            names.add("test"+(i+1));
//        }
//        adapter.setData(names);
    }

    @Override
    public void onChatExtendMenuItemClick(int itemId, View view) {
        Toast.makeText(HorizontalPageActivity.this, "itemId = "+itemId, Toast.LENGTH_SHORT).show();
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_layout_horizontal_page, parent, false);
            return new ViewHolder(view);
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
                tvName = itemView.findViewById(R.id.tv_name);
            }
        }
    }

}

