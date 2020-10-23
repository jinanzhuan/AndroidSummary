package com.example.androidsummary.android.widget.viewcomponent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.androidsummary.R;

import java.util.List;

public class MyListAdapter extends BaseAdapter {
    private List<String> mData;

    @Override
    public int getCount() {
        return (mData == null || mData.isEmpty()) ? 0 : mData.size();
    }

    @Override
    public String getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, null);
            holder = new MyViewHolder();
            holder.tv = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        }else {
            holder = (MyViewHolder) convertView.getTag();
        }
        String item = mData.get(position);
        holder.tv.setText(item);
        return convertView;
    }

    public void setData(List<String> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    private class MyViewHolder {
        private TextView tv;
    }
}

