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
                com.example.fxr.myapplication.releaseInformation.Moments moments
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
            com.example.fxr.myapplication.releaseInformation.Moments apple =
                    new com.example.fxr.myapplication.releaseInformation.Moments(R.drawable.basketball,
                            "   basketball  ", "   33  ",
                    "  2017.09.09 下午4点  ", "  帅呆了sad  ", "   3/5   ");
            momentses.add(apple);
            com.example.fxr.myapplication.releaseInformation.Moments banana =
                    new com.example.fxr.myapplication.releaseInformation.Moments(R.drawable.skate,
                            "  skate  ", "   44",
                    "  2017.09.010 x下午  ", "  adas  ", "   3/9   ");
            momentses.add(banana);
            com.example.fxr.myapplication.releaseInformation.Moments cherry_pic =
                    new com.example.fxr.myapplication.releaseInformation.Moments(R.drawable.burger,
                            "   burger  ", "   打死9  ",
                    "  2017.09.29 大时代  ", "  得问问  ", "   13/15   ");
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



