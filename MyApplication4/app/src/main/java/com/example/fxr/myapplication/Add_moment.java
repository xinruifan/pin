package com.example.fxr.myapplication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fxr.myapplication.R;

public class Add_moment extends AppCompatActivity {


    private TextView date;
    private TextView select_date;
    private LinearLayout ll;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar1, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_add_moment);

        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbarTb);
        //
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);



        ll = (LinearLayout) findViewById(R.id.select_date000);
        select_date = (TextView) findViewById(R.id.select_date);
        date = (TextView) findViewById(R.id.time0);
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder localBuilder = new AlertDialog.Builder(Add_moment.this);

                localBuilder.setTitle("日期").setIcon(R.mipmap.ic_launcher);
                //
                final LinearLayout layout_alert = (LinearLayout) getLayoutInflater().inflate(R.layout.date_select, null);
                localBuilder.setView(layout_alert);
                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        DatePicker datepicker1 = (DatePicker) layout_alert.findViewById(R.id.datepicker1);
                        int y = datepicker1.getYear();
                        int m = datepicker1.getMonth() + 1;
                        int d = datepicker1.getDayOfMonth();
                        System.out.println("y:" + y + " m:" + m + " d:" + d);
                        date.setText(y + "-" + m + "-" + d); //


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                    }
                }).create().show();
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
