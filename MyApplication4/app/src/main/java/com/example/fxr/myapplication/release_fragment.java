package com.example.fxr.myapplication;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;
import com.example.fxr.myapplication.releaseInformation.Moments;


import java.util.ArrayList;
import java.util.List;



/**
 * Created by fxr on 2017/5/24.
 */

 public class release_fragment extends Fragment {
    private ListView listView;
    private List<Moments> momentses = new ArrayList<>();

//-------------------------------------------------
    /**
     * 展示时间的数据
     */
    private List<String> mTimes = new ArrayList<>();
    /**
     * 展示人数的数据
     */
    private List<String> mPeoples = new ArrayList<>();
    /**
     * 展示分类的数据
     */
    private List<String> mTypes = new ArrayList<>();
    /**
     * 筛选时间整体
     */
    private LinearLayout mTimeAll;
    /**
     * 筛选时间cb
     */
    private CheckBox mTimeCb;
    /**
     * 筛选人数整体
     */
    private LinearLayout mPeopleAll;
    /**
     * 筛选人数整体
     */
    private CheckBox mPeopleCb;
    /**
     * 筛选分类整体
     */
    private LinearLayout mTypeAll;
    /**
     * 筛选分类整体
     */
    private CheckBox mTypeCb;

    chooseItemFilterPop mPopupWindow;
    //--------------------------------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contactsLayout = inflater.inflate(R.layout.release_layout, container, false);
        init();


        Toolbar toolbar = (Toolbar)contactsLayout.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
       // final TextView textView=(TextView) contactsLayout.findViewById(R.id.pp_name);

        //------------------------------------------------------------- -----------------------------
        /**
         * 初始化控件
         */
         mPeopleAll = (LinearLayout)contactsLayout.findViewById(R.id.ll_people_tab);
        mPeopleCb = (CheckBox) contactsLayout.findViewById(R.id.cb_people);
         mTypeAll = (LinearLayout)contactsLayout.findViewById(R.id.ll_type_tab);
        mTypeCb = (CheckBox) contactsLayout.findViewById(R.id.cb_type);
         mTimeAll = (LinearLayout)contactsLayout.findViewById(R.id.ll_time_tab);
        mTimeCb = (CheckBox) contactsLayout.findViewById(R.id.cb_time);
        initView();
        initData();
        //----------------------------------------------------------------------------------------------


        // init listview 数据
        // 适配数据源  listview
        com.example.fxr.myapplication.releaseInformation.Moments_Adapter adapter = new
                com.example.fxr.myapplication.releaseInformation.Moments_Adapter(release_fragment.this, R.layout.moments_item, momentses);
        listView = (ListView) contactsLayout.findViewById(R.id.listView);
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


    public void initView() {

        // 点击选择时间整体
        mTimeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimeCb.isChecked())
                    mTimeCb.setChecked(false);
                else
                    mTimeCb.setChecked(true);
            }
        });
        // 点击选择人数整体
        mPeopleAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPeopleCb.isChecked())
                    mPeopleCb.setChecked(false);
                else
                    mPeopleCb.setChecked(true);
            }
        });
        // 点击选择分类整体
        mTypeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTypeCb.isChecked())
                    mTypeCb.setChecked(false);
                else
                    mTypeCb.setChecked(true);
            }
        });

        // 选择时间cb
        mTimeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mPopupWindow = new chooseItemFilterPop(getActivity(), mTimes);
                filterTabToggle(isChecked, mTimeAll, mTimes, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        hidePopListView();
                        mTimeCb.setText(mTimes.get(position));
                    }
                }, mPeopleCb, mTypeCb, mTimeCb);
            }
        });

        // 选择人数cb
        mPeopleCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mPopupWindow = new chooseItemFilterPop(getActivity(), mPeoples);
                filterTabToggle(isChecked, mTypeAll, mPeoples, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        hidePopListView();
                        mPeopleCb.setText(mPeoples.get(position));
                    }
                }, mTypeCb, mPeopleCb, mTimeCb);
            }
        });
        // 选择分类cb
        mTypeCb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
               mPopupWindow = new chooseItemFilterPop(getActivity(), mTypes);
                filterTabToggle(isChecked, mTypeAll, mTypes, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        hidePopListView();
                        mTypeCb.setText(mTypes.get(position));

                    }
                }, mTimeCb, mPeopleCb, mTypeCb);
            }
        });


    }

    public void initData() {

        /**
         * 初始化数据
         */
        // 初始化时间
        mTimes.add("1-3天");
        mTimes.add("4-5天");
        mTimes.add("6-10天");
        mTimes.add("10天以上");
        // 初始化类型,
        mTypes.add("美食");
        mTypes.add("电影");
        mTypes.add("户外");
        mTypes.add("运动");
        mTypes.add("其他");
        // 初始化人数
        mPeoples.add("1-3人");
        mPeoples.add("3-5人");
        mPeoples.add("5-10人");
        mPeoples.add("10人以上");
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

    /**
     * Tab筛选栏切换
     *
     * @param isChecked         选中状态
     * @param showMes           展示选择的数据
     * @param itemClickListener 点击回调
     * @param tabs              所有的cb(需要几个输入几个就可以,cb1,cb2....)
     */
    public void filterTabToggle(boolean isChecked, View showView, List<String> showMes, AdapterView.OnItemClickListener itemClickListener, final CheckBox... tabs) {
        if (isChecked) {
            if (tabs.length <= 0) {
                return;
            }
            // 第一个checkBox为当前点击选中的cb,其他cb进行setChecked(false);
            for (int i = 1; i < tabs.length; i++) {
                tabs[i].setChecked(false);
            }

            showFilterPopupWindow(showView, showMes, itemClickListener,
                    new CustomerDismissListener() {
                @Override
                public void onDismiss() {
                    super.onDismiss();
                    // 当pop消失时对第一个cb进行.setChecked(false)操作
                    tabs[0].setChecked(false);
                }
            });
        } else {
            // 关闭checkBox时直接隐藏popuwindow
            hidePopListView();
        }
    }

    /**
     * 隐藏pop
     */
    public void hidePopListView() {
        // 判断当前是否显示,如果显示则dismiss
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
    }

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemTexts         列表项文本集合
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      List<String> itemTexts,
                                      AdapterView.OnItemClickListener itemClickListener,
                                      CustomerDismissListener dismissListener) {
        showFilterPopupWindow(parentView, itemTexts, itemClickListener, dismissListener, 0);
    }

    /**
     * 列表选择popupWindow
     *
     * @param parentView        父View
     * @param itemTexts         列表项文本集合
     * @param itemClickListener 列表项点击事件
     */
    public void showFilterPopupWindow(View parentView,
                                      List<String> itemTexts,
                                      AdapterView.OnItemClickListener itemClickListener,
                                      CustomerDismissListener dismissListener, float alpha) {

        // 判断当前是否显示
        if (mPopupWindow != null && mPopupWindow.isShowing()) {
            mPopupWindow.dismiss();
            mPopupWindow = null;
        }
//        mPopupWindow = new chooseItemFilterPop(getActivity(), itemTexts);
        mPopupWindow.setOnDismissListener(dismissListener);
        // 绑定筛选点击事件
        mPopupWindow.setOnItemSelectedListener(itemClickListener);




            alpha =1.0f;

        // 设置背景透明度
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = alpha;

        getActivity().getWindow().setAttributes(lp);
        // 显示pop
        mPopupWindow.showAsDropDown(parentView);
    }


    /**
     * 自定义OnDismissListener
     */
    public class CustomerDismissListener implements PopupWindow.OnDismissListener {
        @Override
        public void onDismiss() {
            // 当pop消失的时候,重置背景色透明度
            WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
            lp.alpha = 1.0f;
            getActivity().getWindow().setAttributes(lp);
        }
    }

 }





