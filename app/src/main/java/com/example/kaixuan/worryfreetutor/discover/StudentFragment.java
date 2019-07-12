package com.example.kaixuan.worryfreetutor.discover;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;
import com.example.kaixuan.worryfreetutor.base.BaseFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class StudentFragment extends BaseFragment {
    private View mView = null;
    /**
     * 标志，记录View初始化是否完成。
     */
    private boolean isPrepared;
    /**
     * 避免多次请求数据
     */
    private boolean mHasLoadedOnce;

    private RecyclerView recyclerView;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(mView == null){
            mView = inflater.inflate(R.layout.fragment_student,container,false);
            initView();
            init();
            isPrepared = true;
            lazyLoad();
        }
        /**
         * 判断mView是否拥有parent，如果有，得删除，否则会有mView已有parent的错误

         ViewGroup parent = (ViewGroup)mView.getParent();
         if(parent != null){
         parent.removeView(mView);
         }
         */

        return mView;
    }



    /**
     * 初始化控件
     */
    private void initView() {
        swipeRefreshLayout = mView.findViewById(R.id.swipeRefreshLayout);
        recyclerView = (RecyclerView)mView.findViewById(R.id.recyclerView);
        floatingActionButton = (FloatingActionButton) mView.findViewById(R.id.student_FButton);
    }

    private ArrayList<String> arrayList = new ArrayList<>();
    private void getData(){
        for(int i=0;i<=30;i++){
            arrayList.add("学生"+i+"item");
        }
    }
    private void init() {
        getData();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),StudentPostActivity.class);
                startActivity(intent);
            }
        });

        final MyAdapter myAdapter = new MyAdapter(arrayList);
        myAdapter.setOnItemSelectedListener(new MyAdapter.OnItemSelectedListener() {
            @Override
            public void OnItemClick(View view, int position) {
                Toast.makeText(mView.getContext(),"click " + position + " item", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),StudentDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public void OnItemLongClick(View view, int position) {
                Toast.makeText(mView.getContext(),"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView.addOnScrollListener(new LoadMoreRecycleListener() {
            @Override
            public void onLoadMore() {
                myAdapter.setLoadState(myAdapter.LOADING);
                if (arrayList.size() < 90){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            getData();
                            myAdapter.setLoadState(myAdapter.LOADING_COMPLETED);
                        }
                    },1000);
                }else {
                    myAdapter.setLoadState(myAdapter.LOADING_END);
                }

            }
        });

        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //链接适配器
        recyclerView.setAdapter(myAdapter);
        //设置分隔线 可选（默认为空）
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL));
        //设置增加或删除条目的动画 可选(默认为DefaultItemAnimator)
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        swipeRefreshLayout.setEnabled(true);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(mView.getContext(),"成功刷新", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void lazyLoad() {
        if(!isPrepared || !isVisible() || mHasLoadedOnce){
            return ;
        }
        /**
         * 在这里填充数据请求的操作
         */
        mHasLoadedOnce = true;
    }
}
