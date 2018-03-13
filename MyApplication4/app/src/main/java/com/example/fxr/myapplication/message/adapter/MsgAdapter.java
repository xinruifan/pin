package com.example.fxr.myapplication.message.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.fxr.myapplication.R;
import com.example.fxr.myapplication.message.Msg;

import java.util.List;

/**
 * Created by hxm on 2017/11/5.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.ViewHolder>{
    List<Msg> msgList;
    static class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView leftMsg;
        TextView rightMsg;
        ViewHolder(View view)
        {
            super(view);
            leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
            leftMsg = (TextView) view.findViewById(R.id.left_msg);
            rightMsg = (TextView) view.findViewById(R.id.right_msg);
        }


    }
    public MsgAdapter(List<Msg> msg){
        this.msgList=msg;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Msg msg=msgList.get(position);
        /**
         * 如果收到消息 左布局设置显示，右布局设置隐藏；
         */
        if(msg.getType()==msg.TYPE_RECEIVE)
        {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.leftMsg.setText(msg.getContent());
        }
        if(msg.getType()==msg.TYPE_SEND){
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.rightMsg.setText(msg.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_item,parent,false);
        ViewHolder v=new ViewHolder(view);
        return v;
    }

}
