package com.example.fxr.myapplication.mineInformation;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.UUID;

/**
 * Created by &nbsp on 2017/9/27.
 */

public class SavePictureTool {
    private String filename;
    private String dir;
    public SavePictureTool(Bitmap src) {

         filename= UUID.randomUUID().toString();
         dir= Environment.getExternalStorageDirectory().getAbsolutePath()+"/com.fivecoder.pinpin/";
        File file=new File(dir);
        if(! file.exists()){
            file.mkdir();
            Log.i("file","dir1 exists:"+file.exists()+"  dir="+dir);

        }
        dir+="/picture/";
        file=new File(dir);
        if(! file.exists()){
            file.mkdir();
            Log.i("file","dir2 exists:"+file.exists()+"  dir="+dir);

        }
        file=new File(dir+filename+".png");
        if(!file.exists()){
            try {
                FileOutputStream out=new FileOutputStream(file);
                Bitmap bm=Bitmap.createBitmap(src);
                bm.compress(Bitmap.CompressFormat.PNG,100,out);
                bm.recycle();
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

    }
    public String getFilePath(){
        return dir+filename+".png";

    }
}
