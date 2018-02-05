package com.example.fxr.myapplication.message;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.example.fxr.myapplication.R;

/**
 * Created by hxm on 2017/9/14.
 */

public class my_group extends Activity implements View.OnClickListener{

    private ImageView reply;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mygroup);
        init();
    }

    @Nullable


    private void init(){


        reply=(ImageView) findViewById(R.id.myGroupReturn_up);
        reply.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            case R.id.myGroupReturn_up:
                finish();
        }
    }
}
