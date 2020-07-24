package com.example.androidsummary.android.widget.recyclerview.contactAdapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.common.OnItemClickListener;

import java.nio.file.WatchEvent;
import java.util.List;

public class HeaderAdapter extends RecyclerView.Adapter<HeaderAdapter.ViewHolder> {
    private List<HeaderBean> mData;
    private OnItemClickListener onItemClickListener;

    public HeaderAdapter(List<HeaderBean> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_header, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HeaderBean bean = mData.get(position);
        holder.tvContent.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvContent;
        private ImageView ivIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivIcon = itemView.findViewById(R.id.iv_icon);
        }
    }
}

