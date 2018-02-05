package com.example.fxr.myapplication.mineInformation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fxr.myapplication.R;

import java.io.File;

/**
 * Created by &nbsp on 2017/9/19.
 */

public class Mine_info_seehead extends Activity{
    private TextView title;
    private RelativeLayout back;
    private ImageView mine_info_bighead;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_info_seehead);
        //初始化控件
        title=(TextView) findViewById(R.id.mine_info_bighead_title );
        back=(RelativeLayout) findViewById(R.id.mine_info_bighead_layout);
        mine_info_bighead=(ImageView)findViewById(R.id.mine_info_bighead);
        //绑定监听器
        setIntentPicture();
        mine_info_bighead.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu pop=new PopupMenu(Mine_info_seehead.this,title);
                pop.getMenuInflater().inflate(R.menu.save_picture,pop.getMenu());
                pop.show();
                pop.setOnMenuItemClickListener(new mMenuItemClickListener());

                return false;

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setIntentPicture() {
        Intent i=getIntent();
        if(i!=null) {
            //byte[] bitmapByte=i.getByteArrayExtra("bitmap");
            //Bitmap bitmap= BitmapFactory.decodeByteArray(bitmapByte,0,bitmapByte.length);
            //mine_info_bighead.setImageBitmap(bitmap);
            //Toast.makeText(this,"hello",Toast.LENGTH_SHORT).show();
        }
    }

    class mMenuItemClickListener implements PopupMenu.OnMenuItemClickListener{

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(item.getItemId()==R.id.save_picture) {
                mine_info_bighead.setDrawingCacheEnabled(true);
                SavePictureTool spt=new SavePictureTool(mine_info_bighead.getDrawingCache());
                Toast.makeText(Mine_info_seehead.this,"成功保存到"+spt.getFilePath(),
                        Toast.LENGTH_LONG).show();
                mine_info_bighead.setDrawingCacheEnabled(false);
                Uri uri=Uri.fromFile(new File(spt.getFilePath()));
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,uri));
            }
            return false;
        }
    }
}
