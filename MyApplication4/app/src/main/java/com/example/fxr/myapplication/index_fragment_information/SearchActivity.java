package com.example.fxr.myapplication.index_fragment_information;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import com.example.fxr.myapplication.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by fxr on 2017/9/6.
 */

public class SearchActivity extends AppCompatActivity {
    private SearchView searchView;
    private Button search_back;
    private Button search_sure;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        //搜索框控件
        searchView=(SearchView)findViewById(R.id.searchview);
        searchView.setIconifiedByDefault(false);

        //返回按钮事件 不返回参数
        search_back=(Button)findViewById(R.id.search_back);
        search_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        //确定按钮事件 返回用户搜索信息
        search_sure=(Button) findViewById(R.id.search_sure);
        search_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               socketdemo();
            }
        });

    }
    public void socketdemo() {
        new Thread(){
            @Override
            public void run() {
                try{

                    Socket socket = new Socket("140.143.87.164", 28888);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    final String line = br.readLine();
                    Log.e("hxm", line);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), line, Toast.LENGTH_SHORT).show();
                        }
                    });
                    br.close();
                    socket.close();
                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();

    }



}

