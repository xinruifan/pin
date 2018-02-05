package com.example.fxr.myapplication;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import com.example.fxr.myapplication.index_fragment_information.InfoWindowHolder;
import com.example.fxr.myapplication.index_fragment_information.SearchActivity;
import com.example.fxr.myapplication.index_fragment_information.markbean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fxr on 2017/5/24.
 */

@RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
public class index_fragment extends Fragment{
    private MapView mMapView;
    private BaiduMap baidumap;
    private ImageView my_location;

    //定位
    private LocationClient mLocationClient;
    private MyLocationListener mLocationlistener;
    private boolean isFristIn=true;
    private double mLatitude;
    private double mLongitude;

    //覆盖物
    private List<markbean> marks;
    private MarkerOnInfoWindowClickListener markerListener;
    private InfoWindow mInfoWindow;
    private LinearLayout baidumap_infowindow;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        SDKInitializer.initialize(getActivity().getApplicationContext());
        View contactsLayout = inflater.inflate(R.layout.index_layout, container, false);
        mMapView = (MapView)contactsLayout.findViewById(R.id.mapView_1);
        my_location=(ImageView) contactsLayout.findViewById(R.id.myLocation);
        mapView();
        //初始定位
        initLocation();
        initmylocation();
        setMarkerInfo();
        addOverlay(marks);

        Toolbar toolbar = (Toolbar)contactsLayout.findViewById(R.id.index_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        final ImageView searcf=(ImageView)contactsLayout.findViewById(R.id.search);
        searcf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity().getApplicationContext(),SearchActivity.class);
                startActivity(intent);
            }
        });
        return contactsLayout;
    }

    /**
     * 自定义位置 西部博览城覆盖物
     */
    private void setMarkerInfo() {
        marks = new ArrayList<markbean>();
        marks.add(new markbean(30.4261549589,104.0890705903,
                "旅游","西部博览城","13",
                "14",3,6));
    }

    /**
     * 用于地图显示覆盖物的方法
     * @param infos2
     */
    private void addOverlay(List<markbean> infos2) {
        //清空地图
        baidumap.clear();
        //创建marker的显示图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.mark1);
        LatLng latLng = null;
        Marker marker;
        OverlayOptions options;
        for(markbean info:marks){
            //获取经纬度
            latLng = new LatLng(info.getLatitude(),info.getLongitude());
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
        //将地图显示在最后一个marker的位置
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo (14.0f);
        baidumap.setMapStatus (msu);
       // MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
       // baidumap.setMapStatus(msu);

        markerListener = new MarkerOnInfoWindowClickListener ();
        baidumap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                //从marker中获取info信息
               // Bundle bundle = marker.getExtraInfo();
                //markbean infoUtil = (markbean) bundle.getSerializable("info");
                markbean bean = (markbean) marker.getExtraInfo ().get ("info");
                createInfoWindow(baidumap_infowindow, bean);

                //将marker所在的经纬度的信息转化成屏幕上的坐标
                final LatLng ll = marker.getPosition();

                mInfoWindow = new InfoWindow (BitmapDescriptorFactory.fromView (baidumap_infowindow), ll, -47, markerListener);
                //显示InfoWindow
                baidumap.showInfoWindow(mInfoWindow);

                return true;
            }
        });
    }
    /**
     *@Description: 创建 弹出窗口
     *@param baidumap_infowindow
     *@param bean
     */
    private void createInfoWindow(LinearLayout baidumap_infowindow,markbean bean){

        InfoWindowHolder holder=null;
        if(baidumap_infowindow.getTag () == null){
            holder = new InfoWindowHolder ();

            holder.tv_entname = (TextView) baidumap_infowindow.findViewById (R.id.tv_entname);
            holder.tv_checkdept = (TextView) baidumap_infowindow.findViewById (R.id.tv_checkdept);
            holder.tv_checkuser = (TextView) baidumap_infowindow.findViewById (R.id.tv_checkuser);
            holder.tv_checktime = (TextView) baidumap_infowindow.findViewById (R.id.tv_checktime);
            baidumap_infowindow.setTag (holder);
        }

        holder = (InfoWindowHolder) baidumap_infowindow.getTag ();

        holder.tv_entname.setText ( bean.getItem());
        holder.tv_checkdept.setText ( bean.getPlace());
        holder.tv_checkuser.setText ( bean.getStarttime());
        holder.tv_checktime.setText (bean.getNumofnow());
    }
    private final class  MarkerOnInfoWindowClickListener implements InfoWindow.OnInfoWindowClickListener {


        public void onInfoWindowClick(){
            //隐藏InfoWindow
            baidumap.hideInfoWindow();
        }

    }
    private void initmylocation() {
        my_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng latLng = new LatLng(mLatitude,mLongitude);
                MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
                baidumap.animateMapStatus(msu);
            }
        });
    }

    private void mapView(){
        baidumap=mMapView.getMap();
        //实现地图初始在200m左右
        MapStatusUpdate mapsize= MapStatusUpdateFactory.zoomTo(16.0f);
        baidumap.setMapStatus(mapsize);
    }

    private void initLocation() {
        mLocationClient =new LocationClient(getActivity().getApplicationContext());
        mLocationlistener =new MyLocationListener();
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
        if(!mLocationClient.isStarted())
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

    private void startLocation(){

        if(Build.VERSION.SDK_INT>=23){
            int checkPermission = ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION);

            if (checkPermission != PackageManager.PERMISSION_GRANTED) { ActivityCompat.
                                                                        requestPermissions(getActivity(),
                                                                        new
                    String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }
            else
                mLocationClient.start();
        }else{
            mLocationClient.start();
        }
    }

    /**
     *运行了权限之后立即就可以获取到位置信息
     */
    @Override

    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {

        switch(requestCode) {
            case 1: if (grantResults[0] == PackageManager.PERMISSION_GRANTED) mLocationClient.start();
            else
                    { Log.d("TTTT","啊偶，被拒绝了，少年不哭，站起来撸");
            } break;

            default:super.onRequestPermissionsResult(requestCode,
                    permissions, grantResults);
            }
    }



    private class MyLocationListener implements BDLocationListener{
        @Override
        public void onReceiveLocation(BDLocation location) {
            MyLocationData data=new MyLocationData.Builder()//
            .accuracy(location.getRadius())//
            .latitude(location.getLatitude())//
            .longitude(location.getLongitude())//
            .build();

            baidumap.setMyLocationData(data);
            mLatitude=location.getLatitude();
            mLongitude=location.getLongitude();

            if(isFristIn) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                MapStatusUpdate msu=MapStatusUpdateFactory.newLatLng(latLng);
                baidumap.animateMapStatus(msu);
                isFristIn=false;
            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
}
