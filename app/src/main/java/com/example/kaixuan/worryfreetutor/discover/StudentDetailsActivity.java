package com.example.kaixuan.worryfreetutor.discover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.*;
import com.example.kaixuan.worryfreetutor.R;

import java.util.ArrayList;

public class StudentDetailsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
      //  initView();

    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        final ArrayList<String> arrayList = new ArrayList<>();
        for(int i=0;i<=30;i++){
            arrayList.add("学生"+i+"item");
        }
        MyAdapter myAdapter = new MyAdapter(arrayList);
        //设置布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //链接适配器
        recyclerView.setAdapter(myAdapter);
        //设置分隔线 可选（默认为空）
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        //设置增加或删除条目的动画 可选(默认为DefaultItemAnimator)
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }


}
