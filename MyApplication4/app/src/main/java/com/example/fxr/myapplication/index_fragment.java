package com.example.fxr.myapplication;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import com.example.fxr.myapplication.index_fragment_information.SearchActivity;
import com.example.fxr.myapplication.index_fragment_information.markbean;
import com.example.fxr.myapplication.releaseInformation.Moments;
import com.example.fxr.myapplication.releaseInformation.Moments_Adapter;
import com.pin.database.data.Activity;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by fxr on 2017/5/24.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class index_fragment extends Fragment {
    private MapView mMapView;
    private BaiduMap baidumap;
    private ImageView my_location;
    private ImageView reload;


    private Moments_Adapter adapter;
    private RecyclerView recyclerView;
    private List<Moments> momentses = new ArrayList<>();

    private SwipeRefreshLayout mRefreshLayout;

    List<Activity> actvits = new ArrayList<>();


    //定位
    private LocationClient mLocationClient;
    private MyLocationListener mLocationlistener;
    private boolean isFristIn = true;
    private double mLatitude;
    private double mLongitude;
     private  Handler handler;
    //覆盖物
    private static  List<markbean> marks;
    private static  List<markbean> marks1;
    private BitmapDescriptor mMarker;
    private RelativeLayout r_marker;
    private BitmapDescriptor bitmapDescriptor;
    private Marker marker;


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

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SDKInitializer.initialize(getActivity().getApplicationContext());
        View contactsLayout = inflater.inflate(R.layout.index_layout, container, false);
        mMapView = (MapView) contactsLayout.findViewById(R.id.mapView_1);
        my_location = (ImageView) contactsLayout.findViewById(R.id.myLocation);
        r_marker = (RelativeLayout) contactsLayout.findViewById(R.id.r_marker);
        reload=(ImageView) contactsLayout.findViewById(R.id.reload);
        mapView();
        setMarks();
        //初始定位
        initLocation();
        initmylocation();

        //覆盖物图标实现


        markClick();
        //筛选
//------------------------------------------------------------- -----------------------------
        /**
         * 初始化控件
         */
        mPeopleAll = (LinearLayout) contactsLayout.findViewById(R.id.ll_people_tab);
        mPeopleCb = (CheckBox) contactsLayout.findViewById(R.id.cb_people);
        mTypeAll = (LinearLayout) contactsLayout.findViewById(R.id.ll_type_tab);
        mTypeCb = (CheckBox) contactsLayout.findViewById(R.id.cb_type);
        mTimeAll = (LinearLayout) contactsLayout.findViewById(R.id.ll_time_tab);
        mTimeCb = (CheckBox) contactsLayout.findViewById(R.id.cb_time);
        initView();
        initData();
        //----------------------------------------------------------------------------------------------


        Toolbar toolbar = (Toolbar) contactsLayout.findViewById(R.id.index_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        final ImageView searcf = (ImageView) contactsLayout.findViewById(R.id.search);
        searcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
        return contactsLayout;
    }
    /**
     * 自定义位置 四川理工学院
     */

    /**
     * 用于地图显示覆盖物的方法
     */
    public void  setMarks() {

        //获取活动所有数据并构建Marker图标
        new Thread() {
            @Override
            public void run() {
                try {

                    Socket socket;
                    //创建连接
                    socket = new Socket("192.168.1.110", 28887);

                    //输入流的封装
                    BufferedReader br = new BufferedReader(new InputStreamReader(
                            socket.getInputStream(),"utf-8"

                    ));

                    String acjson = br.readLine();

                    //String  reslut= URLEncoder.encode(acjson.toString(), "utf-8");
                    //String reslut =new String(acjson.getBytes("gbk"),"UTF-8");
                    Log.e("sda",acjson);
                    JSONArray jsonarray = new JSONArray(acjson);

                    // 创建封装数据的集合
                    marks = new ArrayList<>();
                    for (int i = 0; i < jsonarray.length(); i++) {
                        markbean ac = new markbean();
                        ac.setAc_id(Integer.parseInt(jsonarray
                                .getJSONObject(i).getString("ac_id")));
                        ac.setAc_theme(jsonarray
                                .getJSONObject(i).getString("ac_theme"));
                        ac.setAc_place(jsonarray.getJSONObject(i).getString("ac_place"));
                        ac.setAc_number(Integer.parseInt(jsonarray
                                .getJSONObject(i).getString("ac_number")));
                        ac.setAc_start(jsonarray
                                .getJSONObject(i).getString("ac_start"));
                        ac.setAc_content(jsonarray
                                .getJSONObject(i).getString("ac_content"));
                        ac.setUser_id(Integer.parseInt(jsonarray
                                .getJSONObject(i).getString("user_id")));
                        ac.setAc_list(jsonarray
                                .getJSONObject(i).getString("ac_list"));
                        ac.setAc_latitude(Double.parseDouble(jsonarray
                                .getJSONObject(i).getString("ac_latitude")));
                        ac.setAc_longitude(Double.parseDouble(jsonarray
                                .getJSONObject(i).getString("ac_longitude")));

                        marks.add(ac);


                    }
                  getActivity().runOnUiThread(new Runnable() {
                      @Override
                      public void run() {
                          LatLng latLng = null;
                          BitmapDescriptor bitmap = BitmapDescriptorFactory
                                  .fromResource(R.drawable.mark1);
                          OverlayOptions options;
                          Log.e("测试数据",marks.size()+"");
                          for (markbean info : marks) {
                              //获取经纬度

                              latLng = new LatLng(info.getAc_latitude(), info.getAc_longitude());
                              //设置marker
                              options = new MarkerOptions()
                                      .position(latLng)//设置位置
                                      .icon(bitmap)//设置图标样式
                                      .zIndex(9) // 设置marker所在层级
                                      .draggable(true); // 设置手势拖拽;
                              //添加marker
                              marker = (Marker) baidumap.addOverlay(options);
                              //使用marker携带info信息，当点击事件的时候可以通过marker获得info信息
                              Bundle bundle = new Bundle();
                              //info必须实现序列化接口
                              bundle.putSerializable("info", info);
                              marker.setExtraInfo(bundle);
                          }
                      }
                  });


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.start();

//   marks.add(new markbean(2,"美食","四川理工学院",3,
//                    "明天中午","吃火锅","2,3,4",2,
//            29.33854299126275,104.77203338333561));
/*         handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
               switch (msg.what){
                   case  1 :
                    marks1=(List<markbean>)msg.obj;
                   break;
               }
            }
        };*/



    }

    /**
     * mark点击事件和弹出窗口点击事件
     */
    private void markClick() {
        baidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
                Bundle bundle = marker.getExtraInfo();
                markbean infoUtil = (markbean) bundle.getSerializable("info");
                //将信息显示在界面上
                //头像
                ImageView iv_img = (ImageView) r_marker.findViewById(R.id.v_touxiang);
                //iv_img.setBackgroundResource(infoUtil.getImgId());
                //用户名
                TextView tv_name = (TextView) r_marker.findViewById(R.id.v_name);
                tv_name.setText(infoUtil.getAc_theme());
                //加入图标
                ImageView v_jion = (ImageView) r_marker.findViewById(R.id.v_jion);
                //iv_img.setBackgroundResource(infoUtil.getImgId());
                //主题
                TextView v_them = (TextView) r_marker.findViewById(R.id.v_them);
                v_them.setText(infoUtil.getAc_theme());
                //活动时间
                TextView v_time = (TextView) r_marker.findViewById(R.id.v_time);
                v_time.setText(infoUtil.getAc_start());
                //活动地点
                TextView v_palce = (TextView) r_marker.findViewById(R.id.v_palce);
                v_palce.setText(infoUtil.getAc_place());
                //活动内容描述
                TextView tv_description = (TextView) r_marker.findViewById(R.id.v_description);
                tv_description.setText(infoUtil.getAc_content());


                //将布局显示出来
                r_marker.setVisibility(View.VISIBLE);

                //infowindow中的布局
                TextView tv = new TextView(getActivity());
                // tv.setBackgroundResource(R.drawable.mark);
                tv.setPadding(20, 10, 20, 20);
                tv.setTextColor(android.graphics.Color.WHITE);
                // tv.setText(infoUtil.getItem());
                tv.setGravity(Gravity.CENTER);
                bitmapDescriptor = BitmapDescriptorFactory.fromView(tv);
                //infowindow位置
                LatLng latLng = new LatLng(infoUtil.getAc_latitude(),
                        infoUtil.getAc_longitude());
                //infowindow点击事件
                InfoWindow.OnInfoWindowClickListener listener = new InfoWindow.OnInfoWindowClickListener() {

                    public void onInfoWindowClick() {
                        //隐藏infowindow
                        baidumap.hideInfoWindow();
                    }
                };
                //显示infowindow
                InfoWindow infoWindow = new InfoWindow(bitmapDescriptor, latLng, -47, listener);
                baidumap.showInfoWindow(infoWindow);
                return true;
            }
        });
    }

    /**
     * 初始化自己的位置
     */

    private void initmylocation() {
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(mLatitude, mLongitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baidumap.animateMapStatus(msu);
            }
        });
        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMarks();

             //   Toast.makeText(getActivity().getApplicationContext(),marks1.size()+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mapView() {
        baidumap = mMapView.getMap();
        //实现地图初始在200m左右
        MapStatusUpdate mapsize = MapStatusUpdateFactory.zoomTo(16.0f);
        baidumap.setMapStatus(mapsize);


        //地图点击事件
        baidumap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            public boolean onMapPoiClick(MapPoi arg0) {
                return false;
            }

            @Override
            public void onMapClick(LatLng arg0) {
                r_marker.setVisibility(View.GONE);
            }
        });
    }

    private void initLocation() {
        mLocationClient = new LocationClient(getActivity().getApplicationContext());
        mLocationlistener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationlistener);
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系
        option.setScanSpan(1000);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps
        mLocationClient.setLocOption(option);

    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        baidumap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            startLocation();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        baidumap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    private void startLocation() {

        if (Build.VERSION.SDK_INT >= 23) {
            int checkPermission = ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.
                        requestPermissions(getActivity(),
                                new
                                        String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            } else
                mLocationClient.start();
        } else {
            mLocationClient.start();
        }
    }

    private class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData data = new MyLocationData.Builder()//
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();

            baidumap.setMyLocationData(data);
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();

            if (isFristIn) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                baidumap.animateMapStatus(msu);
                isFristIn = false;
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    //---------------------------------------------------------------
    //筛选
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
                   new CustomerDismissListener(){
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


        alpha = 1.0f;

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


