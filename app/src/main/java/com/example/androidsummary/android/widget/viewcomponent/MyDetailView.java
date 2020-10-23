package com.example.androidsummary.android.widget.viewcomponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidsummary.R;

public class MyDetailView extends LinearLayout {
    TextView textView;

    private DetailPresenter presenter;

    public MyDetailView(Context context) {
        this(context, null);
    }

    public MyDetailView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyDetailView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        presenter = new DetailPresenter();
        if(context instanceof AppCompatActivity) {
            ((AppCompatActivity) context).getLifecycle().addObserver(presenter);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        presenter.setView(this);
        textView = (TextView) findViewById(R.id.text);
        findViewById(R.id.button).setOnClickListener(new OnClickListener() {
            @Override public void onClick(View v) {
                presenter.buttonClicked();
            }
        });
    }

    public void setItem(String item) {
        textView.setText(item);
    }
}

