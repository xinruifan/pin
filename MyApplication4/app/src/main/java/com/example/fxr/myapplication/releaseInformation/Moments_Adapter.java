package com.example.fxr.myapplication.releaseInformation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


import com.example.fxr.myapplication.R;

import java.util.List;

/**
 * Created by slion on 2017/9/14.
 */

public class Moments_Adapter extends RecyclerView.Adapter<Moments_Adapter.ViewHolder> {


    private List<Moments> mMomentsList;


    static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView pp_mimage;

        TextView pp_mthem;

        TextView pp_mcreator;

        TextView pp_mtime;

        TextView pp_mlocation;

        TextView pp_mjion;

        TextView pp_mcontent;


        public ViewHolder(View view) {
            super(view);

            pp_mimage = (ImageView) view.findViewById (R.id.pp_mimage);
            pp_mthem = (TextView) view.findViewById (R.id.pp_mthem);
            pp_mcreator = (TextView) view.findViewById (R.id.pp_mcreator);
            pp_mtime = (TextView) view.findViewById (R.id.pp_mtime);
            pp_mlocation = (TextView) view.findViewById (R.id.pp_mlocation);
            pp_mjion = (TextView) view.findViewById (R.id.pp_mjion);
            pp_mcontent = (TextView) view.findViewById (R.id.pp_mcontent);
        }
    }


    public Moments_Adapter(List<Moments> momentsList) {
        mMomentsList = momentsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.moments_item, parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Moments moments = mMomentsList.get(position);
        holder.pp_mimage.setImageResource(moments.getPp_picure());   //璁剧疆鍥剧墖婧?        holder.pp_mthem.setText(moments.getPp_them());
        holder.pp_mcreator.setText(moments.getPp_creator());
        holder.pp_mtime.setText(moments.getPp_time());
        holder.pp_mlocation.setText(moments.getPp_location());
        holder.pp_mjion.setText(moments.getPp_join());
        holder.pp_mcontent.setText(moments.getPp_content());
    }

    @Override
    public int getItemCount() {
        return mMomentsList.size();
    }

}
