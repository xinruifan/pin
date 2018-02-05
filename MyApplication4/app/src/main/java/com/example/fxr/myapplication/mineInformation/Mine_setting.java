package com.example.fxr.myapplication.mineInformation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.fxr.myapplication.R;


public class Mine_setting extends Activity implements View.OnClickListener{
    private RelativeLayout back;
    private RelativeLayout logout;
    private RelativeLayout clear;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_setting);
        //初始化控件
        InitViews();
        SetListener();
    }

    private void SetListener() {//注册监听器
        back.setOnClickListener(this);
        logout.setOnClickListener(this);
        clear.setOnClickListener(this);
    }

    private void InitViews() {//初始化控件
        back= (RelativeLayout) findViewById(R.id.mine_setting_back);
        logout= (RelativeLayout) findViewById(R.id.mine_setting_logout);
        clear= (RelativeLayout) findViewById(R.id.mine_setting_clear);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mine_setting_back){
            finish();
        }
        if (v.getId()==R.id.mine_setting_logout){
            Logout();
        }
        if (v.getId()==R.id.mine_setting_clear){
            Toast.makeText(this,"清理成功",Toast.LENGTH_SHORT).show();
        }

    }

    private void Logout() {//注销
        AlertDialog.Builder LogoutDialog=new AlertDialog.Builder(Mine_setting.this);
        LogoutDialog.setTitle("确定要注销吗？");
        LogoutDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Mine_login.isNotLogin=false;
                finish();
            }
        });
        LogoutDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        LogoutDialog.show();
    }


}
