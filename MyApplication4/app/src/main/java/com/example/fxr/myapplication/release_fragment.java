package com.example.fxr.myapplication;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.fxr.myapplication.releaseInformation.ExpandTabView;
import com.example.fxr.myapplication.releaseInformation.FilterTabView;
import com.example.fxr.myapplication.releaseInformation.Moments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by fxr on 2017/5/24.
 */

public class release_fragment extends Fragment implements ExpandTabView.OnFilterSelected {
    private ExpandTabView expandTabView;
    private ListView listView;
    private ArrayList<String> nameList;//顶部tab条目列表
    private List<Moments> momentses = new ArrayList<>();
    private List<Map<String,Object>>dataList;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contactsLayout = inflater.inflate(R.layout.release_layout, container,false);


        // 初始化布局元素
        expandTabView = (ExpandTabView) contactsLayout.findViewById(R.id.expand_tabview);
        expandTabView.setOnFilterSelected(this);
        nameList = new ArrayList<>();
        nameList.add("时间");
        nameList.add("人数");
        nameList.add("分类");
        expandTabView.setNameList(nameList);

        init();
        // init listview 数据
        // 适配数据源  listview
       com.example.fxr.myapplication.releaseInformation.Moments_Adapter adapter = new
               com.example.fxr.myapplication.releaseInformation.Moments_Adapter(getActivity(),
               R.layout.moments_item, momentses);
        listView = (ListView)contactsLayout.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        //listview  item 点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Moments moments
                        = momentses.get(position);
                Toast.makeText(getActivity(), "this is a pp_activity", Toast.LENGTH_SHORT).show();
            }
        });





        //
        FloatingActionButton floatingActionButton = (FloatingActionButton) contactsLayout.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Add_moment.class);
                startActivity(intent);
            }
        });
        return contactsLayout;

    }




    private void init() {
        for (int i = 0; i < 20; i++) {
            Moments apple =
                    new Moments(R.drawable.basketball,
                            "运动", "飞翔的企鹅", "2018.01.09", "四川理工学院操场", "3/6",
                            "周六下午，四川理工学院操场计算机学院班级友谊赛，速来报名！");
            momentses.add(apple);
            Moments banana =
                    new Moments(R.drawable.skate,
                            "户外", "飞翔的小鸟", "2017.02.09", "花海", "3/10",
                    "10号中午约一波花海自助游，这有俩妹子了，想朋友多点一起耍，赶快加入吧！");
            momentses.add(banana);
            Moments cherry_pic =
                    new Moments(R.drawable.burger,
                            "美食", "飞翔的兔子", "2017.03.09", "盐工号子", "3/8",
                    "还记得华商有一家很好吃的火锅，最近想去吃火锅了，找不到人，有没有小哥哥小姐姐一起啊，我们已经有三个了，再来几个啊，不嫌多！");
            momentses.add(cherry_pic);
        }
    }
    private ListView getTimeView() {
        ListView list_View = new ListView(getActivity());
        list_View.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"3天以内", "一周以内", "一周以上"})));
        return list_View;
    }

    private View getClassificationView() {
        ListView list_View = new ListView(getActivity());
        list_View.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"户外", "美食", "其他"})));
        return list_View;
    }
    private View getNumberView() {
        ListView list_View = new ListView(getActivity());
        list_View.setAdapter(new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, Arrays.asList(new String[]{"1-3人", "3-5人", "5-10人","10人以上"})));
        return list_View;
    }

    @Override
    public void onSelected(FilterTabView tabView, int position, boolean singleCheck) {
        if (singleCheck){
            if (position==0){
                expandTabView.setExpandView(getTimeView());
            }else if(position==1){
                expandTabView.setExpandView(getNumberView());
            }else{
                expandTabView.setExpandView(getClassificationView());
            }

        }
    }
}



