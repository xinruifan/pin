package com.example.fxr.myapplication.mineInformation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fxr.myapplication.R;

import java.io.FileNotFoundException;

/**
 * Created by &nbsp on 2017/9/5.
 */

public class Mine_information extends Activity {
    private final int regionCode=12;
    private PopupWindow popwindow;
    private RelativeLayout headEdit;
    private RelativeLayout borthEdit;
    private RelativeLayout back;
    private RelativeLayout nickLayout;
    private RelativeLayout sexLayout;
    private RelativeLayout cityLayout;
    private RoundImageView headShow;
    private TextView borthShow;
    private TextView nickShow;
    private TextView cityShow;
    private TextView sexShow;
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_information);
        //初始化控件

        headShow= (RoundImageView) findViewById(R.id.mine_info_head_show);
        headEdit=(RelativeLayout)findViewById(R.id.mine_info_headedit_layout);
        nickLayout=(RelativeLayout)findViewById(R.id.mine_nickname_edit_layout);
        sexLayout= (RelativeLayout) findViewById(R.id.mine_sex_layout);
        cityLayout= (RelativeLayout) findViewById(R.id.mine_info_regionlayout);
        borthEdit=(RelativeLayout)findViewById(R.id.mine_borthlayout);
        borthShow=(TextView)findViewById(R.id.mine_info_borthshow);
        nickShow=(TextView)findViewById(R.id.mine_nickname_show);
        sexShow= (TextView) findViewById(R.id.mine_info_sexshow);
        cityShow=(TextView)findViewById(R.id.mine_info_region_show) ;
        //绑定监听器
        headEdit.setOnClickListener(new mOnClickListner());
        nickLayout.setOnClickListener(new mOnClickListner());
        sexLayout.setOnClickListener(new mOnClickListner());
        cityLayout.setOnClickListener(new mOnClickListner());
        InitDatePicker();//初始化日期选择器
        back=(RelativeLayout) findViewById(R.id.mine_info_backLayout);
        headEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopwindow();
            }
        });
        back.setOnClickListener(new mOnClickListner());

    }

    private void InitDatePicker() {//初始化日期选择器
        borthEdit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                mOnDateSetListener m1=new mOnDateSetListener();
                DatePickerDialog dpd=new DatePickerDialog(Mine_information.this,m1,1995,1,1);
                DatePicker dp=dpd.getDatePicker();
                dp.setMinDate(0);
                dp.setMaxDate(System.currentTimeMillis());
                Long ct=System.currentTimeMillis();
                Log.i("o",String.valueOf(System.currentTimeMillis()));
                dpd.setOnDateSetListener(new mOnDateSetListener());
                dpd.show();
            }
        });

    }

    class mOnDateSetListener implements DatePickerDialog.OnDateSetListener{//时间选择器监听

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            String result=year+"-"+(month+1)+"-"+dayOfMonth;
            borthShow.setText(result);
        }
    }

    private void showPopwindow() {//初始化弹出窗口
        View contentView = LayoutInflater.from(Mine_information.this).
                inflate(R.layout.mine_info_popwindow, null);
        popwindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popwindow.setContentView(contentView);
        //设置各个控件的点击响应
        RelativeLayout camera = (RelativeLayout) contentView.findViewById(R.id.mine_info_popwindow_camera);
        RelativeLayout photo = (RelativeLayout) contentView.findViewById(R.id.mine_info_popwindow_photo);
        RelativeLayout see = (RelativeLayout) contentView.findViewById(R.id.mine_info_popwindow_see);
        RelativeLayout cancel= (RelativeLayout) contentView.findViewById(R.id.mine_info_popwindow_cancel);
        mOnClickListner m1=new mOnClickListner();
        camera.setOnClickListener(m1);
        photo.setOnClickListener(m1);
        see.setOnClickListener(m1);
        cancel.setOnClickListener(m1);
        //显示PopupWindow
        View rootview = LayoutInflater.from(Mine_information.this).
                inflate(R.layout.mine_information, null);
        popwindow.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
class mOnClickListner implements View.OnClickListener{//onclick监听器
    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.mine_info_popwindow_camera){//监听拍照
            popwindow.dismiss();
            Log.i("tag","camera");
            Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            i.putExtra(MediaStore.EXTRA_OUTPUT,new File
//                    (Environment.getExternalStorageDirectory().getAbsolutePath()+"tem.jpg"));
            startActivityForResult(i,99);
        }
        if(v.getId()==R.id.mine_info_popwindow_photo){//监听从相册选择按钮
            popwindow.dismiss();
            Intent i=new Intent(Intent.ACTION_PICK,
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i,98);
        }
        if(v.getId()==R.id.mine_info_popwindow_see){//监听查看大图
            popwindow.dismiss();
            Log.i("tag","clicked see");
            Intent i1=new Intent();
            i1.setClass(Mine_information.this,Mine_info_seehead.class);
            headShow.setDrawingCacheEnabled(true);
            Bitmap bitmap=((BitmapDrawable)(headShow.getDrawable())).getBitmap();
            headShow.setDrawingCacheEnabled(false);
//            ByteArrayOutputStream bao=new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.PNG,100,bao);
//            byte[] bitmapByte=bao.toByteArray();

            //i1.putExtra("hello","ok");
            startActivity(i1);
        }
        if(v.getId()==R.id.mine_info_popwindow_cancel){//监听取消键
            popwindow.dismiss();
        }
        if(v.getId()==R.id.mine_nickname_edit_layout){//监听昵称layout
            //Toast.makeText(Mine_information.this,"你点击了",Toast.LENGTH_LONG).show();
            showInputDialog();
        }
        if(v.getId()==R.id.mine_sex_layout){//监听sex layout
            showSelectDialog();
        }
        if(v.getId()==R.id.mine_info_regionlayout){//监听region layout
           // Toast.makeText(Mine_information.this,"ok",Toast.LENGTH_LONG).show();
            Intent i=new Intent(Mine_information.this,Mine_info_cityEdit.class);
            String regionOld=  cityShow.getText().toString();
            i.putExtra("region",regionOld);
            startActivityForResult(i,regionCode);
        }
        if(v.getId()==R.id.mine_info_backLayout){//监听返回layout
            returnData();
        }
    }
}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//监听实体返回键
        if(keyCode==KeyEvent.KEYCODE_BACK){
            returnData();
        }
        return super.onKeyDown(keyCode, event);

    }

    private void returnData() {
        Intent i=getIntent();
        i.putExtra("nickname",nickShow.getText().toString());
        setResult(1,i);
        finish();
    }

    private void showSelectDialog() {//打开选择对话框
        final String[] item={"男","女"};
        int checkedItem=0;
        if(sexShow.getText().toString().equals("男")){
            checkedItem=0;
        }else{
            checkedItem=1;
        }
        final AlertDialog.Builder select=new AlertDialog.Builder(Mine_information.this);
        select.setTitle("选择性别");
        select.setSingleChoiceItems(item, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sexShow.setText(item[which]);
                dialog.dismiss();
            }
        });
        select.show();

    }

    protected void showInputDialog() {//打开编辑对话框

        AlertDialog.Builder inputDialog=new AlertDialog.Builder(Mine_information.this);
        final EditText editText_inputDialog=new EditText(Mine_information.this);
        inputDialog.setView(editText_inputDialog);
        inputDialog.setTitle("请输入新的昵称");
        inputDialog.setPositiveButton("确认",new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialog, int which) {
                nickShow.setText(editText_inputDialog.getText());
            }
        } );
        inputDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        inputDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==99&&resultCode==RESULT_CANCELED){//取消拍照
            Toast.makeText(Mine_information.this,"取消操作",Toast.LENGTH_SHORT).show();
        }
        if (requestCode==99 && resultCode==RESULT_OK){//拍照并设为头像
            Bundle bundle=data.getExtras();
            if(bundle !=null){
                Bitmap photo=bundle.getParcelable("data");
                headShow.setImageBitmap(photo);
                //保存照片到本地
                //SavePictureTool spt=new SavePictureTool(photo);

            }
           // Toast.makeText(Mine_information.this,"成功",Toast.LENGTH_SHORT).show();
        }
        if(requestCode==98&&resultCode==RESULT_CANCELED){//取消从相册选择
            //Toast.makeText(Mine_information.this,"取消",Toast.LENGTH_SHORT).show();
        }
        if (requestCode==98&&resultCode==RESULT_OK){//将相册图片设为头像
            Uri uri=data.getData();
            ContentResolver contentResolver=this.getContentResolver();
            try {
                Bitmap bm= BitmapFactory.decodeStream(contentResolver.openInputStream(uri));
                headShow.setImageBitmap(bm);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            //Toast.makeText(Mine_information.this,"成功",Toast.LENGTH_SHORT).show();
        }
        if(requestCode==regionCode&&resultCode==regionCode){
            String region=data.getStringExtra("region");
            cityShow.setText(region);
        }

    }
}
