package com.example.fxr.myapplication.message;

import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import com.example.fxr.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class addfriend extends ExpandableListActivity {
    /**
     * 创建一级条目容器
     */
    List<Map<String, String>> gruops = new ArrayList<Map<String, String>>();
    /**
     * 存放内容, 以便显示在列表中
     */
    List<List<Map<String, String>>> childs = new ArrayList<List<Map<String, String>>>();
private TextView nickName_text;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriends);
        setListData();
        init();
    }

    /**
     * 设置列表内容
     */
    public void setListData() {
        // 创建二个一级条目标题
        Map<String, String> title_1 = new HashMap<String, String>();
        Map<String, String> title_2 = new HashMap<String, String>();

        title_1.put("group", "好友");
        title_2.put("group", "明星");
        gruops.add(title_1);
        gruops.add(title_2);

        // 创建二级条目内容
        // 内容一
        Map<String, String> title_1_content_1 = new HashMap<String, String>();
        Map<String, String> title_1_content_2 = new HashMap<String, String>();
        Map<String, String> title_1_content_3 = new HashMap<String, String>();
        title_1_content_1.put("child", "李荣浩");
        title_1_content_2.put("child", "黄家驹");
        title_1_content_3.put("child", "李易峰");

        List<Map<String, String>> childs_1 = new ArrayList<Map<String, String>>();
        childs_1.add(title_1_content_1);
        childs_1.add(title_1_content_2);
        childs_1.add(title_1_content_3);

        // 内容二
        Map<String, String> title_2_content_1 = new HashMap<String, String>();
        Map<String, String> title_2_content_2 = new HashMap<String, String>();
        Map<String, String> title_2_content_3 = new HashMap<String, String>();
        title_2_content_1.put("child", "迪丽热巴");
        title_2_content_2.put("child", "李冰冰");
        title_2_content_3.put("child", "杨幂");
        List<Map<String, String>> childs_2 = new ArrayList<Map<String, String>>();
        childs_2.add(title_2_content_1);
        childs_2.add(title_2_content_2);
        childs_2.add(title_2_content_3);

        childs.add(childs_1);
        childs.add(childs_2);

        /**
         * 创建ExpandableList的Adapter容器 参数: 1.上下文 2.一级集合 3.一级样式文件 4. 一级条目键值
         * 5.一级显示控件名 6. 二级集合 7. 二级样式 8.二级条目键值 9.二级显示控件名
         *
         */
        SimpleExpandableListAdapter sela = new SimpleExpandableListAdapter(
                this, gruops, R.layout.groups, new String[] { "group" },
                new int[] { R.id.textGroup }, childs, R.layout.childs,
                new String[] { "child" }, new int[] { R.id.textChild });
        // 加入列表
        setListAdapter(sela);
    }

    /**
     * 列表内容按下
     */
    @Override
    public boolean onChildClick(ExpandableListView parent, View v,
                                int groupPosition, int childPosition, long id) {


        String nickName="";

        nickName_text=(TextView) v.findViewById(R.id.textChild);
        nickName+=nickName_text.getText().toString();
        Log.e("昵称为",nickName_text.getText().toString());
        Intent intent=new Intent();
        intent.setClass(addfriend.this,message_mainlayout.class);
        intent.putExtra("nick",nickName);

        startActivity(intent);
        return true;
    }

    /**
     * 二级标题按下
     */
   @Override
     public boolean setSelectedChild(int groupPosition, int childPosition,
                                    boolean shouldExpandGroup) {
        return super.setSelectedChild(groupPosition, childPosition,
                shouldExpandGroup);
    }

    /**
     * 一级标题按下
     */
    @Override
    public void setSelectedGroup(int groupPosition) {
        super.setSelectedGroup(groupPosition);
    }
    public void init(){
        ImageView goodfirend_return=(ImageView) findViewById(R.id.addFriendReturn_up);
        goodfirend_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }
}