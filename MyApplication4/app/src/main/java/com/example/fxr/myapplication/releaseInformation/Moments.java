package com.example.fxr.myapplication.releaseInformation;

import android.widget.ImageView;
import android.widget.TextView;

import java.security.PrivateKey;


public class Moments {

    private int pp_picure;
    private String pp_them;
    private String pp_creator;
    private String pp_time;
    private String pp_location;
    private String pp_join;

    public Moments() {
    }

    public Moments(int pp_picure, String pp_them, String pp_creator,
                   String pp_time, String pp_location, String pp_join) {
        this.pp_picure = pp_picure;
        this.pp_them = pp_them;
        this.pp_creator = pp_creator;
        this.pp_time = pp_time;
        this.pp_location = pp_location;
        this.pp_join = pp_join;
    }

    public int getPp_picure() {
        return pp_picure;
    }

    public void setPp_picure(int pp_picure) {
        this.pp_picure = pp_picure;
    }

    public String getPp_them() {
        return pp_them;
    }

    public void setPp_them(String pp_them) {
        this.pp_them = pp_them;
    }

    public String getPp_creator() {
        return pp_creator;
    }

    public void setPp_creator(String pp_activity_creator) {
        this.pp_creator = pp_activity_creator;
    }

    public String getPp_time() {
        return pp_time;
    }

    public void setPp_time(String pp_time) {
        this.pp_time = pp_time;
    }

    public String getPp_location() {
        return pp_location;
    }

    public void setPp_location(String pp_location) {
        this.pp_location = pp_location;
    }

    public String getPp_join() {
        return pp_join;
    }

    public void setPp_join(String pp_join) {
        this.pp_join = pp_join;
    }
}
