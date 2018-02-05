package com.example.fxr.myapplication.index_fragment_information;

import java.io.Serializable;


/**
 * 地图覆盖物标识信息
 * Created by fxr on 2018/1/23.
 */

public class markbean implements Serializable{
    private double latitude; //纬度
    private double longitude; //经度
    private String item; //活动主题
    private String place; //活动地点
    private String starttime; //活动开始时间
    private String endtime; // 活动结束时间
    private int numofnow;//活动当前人数
    private int numofpeople;// 活动总人数



    public markbean(){}
    public markbean(double latitude,double longitude,String item,String place,
                    String starttime,String endtime,int numofnow,
                    int numofpeople){
        this.latitude=latitude;
        this.longitude=longitude;
        this.item=item;
        this.place=place;
        this.starttime=starttime;
        this.endtime=endtime;
        this.numofnow=numofnow;
        this.numofpeople=numofpeople;
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public int getNumofnow() {
        return numofnow;
    }

    public void setNumofnow(int numofnow) {
        this.numofnow = numofnow;
    }

    public int getNumofpeople() {
        return numofpeople;
    }

    public void setNumofpeople(int numofpeople) {
        this.numofpeople = numofpeople;
    }
}
