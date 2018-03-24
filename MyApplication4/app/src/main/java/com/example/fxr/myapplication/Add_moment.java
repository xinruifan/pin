package com.example.fxr.myapplication;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextPaint;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fxr.myapplication.releaseInformation.loc_change;
import com.pin.database.data.Activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Add_moment extends AppCompatActivity {


    public static final String TAG="Add_moment";

    private TextView activity_date;
    private TextView activity_location;
    private Button activity_position;
    private EditText activity_content;
    private LinearLayout choose_date_LL;
    private Calendar tmp_date=Calendar.getInstance();
    private int year=2018;
    private int moth=1;
    private int date=1;
    private String ac_return_msg = "success";
    private Activity mActivity = new Activity();
    private Spinner spinner0;
    private Spinner spinner1;
    public static int spinner0_pos=0;
    public static int spinner1_pos=0;




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_ac_2bar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return true;
    }


    public static String dateToString(Date date) {
        String formatType="yyyy-MM-dd";
        return new SimpleDateFormat(formatType).format(date);
    }


    public void add_ac_To_DB(){
        new Thread(){
            @Override
            public void run() {
                try {

                    Socket socket = new Socket("192.168.1.111", 28888);


                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(mActivity);
                    out.flush();



                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    final String line = br.readLine();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            ac_return_msg = line;

                            if (ac_return_msg.equals("success")){
                                Toast.makeText(Add_moment.this, ac_return_msg, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(Add_moment.this, "wangl", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                    br.close();
                    out.close();
                    socket.close();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }



    public void onClickLinerLayout(View v) {
        switch (v.getId()){
            case R.id.select_date000:

                AlertDialog.Builder localBuilder = new AlertDialog.Builder(Add_moment.this);
                localBuilder.setTitle("日期").setIcon(R.mipmap.ic_launcher);
                //
                final LinearLayout layout_alert = (LinearLayout) getLayoutInflater().inflate(R.layout.date_select, null);
                localBuilder.setView(layout_alert);
                localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        DatePicker datepicker1 = (DatePicker) layout_alert.findViewById(R.id.datepicker_date);
                        year = datepicker1.getYear();
                        moth = datepicker1.getMonth()+1;
                        date = datepicker1.getDayOfMonth();
                        activity_date.setText(year + "-" + moth + "-" + date);


                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {

                    }
                }).create().show();

                break;
            case R.id.select_loc:
                Intent intent = new Intent(Add_moment.this, loc_change.class);
                startActivityForResult(intent, 1);
                break;
        }
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_add_moment);


        activity_location = (TextView) findViewById(R.id.ac_location);
        activity_content = (EditText) findViewById(R.id.ac_content);
        activity_date = (TextView) findViewById(R.id.ac_time);


        Intent intent = getIntent();
        if (intent.getStringExtra("extra_loc")!=null){
            activity_location.setText(intent.getStringExtra("extra_loc"));
        }else {
            activity_location.setText("点击输入位置！");
        }

        Toolbar mToolbarTb = (Toolbar) findViewById(R.id.add_ac_toolbar);
        setSupportActionBar(mToolbarTb);
        //tianjia toolbar fanhui anniu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //ying cahng zi dai de title
        getSupportActionBar().setDisplayShowTitleEnabled(false);





        //sipner init
        // 初始化控件
        spinner0 = (Spinner) findViewById(R.id.spiner_p_count);
        spinner1 = (Spinner) findViewById(R.id.spiner_them);
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
        spinner0.setSelection(spinner0_pos);
        spinner1.setSelection(spinner1_pos);
        //第五步：为下拉列表设置各种事件的响应，这个事响应菜单被选中
        spinner0.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                spinner0_pos=pos;
                //Toast.makeText(Add_moment.this, ""+spinner0_pos, Toast.LENGTH_SHORT).show();
                TextView tv = (TextView)view;
//                TextPaint tp = tv.getPaint();
//                tp.setFakeBoldText(true);
                tv.setTextSize(21.0f);    //设置大小
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                mActivity.setAc_number(spinner0_pos+1);
                //Toast.makeText(add_moments_Activity.this, "你点击的是:" + num[pos], Toast.LENGTH_SHORT).show();
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
                spinner1_pos=pos;
                String[] theme = getResources().getStringArray(R.array.them_context);
                //Toast.makeText(Add_moment.this, ""+spinner1_pos, Toast.LENGTH_SHORT).show();
                TextView tv = (TextView)view;
//                TextPaint tp = tv.getPaint();
//                tp.setFakeBoldText(true);
                tv.setTextSize(21.0f);    //设置大小
                tv.setGravity(android.view.Gravity.CENTER_HORIZONTAL);   //设置居中
                mActivity.setAc_theme(theme[spinner1_pos].toString());
                Toast.makeText(Add_moment.this, "你点击的是:" + theme[pos], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });





        mToolbarTb.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                int menuItemId = item.getItemId();


                if (activity_content.getText() !=null && !activity_content.getText().equals("")){
                    mActivity.setAc_content(activity_content.getText().toString());
                }else {
                    Toast.makeText(Add_moment.this, "活动内容不能为空!", Toast.LENGTH_LONG).show();
                }

                if (activity_location.getText() !=null && !activity_location.getText().equals("")){
                    mActivity.setAc_place(activity_location.getText().toString());
                }

                tmp_date.set(year, moth-1, date);
                mActivity.setAc_start(tmp_date.getTime());
                mActivity.setAc_list("0");
                mActivity.setUser_id(1);
                if (menuItemId == R.id.submit) {
                    Toast.makeText(Add_moment.this, "主题："+mActivity.getAc_theme()+" ren"+mActivity.getAc_number()+" 时间："+
                            dateToString(mActivity.getAc_start())+""+mActivity.getAc_place()
                                    +""+mActivity.getAc_content(), Toast.LENGTH_SHORT).show();

                }

                add_ac_To_DB();

                activity_content.setText("");

                Intent intent = new Intent(Add_moment.this, MainActivity.class);
                startActivity(intent);


                return true;
            }
        });

    }
}
