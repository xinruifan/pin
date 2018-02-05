package com.example.fxr.myapplication.releaseInformation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.fxr.myapplication.R;

import java.util.List;

/**
 * Created by slion on 2017/9/14.
 */

public class Moments_Adapter extends ArrayAdapter<Moments> {


    private int resourceId;
    public Moments_Adapter(Context context, int textViewResourceId,
                           List<Moments> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Moments
                moments = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.pp_mimage = (ImageView) view.findViewById (R.id.pp_mimage);
            viewHolder.pp_mthem = (TextView) view.findViewById (R.id.pp_mthem);
            viewHolder.pp_mcreator = (TextView) view.findViewById (R.id.pp_mcreator);
            viewHolder.pp_mtime = (TextView) view.findViewById (R.id.pp_mtime);
            viewHolder.pp_location = (TextView) view.findViewById (R.id.pp_location);
            viewHolder.pp_jion = (TextView) view.findViewById (R.id.pp_jion);
            view.setTag(viewHolder); // 将ViewHolder存储在View中
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag(); // 重新获取ViewHolder
        }
        viewHolder.pp_mimage.setImageResource(moments.getPp_picure());   //设置图片源
        viewHolder.pp_mthem.setText(moments.getPp_them());
        viewHolder.pp_mcreator.setText(moments.getPp_creator());
        viewHolder.pp_mtime.setText(moments.getPp_time());
        viewHolder.pp_location.setText(moments.getPp_location());
        viewHolder.pp_jion.setText(moments.getPp_join());
        return view;
    }

    class ViewHolder {

        ImageView pp_mimage;

        TextView pp_mthem;

        TextView pp_mcreator;

        TextView pp_mtime;

        TextView pp_location;

        TextView pp_jion;

    }


}
