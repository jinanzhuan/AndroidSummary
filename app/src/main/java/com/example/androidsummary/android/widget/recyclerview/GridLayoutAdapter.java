package com.example.androidsummary.android.widget.recyclerview;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.androidsummary.android.base.BaseRecyclerViewAdapter;

public class GridLayoutAdapter extends BaseRecyclerViewAdapter<String> {

    @Override
    public ViewHolder getViewHolder(ViewGroup parent, int viewType) {
        TextView tv = new TextView(parent.getContext());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 200);
        tv.setLayoutParams(params);
        tv.setTextSize(16);
        tv.setTextColor(Color.BLACK);
        tv.setGravity(Gravity.CENTER);
        return new MyViewHolder(tv);
    }

    private class MyViewHolder extends ViewHolder<String> {
        private TextView name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void initView(View itemView) {
            name = (TextView) itemView;
        }

        @Override
        public void setData(String item, int position) {
            name.setText(item);
        }
    }
}

