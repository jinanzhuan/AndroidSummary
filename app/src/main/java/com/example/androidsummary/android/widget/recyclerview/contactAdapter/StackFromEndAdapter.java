package com.example.androidsummary.android.widget.recyclerview.contactAdapter;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.androidsummary.R;
import com.example.androidsummary.android.widget.recyclerview.GlideLoadListener;
import com.example.androidsummary.common.OnItemClickListener;

import java.util.List;

public class StackFromEndAdapter extends RecyclerView.Adapter<StackFromEndAdapter.ViewHolder> {
    private List<ContentBean> mData;
    private OnItemClickListener onItemClickListener;
    private GlideLoadListener glideLoadListener;

    public StackFromEndAdapter(List<ContentBean> data) {
        mData = data;
    }

    public void setGlideLoadListener(GlideLoadListener listener) {
        this.glideLoadListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_stack_from_end, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ContentBean bean = mData.get(holder.getBindingAdapterPosition());
        holder.tvContent.setText(bean.getName());
        Glide.with(holder.itemView)
                .load(bean.getUrl())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(glideLoadListener != null) {
                            glideLoadListener.onLoadFailed(holder.getBindingAdapterPosition(), e);
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(glideLoadListener != null) {
                            glideLoadListener.onResourceReady(holder.getBindingAdapterPosition());
                        }
                        return false;
                    }
                })
                .into(holder.iv_icon);
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
        private ImageView iv_icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContent = itemView.findViewById(R.id.tv_content);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }
    }
}

