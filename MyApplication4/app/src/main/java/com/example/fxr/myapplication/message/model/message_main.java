package com.example.fxr.myapplication.message.model;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxr.myapplication.R;
import com.example.fxr.myapplication.message.Msg;
import com.example.fxr.myapplication.message.adapter.MsgAdapter;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxm on 2017/11/5.
 */

public class message_main extends AppCompatActivity {
    private List<Msg> msgList = new ArrayList<>();
    private EditText inputText;
    private Button send;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;
    private String nickName;
    private ImageView reply;
    private TextView nick_textView;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Socket socket;
    private PrintStream ps;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_main);
        Intent nick_intent = getIntent();
        nickName = nick_intent.getStringExtra("nick");
        nick_textView = (TextView) findViewById(R.id.msg_NickName);
        nick_textView.setText(nickName);
        inputText = (EditText) findViewById(R.id.input_text);
        send = (Button) findViewById(R.id.send);
        msgRecyclerView = (RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        reply=(ImageView) findViewById(R.id.msg_return);
       // accept_message();
        setListener();

    }
    public void setListener() {

/**
 * 点击发送按钮把数据添加到界面，并上传至后台
 */
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                if (!"".equals(content)) {
                    final Msg msg = new Msg(
                            content,
                            Msg.TYPE_SEND,
                            nick_textView.getText().toString(),
                            nick_textView.getText().toString());
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);//当有消息的时候刷新RecyclerView显示
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);//将RecyclerView定位到最后一行
                    inputText.setText("");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket("192.168.1.111", 8087);
                                out = new ObjectOutputStream(socket.getOutputStream());
                                out.writeObject(msg);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                    hideKeyBoard();
                }
            }
        });
        reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //隐藏键盘
    private void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //   得到InputMethodManager的实例
        if (imm.isActive()) {
            //   如果开启
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT,
                    InputMethodManager.HIDE_NOT_ALWAYS);
            //   关闭软键盘，开启方法相同，这个方法是切换开启与关闭状态的
        }
    }

    /**
     * 使用Socket实现用户即时聊天
     */
    private void accept_message() {

        new Thread() {
            @Override
            public void run() {
                while(this.isAlive()){
                    try {
                            socket = new Socket("192.168.1.111", 8087);
                        if(socket!=null)
                        {  in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                            Object obj = in.readObject();
                            Msg msg2 = (Msg) obj;
                            Log.e("msg:",msg2.getContent());
                            msgList.add(msg2);
                        }
                    }catch(IOException  | ClassNotFoundException e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}


