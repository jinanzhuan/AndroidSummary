package com.example.androidsummary.android.widget.viewcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class ItemListView extends ListView {
    public ItemListView(Context context) {
        this(context, null);
    }

    public ItemListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MyListAdapter adapter = new MyListAdapter();
        setAdapter(adapter);
        List<String> data = new ArrayList<>();
        for(int i = 0; i < 30; i++) {
            data.add("index-"+i);
        }
        adapter.setData(data);
        setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = adapter.getItem(position);
                if(getContext() instanceof ViewComponentActivity) {
                    Container container = ((ViewComponentActivity) getContext()).getContainer();
                    container.showItem(item);
                }
            }
        });
    }
}

