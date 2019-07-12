package com.example.kaixuan.worryfreetutor.discover;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.example.kaixuan.worryfreetutor.R;

import java.util.ArrayList;

public class StudentDetailsActivity extends AppCompatActivity {
    //private RecyclerView recyclerView;

    private Toolbar toolbar;

    @Override
    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("可动态设置标题");

        // 设置navigation button 点击事件
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //Menu
        toolbar.inflateMenu(R.menu.menu_student_details);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.student_details_complaint:
                        Toast.makeText(StudentDetailsActivity.this,"投诉",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.student_details_favorite:
                        Toast.makeText(StudentDetailsActivity.this,"收藏",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.student_details_share:
                        Toast.makeText(StudentDetailsActivity.this,"分享",Toast.LENGTH_SHORT).show();

                        break;

                    default:
                        break;

                }
                return false;
            }
        });

        //initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_student_details,menu);
        return true;
    }
   private void initView() {
//        recyclerView = findViewById(R.id.recyclerView);
//        final ArrayList<String> arrayList = new ArrayList<>();
//        for(int i=0;i<=30;i++){
//            arrayList.add("学生"+i+"item");
//        }
//        MyAdapter myAdapter = new MyAdapter(arrayList);
//        //设置布局管理器
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        recyclerView.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(OrientationHelper.VERTICAL);
//        //链接适配器
//        recyclerView.setAdapter(myAdapter);
//        //设置分隔线 可选（默认为空）
//        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
//        //设置增加或删除条目的动画 可选(默认为DefaultItemAnimator)
//        recyclerView.setItemAnimator(new DefaultItemAnimator());

   }


}
