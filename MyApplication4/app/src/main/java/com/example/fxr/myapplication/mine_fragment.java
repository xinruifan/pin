package com.example.fxr.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fxr.myapplication.mineInformation.Mine_information;
import com.example.fxr.myapplication.mineInformation.Mine_login;
import com.example.fxr.myapplication.mineInformation.Mine_setting;
import com.example.fxr.myapplication.mineInformation.RoundImageView;

import static com.example.fxr.myapplication.mineInformation.Mine_login.isNotLogin;


public class mine_fragment extends Fragment implements View.OnClickListener{
    private RoundImageView mine_head;
    private RelativeLayout mine_info;
    private RelativeLayout mine_release;
    private RelativeLayout mine_join;
    private RelativeLayout mine_collect;
    private RelativeLayout mine_assess;
    private RelativeLayout mine_setting;

    private TextView mine_nickname;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                                      Bundle savedInstanceState) {
        final View contactsLayout = inflater.inflate(R.layout.mine_layout, container, false);
        //初始化控件
        mine_nickname =(TextView)contactsLayout.findViewById(R.id.mine_nickname);
        mine_info= (RelativeLayout) contactsLayout.findViewById(R.id.mine_info);
        mine_release= (RelativeLayout) contactsLayout.findViewById(R.id.mine_release);
        mine_join= (RelativeLayout) contactsLayout.findViewById(R.id.mine_join);
        mine_collect= (RelativeLayout) contactsLayout.findViewById(R.id.mine_collect);
        mine_assess=(RelativeLayout)contactsLayout.findViewById(R.id.mine_assess);
        mine_head =(RoundImageView)contactsLayout.findViewById(R.id.mine_head);
        mine_setting= (RelativeLayout) contactsLayout.findViewById(R.id.mine_setting);
        //绑定监听器
        mine_release.setOnClickListener(this);
        mine_join.setOnClickListener(this);
        mine_collect.setOnClickListener(this);
        mine_assess.setOnClickListener(this);
        mine_setting.setOnClickListener(this);
        if(isNotLogin){
            mine_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(getActivity().getApplicationContext(),
                            Mine_information.class);

                    startActivityForResult(i,1);
                }
            });
        }else{
            mine_nickname.setText("登录");
            mine_head.setImageResource(R.drawable.mine_1);
            mine_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i1=new Intent();
                    i1.setClass(getActivity().getApplicationContext(), Mine_login.class);
                    startActivity(i1);

                }
            });

        }
        return contactsLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==1){
            mine_nickname.setText(data.getStringExtra("nickname").toString());
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mine_setting){
            Intent i=new Intent(getActivity().getApplicationContext(),Mine_setting.class);
            startActivity(i);
        }

    }


}
