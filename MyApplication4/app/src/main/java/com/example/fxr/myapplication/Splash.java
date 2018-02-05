package com.example.fxr.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

/**
 * Created by hxm on 2017/7/20.
 */

public class Splash extends Activity{
    private final int displayTime=1000;
    private Handler handler;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {

            public void run() {
                Intent intent=new Intent();
                intent.setClass(Splash.this,MainActivity.class);
                startActivity(intent);
                Splash.this.finish();
            }
        },displayTime);
    }
}