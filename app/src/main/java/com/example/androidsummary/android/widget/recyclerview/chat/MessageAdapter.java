package com.example.androidsummary.android.widget.recyclerview.chat;

import android.text.Spannable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidsummary.R;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMTextMessageBody;
import com.hyphenate.easeui.adapter.EaseBaseRecyclerViewAdapter;
import com.hyphenate.easeui.utils.EaseImageUtils;
import com.hyphenate.easeui.utils.EaseSmileUtils;

public class MessageAdapter extends EaseBaseRecyclerViewAdapter<EMMessage> {
    private static final int TXT = 11;
    private static final int IMAGE = 12;
    private static final int VIDEO = 13;
    private static final int VOICE = 14;
    private static final int FILE = 15;
    private static final int LOCATION = 16;
    private static final int CUSTOM = 17;
    private static final int DEFAULT_TYPE = 18;

    @Override
    public int getItemViewType(int position) {
        int itemViewType = super.getItemViewType(position);
        if(itemViewType < 0) {
            return itemViewType;
        }
        int viewType = DEFAULT_TYPE;
        EMMessage emMessage = mData.get(position);
        EMMessage.Type type = emMessage.getType();
        if(type == EMMessage.Type.TXT) {
            viewType = TXT;
        }else if(type == EMMessage.Type.IMAGE) {
            viewType = IMAGE;
        }else if(type == EMMessage.Type.VIDEO) {
            viewType = VIDEO;
        }else if(type == EMMessage.Type.VOICE) {
            viewType = VOICE;
        }else if(type == EMMessage.Type.FILE) {
            viewType = FILE;
        }else if(type == EMMessage.Type.LOCATION) {
            viewType = LOCATION;
        }else if(type == EMMessage.Type.CUSTOM) {
            viewType = CUSTOM;
        }
        return viewType;
    }

    @Override
    public ViewHolder<EMMessage> getViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_chat, parent, false);
        return new MessageViewHolder(view);
    }

    private class MessageViewHolder extends ViewHolder<EMMessage> {
        private TextView tv_title;
        private ImageView iv_image;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            tv_title = findViewById(R.id.tv_title);
            iv_image = findViewById(R.id.iv_image);
        }

        @Override
        public void setData(EMMessage item, int position) {
            EMMessage.Type type = item.getType();
            tv_title.setText(type.name());

            if(type == EMMessage.Type.IMAGE) {
                EaseImageUtils.showImage(mContext, iv_image, item);
            }else if(type == EMMessage.Type.VIDEO) {
                EaseImageUtils.showVideoThumb(mContext, iv_image, item);
            }else if(type == EMMessage.Type.TXT) {
                Spannable smiledText = EaseSmileUtils.getSmiledText(mContext, ((EMTextMessageBody) item.getBody()).getMessage());
                tv_title.setText(smiledText, TextView.BufferType.SPANNABLE);
                String trim = tv_title.getText().toString().trim();
                tv_title.setText(type.name()+"\n\n"+trim);
            }
        }
    }
}
