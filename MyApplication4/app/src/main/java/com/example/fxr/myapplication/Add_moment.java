package com.example.fxr.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class Add_moment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_moment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();


        Button button_submit = (Button) findViewById(R.id.submit);
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_moment.this, "点了也不会提交", Toast.LENGTH_SHORT).show();
            }
        });

        Button button_position = (Button) findViewById(R.id.button_local);
        button_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Add_moment.this, "点了也不会定位", Toast.LENGTH_SHORT).show();
            }
        });

        //sipner init
        // 初始化控件
        Spinner spinner0 = (Spinner) findViewById(R.id.spiner_p_count);
        Spinner spinner1 = (Spinner) findViewById(R.id.spiner_them);
        // 建立数据源
        String[] items0 = getResources().getStringArray(R.array.num_people);
        String[] items1 = getResources().getStringArray(R.array.them_context);
        // 建立Adapter并且绑定数据源
        //第二个参数是Spinner未展开菜单时Spinner的样式，R.layout.custom_spiner_text_item是系统自定义的布局。
        ArrayAdapter<String> adapter0 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items0);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items1);
        //为适配器设置下拉列表下拉时的菜单样式。
        adapter0.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //将适配器添加到下拉列表上
        spinner0.setAdapter(adapter0);
        spinner1.setAdapter(adapter1);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner0.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] num = getResources().getStringArray(R.array.num_people);
                Toast.makeText(Add_moment.this, "你点击的是:" + num[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        spinner1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] them = getResources().getStringArray(R.array.them_context);
                Toast.makeText(Add_moment.this, "你点击的是:" + them[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

    }
}
