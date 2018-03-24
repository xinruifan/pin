package com.example.fxr.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.fxr.myapplication.message.addfriend;
import com.example.fxr.myapplication.message.model.message_main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by fxr on 2017/5/24.
 */

public class message_fragment extends Fragment implements View.OnClickListener{
    private ImageView addfriends;
    //生成三个储存信息的数组
    private  String[] names=new String[]{"小李","小王","小张"};
    private String[] msgs=new String[]{"你好呀！","明天一起玩吧","周末一起打台球吧"};
    private  int[] head_imgs=new int[]{R.drawable.h1,R.drawable.h2,R.drawable.h3};
private  ListView list;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contactsLayout = inflater.inflate(R.layout.message_layout, container, false);
        // 初始化布局元素

        //创建一个List集合，集合的参数为map
        List<Map<String,Object>> msg_lists =new ArrayList<Map<String,Object>>();
        for(int i=0;i<names.length;i++){
          Map<String,Object>  msg_item=new HashMap<String,Object>();
            msg_item.put("header",head_imgs[i]);
            msg_item.put("name",names[i]);
            msg_item.put("msg",msgs[i]);
            msg_lists.add(msg_item);
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(getActivity(),msg_lists,
                R.layout.message_mymsg,     //每个Item显示的XML文件
                new String[]{"name","header","msg"}, //用Map里面的键值对填充下面的控件
                new int[]{R.id.msg_name,R.id.msg_head,R.id.msg_msg}
                );
         list=(ListView)contactsLayout.findViewById(R.id.msg_list);
        list.setAdapter(simpleAdapter);
        //为listView添加监听器
        setOnclickListener();
        init(contactsLayout);
        return contactsLayout;
    }
    //初始化toolbar并且添加监听器
public void init(View v){
    Toolbar toolbar = (Toolbar)v.findViewById(R.id.message_toolbar);
    //((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    setHasOptionsMenu(true);
    addfriends=(ImageView)v.findViewById(R.id.addFriend_image);
    addfriends.setOnClickListener(this);
}
//进入好友页面
public void onClick(View v){
    switch (v.getId()){
        case R.id.addFriend_image:
            Intent intent=new Intent(getActivity().getApplicationContext(),addfriend.class);
            startActivity(intent);
break;
    }
}
//给每个消息Item添加监听器
public void setOnclickListener(){
    list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
           String nick_name=names[i];
            Intent intent = new Intent();
            intent.setClass(getActivity().getApplicationContext(), message_main.class);
            intent.putExtra("nick",nick_name);
            startActivity(intent);

        }
    });
}
}
