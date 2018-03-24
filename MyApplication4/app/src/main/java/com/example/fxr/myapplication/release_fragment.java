package com.example.fxr.myapplication;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import com.example.fxr.myapplication.releaseInformation.Moments_Adapter;
import com.pin.database.data.Activity;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by fxr on 2017/5/24.
 */

 public class release_fragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

     private Moments_Adapter adapter;
    private RecyclerView recyclerView;
    private List<Moments> momentses = new ArrayList<>();
    public static  String return_msg=null;
    private SwipeRefreshLayout mRefreshLayout;

    List<Activity> actvits = new ArrayList<>();




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

    //为recycleview 重写item类
    public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

        private int mSpace;

        public SpaceItemDecoration(Context context,int dpValue) {
            mSpace = dp2px(context,dpValue);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            if(parent.getChildAdapterPosition(view) > 0) {
                //从第二个条目开始，距离上方Item的距离
                outRect.top = mSpace;
            }
        }

        /**
         * dp to px转换
         * @param context
         * @param dpValue
         * @return
         */
        private int dp2px(Context context, int dpValue){
            int pxValue = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, context.getResources().getDisplayMetrics());
            return pxValue;
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.ac_2bar, menu);
    }

    public void fresh_ac(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(2000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                //
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        init1();
                        adapter.notifyDataSetChanged();
                        mRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();

    }

    @Override
    public void onRefresh() {
        mRefreshLayout.postDelayed(new Runnable() { // 发送延迟消息到消息队列
            @Override
            public void run() {
                fresh_ac();
                mRefreshLayout.setRefreshing(true); // 是否显示刷新进度;false:不显示
            }
        },1000);
    }

    private void init() {

        new Thread() {
            @Override
            public void run() {

                try {

                    Socket socket = new Socket("192.168.1.111", 28889);
                    PrintStream ps = null;
                    ObjectInputStream in = null;

                    ps = new PrintStream(socket.getOutputStream());

                    in = new ObjectInputStream(socket.getInputStream());
                    actvits = (List<Activity>) in.readObject();
                    if (actvits != null) {
                        ps.println("success");
                        ps.flush();
                        return_msg="初始化成功！";
                    }else {
                        return_msg="服务器繁忙！";
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i=actvits.size()-1; i>=0; i--) {
                                Moments m1 =
                                        new Moments(R.drawable.basketball, R.drawable.join_ac,actvits.get(i).getAc_theme() + "", "飞翔的企鹅", actvits.get(i).getAc_start().toString() + "",
                                                actvits.get(i).getAc_place() + "", actvits.get(i).getAc_number() + "",
                                                actvits.get(i).getAc_content() + "");
                                momentses.add(m1);
                                adapter.notifyDataSetChanged();
                            }
                            Toast.makeText(getActivity().getApplicationContext(), return_msg, Toast.LENGTH_SHORT).show();
                        }
                    });


                    in.close();
                    ps.close();
                    socket.close();
                } catch (UnknownHostException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }


    private void init1() {



        recyclerView.removeAllViews();
        momentses.clear();


        new Thread() {
            @Override
            public void run() {

                try {

                    Socket socket = new Socket("192.168.1.111", 28889);
                    PrintStream ps = null;
                    ObjectInputStream in = null;

                    ps = new PrintStream(socket.getOutputStream());

                    in = new ObjectInputStream(socket.getInputStream());
                    actvits = (List<Activity>) in.readObject();

                    if (actvits != null) {
                        ps.println("success");
                        ps.flush();
                        return_msg="刷新成功！";
                    }else {
                        return_msg="服务器繁忙！";
                    }


                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            for (int i=actvits.size()-1; i>=0; i--) {
                                Moments m1 =
                                        new Moments(R.drawable.basketball, R.drawable.join_ac, actvits.get(i).getAc_theme() + "", "飞翔的企鹅", actvits.get(i).getAc_start().toString() + "",
                                                actvits.get(i).getAc_place() + "", actvits.get(i).getAc_number() + "",
                                                actvits.get(i).getAc_content() + "");
                                momentses.add(m1);
                                adapter.notifyDataSetChanged();
                            }

                            adapter.notifyDataSetChanged();

                            Toast.makeText(getActivity().getApplicationContext(), return_msg, Toast.LENGTH_SHORT).show();
                        }
                    });


                    in.close();
                    ps.close();
                    socket.close();
                } catch (UnknownHostException | ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

//------------------------------------------


    //------------------------------------------


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


        recyclerView = (RecyclerView) contactsLayout.findViewById(R.id.ac_recycVu);
        SpaceItemDecoration decoration = new SpaceItemDecoration(getActivity(), 20);
        recyclerView.addItemDecoration(decoration);

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(manager);
        adapter = new Moments_Adapter(momentses);
        recyclerView.setAdapter(adapter);

        mRefreshLayout= (SwipeRefreshLayout) contactsLayout.findViewById(R.id.refresh);
        mRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRefreshLayout.setOnRefreshListener(this); // 设置刷新监听


        Toolbar mToolbarTb = (Toolbar) contactsLayout.findViewById(R.id.ac_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbarTb);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        mToolbarTb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int menuItemId = item.getItemId();

                if (menuItemId == R.id.plus) {


                    Toast.makeText(getActivity(), "添加活动", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), Add_moment.class);
                    startActivity(intent);

                }

                return true;
            }
        });
        //--------------------------------------------------------




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





