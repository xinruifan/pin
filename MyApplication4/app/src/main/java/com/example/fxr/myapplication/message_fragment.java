package com.example.fxr.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.fxr.myapplication.message.addfriend;
import com.example.fxr.myapplication.message.my_group;

/**
 * Created by fxr on 2017/5/24.
 */

public class message_fragment extends Fragment implements View.OnClickListener{
private ImageView addfriends;
    private ImageView mygroup;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contactsLayout = inflater.inflate(R.layout.message_layout, container, false);
        // 初始化布局元素

       init(contactsLayout);
        return contactsLayout;
    }
public void init(View v){
    Toolbar toolbar = (Toolbar)v.findViewById(R.id.message_toolbar);
    ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    addfriends=(ImageView)v.findViewById(R.id.addFriend_image);
    mygroup=(ImageView)v.findViewById(R.id.group_image) ;
    mygroup.setOnClickListener(this);
    addfriends.setOnClickListener(this);

}
public void onClick(View v){
    switch (v.getId()){
        case R.id.addFriend_image:
            Intent intent=new Intent(getActivity().getApplicationContext(),addfriend.class);
            startActivity(intent);
break;
        case R.id.group_image:
            Intent intent1=new Intent(getActivity().getApplicationContext(), my_group.class);
            startActivity(intent1);
    }
}


}
