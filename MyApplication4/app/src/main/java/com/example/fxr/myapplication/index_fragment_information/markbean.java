package com.example.fxr.myapplication.index_fragment_information;

import java.io.Serializable;

/**
 * 地图覆盖物标识信息
 * Created by fxr on 2018/1/23.
 */

public class markbean implements Serializable{
    private Integer ac_id; // 活动id
    private String ac_theme; // 活动主题
    private String ac_place; // 活动地点
    private Integer ac_number; // 活动人数
    private String ac_start; // 活动开始时间
    private String ac_content; // 活动内容
    private Integer user_id; // 活动发布者用户id
    private String ac_list;//参与活动用户列表
    private double ac_latitude; //经度
    private double ac_longitude;//纬度


    public markbean() {
    }
    public markbean(int ac_id,
                    String ac_theme,
                    String ac_place,
                    Integer ac_number,
                    String ac_start,
                    String ac_content,
                    String ac_list,
                    Integer user_id,
                    double ac_latitude,
                    double ac_longitude) {
        this.ac_id=ac_id;
        this.ac_theme = ac_theme;
        this.ac_place = ac_place;
        this.ac_number = ac_number;
        this.ac_start = ac_start;
        this.ac_content = ac_content;
        this.ac_list=ac_list;
        this.user_id = user_id;
        this.ac_longitude=ac_longitude;
        this.ac_latitude=ac_latitude;
    }



    public Integer getAc_id() {
        return ac_id;
    }

    public void setAc_id(Integer ac_id) {
        this.ac_id = ac_id;
    }

    public String getAc_theme() {
        return ac_theme;
    }

    public void setAc_theme(String ac_theme) {
        this.ac_theme = ac_theme;
    }

    public String getAc_place() {
        return ac_place;
    }

    public void setAc_place(String ac_place) {
        this.ac_place = ac_place;
    }

    public Integer getAc_number() {
        return ac_number;
    }

    public void setAc_number(Integer ac_number) {
        this.ac_number = ac_number;
    }

    public String getAc_start() {
        return ac_start;
    }

    public void setAc_start(String ac_start) {
        this.ac_start = ac_start;
    }


    public String getAc_content() {
        return ac_content;
    }

    public void setAc_content(String ac_content) {
        this.ac_content = ac_content;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getAc_list() {
        return ac_list;
    }

    public void setAc_list(String ac_list) {
        this.ac_list = ac_list;
    }

    public double getAc_latitude() {
        return ac_latitude;
    }

    public void setAc_latitude(double ac_latitude) {
        this.ac_latitude = ac_latitude;
    }

    public double getAc_longitude() {
        return ac_longitude;
    }

    public void setAc_longitude(double ac_longitude) {
        this.ac_longitude = ac_longitude;
    }



}
