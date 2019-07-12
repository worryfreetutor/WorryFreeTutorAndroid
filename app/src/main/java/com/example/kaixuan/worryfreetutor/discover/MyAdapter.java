package com.example.kaixuan.worryfreetutor.discover;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.kaixuan.worryfreetutor.R;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<String> mData;

    private final int TYPE_ITEM = 1; //普通布局
    private final int TYPE_FOOTER = 2;  //脚布局
    private int loadState = 2; //当前加载状态，默认为加载完成
    public final int LOADING = 1;//正在加载
    public final int LOADING_COMPLETED = 2;//加载完成
    public final int LOADING_END =3; //加载到底

    public MyAdapter(ArrayList<String> data) {
        this.mData = data;
    }

//    public void updateAllData(ArrayList<String> data) {
//        this.mData = data;
//        notifyDataSetChanged();
//    }

    @Override
    public int getItemViewType(int position){
        if(position + 1 == getItemCount()){
            return TYPE_FOOTER;
        }else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //进行判断显示类型
        if(viewType == TYPE_ITEM){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_student, parent, false);
            return new CommonViewHolder(view);
        }else if(viewType == TYPE_FOOTER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_loadmore,parent,false);
            return new BottomViewHolder(view);
        }
        
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof CommonViewHolder){

            final CommonViewHolder commonViewHolder = (CommonViewHolder) holder;
            commonViewHolder.title.setText(mData.get(position));

            commonViewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = commonViewHolder.getLayoutPosition();
                    onItemSelectedListener.OnItemClick(commonViewHolder.itemView,pos);
                }
            });
            commonViewHolder.constraintLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(onItemSelectedListener !=null){
                        int pos = commonViewHolder.getLayoutPosition();
                        onItemSelectedListener.OnItemLongClick(commonViewHolder.itemView,pos);
                    }
                    //表示此事件已经消费，不会触发单击事件
                    return true;
                }

            });


        }else if (holder instanceof BottomViewHolder){
            BottomViewHolder bottomViewHolder = (BottomViewHolder) holder;
            switch (loadState){
                case LOADING:
                    bottomViewHolder.progressBar.setVisibility(View.VISIBLE);
                    bottomViewHolder.textView.setVisibility(View.VISIBLE);
                    bottomViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case LOADING_COMPLETED:
                    bottomViewHolder.progressBar.setVisibility(View.INVISIBLE);
                    bottomViewHolder.textView.setVisibility(View.INVISIBLE);
                    bottomViewHolder.linearLayout.setVisibility(View.GONE);
                    break;
                case LOADING_END:
                    bottomViewHolder.progressBar.setVisibility(View.GONE);
                    bottomViewHolder.textView.setVisibility(View.GONE);
                    bottomViewHolder.linearLayout.setVisibility(View.VISIBLE);
                    break;
                    default:break;
            }
        }
    }

    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    /**
    * 增添和减少Item
    * */
//    public void addNewItem() {
//        if(mData == null) {
//            mData = new ArrayList<>();
//        }
//        mData.add(0, "new Item");
//        notifyItemInserted(0);
//    }
//
//    public void deleteItem() {
//        if(mData == null || mData.isEmpty()) {
//            return;
//        }
//        mData.remove(0);
//        notifyItemRemoved(0);
//    }
    /**
     * public void onClick(View v) {
     *     int id = v.getId();
     *     if(id == R.id.rv_add_item_btn) {
     *         mAdapter.addNewItem();
     *         // 由于Adapter内部是直接在首个Item位置做增加操作，增加完毕后列表移动到首个Item位置
     *         mLayoutManager.scrollToPosition(0);
     *     } else if(id == R.id.rv_del_item_btn){
     *         mAdapter.deleteItem();
     *         // 由于Adapter内部是直接在首个Item位置做删除操作，删除完毕后列表移动到首个Item位置
     *         mLayoutManager.scrollToPosition(0);
     *     }
     * }
     */


    /**
     * 设置监听
     * */
    private MyAdapter.OnItemSelectedListener onItemSelectedListener;

    public interface OnItemSelectedListener {
        void OnItemClick (View view,int position);
        void OnItemLongClick (View view,int position);
    }

    public void setOnItemSelectedListener(MyAdapter.OnItemSelectedListener listener){
        this.onItemSelectedListener =listener;
    }


    private  class CommonViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView price;
        TextView address;
        TextView hot;
        TextView subject;
        ConstraintLayout constraintLayout;

        CommonViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.item_title);
            price = (TextView) itemView.findViewById(R.id.item_price);
            address = (TextView) itemView.findViewById(R.id.item_address);
            hot = (TextView) itemView.findViewById(R.id.item_hot);
            subject = (TextView) itemView.findViewById(R.id.subject);
            constraintLayout = (ConstraintLayout) itemView.findViewById(R.id.whole_item);
        }

    }

    private class BottomViewHolder extends RecyclerView.ViewHolder{
        
        ProgressBar progressBar;
        TextView textView;
        LinearLayout linearLayout;

        BottomViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
            textView = (TextView) itemView.findViewById(R.id.textView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);
        }
        
    }
}
