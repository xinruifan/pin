package com.example.fxr.myapplication.mineInformation;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.fxr.myapplication.R;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by &nbsp on 2017/9/20.
 */

public class Mine_login extends Activity{
    private String res;
    private Button login;
    private TextView callBack;
    public static Boolean isNotLogin=true;
    private String myUrl="http://171.94.200.19:83/ServerTest/login";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_login);
        login=(Button) findViewById(R.id.login);
        callBack= (TextView) findViewById(R.id.callBack);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    doget();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doget() throws IOException {
        OkHttpClient ohc=new OkHttpClient();
        Request.Builder builder=new Request.Builder();
        final Request request=builder.get().url(myUrl).build();
        Call call=ohc.newCall(request);
       // Response response=call.execute();
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("IOException:",e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
              res=response.body().string();
                Log.i("isnotLogin"," ="+isNotLogin);
                if(res.equals("success!"))isNotLogin=true;
                Log.i("isnotLogin"," ="+isNotLogin);
                Log.i("callback response",res);
                runOnUiThread(new Runnable() {

                    public void run() {
                        callBack.setText(res);
                    }
                });
            }
        });
    }
}
