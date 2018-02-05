package com.example.fxr.myapplication.mineInformation;

/**
 * Created by &nbsp on 2017/9/27.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.fxr.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Mine_info_cityEdit extends Activity implements View.OnClickListener{
    private final int regionCode=12;
    private RelativeLayout back;
    private TextView text;
    String region="";
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_info_city_edit);
        //初始化控件
        text= (TextView) findViewById(R.id.mine_city_textView);
        back= (RelativeLayout) findViewById(R.id.mine_city_back_layout);
        //绑定监听器
        setRegion();
        text.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    private void setRegion() {//将前一个见面的region值显示在textview上
        Intent i=getIntent();
        if(i!=null){
            region+=i.getStringExtra("region");
            Log.i("regionOld=",region );
            text.setText(region);

        }
    }

    private void openDialog() {//打开省级选择会话框
        region="";
        AlertDialog.Builder provinceDialog=new AlertDialog.Builder(Mine_info_cityEdit.this);
        final String[] item =SearchJson("provinces.json","*");

        provinceDialog.setTitle("选择省级行政区");
        provinceDialog.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.i("checked province",item[which]);
                region +=item[which]+" ";
                CityDialog(item[which]);
                dialog.dismiss();
                text.setText(region);
            }
        });
        provinceDialog.show();
    }

    public void CityDialog(String province) {//打开市级行政区选择对话框
        String[] str=SearchJson("provinces.json",province);
        Log.i("str[0]=",str[0]);
        final String[] item=SearchJson("cities.json",str[0]);
        Log.i("item.length=",item.length+"");
        AlertDialog.Builder cityDialog=new AlertDialog.Builder(Mine_info_cityEdit.this);
        cityDialog.setTitle("请选择市级行政区");
        cityDialog.setSingleChoiceItems(item,0,new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                region+=item[which]+" ";
                text.setText(region);
                dialog.dismiss();
                CountyDialog(item[which]);
            }
        }).show();

    }

    private void CountyDialog(String city) {//打开县级行政区选择对话框
        String[] str=SearchJson("cities.json",city);
        Log.i("areas str[0]=",str[0]);
        final String[] item=SearchJson("areas.json",str[0]);
        Log.i("areas item.length=",item.length+"");
        AlertDialog.Builder countyDialog= new AlertDialog.Builder(Mine_info_cityEdit.this);
        countyDialog.setTitle("请选择县级行政区");
        countyDialog.setSingleChoiceItems(item, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                region+=item[which];
                text.setText(region);
                dialog.dismiss();
            }
        }).show();

    }
//    输入json文件名和key
//    key：
//    输入code返回name
//    输入name返回code
//    输入parent_code返回name
    public  String[] SearchJson(String jsonName,String key)  {
        Context context=this;
        ArrayList item=new ArrayList();
        String jsonContext = "";
        try {
            InputStream is= null;
            try {
                is = getAssets().open(jsonName);
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStreamReader inputStreamReader=new InputStreamReader(is,"UTF-8");
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String tem="";
            try {
                while((tem=bufferedReader.readLine())!=null){
                    jsonContext+=tem;
                }
                bufferedReader.close();
                inputStreamReader.close();
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray=new JSONArray(jsonContext);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                if(key.equals("*")) {
                    if(! obj.getString("name").equals(null)){
                        item.add(obj.getString("name"));
                    }
                }
                else{
                    if(obj.getString("code").equals(key)) {
                        item.add(obj.getString("name"));
                    }

                    if(obj.get("name").equals(key)) {
                        item.add(obj.getString("code"));
                    }
                    if(jsonName.equals("cities.json")||jsonName.equals("areas.json")){
                        if(obj.getString("parent_code").equals(key)) {
                            item.add(obj.getString("name"));
                        }
                    }



                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String[] str=new String[item.size()];
        for(int i=0;i<item.size();i++){
            str[i]= (String) item.get(i);
        }
        return str;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mine_city_back_layout){
            returnRegion();
        }
        if(v.getId()==R.id.mine_city_textView){
            openDialog();


        }
    }

    private void returnRegion() {
        Intent i=getIntent();
        i.putExtra("region",region);
        setResult(regionCode,i);
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK){
            returnRegion();
        }
        return super.onKeyDown(keyCode, event);

    }
}
