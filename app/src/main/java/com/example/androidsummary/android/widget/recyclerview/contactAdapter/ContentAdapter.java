package com.example.androidsummary.android.widget.recyclerview.contactAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.androidsummary.R;
import com.example.androidsummary.common.OnItemClickListener;

import java.util.List;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {
    private List<ContentBean> mData;
    private OnItemClickListener onItemClickListener;

    public ContentAdapter(List<ContentBean> data) {
        mData = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentBean bean = mData.get(position);
        holder.tvContent.setText(bean.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int absoluteAdapterPosition = holder.getAbsoluteAdapterPosition();
                int bindingAdapterPosition = holder.getBindingAdapterPosition();
                Log.e("TAG", "absoluteAdapterPosition = "+absoluteAdapterPosition + " bindingAdapterPosition = "+bindingAdapterPosition);
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(v, bindingAdapterPosition);
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }
}

