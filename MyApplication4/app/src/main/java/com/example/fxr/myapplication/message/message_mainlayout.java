package com.example.fxr.myapplication.message;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fxr.myapplication.R;
import com.example.fxr.myapplication.message.TestData.TestData;
import com.example.fxr.myapplication.message.adapter.ChatAdapter;
import com.example.fxr.myapplication.message.model.ChatModel;
import com.example.fxr.myapplication.message.model.ItemModel;

import java.util.ArrayList;

/**
 * Created by hxm on 2017/9/27.
 */

public class message_mainlayout extends AppCompatActivity {

    private TextView nickname;
    private String nick_name;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private EditText et;
    private TextView tvSend;
    private String content;
    private  TextView  message_reload;
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.message_mesages);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        nick_name=bundle.getString("nick");
        Log.e("获取的id为：",nick_name);
        nickname=(TextView) findViewById(R.id.message_nick_name);
        nickname.setText(nick_name);
        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        message_reload=(TextView) findViewById(R.id.chat_msg_TextView);
        et = (EditText) findViewById(R.id.et);
        tvSend = (TextView) findViewById(R.id.tvSend);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        //设置适配器
        recyclerView.setAdapter(adapter=new ChatAdapter());
        adapter.replaceAll(TestData.getTestAdData());
        init();

init();
        super.onCreate(savedInstanceState);
    }

    /**
     * 给编辑框加监听器
     */
    public void init(){


        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable  s) {
                content = s.toString().trim();
            }
        });
        /**
         * 点击发送按钮
         */
        tvSend.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.M)

            @Override
            public void onClick(View view) {
                ArrayList<ItemModel> data=new ArrayList<>();
                ChatModel model= new ChatModel();
                model.setIcon("http://b346.photo.store.qq.com/psb?/V10rgyvD0Nz3t7/HkmbtFiJe2OqAvan86*rtPxIOEw*T99GLQsmXmFhHvg!/b/dFoBAAAAAAAA&amp;bo=gAJVAwAAAAABAPM!&amp;rf=viewer_4");
                model.setContent(content);
                data.add(new ItemModel(ItemModel.CHAT_B,model));
                adapter.addAll(data);
                et.setText("");
                //隐藏键盘
                hideKeyBorad(et);

            }
        });
        /**
         * 点击消息按钮返回
         */
        message_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
    private void hideKeyBorad(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
        }
    }
}
