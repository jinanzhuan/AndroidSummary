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
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;

import java.util.List;

public class StackFromEndAdapter extends EaseBaseRecyclerViewAdapter<ContentBean> {
    private OnItemClickListener onItemClickListener;
    private GlideLoadListener glideLoadListener;

    public void setGlideLoadListener(GlideLoadListener listener) {
        this.glideLoadListener = listener;
    }

    @Override
    public ViewHolder<ContentBean> getViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item_stack_from_end, parent, false));
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    public class MyViewHolder extends ViewHolder<ContentBean> {
        private TextView tvContent;
        private ImageView iv_icon;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            tvContent = itemView.findViewById(R.id.tv_content);
            iv_icon = itemView.findViewById(R.id.iv_icon);
        }

        @Override
        public void setData(ContentBean bean, int position) {
            tvContent.setText(bean.getName());
            Glide.with(itemView)
                    .load(bean.getUrl())
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(glideLoadListener != null) {
                                glideLoadListener.onLoadFailed(getBindingAdapterPosition(), e);
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(glideLoadListener != null) {
                                glideLoadListener.onResourceReady(getBindingAdapterPosition());
                            }
                            return false;
                        }
                    })
                    .into(iv_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int absoluteAdapterPosition = getAbsoluteAdapterPosition();
                    int bindingAdapterPosition = getBindingAdapterPosition();
                    Log.e("TAG", "absoluteAdapterPosition = "+absoluteAdapterPosition + " bindingAdapterPosition = "+bindingAdapterPosition);
                    if(onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, bindingAdapterPosition);
                    }
                }
            });
        }
    }
}

