package com.example.fxr.myapplication.releaseInformation;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.fxr.myapplication.R;


public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attributeSet) {
        super(context);
        //动态加载标题布局
        LayoutInflater.from(context).inflate(R.layout.title, this);
        Button buttonback = (Button) findViewById(R.id.back);
        buttonback.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity) getContext()).finish();
            }
        });
    }
}
