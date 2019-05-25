package com.example.kaixuan.worryfreetutor.discover;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.kaixuan.worryfreetutor.R;

import java.util.ArrayList;
import java.util.List;

// ① 创建Adapter
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.VH>{

    // 展示数据
    private List<String> mDatas;
    // 事件回调监听
    private StudentAdapter.OnItemClickListener onItemClickListener;

    //② 创建ViewHolder
    public static class VH extends RecyclerView.ViewHolder{
        public final TextView title;
        public VH(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
        }
    }

    public StudentAdapter(List<String> data) {
        this.mDatas = data;
    }

    public void updateData(ArrayList<String> data){
        this.mDatas = data;
        notifyDataSetChanged();
    }

    //1.定义点击回调接口
    public interface OnItemClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    //2.定义一个设置点击监听器的方法
    public void setOnItemClickListener(StudentAdapter.OnItemClickListener listener){
        this.onItemClickListener = listener;
    }

    //③ 在Adapter中实现3个方法
    @Override
    public void onBindViewHolder(final VH holder, int position) {
        holder.title.setText(mDatas.get(position));
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //item 点击事件
            }
        });*/
        //3.对RecycleView的每一个itemView设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }
                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        //LayoutInflater.from指定写法
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
        //不能是inflate(R.layout.item_1,null);
        return new VH(v);
    }
}
